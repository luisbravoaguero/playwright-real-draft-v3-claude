package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctrMineria.mesVencido.InclusionSctrMineria;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.InclusionPlanillaSctrMineriaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.PerfilClienteSctrMineriaPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class InclusionSctrMineriaMesVencidoSteps {
    private final PerfilClienteSctrMineriaPage perfilClienteSctrMineriaPage;
    private final InclusionPlanillaSctrMineriaPage inclusionPlanillaSctrMineriaPage;

    public InclusionSctrMineriaMesVencidoSteps(PageProvider pageProvider) {
        this.perfilClienteSctrMineriaPage = new PerfilClienteSctrMineriaPage(pageProvider.get());
        this.inclusionPlanillaSctrMineriaPage = new InclusionPlanillaSctrMineriaPage(pageProvider.get());

    }
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para incluir sctr mineria mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaIncluirSctrMineriaMesVencido() {
        perfilClienteSctrMineriaPage.assertLoadedSctrMineria();
        perfilClienteSctrMineriaPage.abrirPanelDeAplicacionesSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarAplicacionConEstadoNoDeclaradaParaIncluirMesVencidoSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarIncluirAplicacionSctrMineria();
        perfilClienteSctrMineriaPage.aceptarModalUnaSolaPolizaSiExiste();
    }

    @And("en la pagina Inclusion de Planilla agregar Excel de trabajadores")
    public void enLaPaginaInclusionDePlanillaAgregarExcelDeTrabajadores() {
        inclusionPlanillaSctrMineriaPage.cargarPlanillaInclusionMineria();
        inclusionPlanillaSctrMineriaPage.clickBtnProcesar();
        inclusionPlanillaSctrMineriaPage.clickBtnGenerar();
        inclusionPlanillaSctrMineriaPage.aceptarListaObservacionesInclusionMineria();

    }

    @Then("en la pagina Inclusion de planilla se genera la inclusion")
    public void enLaPaginaInclusionDePlanillaSeGeneraLaInclusion() {

    }
}
