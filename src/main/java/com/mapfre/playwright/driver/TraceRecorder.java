package com.mapfre.playwright.driver;

import com.mapfre.config.ConfigManager;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Trace de Playwright por escenario (red, snapshots del DOM y capturas), para abrir en https://trace.playwright.dev.
 * {@code trace.mode}: off | on | retain-on-failure (por defecto: solo se guarda si el escenario falla).
 * Nunca lanza excepcion: un fallo del trace no debe ocultar el resultado real del test.
 */
public final class TraceRecorder {
    private static final Logger log = LoggerFactory.getLogger(TraceRecorder.class);
    private static final DateTimeFormatter SELLO = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private TraceRecorder() {}

    public static void iniciar(BrowserContext ctx) {
        if ("off".equals(modo())) return;
        try {
            ctx.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
        } catch (Exception e) {
            log.warn("No se pudo iniciar el trace de Playwright: {}", e.toString());
        }
    }

    /** Detiene el trace del contexto del hilo; devuelve la ruta del zip si se guardo, o null. */
    public static Path detener(boolean fallo, String escenario) {
        String modo = modo();
        BrowserContext ctx = DriverManager.context();
        if ("off".equals(modo) || ctx == null) return null;

        boolean guardar = "on".equals(modo) || fallo;
        try {
            if (!guardar) {
                ctx.tracing().stop();
                return null;
            }
            Path destino = Path.of(directorio(), nombreArchivo(escenario));
            ctx.tracing().stop(new Tracing.StopOptions().setPath(destino));
            log.info("Trace de Playwright guardado: {}", destino.toAbsolutePath());
            return destino;
        } catch (Exception e) {
            log.warn("No se pudo detener/guardar el trace de Playwright: {}", e.toString());
            return null;
        }
    }

    private static String modo() {
        String v = ConfigManager.getDriver("trace.mode");
        String modo = v == null || v.isBlank() ? "retain-on-failure" : v.trim().toLowerCase();
        if (!modo.equals("off") && !modo.equals("on") && !modo.equals("retain-on-failure")) {
            log.warn("trace.mode invalido ({}); se usa retain-on-failure", v);
            return "retain-on-failure";
        }
        return modo;
    }

    private static String directorio() {
        String v = ConfigManager.getDriver("trace.dir");
        return v == null || v.isBlank() ? "target/traces" : v.trim();
    }

    private static String nombreArchivo(String escenario) {
        String base = escenario == null ? "escenario" : escenario.replaceAll("[^A-Za-z0-9_-]+", "_");
        if (base.length() > 80) base = base.substring(0, 80);
        return base + "-" + LocalDateTime.now().format(SELLO) + "-" + Thread.currentThread().getId() + ".zip";
    }
}
