package com.mapfre.test.stepdefinitions.poliza.riegosgenerales.cotizacion;

import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesRcDemolicionesPage;
import com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion.CotizacionRiesgosGeneralesRcEventosPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class CotizacionRiesgosGeneralesRCDemolicionesSteps {

    private final CotizacionRiesgosGeneralesRcDemolicionesPage cotizacionRiesgosGeneralesRcDemolicionesPage;


    public CotizacionRiesgosGeneralesRCDemolicionesSteps(PageProvider pageProvider) {
        this.cotizacionRiesgosGeneralesRcDemolicionesPage =  new CotizacionRiesgosGeneralesRcDemolicionesPage(pageProvider.get());

    }

    @And("en la pagina Cotizador de Riesgos Generales en la seccion Duracion del evento ingresamos la fecha desde {string} hasta {string}, asegurado {string}, actividad a realizar {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesEnLaSeccionDuracionDelEventoIngresamosLaFechaDesdeHastaAseguradoActividadARealizar(
            String fechaDesde, String fechaHasta, String asegurado,  String actividad) {
        cotizacionRiesgosGeneralesRcDemolicionesPage.ingresarDuracionEvento( fechaDesde, fechaHasta, asegurado, actividad);

    }

    @And("en la pagina Cotizador de Riesgos Generales se ingresa direccion de riesgo {string}, suma asegurada {string}")
    public void enLaPaginaCotizadorDeRiesgosGeneralesSeIngresaDireccionDeRiesgoSumaAsegurada(
            String direccionRiesgo,  String sumaAsegurada) {
        cotizacionRiesgosGeneralesRcDemolicionesPage.ingresarDatosRiesgo(direccionRiesgo,sumaAsegurada);
        cotizacionRiesgosGeneralesRcDemolicionesPage.clickSiguiente();

    }

}