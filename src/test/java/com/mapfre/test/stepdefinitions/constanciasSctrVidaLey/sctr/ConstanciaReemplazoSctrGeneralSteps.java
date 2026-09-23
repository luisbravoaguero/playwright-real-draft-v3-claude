package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.ConstanciasSCTRGeneralPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.ReemplazarPlanillaSctrGeneralPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.PerfilClientePage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.PerfilClienteSctrGeneralPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;

import java.nio.file.Path;

public class ConstanciaReemplazoSctrGeneralSteps {
    private final PerfilClienteSctrGeneralPage perfilClienteSctrGeneralPage;
    private final ConstanciasSCTRGeneralPage constanciasSCTRGeneralPage;
    private final ReemplazarPlanillaSctrGeneralPage reemplazarPlanillaSctrGeneralPage;
    private final ScenarioContext scenarioContext;
    public ConstanciaReemplazoSctrGeneralSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.perfilClienteSctrGeneralPage = new PerfilClienteSctrGeneralPage(pageProvider.get());
        this.constanciasSCTRGeneralPage = new ConstanciasSCTRGeneralPage(pageProvider.get());
        this.reemplazarPlanillaSctrGeneralPage = new ReemplazarPlanillaSctrGeneralPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Cliente selecciona una aplicacion con estado declarada para reemplazar")
    public void enLaPaginaPerfilClienteSeleccionaUnaAplicacionConEstadoDeclaradaParaReemplazar() {
        perfilClienteSctrGeneralPage.assertLoadedSctrGeneral();
        perfilClienteSctrGeneralPage.abrirPanelDeAplicacionesSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarPrimeraAplicacionConEstadoDeclaradaConReciboParaConstanciaReemplazoSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarPlanillaAplicacionButtonSctrGeneral();
    }

    @And("en la pagina Constancias SCTR General selecciona un registro de la tabla de asegurados para reemplazar")
    public void enLaPaginaConstanciasSCTRGeneralSeleccionaUnRegistroDeLaTablaDeAseguradosParaReemplazar() {
        constanciasSCTRGeneralPage.proccessAndAssertSuccessBotonPlanillaSctrGeneral(60_000, 500);
        constanciasSCTRGeneralPage.validarVisualizacionTablaAseguradosSctrGeneral();
        String numeroDocumentoActivo = constanciasSCTRGeneralPage.obtenerPrimerDocumentoActivoUnicoSctrGeneral();
        constanciasSCTRGeneralPage.seleccionarAseguradoActivoParaReemplazoSctrGeneral(numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de seleccionar asegurado ACTIVO para reemplazo");
        String montoSueldoAseguradoReemplazado = constanciasSCTRGeneralPage.obtenerSueldoAseguradoActivoPorNumeroDocumentoSctrGeneral(numeroDocumentoActivo);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO, montoSueldoAseguradoReemplazado);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO, numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de obtener el sueldo del asegurado activo: " + montoSueldoAseguradoReemplazado);
        constanciasSCTRGeneralPage.proccessAndAssertSuccessReemplazarPlanillaSctrGeneral(60_000, 500);
    }

    @And("en la pagina Reemplazar Planilla SCTR General carga la planilla del asegurado a reemplazar")
    public void enLaPaginaReemplazarPlanillaSCTRGeneralCargaLaPlanillaDelAseguradoAReemplazar() {
        String numeroDocumentoAseguradoReemplazante = RandomData.idDocumentPeru(9,8);
        String nombreArchivo = "File_constancia_reemplazo_sctr";
        String montoSueldoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE, numeroDocumentoAseguradoReemplazante);

        Path template = TemplateFactory.buildPlanillaConstanciaReemplazo(numeroDocumentoAseguradoReemplazante, nombreArchivo, montoSueldoAseguradoReemplazado);
        reemplazarPlanillaSctrGeneralPage.fillFormDatosDeLosAseguradosSctrGeneral(template,nombreArchivo);
        ExtentEvidence.shot("Evidencia despues de completar cargar el asegurado "+numeroDocumentoAseguradoReemplazante+", con sueldo de "+montoSueldoAseguradoReemplazado);

        reemplazarPlanillaSctrGeneralPage.clickMostrarPlanillaButtonSctrGeneral();
        reemplazarPlanillaSctrGeneralPage.proccessAndAssertSuccessMostrarPlanillaSctrGeneral(180_000, 500);
        reemplazarPlanillaSctrGeneralPage.validarListaPlanillaReemplazoContenedor(numeroDocumentoAseguradoReemplazante);

    }

    @And("en la pagina Reemplazar Planilla SCTR General realiza el reemplazo de planilla")
    public void enLaPaginaReemplazarPlanillaSCTRGeneralRealizaElReemplazoDePlanilla() {
        reemplazarPlanillaSctrGeneralPage.proccessAndAssertSuccessReemplazarPlanillaSctrGeneral(60_000, 500);
        ExtentEvidence.shot("Evidencia despues hacer click en el boton Reemplazar Planilla SCTR General");
        reemplazarPlanillaSctrGeneralPage.proccessAndAssertSuccessReemplazarModalSctrGeneral(90_000, 500);
        ExtentEvidence.shot("Evidencia despues de confirmar el reemplazo de planilla SCTR General");
    }

    @Then("en la pagina Constancias SCTR General se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO")
    public void enLaPaginaConstanciasSCTRGeneralSeMuestraElAseguradoReemplazanteConTipoMovimientoREEMPLAZOYEstadoACTIVO() {
        constanciasSCTRGeneralPage.assertLoadedSctrGeneral();
        constanciasSCTRGeneralPage.validarVisualizacionTablaAseguradosSctrGeneral();
        String numeroDocumentoAseguradoReemplazante = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE);
        constanciasSCTRGeneralPage.validarAseguradoReemplazantePorNumeroDeDocumentoSctrGeneral(numeroDocumentoAseguradoReemplazante);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazante+" reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO");
    }

    @And("el asegurado reemplazado con estado INACTIVO")
    public void elAseguradoReemplazadoConEstadoINACTIVO() {
        String numeroDocumentoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO);
        constanciasSCTRGeneralPage.validarAseguradoReemplazadoConEstadoInactivoSctrGeneral(numeroDocumentoAseguradoReemplazado);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazado+" reemplazado y estado INACTIVO");
    }
}
