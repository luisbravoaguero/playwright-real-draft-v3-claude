package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral.ConstanciasSctrPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.PerfilClientePage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ActualizacionPlanillaSctrSteps {
    private final PerfilClientePage perfilClientePage;
    private final ConstanciasSctrPage constanciasSctrPage;
    private final ScenarioContext scenarioContext;
    public ActualizacionPlanillaSctrSteps(PageProvider pageProvider, ScenarioContext scenarioContext){
        this.perfilClientePage = new PerfilClientePage(pageProvider.get());
        this.constanciasSctrPage = new ConstanciasSctrPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Clientes seleccionamos una aplicacion para actualizar la planilla")
    public void enLaPaginaPerfilClientesSeleccionamosUnaAplicacionParaActualizarLaPlanilla() {
        perfilClientePage.assertLoaded();
        perfilClientePage.seleccionarAplicacionParaActualizarPlanilla();
        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion");
        perfilClientePage.seleccionarBotonPlanillaDelPanelFooter();
        perfilClientePage.processAndAssertSuccessPlanillaButton(45_000, 500);
    }

    @And("en la pagina Constancias cambiamos el estado del asegurado a inactivo por medio de la baja")
    public void enLaPaginaConstanciasCambiamosElEstadoDelAseguradoAInactivoPorMedioDeLaBaja() {
        String numeroDocumentoAsegurado = constanciasSctrPage.obtenerPrimerDocumentoActivo();
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO, numeroDocumentoAsegurado);
        ExtentEvidence.shot("Evidencia despues validar la visibilidad del asegurado con estado activo");
        constanciasSctrPage.seleccionarBotonEditarDeUnAseguradoActivo(numeroDocumentoAsegurado);
        constanciasSctrPage.processAndAssertSuccessEditarAseguradoButton(25_000, 500);
        constanciasSctrPage.validarEstadoHabilitadoDelAseguradoDentroDelModal(numeroDocumentoAsegurado);
        ExtentEvidence.shot("Evidencia despues validar la visibilidad del modal del asegurado con estado habilitado");
        constanciasSctrPage.processAndAssertSuccessDarBajaAseguradoButton(30_000, 500);
        ExtentEvidence.shot("Evidencia despues de dar de baja al asegurado");
        constanciasSctrPage.processAndAssertSuccessModalOkAseguradoButton(30_000, 500);
    }

    @Then("en la pagina Constancias se muestra el estado inactivo del asegurado actualizado")
    public void enLaPaginaConstanciasSeMuestraElEstadoInactivoDelAseguradoActualizado() {
        String numeroDocumentoAsegurado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO);
        constanciasSctrPage.validarEstadoInactivoDelAsegurado(numeroDocumentoAsegurado);
        ExtentEvidence.shot("Evidencia despues de validar la visibilidad de los datos del asegurado");
        constanciasSctrPage.visualizarParteDerechaDeLContenido();
    }
}
