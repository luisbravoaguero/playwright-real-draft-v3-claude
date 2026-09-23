package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral.ConstanciasSctrPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.Then;

public class ReimpresionConstanciaVidaLeySteps {
    private final ConstanciasSctrPage constanciasSctrPage;
    public ReimpresionConstanciaVidaLeySteps(PageProvider pageProvider){
        this.constanciasSctrPage = new ConstanciasSctrPage(pageProvider.get());
    }
    @Then("en la pagina Constancias se muestra la reimpresion y descarga de la constancia vidaley")
    public void enLaPaginaConstanciasSeMuestraLaReimpresionYDescargaDeLaConstanciaVidaley() {
        constanciasSctrPage.processAndAssertSuccessModalGenerarConstanciaIndividualVidaLeyButton(30_000, 500);
        ExtentEvidence.shot("Evidencia despues de generar la constancia individual vidaley");
        constanciasSctrPage.processAndAssertSuccessDownloadReimpresionConstanciaVidaLey(120_000, 300);
    }
}
