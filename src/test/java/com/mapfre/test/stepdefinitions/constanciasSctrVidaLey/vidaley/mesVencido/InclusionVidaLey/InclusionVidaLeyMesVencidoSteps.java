package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley.mesVencido.InclusionVidaLey;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.CargaIndividualInclusionVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.PerfilClienteVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.InclusionPlanillaVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.ReporteConstanciaInclusionVidaLeyPage;

import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InclusionVidaLeyMesVencidoSteps {

    private final PerfilClienteVidaLeyPage perfilClienteVidaLeyPage;
    private final InclusionPlanillaVidaLeyPage inclusionPlanillaVidaLeyPage;
    private final CargaIndividualInclusionVidaLeyPage cargaIndividualInclusionVidaLeyPage;
    private final ReporteConstanciaInclusionVidaLeyPage reporteConstanciaInclusionVidaLeyPage;

    public InclusionVidaLeyMesVencidoSteps(PageProvider pageProvider) {
        this.perfilClienteVidaLeyPage = new PerfilClienteVidaLeyPage(pageProvider.get());
        this.inclusionPlanillaVidaLeyPage = new InclusionPlanillaVidaLeyPage(pageProvider.get());
        this.cargaIndividualInclusionVidaLeyPage = new CargaIndividualInclusionVidaLeyPage(pageProvider.get());
        this.reporteConstanciaInclusionVidaLeyPage = new ReporteConstanciaInclusionVidaLeyPage(pageProvider.get());

    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para incluir vidaley mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaIncluirVidaleyMesVencido() {
        perfilClienteVidaLeyPage.assertLoadedVidaLey();
        perfilClienteVidaLeyPage.abrirPanelDeAplicacionesVidaLey();
        perfilClienteVidaLeyPage.seleccionarAplicacionConEstadoNoDeclaradaParaIncluirMesVencidoVidaLey();
        perfilClienteVidaLeyPage.assertTipoDeclaracionMesVencido();
        perfilClienteVidaLeyPage.seleccionarBotonIncluir();
    }


    @And("en la pagina Incluir Planilla completamos los datos del asegurado")
    public void enLaPaginaIncluirPlanillaCompletamoslosdatosdelasegurados() {
        cargaIndividualInclusionVidaLeyPage.assertLoaded();
        String numeroDocumento = RandomData.idDocumentPeru(8,8);
        cargaIndividualInclusionVidaLeyPage.fillAseguradosCargaIndividualVidaLey(numeroDocumento);
        cargaIndividualInclusionVidaLeyPage.seleccionarLugarExposicionVidaLey();
        cargaIndividualInclusionVidaLeyPage.seleccionarSexoVidaLey();
        ExtentEvidence.shot("Evidencia despues de llenar el formulario Datos de los asegurados");
        inclusionPlanillaVidaLeyPage.seleccionarBotonProcesar();
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton Procesar");
        inclusionPlanillaVidaLeyPage.validarPopupListaObservaciones();
        ExtentEvidence.shot("Evidencia despues de validar el popup Lista de observaciones");
        inclusionPlanillaVidaLeyPage.aceptarPopupListaObservaciones();
    }


    @And("en la pagina Inclusión de planilla seleccionamos el boton Generar")
    public void enLaPaginaInclusionDePlanillaSeleccionamosElBotonGenerar() {
        inclusionPlanillaVidaLeyPage.seleccionarBotonGenerar();
        inclusionPlanillaVidaLeyPage.validarPopupGenerarInclusion();
        ExtentEvidence.shot("Evidencia despues de validar el popup Generar inclusión");
        inclusionPlanillaVidaLeyPage.confirmarGenerarInclusion();
        inclusionPlanillaVidaLeyPage.validarPopupInclusionExitosa();
        ExtentEvidence.shot("Evidencia despues de validar el popup Se genero la inclusión con éxito");
        inclusionPlanillaVidaLeyPage.aceptarPopupInclusionExitosa();
    }


    @Then("en la pagina Inclusion de Planilla se muestra el Reporte constancia")
    public void enLaPaginaInclusionDePlanillaSeMuestraElReporteConstancia() {
        /* reporteConstanciaInclusionVidaLeyPage.assertLoaded();*/
        reporteConstanciaInclusionVidaLeyPage.processAndAssertSuccessProcesarInclusion(120_000, 500);
        reporteConstanciaInclusionVidaLeyPage.validarReporteGenerado();
    }

    @And("en la pagina Inclusion de Planilla se muestran los documentos asociados a la constancia")
    public void enLaPaginaInclusionDePlanillaSeMuestranLosDocumentosAsociadosALaConstancia() {
        reporteConstanciaInclusionVidaLeyPage.validarDocumentosAsociados();
    }
    @And("en la pagina Inclusion de Planilla se descarga la constancia")
    public void enLaPaginaInclusionDePlanillaSeDescargaLaConstancia() {
        reporteConstanciaInclusionVidaLeyPage.descargarConstancia();
    }

}
