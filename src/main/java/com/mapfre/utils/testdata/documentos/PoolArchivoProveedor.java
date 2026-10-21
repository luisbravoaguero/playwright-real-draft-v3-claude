package com.mapfre.utils.testdata.documentos;

import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Entrega numeros de documento desde un archivo pool (un valor por linea, extraido previamente con el query de BD).
 *
 * <p>Sin coordinacion entre pods: el indice es deterministico.
 * <ul>
 *   <li>Jenkins (existe BUILD_NUMBER): usa la zona [0, N - reservaLocal) con indice = (runId * stride + slot) mod zona.
 *       runId = BUILD_NUMBER - runIdBase, igual en todos los pods de un build y creciente entre builds.
 *       runIdBase se declara en el propio archivo ("#runIdBase=1234", el BUILD_NUMBER al generar el pool), asi
 *       al regenerar el archivo el conteo vuelve a 0 y el warning de vuelta es exacto.</li>
 *   <li>Local: usa la zona final [N - reservaLocal, N), recorrida desde el ultimo valor hacia atras, con un
 *       contador persistido en config/local para que cada ejecucion local avance. Asi no gasta valores de Jenkins.</li>
 * </ul>
 * Mismo runId + slots distintos = valores distintos. Los valores duplicados del archivo se descartan al cargar.
 * Cuando una zona da la vuelta se registra un warning: hay que regenerar el pool con el query
 * (que excluye documentos ya usados).
 */
public final class PoolArchivoProveedor implements ProveedorDocumentos {
    private static final Logger log = LoggerFactory.getLogger(PoolArchivoProveedor.class);

    static final String KEY_POOL_PATH = "testdata.documentos.pool.path";
    static final String KEY_STRIDE = "testdata.documentos.stride";
    static final String KEY_RESERVA_LOCAL = "testdata.documentos.reservaLocal";
    static final String KEY_RUN_ID = "data.runId";
    static final String KEY_BUILD_NUMBER = "build.number"; // env BUILD_NUMBER (Jenkins)
    private static final Path CONTADOR_LOCAL = Path.of("config", "local", "documentos_pool.contador");
    private static final Pattern CABECERA_RUN_ID_BASE = Pattern.compile("^#\\s*runIdBase\\s*=(.*)$", Pattern.CASE_INSENSITIVE);

    /** Contenido del archivo pool: valores en orden y el BUILD_NUMBER de referencia (0 si no se declara). */
    record ContenidoPool(List<String> valores, long runIdBase) {}

    private final List<String> valores;
    private final int stride;
    private final int reservaLocal;
    private final long runId;
    private final boolean jenkins;

    PoolArchivoProveedor(List<String> valores, int stride, int reservaLocal, long runId, boolean jenkins) {
        validarSlots(stride);
        // Un valor repetido en el archivo podria entregarse a dos escenarios en paralelo: se conserva solo la primera aparicion
        List<String> unicos = List.copyOf(new LinkedHashSet<>(valores));
        if (unicos.size() < valores.size()) {
            log.warn("El pool de documentos tenia {} valores duplicados; se descartaron. Usar SELECT DISTINCT en el query de extraccion.",
                    valores.size() - unicos.size());
        }
        valores = unicos;
        if (valores.isEmpty()) {
            throw new FrameworkException("El pool de documentos esta vacio. Regenerarlo con el query de extraccion.");
        }
        if (reservaLocal < stride || valores.size() - reservaLocal < stride) {
            throw new FrameworkException("Pool de documentos muy pequeño: " + valores.size() + " valores, reservaLocal="
                    + reservaLocal + ", stride=" + stride + ". Se necesitan al menos reservaLocal + stride valores"
                    + " y reservaLocal >= stride.");
        }
        if (runId < 0) {
            throw new FrameworkException("runId invalido para el pool de documentos: " + runId);
        }
        this.valores = valores;
        this.stride = stride;
        this.reservaLocal = reservaLocal;
        this.runId = runId;
        this.jenkins = jenkins;
    }

    /** Construye el proveedor con la configuracion (ConfigManager) y el archivo pool. */
    public static PoolArchivoProveedor desdeConfiguracion() {
        String ruta = ConfigManager.getGlobal(KEY_POOL_PATH);
        if (ruta == null) {
            throw new FrameworkException("No esta configurado el pool de documentos (" + KEY_POOL_PATH + "). Definirlo con -D"
                    + KEY_POOL_PATH + "=..., la variable de entorno TESTDATA_DOCUMENTOS_POOL_PATH o en config/local/global.properties.");
        }
        Path archivo = Path.of(ruta.trim());
        ContenidoPool contenido = parsear(leerLineas(archivo), archivo.toString());
        int stride = enteroConfig(KEY_STRIDE, 20);
        int reservaLocal = enteroConfig(KEY_RESERVA_LOCAL, 1000);

        String buildNumber = ConfigManager.getGlobal(KEY_BUILD_NUMBER);
        boolean jenkins = buildNumber != null;
        String runIdOverride = ConfigManager.getGlobal(KEY_RUN_ID);
        long runId;
        if (runIdOverride != null) {
            // Override explicito: se usa tal cual, sin restar runIdBase
            runId = parseLong(KEY_RUN_ID, runIdOverride);
        } else if (jenkins) {
            runId = runIdJenkins(parseLong("BUILD_NUMBER", buildNumber), contenido.runIdBase());
            if (contenido.runIdBase() == 0) {
                log.warn("El pool de documentos no declara '#runIdBase=<BUILD_NUMBER>' en su primera linea; se cuenta desde el build 0"
                        + " y el aviso de vuelta del pool no sera exacto.");
            }
        } else {
            runId = siguienteContadorLocal();
        }
        log.info("Pool de documentos: {} valores leidos (antes de deduplicar), stride={}, reservaLocal={}, runIdBase={}, runId={}, zona={}",
                contenido.valores().size(), stride, reservaLocal, contenido.runIdBase(), runId, jenkins ? "jenkins" : "local");
        return new PoolArchivoProveedor(contenido.valores(), stride, reservaLocal, runId, jenkins);
    }

    /** runId de Jenkins relativo al build en que se genero el pool. */
    static long runIdJenkins(long buildNumber, long runIdBase) {
        if (buildNumber < runIdBase) {
            throw new FrameworkException("BUILD_NUMBER " + buildNumber + " es menor que el runIdBase " + runIdBase
                    + " declarado en el pool de documentos. Corregir la linea '#runIdBase=' del archivo.");
        }
        return buildNumber - runIdBase;
    }

    /**
     * Interpreta las lineas del archivo: valores (sin espacios, sin lineas vacias) y la cabecera opcional
     * "#runIdBase=N". Las demas lineas que empiezan con # son comentarios.
     */
    static ContenidoPool parsear(List<String> lineas, String origen) {
        List<String> valores = new ArrayList<>();
        Long runIdBase = null;
        for (String linea : lineas) {
            // Se quita el BOM de UTF-8 (﻿) que agregan algunos editores de Windows: strip() no lo considera espacio
            String l = linea.replace("﻿", "").strip();
            if (l.isEmpty()) continue;
            Matcher m = CABECERA_RUN_ID_BASE.matcher(l);
            if (m.matches()) {
                if (runIdBase != null) {
                    throw new FrameworkException("El pool de documentos declara '#runIdBase' mas de una vez: " + origen);
                }
                runIdBase = parseLong("#runIdBase de " + origen, m.group(1));
                if (runIdBase < 0) {
                    throw new FrameworkException("#runIdBase no puede ser negativo en " + origen + ": " + runIdBase);
                }
            } else if (!l.startsWith("#")) {
                valores.add(l);
            }
        }
        return new ContenidoPool(valores, runIdBase == null ? 0L : runIdBase);
    }

    @Override
    public DocumentoAsignado asignar(ConsumidorDocumento consumidor) {
        int indice = indice(consumidor.slot());
        DocumentoAsignado asignado = new DocumentoAsignado(valores.get(indice), consumidor, indice, runId);
        log.info("Asignado {}", asignado.descripcion());
        return asignado;
    }

    int indice(int slot) {
        int zonaJenkins = valores.size() - reservaLocal;
        int zona = jenkins ? zonaJenkins : reservaLocal;
        long posicion = runId * stride + slot;
        if (posicion >= zona) {
            log.warn("El pool de documentos ya dio la vuelta en la zona {} (runId={}, capacidad={} ejecuciones). "
                    + "Regenerar el pool con el query de extraccion para no reutilizar documentos consumidos.",
                    jenkins ? "jenkins" : "local", runId, zona / stride);
        }
        int offset = (int) Math.floorMod(posicion, (long) zona);
        return jenkins ? offset : valores.size() - 1 - offset;
    }

    private static void validarSlots(int stride) {
        if (stride <= 0) {
            throw new FrameworkException(KEY_STRIDE + " debe ser mayor que 0: " + stride);
        }
        Set<Integer> usados = new HashSet<>();
        for (ConsumidorDocumento c : ConsumidorDocumento.values()) {
            if (c.slot() < 0 || c.slot() >= stride) {
                throw new FrameworkException("Slot " + c.slot() + " de " + c + " fuera de rango [0, " + stride + ")."
                        + " Aumentar " + KEY_STRIDE + " solo junto con una regeneracion del pool.");
            }
            if (!usados.add(c.slot())) {
                throw new FrameworkException("Slot duplicado en ConsumidorDocumento: " + c.slot() + " (" + c + ")");
            }
        }
    }

    private static List<String> leerLineas(Path archivo) {
        if (!Files.isRegularFile(archivo)) {
            throw new FrameworkException("No existe el archivo pool de documentos: " + archivo.toAbsolutePath());
        }
        try {
            return Files.readAllLines(archivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FrameworkException("No se pudo leer el pool de documentos: " + archivo.toAbsolutePath(), e);
        }
    }

    /** Contador local persistente: cada JVM local toma el siguiente runId para no repetir valores entre corridas. */
    private static synchronized long siguienteContadorLocal() {
        try {
            long actual = Files.isRegularFile(CONTADOR_LOCAL)
                    ? parseLong(CONTADOR_LOCAL.toString(), Files.readString(CONTADOR_LOCAL, StandardCharsets.UTF_8).trim())
                    : 0L;
            Files.createDirectories(CONTADOR_LOCAL.getParent());
            Files.writeString(CONTADOR_LOCAL, Long.toString(actual + 1), StandardCharsets.UTF_8);
            return actual;
        } catch (IOException e) {
            throw new FrameworkException("No se pudo actualizar el contador local del pool: " + CONTADOR_LOCAL.toAbsolutePath(), e);
        }
    }

    private static int enteroConfig(String key, int porDefecto) {
        String v = ConfigManager.getGlobal(key);
        if (v == null) return porDefecto;
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            throw new FrameworkException("Valor invalido para " + key + ": " + v, e);
        }
    }

    private static long parseLong(String origen, String v) {
        try {
            return Long.parseLong(v.trim());
        } catch (NumberFormatException e) {
            throw new FrameworkException("Valor numerico invalido en " + origen + ": " + v, e);
        }
    }
}
