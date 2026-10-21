package com.mapfre.utils.testdata.documentos;

/** Valor entregado a un escenario y su origen en el pool (para trazar fallos en el reporte). */
public record DocumentoAsignado(String valor, ConsumidorDocumento consumidor, int indice, long runId) {

    public String descripcion() {
        return "documento " + valor + " (consumidor " + consumidor + ", indice pool " + indice + ", runId " + runId + ")";
    }
}
