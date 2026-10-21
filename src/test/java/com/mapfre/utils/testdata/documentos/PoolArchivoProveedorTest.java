package com.mapfre.utils.testdata.documentos;

import com.mapfre.exceptions.FrameworkException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.expectThrows;

/** Prueba unitaria (sin navegador) de la asignacion de indices del pool. Ejecutar con -Dtest=PoolArchivoProveedorTest. */
public class PoolArchivoProveedorTest {
    private static final int N = 2000;
    private static final int STRIDE = 20;
    private static final int RESERVA = 200;
    private static final List<String> POOL = IntStream.range(0, N).mapToObj(i -> "DOC" + i).toList();

    private static PoolArchivoProveedor proveedor(long runId, boolean jenkins) {
        return new PoolArchivoProveedor(POOL, STRIDE, RESERVA, runId, jenkins);
    }

    @Test
    public void consumidoresDelMismoRunObtienenValoresDistintos() {
        // Cada pod crea su propio proveedor con el mismo BUILD_NUMBER
        Set<String> valores = new HashSet<>();
        for (ConsumidorDocumento c : ConsumidorDocumento.values()) {
            valores.add(proveedor(57, true).asignar(c).valor());
        }
        assertEquals(valores.size(), ConsumidorDocumento.values().length);
    }

    @Test
    public void runsJenkinsNoRepitenValoresHastaAgotarLaZona() {
        int capacidad = (N - RESERVA) / STRIDE;
        List<Integer> indices = new ArrayList<>();
        for (long run = 0; run < capacidad; run++) {
            for (ConsumidorDocumento c : ConsumidorDocumento.values()) {
                indices.add(proveedor(run, true).indice(c.slot()));
            }
        }
        assertEquals(new HashSet<>(indices).size(), indices.size());
        assertTrue(indices.stream().allMatch(i -> i < N - RESERVA), "Jenkins no debe usar la reserva local");
    }

    @Test
    public void runsLocalesUsanSoloLaReservaFinal() {
        int capacidad = RESERVA / STRIDE;
        List<Integer> indices = new ArrayList<>();
        for (long run = 0; run < capacidad; run++) {
            for (ConsumidorDocumento c : ConsumidorDocumento.values()) {
                indices.add(proveedor(run, false).indice(c.slot()));
            }
        }
        assertEquals(new HashSet<>(indices).size(), indices.size());
        assertTrue(indices.stream().allMatch(i -> i >= N - RESERVA && i < N), "Local solo debe usar la reserva final");
    }

    @Test
    public void poolVacioOPequenoFallaConMensajeClaro() {
        FrameworkException vacio = expectThrows(FrameworkException.class,
                () -> new PoolArchivoProveedor(List.of(), STRIDE, RESERVA, 1, true));
        assertTrue(vacio.getMessage().contains("vacio"));
        expectThrows(FrameworkException.class,
                () -> new PoolArchivoProveedor(POOL.subList(0, RESERVA + 5), STRIDE, RESERVA, 1, true));
    }

    @Test
    public void duplicadosSeDescartanYNoSeEntreganADosConsumidores() {
        // Cada valor aparece dos veces seguidas: sin deduplicar, los slots 0 y 1 recibirian el mismo documento
        List<String> conDuplicados = new ArrayList<>();
        POOL.forEach(v -> { conDuplicados.add(v); conDuplicados.add(v); });
        PoolArchivoProveedor p = new PoolArchivoProveedor(conDuplicados, STRIDE, RESERVA, 0, true);
        assertEquals(p.asignar(ConsumidorDocumento.REEMPLAZO_SCTR_GENERAL).valor(), "DOC0");
        assertEquals(p.asignar(ConsumidorDocumento.REEMPLAZO_VIDA_LEY).valor(), "DOC1");
        assertEquals(p.asignar(ConsumidorDocumento.REEMPLAZO_SCTR_MINERIA).valor(), "DOC2");
    }

    @Test
    public void poolPequenoTrasDeduplicarFalla() {
        List<String> repetido = java.util.Collections.nCopies(N, "DOC0");
        expectThrows(FrameworkException.class, () -> new PoolArchivoProveedor(repetido, STRIDE, RESERVA, 0, true));
    }

    @Test
    public void parsearLeeCabeceraRunIdBaseYComentarios() {
        PoolArchivoProveedor.ContenidoPool c = PoolArchivoProveedor.parsear(
                List.of("﻿#runIdBase = 1234", "# comentario", "", "  111  ", "222"), "test");
        assertEquals(c.runIdBase(), 1234L);
        assertEquals(c.valores(), List.of("111", "222"));
    }

    @Test
    public void parsearSinCabeceraUsaBaseCero() {
        assertEquals(PoolArchivoProveedor.parsear(List.of("111"), "test").runIdBase(), 0L);
    }

    @Test
    public void parsearCabeceraInvalidaODuplicadaFalla() {
        expectThrows(FrameworkException.class, () -> PoolArchivoProveedor.parsear(List.of("#runIdBase=abc", "1"), "test"));
        expectThrows(FrameworkException.class, () -> PoolArchivoProveedor.parsear(List.of("#runIdBase=-5", "1"), "test"));
        expectThrows(FrameworkException.class,
                () -> PoolArchivoProveedor.parsear(List.of("#runIdBase=1", "#runIdBase=2", "1"), "test"));
    }

    @Test
    public void runIdJenkinsEsRelativoALaBase() {
        assertEquals(PoolArchivoProveedor.runIdJenkins(1240, 1234), 6L);
        assertEquals(PoolArchivoProveedor.runIdJenkins(1234, 1234), 0L);
        FrameworkException e = expectThrows(FrameworkException.class, () -> PoolArchivoProveedor.runIdJenkins(1200, 1234));
        assertTrue(e.getMessage().contains("runIdBase"));
    }

    @Test
    public void strideMenorQueLosSlotsFalla() {
        expectThrows(FrameworkException.class, () -> new PoolArchivoProveedor(POOL, 2, RESERVA, 1, true));
    }
}
