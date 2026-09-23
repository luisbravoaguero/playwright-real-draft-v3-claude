package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.BusquedaConstanciasVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.ClientesVidaLeyPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class AnulacionReciboVidaLeySteps {
    private final BusquedaConstanciasVidaLeyPage busquedaConstanciasVidaLeyPage;
    private final ClientesVidaLeyPage clientesVidaLeyPage;
    public AnulacionReciboVidaLeySteps(PageProvider pageProvider) {
        this.busquedaConstanciasVidaLeyPage = new BusquedaConstanciasVidaLeyPage(pageProvider.get());
        this.clientesVidaLeyPage = new ClientesVidaLeyPage(pageProvider.get());
    }
    @And("en la pagina Clientes Vida Ley seleccionamos la opcion Constancia dentro del menu Consultas")
    public void enLaPaginaClientesVidaLeySeleccionamosLaOpcionConstanciaDentroDelMenuConsultas() {
        clientesVidaLeyPage.assertLoadedVidaLey();
        clientesVidaLeyPage.seleccionarConstanciaMenuConsultasVidaLey();
    }

    @And("en la pagina Busqueda de Constancias Vida Ley, selecciona la pestana Poliza")
    public void enLaPaginaBusquedaDeConstanciasVidaLeySeleccionaLaPestanaPoliza() {
        busquedaConstanciasVidaLeyPage.assertLoadedVidaLey();
        busquedaConstanciasVidaLeyPage.seleccionarPolizaTabVidaLey();
    }

    @And("en la pagina Busqueda de Constancias Vida Ley, ingresa el numero de poliza {string} y el numero de recibo {string}")
    public void enLaPaginaBusquedaDeConstanciasVidaLeyIngresaElNumeroDePolizaYElNumeroDeRecibo(String numero_poliza, String numero_recibo) {
        busquedaConstanciasVidaLeyPage.fillFormBusquedaConstanciaPolizaTabVidaLey(numero_poliza, numero_recibo);
    }

    @And("en la pagina Busqueda de Constancias Vida Ley, realiza la anulacion del recibo {string} de la poliza {string}")
    public void enLaPaginaBusquedaDeConstanciasVidaLeyRealizaLaAnulacionDelReciboDeLaPoliza(String numero_poliza, String numero_recibo) {
        busquedaConstanciasVidaLeyPage.processAndAssertSuccessBotonBuscarVidaLey(60_000, 500);
        busquedaConstanciasVidaLeyPage.validarResultadoBusquedaParaAnularVidaLey(numero_poliza, numero_recibo);
        ExtentEvidence.shot("Evidencia despues de validar el resultado de la busqueda para anular el recibo");
        busquedaConstanciasVidaLeyPage.processAndAssertSuccessAbrirModalAnulacionVidaLey(60_000, 500);
        busquedaConstanciasVidaLeyPage.seleccionarReciboParaAnularVidaLey();
        busquedaConstanciasVidaLeyPage.ingresarMotivoAnulacionVidaLey();
    }

    @Then("en la pagina Busqueda de Constancias Vida Ley, se muestra el detalle del recibo anulado {string} en una ventana emergente")
    public void enLaPaginaBusquedaDeConstanciasVidaLeySeMuestraElDetalleDelReciboAnuladoEnUnaVentanaEmergente(String numero_recibo) {
        busquedaConstanciasVidaLeyPage.processAndAssertSuccessAnulacionVidaLey(300_000, 500);
    }
}
