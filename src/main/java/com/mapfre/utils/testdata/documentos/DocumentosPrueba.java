package com.mapfre.utils.testdata.documentos;

/**
 * Punto de entrada para los steps: numeros de documento reales, no usados y distintos por escenario.
 * El proveedor se crea una sola vez por JVM (thread-safe) en el primer uso. Si la configuracion falla,
 * cada llamada vuelve a lanzar el error claro (no se cachea un estado roto).
 */
public final class DocumentosPrueba {
    private static ProveedorDocumentos proveedor;

    private DocumentosPrueba() {}

    private static synchronized ProveedorDocumentos proveedor() {
        if (proveedor == null) {
            proveedor = PoolArchivoProveedor.desdeConfiguracion();
        }
        return proveedor;
    }

    public static DocumentoAsignado asignar(ConsumidorDocumento consumidor) {
        return proveedor().asignar(consumidor);
    }
}
