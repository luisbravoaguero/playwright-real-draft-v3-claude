package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral.ConstanciasSctrPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class ReimpresionConstanciaSctrSteps {
    private final ConstanciasSctrPage constanciasSctrPage;
    public ReimpresionConstanciaSctrSteps(PageProvider pageProvider){
        this.constanciasSctrPage = new ConstanciasSctrPage(pageProvider.get());
    }
    @And("en la pagina Constancias generamos la reimpresión de constancia")
    public void enLaPaginaConstanciasGeneramosLaReimpresiónDeConstancia() {
        constanciasSctrPage.esperarHastaCargarTablaAsegurados();
        constanciasSctrPage.processAndAssertSuccessGenerarConstaciaIndividualButton(30_000, 500);
        constanciasSctrPage.fillModalFormGenerarConstanciaIndividual();
    }
}
