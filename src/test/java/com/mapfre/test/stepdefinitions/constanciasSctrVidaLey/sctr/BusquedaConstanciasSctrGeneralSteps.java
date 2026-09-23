package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.BusquedaConstanciasSctrGeneralPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.ClientesSctrGeneralPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.PerfilClienteSctrGeneralPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class BusquedaConstanciasSctrGeneralSteps {
    private final ClientesSctrGeneralPage clientesSctrGeneralPage;
    private final BusquedaConstanciasSctrGeneralPage busquedaConstanciasSctrGeneralPage;
    public BusquedaConstanciasSctrGeneralSteps(PageProvider pageProvider) {
        this.busquedaConstanciasSctrGeneralPage = new BusquedaConstanciasSctrGeneralPage(pageProvider.get());
        this.clientesSctrGeneralPage = new ClientesSctrGeneralPage(pageProvider.get());
    }
    @And("en la pagina Busqueda de Constancias, selecciona la pestana Poliza")
    public void enLaPaginaBusquedaDeConstanciasSeleccionaLaPestanaPoliza() {
        busquedaConstanciasSctrGeneralPage.assertLoaded();
        busquedaConstanciasSctrGeneralPage.seleccionarPolizaTab();
    }

    @And("en la pagina Clientes SCTR General seleccionamos la opcion Constancia dentro del menu Consultas")
    public void enLaPaginaClientesSCTRGeneralSeleccionamosLaOpcionConstanciaDentroDelMenuConsultas() {
        clientesSctrGeneralPage.assertLoadedSctrGeneral();
        clientesSctrGeneralPage.seleccionarConstanciaMenuConsultas();
    }
    @And("en la pagina Busqueda de Constancias SCTR General, selecciona la pestana Poliza")
    public void enLaPaginaBusquedaDeConstanciasSCTRGeneralSeleccionaLaPestanaPoliza() {
        busquedaConstanciasSctrGeneralPage.assertLoaded();
        busquedaConstanciasSctrGeneralPage.seleccionarPolizaTab();
    }

    @And("en la pagina Busqueda de Constancias SCTR General, ingresa el numero de poliza {string} y el numero de recibo {string}")
    public void enLaPaginaBusquedaDeConstanciasSCTRGeneralIngresaElNumeroDePolizaYElNumeroDeRecibo(String numero_poliza, String numero_recibo) {
        busquedaConstanciasSctrGeneralPage.fillFormBusquedaConstanciaPolizaTab(numero_poliza, numero_recibo);
    }

    @Then("en la pagina Busqueda de Constancias SCTR General, se muestra el detalle del recibo anulado {string} en una ventana emergente")
    public void enLaPaginaBusquedaDeConstanciasSCTRGeneralSeMuestraElDetalleDelReciboAnuladoEnUnaVentanaEmergente(String numero_recibo) {
        busquedaConstanciasSctrGeneralPage.processAndAssertSuccessBotonBuscar(50_000, 500);
        String numeroConstancia = busquedaConstanciasSctrGeneralPage.obtenerNumeroConstanciaDetalleBusqueda();
        busquedaConstanciasSctrGeneralPage.validarResultadoNumeroReciboAnulado(numero_recibo);
        ExtentEvidence.shot("Evidencia despues de validar el resultado del número de recibo anulado");
        busquedaConstanciasSctrGeneralPage.processAndAssertSuccessVerDetalleAnulacion(50_000, 500);
        busquedaConstanciasSctrGeneralPage.validarInformacionDetalleAnulacion(numeroConstancia);
    }


}
