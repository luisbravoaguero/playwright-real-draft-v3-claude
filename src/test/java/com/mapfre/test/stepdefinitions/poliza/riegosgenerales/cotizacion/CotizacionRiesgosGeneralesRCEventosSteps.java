package com.mapfre.test.stepdefinitions.poliza.riegosgenerales.cotizacion;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesRcDeshPage;
import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesRcEventosPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class CotizacionRiesgosGeneralesRCEventosSteps {

    private final CotizacionRiesgosGeneralesRcEventosPage cotizacionRiesgosGeneralesRcEventosPage;

    public CotizacionRiesgosGeneralesRCEventosSteps(PageProvider pageProvider) {
        this.cotizacionRiesgosGeneralesRcEventosPage =  new CotizacionRiesgosGeneralesRcEventosPage(pageProvider.get());
    }
    @And("en la pagina Cotizador de Riesgos Generales en seccion Producto ingresamos el producto {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnSeccionProductoIngresamosElProducto(String producto) {
        cotizacionRiesgosGeneralesRcEventosPage.seleccionarProducto(producto);
    }

    @And("en la pagina Cotizador de Riesgos Generales en la seccion Datos del contratante ingresamos el tipo de documento {string}, numero de documento {string}, asegurado {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnLaSeccionDatosDelContratanteIngresamosElTipoDeDocumentoNumeroDeDocumentoAsegurado(
            String tipoDocumento,
            String numeroDocumento,
            String asegurado) {

        cotizacionRiesgosGeneralesRcEventosPage.ingresarDatosContratante(tipoDocumento, numeroDocumento,asegurado);

    }

    @And("en la pagina Cotizador de Riesgos Generales en la seccion Datos del evento ingresamos Ramo {string}, Moneda {string}, Canal {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnLaSeccionDatosDelEventoIngresamosRamoMonedaCanal(
            String ramo,
            String moneda,
            String canal) {
        cotizacionRiesgosGeneralesRcEventosPage.ingresarDatosEvento(ramo,moneda,canal);
        cotizacionRiesgosGeneralesRcEventosPage.ingresarUbicacionRiesgo();


    }

    @And("en la pagina Cotizador de Riesgos Generales en la seccion Duracion del evento ingresamos la fecha desde {string} hasta {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnLaSeccionDuracionDelEventoIngresamosLaFechaDesdeHasta(
            String fechaDesde,
            String fechaHasta) {
        cotizacionRiesgosGeneralesRcEventosPage.ingresarDuracionEvento(fechaDesde,fechaHasta);


    }

    @And("en la pagina Cotizador de Riesgos Generales ingresamos tipo de evento {string}, evento {string}, aforo {string}, seguridad {string}, suma asegurada {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesIngresamosTipoDeEventoEventoAforoSeguridadSumaAsegurada(
            String tipoEvento,
            String evento,
            String aforo,
            String seguridad,
            String sumaAsegurada) {
        cotizacionRiesgosGeneralesRcEventosPage.ingresarInformacionEvento(tipoEvento,evento,aforo,seguridad,sumaAsegurada);
        cotizacionRiesgosGeneralesRcEventosPage.clickBotonSiguiente();



    }

}