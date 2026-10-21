package com.mapfre.utils.testdata.documentos;

/**
 * Origen de numeros de documento validos y no usados. Hoy: {@link PoolArchivoProveedor}.
 * Si en el futuro los pods tienen acceso a BD, una implementacion JDBC puede reemplazarlo sin tocar los steps.
 */
public interface ProveedorDocumentos {
    DocumentoAsignado asignar(ConsumidorDocumento consumidor);
}
