package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctrMineria;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.BusquedaConstanciasSctrMineriaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.ClientesSctrMineriaPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DetalleAnulacionConstanciaSctrMineriaSteps {
    private final ClientesSctrMineriaPage clientesSctrMineriaPage;
    private final BusquedaConstanciasSctrMineriaPage busquedaConstanciasSctrMineriaPage;
    public DetalleAnulacionConstanciaSctrMineriaSteps(PageProvider pageProvider){
        this.clientesSctrMineriaPage = new ClientesSctrMineriaPage(pageProvider.get());
        this.busquedaConstanciasSctrMineriaPage = new BusquedaConstanciasSctrMineriaPage(pageProvider.get());
    }

    @And("en la pagina Clientes SCTR Mineria seleccionamos la opcion Constancia dentro del menu Consultas")
    public void enLaPaginaClientesSCTRMineriaSeleccionamosLaOpcionConstanciaDentroDelMenuConsultas() {
        clientesSctrMineriaPage.assertLoadedSctrMineria();
        clientesSctrMineriaPage.seleccionarConstanciaMenuConsultasSctrMineria();
    }

    @And("en la pagina Busqueda de Constancias SCTR Mineria, selecciona la pestana Poliza")
    public void enLaPaginaBusquedaDeConstanciasSCTRMineriaSeleccionaLaPestanaPoliza() {
        busquedaConstanciasSctrMineriaPage.assertLoadedSctrMineria();
        busquedaConstanciasSctrMineriaPage.seleccionarPolizaTabSctrMineria();
    }

    @And("en la pagina Busqueda de Constancias SCTR Mineria, ingresa el numero de poliza {string} y el numero de recibo {string}")
    public void enLaPaginaBusquedaDeConstanciasSCTRMineriaIngresaElNumeroDePolizaYElNumeroDeRecibo(String numero_poliza, String numero_recibo) {
        busquedaConstanciasSctrMineriaPage.fillFormBusquedaConstanciaPolizaTabSctrMineria(numero_poliza, numero_recibo);
    }

    @Then("en la pagina Busqueda de Constancias SCTR Mineria, se muestra el detalle del recibo anulado {string} en una ventana emergente")
    public void enLaPaginaBusquedaDeConstanciasSCTRMineriaSeMuestraElDetalleDelReciboAnuladoEnUnaVentanaEmergente(String numero_recibo) {
        busquedaConstanciasSctrMineriaPage.processAndAssertSuccessBotonBuscarSctrMineria(50_000, 500);
        String numeroConstanciaSctrMineria = busquedaConstanciasSctrMineriaPage.obtenerNumeroConstanciaDetalleBusquedaSctrMineria();
        busquedaConstanciasSctrMineriaPage.validarResultadoNumeroReciboAnuladoSctrMineria(numero_recibo);
        busquedaConstanciasSctrMineriaPage.processAndAssertSuccessVerDetalleAnulacionSctrMineria(60_000, 500);
        busquedaConstanciasSctrMineriaPage.validarInformacionDetalleAnulacionSctrMineria(numeroConstanciaSctrMineria);
    }
}

