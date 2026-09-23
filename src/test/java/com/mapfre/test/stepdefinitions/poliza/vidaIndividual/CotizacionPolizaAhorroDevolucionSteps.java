package com.mapfre.test.stepdefinitions.poliza.vidaIndividual;

import com.mapfre.playwright.pageobjects.poliza.PolizaPage;
import com.mapfre.playwright.pageobjects.poliza.vidaIndividual.CotizacionPolizaVidaIndividualPage;
import com.mapfre.playwright.pageobjects.poliza.vidaIndividual.VidaIndividualPage;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.stepdefinitions.poliza.Salud.CotizacionPolizaSaludSteps;
import io.cucumber.java.en.And;

public class CotizacionPolizaAhorroDevolucionSteps {
    private final PolizaPage polizaPage;
    private final VidaIndividualPage vidaIndividualPage;
    private final CotizacionPolizaVidaIndividualPage cotizacionPolizaVidaIndividualPage;


    public CotizacionPolizaAhorroDevolucionSteps(PageProvider pageProvider) {
        this.polizaPage = new PolizaPage(pageProvider.get());
        this.vidaIndividualPage = new VidaIndividualPage(pageProvider.get());
        this.cotizacionPolizaVidaIndividualPage = new CotizacionPolizaVidaIndividualPage(pageProvider.get());

    }

    @And("en la pagina cotizacion poliza vida individual se ingresa las caracteristicas del seguro producto {string}, Anios duracion seguro {string}, porcentaje devolucion {string}")
    public void ingresarCaracteristicasSeguro(String producto,String duracion,String devolucion) {
        cotizacionPolizaVidaIndividualPage.ingresarCaracteristicasSeguroDevolucion(producto,duracion,devolucion);

    }

    @And("en la pagina cotizacion poliza vida individual se ingresa las coberturas la suma asegurada{string}")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeIngresaLasCoberturasLaSumaAsegurada(String suma_asegurada) {
        cotizacionPolizaVidaIndividualPage.ingresarCoberturas(suma_asegurada);
    }

    @And("en la pagina cotizacion poliza vida individual se procesa la cotizacion Vida Devolucion Ahorro")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeProcesaLaCotizacionVidaDevolucionAhorro() {
        cotizacionPolizaVidaIndividualPage.cotizar();
        cotizacionPolizaVidaIndividualPage.validarResultadoCotizacionDevolucionAhorro();
        cotizacionPolizaVidaIndividualPage.guardarCotizacion();
    }
}
