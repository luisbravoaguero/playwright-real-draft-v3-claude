package com.mapfre.utils.testdata.documentos;

/**
 * Escenarios que consumen un numero de documento del pool. Cada consumidor tiene un slot fijo y unico:
 * en una misma ejecucion, slots distintos obtienen siempre valores distintos (aunque corran en pods distintos).
 * Para un escenario nuevo agregar una constante con el siguiente slot libre (debe ser menor que
 * testdata.documentos.stride). No reasignar slots existentes.
 */
public enum ConsumidorDocumento {
    REEMPLAZO_SCTR_GENERAL(0),
    REEMPLAZO_VIDA_LEY(1),
    REEMPLAZO_SCTR_MINERIA(2);

    private final int slot;

    ConsumidorDocumento(int slot) {
        this.slot = slot;
    }

    public int slot() {
        return slot;
    }
}
