package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.ConstanciasVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.ReemplazarPlanillaVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.PerfilClienteVidaLeyPage;
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

public class ConstanciaReemplazoVidaLeySteps {
    private final PerfilClienteVidaLeyPage perfilClienteVidaLeyPage;
    private final ConstanciasVidaLeyPage constanciasVidaLeyPage;
    private final ReemplazarPlanillaVidaLeyPage reemplazarPlanillaVidaLeyPage;
    private final ScenarioContext scenarioContext;
    public ConstanciaReemplazoVidaLeySteps(PageProvider pageProvider, ScenarioContext scenarioContext) {
        this.perfilClienteVidaLeyPage = new PerfilClienteVidaLeyPage(pageProvider.get());
        this.constanciasVidaLeyPage = new ConstanciasVidaLeyPage(pageProvider.get());
        this.reemplazarPlanillaVidaLeyPage = new ReemplazarPlanillaVidaLeyPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Cliente Vida Ley selecciona una aplicacion con estado declarada para reemplazar")
    public void enLaPaginaPerfilClienteVidaLeySeleccionaUnaAplicacionConEstadoDeclaradaParaReemplazar() {
        perfilClienteVidaLeyPage.assertLoadedVidaLey();
        perfilClienteVidaLeyPage.abrirPanelDeAplicacionesVidaLey();
        perfilClienteVidaLeyPage.seleccionarPrimeraAplicacionConEstadoDeclaradaConReciboParaConstanciaReemplazoVidaLey();
        perfilClienteVidaLeyPage.seleccionarPlanillaAplicacionButtonVidaLey();
    }

    @And("en la pagina Constancias Vida Ley selecciona un registro de la tabla de asegurados para reemplazar")
    public void enLaPaginaConstanciasVidaLeySeleccionaUnRegistroDeLaTablaDeAseguradosParaReemplazar() {
        constanciasVidaLeyPage.proccessAndAssertSuccessBotonPlanillaVidaLey(60_000, 500);
        constanciasVidaLeyPage.validarVisualizacionTablaAseguradosVidaLey();
        String numeroDocumentoActivo = constanciasVidaLeyPage.obtenerPrimerDocumentoActivoUnicoVidaLey();
        constanciasVidaLeyPage.seleccionarAseguradoActivoParaReemplazoVidaLey(numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de seleccionar asegurado ACTIVO para reemplazo");
        String montoSueldoAseguradoReemplazado = constanciasVidaLeyPage.obtenerSueldoAseguradoActivoPorNumeroDocumentoVidaLey(numeroDocumentoActivo);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO, montoSueldoAseguradoReemplazado);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO, numeroDocumentoActivo);
        ExtentEvidence.shot("Evidencia despues de obtener el sueldo del asegurado activo: " + montoSueldoAseguradoReemplazado);
        constanciasVidaLeyPage.proccessAndAssertSuccessReemplazarPlanillaVidaLey(60_000, 500);
    }

    @And("en la pagina Reemplazar Planilla Vida Ley carga la planilla del asegurado a reemplazar")
    public void enLaPaginaReemplazarPlanillaVidaLeyCargaLaPlanillaDelAseguradoAReemplazar() {
        DocumentoAsignado documento = DocumentosPrueba.asignar(ConsumidorDocumento.REEMPLAZO_VIDA_LEY);
        ReportLogger.info("Asegurado reemplazante: " + documento.descripcion());
        String numeroDocumentoAseguradoReemplazante = documento.valor();
        String nombreArchivo = "File_constancia_reemplazo_vidaley";
        String montoSueldoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.MONTO_SUELDO_ASEGURADO_REEMPLAZADO);
        scenarioContext.put(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE, numeroDocumentoAseguradoReemplazante);

        Path template = TemplateFactory.buildPlanillaConstanciaReemplazo(numeroDocumentoAseguradoReemplazante, nombreArchivo, montoSueldoAseguradoReemplazado);
        reemplazarPlanillaVidaLeyPage.fillFormDatosDeLosAseguradosVidaLey(template,nombreArchivo);
        ExtentEvidence.shot("Evidencia despues de completar cargar el asegurado "+numeroDocumentoAseguradoReemplazante+", con sueldo de "+montoSueldoAseguradoReemplazado);

        reemplazarPlanillaVidaLeyPage.clickMostrarPlanillaButtonVidaLey();
        reemplazarPlanillaVidaLeyPage.proccessAndAssertSuccessMostrarPlanillaVidaLey(180_000, 500);
        reemplazarPlanillaVidaLeyPage.validarVisibilidadMensajeCargaAseguradosExitosaVidaLey();
        //reemplazarPlanillaVidaLeyPage.validarListaPlanillaReemplazoContenedor(numeroDocumentoAseguradoReemplazante);
    }

    @And("en la pagina Reemplazar Planilla Vida Ley realiza el reemplazo de planilla")
    public void enLaPaginaReemplazarPlanillaVidaLeyRealizaElReemplazoDePlanilla() {
        reemplazarPlanillaVidaLeyPage.proccessAndAssertSuccessReemplazarPlanillaVidaLey(60_000, 500);
        ExtentEvidence.shot("Evidencia despues hacer click en el boton Reemplazar Planilla SCTR General");
        reemplazarPlanillaVidaLeyPage.proccessAndAssertSuccessReemplazarModalVidaLey(90_000, 500);
        ExtentEvidence.shot("Evidencia despues de confirmar el reemplazo de planilla SCTR General");
    }

    @Then("en la pagina Constancias Vida Ley se muestra el asegurado reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO")
    public void enLaPaginaConstanciasVidaLeySeMuestraElAseguradoReemplazanteConTipoMovimientoREEMPLAZOYEstadoACTIVO() {
        constanciasVidaLeyPage.assertLoadedVidaLey();
        constanciasVidaLeyPage.validarVisualizacionTablaAseguradosVidaLey();
        String numeroDocumentoAseguradoReemplazante = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO_REEMPLAZANTE);
        constanciasVidaLeyPage.validarAseguradoReemplazantePorNumeroDeDocumentoVidaLey(numeroDocumentoAseguradoReemplazante);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazante+" reemplazante con tipo movimiento REEMPLAZO y estado ACTIVO");
    }

    @And("el asegurado reemplazado Vida Ley con estado INACTIVO")
    public void elAseguradoReemplazadoVidaLeyConEstadoINACTIVO() {
        String numeroDocumentoAseguradoReemplazado = scenarioContext.getString(ScenarioKeys.ConstanciasSctrVidaLey.NUMERO_DOCUMENTO_ASEGURADO);
        constanciasVidaLeyPage.validarAseguradoReemplazadoConEstadoInactivoVidaLey(numeroDocumentoAseguradoReemplazado);
        ExtentEvidence.shot("Evidencia despues de validar el asegurado "+numeroDocumentoAseguradoReemplazado+" reemplazado y estado INACTIVO");
    }
}
