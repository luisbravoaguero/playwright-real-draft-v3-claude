package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctrMineria;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.ConstanciasSctrMineriaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.ReemplazarPlanillaSctrMineriaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.PerfilClienteSctrMineriaPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.reporting.ReportLogger;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.test.hooks.ScenarioKeys;
import com.mapfre.utils.testdata.documentos.ConsumidorDocumento;
import com.mapfre.utils.testdata.documentos.DocumentoAsignado;
import com.mapfre.utils.testdata.documentos.DocumentosPrueba;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import testdata.TemplateFactory;

import java.nio.file.Path;

public class ConstanciaReemplazoSctrMineriaSteps {
    private final PerfilClienteSctrMineriaPage perfilClienteSctrMineriaPage;
    private final ConstanciasSctrMineriaPage constanciasSctrMineriaPage;
    private final ReemplazarPlanillaSctrMineriaPage reemplazarPlanillaSctrMineriaPage;
    private final ScenarioContext scenarioContext;
    public ConstanciaReemplazoSctrMineriaSteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.perfilClienteSctrMineriaPage = new PerfilClienteSctrMineriaPage(pageProvider.get());
        this.constanciasSctrMineriaPage = new ConstanciasSctrMineriaPage(pageProvider.get());
        this.reemplazarPlanillaSctrMineriaPage = new ReemplazarPlanillaSctrMineriaPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Cliente SCTR Mineria selecciona una aplicacion con estado declarada para reemplazar")
    public void enLaPaginaPerfilClienteSCTRMineriaSeleccionaUnaAplicacionConEstadoDeclaradaParaReemplazar() {
        perfilClienteSctrMineriaPage.assertLoadedSctrMineria();
        perfilClienteSctrMineriaPage.abrirPanelDeAplicacionesSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarPrimeraAplicacionConEstadoDeclaradaConReciboParaConstanciaReemplazoSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarPlanillaAplicacionButtonSctrMineria();
    }

    @And("en la pagina Constancias SCTR Mineria selecciona un registro de la tabla de asegurados para reemplazar")
    public void enLaPaginaConstanciasSCTRMineriaSeleccionaUnRegistroDeLaTablaDeAseguradosParaReemplazar() {
        constanciasSctrMineriaPage.proccessAndAssertSuccessBotonPlanillaSctrMineria(60_000, 500);
        constanciasSctrMineriaPage.validarVisualizacionTablaAseguradosSctrMineria();
        String numeroDocumentoActivo = constanciasSctrMineriaPage.obtenerPrimerDocumentoActivoUnicoSctrMineria();
        constanciasSctrMineriaPage.seleccionarAseguradoActivoParaReemplazoSctrMineria(numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de seleccionar asegurado ACTIVO para reemplazo");
        String montoSueldoAseguradoReemplazado = constanciasSctrMineriaPage.obtenerSueldoAseguradoActivoPorNumeroDocumentoSctrMineria(numeroDocumentoActivo);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO, montoSueldoAseguradoReemplazado);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO, numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de obtener el sueldo del asegurado activo: " + montoSueldoAseguradoReemplazado);
        constanciasSctrMineriaPage.proccessAndAssertSuccessReemplazarPlanillaSctrMineria(60_000, 500);
    }

    @And("en la pagina Reemplazar Planilla SCTR Mineria carga la planilla del asegurado a reemplazar")
    public void enLaPaginaReemplazarPlanillaSCTRMineriaCargaLaPlanillaDelAseguradoAReemplazar() {
        reemplazarPlanillaSctrMineriaPage.assertLoadedReemplazarPlanillaSctrMineria();
        DocumentoAsignado documento = DocumentosPrueba.asignar(ConsumidorDocumento.REEMPLAZO_SCTR_MINERIA);
        ReportLogger.info("Asegurado reemplazante: " + documento.descripcion());
        String numeroDocumentoAseguradoReemplazante = documento.valor();
        String nombreArchivo = "File_constancia_reemplazo_sctr_mineria";
        String montoSueldoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE, numeroDocumentoAseguradoReemplazante);

        Path template = TemplateFactory.buildPlanillaConstanciaReemplazoSctrMineria(numeroDocumentoAseguradoReemplazante, nombreArchivo, montoSueldoAseguradoReemplazado);
        reemplazarPlanillaSctrMineriaPage.fillFormDatosDeLosAseguradosSctrMineria(template,nombreArchivo);
        ExtentEvidence.shot("Evidencia despues de completar cargar el asegurado "+numeroDocumentoAseguradoReemplazante+", con sueldo de "+montoSueldoAseguradoReemplazado);

        reemplazarPlanillaSctrMineriaPage.clickMostrarPlanillaButtonSctrMineria();
        reemplazarPlanillaSctrMineriaPage.proccessAndAssertSuccessMostrarPlanillaSctrMineria(180_000, 500);
        reemplazarPlanillaSctrMineriaPage.validarListaPlanillaReemplazoContenedor(numeroDocumentoAseguradoReemplazante);
    }

    @And("en la pagina Reemplazar Planilla SCTR Mineria realiza el reemplazo de planilla")
    public void enLaPaginaReemplazarPlanillaSCTRMineriaRealizaElReemplazoDePlanilla() {
        reemplazarPlanillaSctrMineriaPage.proccessAndAssertSuccessReemplazarPlanillaSctrMineria(60_000, 500);
        ExtentEvidence.shot("Evidencia despues hacer click en el boton Reemplazar Planilla SCTR General");
        reemplazarPlanillaSctrMineriaPage.proccessAndAssertSuccessReemplazarModalSctrMineria(90_000, 500);
        ExtentEvidence.shot("Evidencia despues de confirmar el reemplazo de planilla SCTR General");
    }

    @Then("en la pagina Constancias SCTR Mineria se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO")
    public void enLaPaginaConstanciasSCTRMineriaSeMuestraElAseguradoReemplazanteConTipoMovimientoREEMPLAZOYEstadoACTIVO() {
        constanciasSctrMineriaPage.assertLoadedSctrMineria();
        constanciasSctrMineriaPage.validarVisualizacionTablaAseguradosSctrMineria();
        String numeroDocumentoAseguradoReemplazante = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE);
        constanciasSctrMineriaPage.validarAseguradoReemplazantePorNumeroDeDocumentoSctrMineria(numeroDocumentoAseguradoReemplazante);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazante+" reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO");
    }

    @And("el asegurado reemplazado SCTR Mineria con estado INACTIVO")
    public void elAseguradoReemplazadoSCTRMineriaConEstadoINACTIVO() {
        String numeroDocumentoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO);
        constanciasSctrMineriaPage.validarAseguradoReemplazadoConEstadoInactivoSctrMineria(numeroDocumentoAseguradoReemplazado);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazado+" reemplazado y estado INACTIVO");
    }
}
