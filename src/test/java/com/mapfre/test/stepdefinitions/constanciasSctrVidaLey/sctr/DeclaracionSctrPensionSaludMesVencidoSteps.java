package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral.ConstanciasSctrPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.PerfilClientePage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import io.cucumber.java.en.And;

public class DeclaracionSctrPensionSaludMesVencidoSteps {
    private final PerfilClientePage perfilClientePage;
    private final ConstanciasSctrPage constanciasSctrPage;
    private final ScenarioContext scenarioContext;
    public DeclaracionSctrPensionSaludMesVencidoSteps(PageProvider pageProvider, ScenarioContext scenarioContext){
        this.perfilClientePage = new PerfilClientePage(pageProvider.get());
        this.constanciasSctrPage = new ConstanciasSctrPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar la poliza mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararLaPolizaMesVencido() {
        perfilClientePage.assertLoaded();

    }
}
