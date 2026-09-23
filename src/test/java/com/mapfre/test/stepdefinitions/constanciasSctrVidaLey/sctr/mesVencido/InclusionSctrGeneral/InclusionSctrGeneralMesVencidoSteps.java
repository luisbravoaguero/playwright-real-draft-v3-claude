package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr.mesVencido.InclusionSctrGeneral;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.PerfilClienteSctrGeneralPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class InclusionSctrGeneralMesVencidoSteps {
    private final PerfilClienteSctrGeneralPage perfilClienteSctrGeneralPage;
    private final InclusionPlanillaPage inclusionPlanillaPage;
    
    public InclusionSctrGeneralMesVencidoSteps(PageProvider pageProvider) {
        this.perfilClienteSctrGeneralPage = new PerfilClienteSctrGeneralPage(pageProvider.get());
        this.inclusionPlanillaPage = new InclusionPlanillaPage(pageProvider.get());
    }
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para incluir sctr general mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaIncluirSctrGeneralMesVencido() {
        perfilClienteSctrGeneralPage.assertLoadedSctrGeneral();
        perfilClienteSctrGeneralPage.abrirPanelDeAplicacionesSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarAplicacionConEstadoNoDeclaradaParaIncluirMesVencidoSctrGeneral();

    }

    @And("en la pagina Perfil Cliente seccion Lista de Polizas incluimos la aplicacion seleccionada")
    public void enLaPaginaPerfilClienteSeccionListaDePolizasIncluimosLaAplicacionSeleccionada() {
        perfilClienteSctrGeneralPage.validarAplicacionSeleccionada();
        perfilClienteSctrGeneralPage.clickIncluirAplicacionSeleccionada();
        perfilClienteSctrGeneralPage.aceptarModalAlertaInclusion();
    }

    @And("en la pagina Inclusión de planilla realizamos la carga individual para la generacion")
    public void enLaPaginaInclusiónDePlanillaRealizamosLaCargaIndividualParaLaGeneracion() {
        inclusionPlanillaPage.assertLoaded();
        inclusionPlanillaPage.fillAseguradosCargaIndividualSctr();
        inclusionPlanillaPage.clickBtnGenerar();

    }

    @Then("en la pagina Inclusión de planilla se muestra el reporte constancia")
    public void enLaPaginaInclusiónDePlanillaSeMuestraElReporteConstancia() {
        inclusionPlanillaPage.assertLoadedReporteConstanciaInclusion();
    }
}
