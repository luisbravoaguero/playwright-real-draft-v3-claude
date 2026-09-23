package com.mapfre.test.stepdefinitions.poliza.vidaIndividual;

import com.mapfre.playwright.pageobjects.poliza.PolizaPage;
import com.mapfre.playwright.pageobjects.poliza.vidaIndividual.CotizacionPolizaVidaIndividualPage;
import com.mapfre.playwright.pageobjects.poliza.vidaIndividual.VidaIndividualPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CotizacionPolizaFondoUniversitarioSteps {
    private final PolizaPage polizaPage;
    private final VidaIndividualPage vidaIndividualPage;
    private final CotizacionPolizaVidaIndividualPage cotizacionPolizaVidaIndividualPage;


    public CotizacionPolizaFondoUniversitarioSteps(PageProvider pageProvider) {
        this.polizaPage = new PolizaPage(pageProvider.get());
        this.vidaIndividualPage = new VidaIndividualPage(pageProvider.get());
        this.cotizacionPolizaVidaIndividualPage = new CotizacionPolizaVidaIndividualPage(pageProvider.get());

    }
    @And("en la pagina Polizas selecciona la opcion Vida Individual")
    public void seleccionarOpcionVidaIndividual() {
        polizaPage.clickVidaIndividualLink();
    }

    @And("en la pagina Vida Individual selecciona la opcion Cotizar Póliza de vida individual")
    public void enLaPaginaVidaIndividualSeleccionaLaOpcionCotizarPólizaDeVidaIndividual() {
        vidaIndividualPage.validarPaginaVidaIndividual();
        vidaIndividualPage.ingresarACotizacionVidaIndividual();


    }

    @And("en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion datos del contratante {string}, {string}, {string}, {string}, {string}, datos contacto {string}")
    public void enLaPaginaCotizacionPolizaVidaIndividualIngresarLosDatosDeLaSeccionDatosDelContratanteDatosContacto(String tipodocumento, String nrodocumento, String nombres, String apellidoPaterno, String apellidoMaterno, String correo) {
        cotizacionPolizaVidaIndividualPage.validarPaginaCotizacionPolizaVidaIndividual();
        cotizacionPolizaVidaIndividualPage.ingresarDatosContratante(tipodocumento,nrodocumento,nombres,apellidoPaterno,apellidoMaterno,correo);

    }

    @And("en la pagina cotizacion poliza vida individual se ingresa los campos de la seccion Datos del Asegurado")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeIngresaLosCamposDeLaSeccionDatosDelAsegurado() {

        cotizacionPolizaVidaIndividualPage.seleccionarContratante();
    }
    @And("en la pagina cotizacion poliza vida individual se ingresa las caracteristicas del seguro producto {string}")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeIngresaLasCaracteristicasDelSeguroProducto(String producto) {
        cotizacionPolizaVidaIndividualPage.ingresarCaracteristicasSeguroFondoUniversitario(producto);
    }

    @And("en la pagina cotizacion poliza vida individual se procesa la cotizacion")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeProcesaLaCotizacion() {
        cotizacionPolizaVidaIndividualPage.cotizar();
        cotizacionPolizaVidaIndividualPage.validarResultadoCotizacion();
        cotizacionPolizaVidaIndividualPage.guardarCotizacion();
    }

    @Then("en la pagina cotizacion poliza vida individual se muestra la cotizacion guardada")
    public void enLaPaginaCotizacionPolizaVidaIndividualSeMuestraLaCotizacionGuardada() {
        cotizacionPolizaVidaIndividualPage.ObtenerNumeroCotizacionGuardada();

    }


}
