package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.BusquedaConstanciasSctrGeneralPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Then;

public class BusquedaConstanciasExclusionSctrGeneralSteps {
    private final BusquedaConstanciasSctrGeneralPage busquedaConstanciasSctrGeneralPage;

    public BusquedaConstanciasExclusionSctrGeneralSteps(PageProvider pageProvider) {
        this.busquedaConstanciasSctrGeneralPage = new BusquedaConstanciasSctrGeneralPage(pageProvider.get());
    }

    @Then("en la pagina Busqueda de Constancias SCTR General, se muestra la constancia de tipo exclusion del numero de poliza {string} y recibo {string}")
    public void enLaPaginaBusquedaDeConstanciasSCTRGeneralSeMuestraLaConstanciaDeTipoExclusionDelNumeroDePolizaYRecibo(String numero_poliza, String numero_recibo) {
        //busquedaConstanciasSctrGeneralPage.assertLoaded();
        //busquedaConstanciasSctrGeneralPage.seleccionarPolizaTab();
        busquedaConstanciasSctrGeneralPage.processAndAssertSuccessBotonBuscar(50_000, 500);
        busquedaConstanciasSctrGeneralPage.validarResultadoPolizaNumeroReciboExclusion(numero_poliza, numero_recibo);
    }
}
