package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral.ConstanciasSctrPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.PerfilClientePage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.InclusionPlanillaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral.PerfilCienteInclusionPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.test.hooks.ScenarioContext;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.en.And;

public class InclusionSctrPensionSaludMesVencidoSteps {
    private final PerfilClientePage perfilClientePage;
    private final PerfilCienteInclusionPage perfilCienteInclusionPage;
    private final InclusionPlanillaPage inclusionPlanillaPage;
    private final ScenarioContext scenarioContext;
    public InclusionSctrPensionSaludMesVencidoSteps(PageProvider pageProvider, ScenarioContext scenarioContext){
        this.perfilClientePage = new PerfilClientePage(pageProvider.get());
        this.perfilCienteInclusionPage = new PerfilCienteInclusionPage(pageProvider.get());
        this.inclusionPlanillaPage = new InclusionPlanillaPage(pageProvider.get());
        this.scenarioContext = scenarioContext;
    }
    @And("en la pagina Perfil Cliente seleccionamos una aplicacion realizar una inclusion mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionRealizarUnaInclusionMesVencido() {
        perfilClientePage.assertLoaded();
        //perfilClientePage.seleccionarAplicacionConEstadoNoDeclaradoParaInclusionDeMesVencido();
        perfilClientePage.buscarAplicacionConEstadoNoDeclaradoParaInclusionDeMesVencido();

        ExtentEvidence.shot("Evidencia despues de seleccionar una aplicacion con estado no declarado");
        perfilCienteInclusionPage.seleccionarIncluirAplicacionButton();
        //perfilClientePage.processAndAssertSuccessMultiplesRecibosPendientes(30_000,500);
    }

    @And("en la pagina Incluir Planilla completamos el formulario Datos de los asegurados mes vencido")
    public void enLaPaginaIncluirPlanillaCompletamosElFormularioDatosDeLosAseguradosMesVencido() {
        inclusionPlanillaPage.assertLoaded();
        // Completa el formulario con los detalles de la póliza
        //->inclusionPlanillaPage.fillFormDetallesPolizas();
        // Toma una captura de pantalla como evidencia después de completar detalles de póliza
        //->ExtentEvidence.shot("Evidencia despues de completar el formulario detalles de poliza");
        // Genera nueve dígitos aleatorios para el documento de identidad del Perú
        String numeroDocumento = RandomData.idDocumentPeru(9,8);
        // Completa el formulario de carga individual de asegurados con el número de documento generado
        inclusionPlanillaPage.fillAseguradosCargaIndividual(numeroDocumento);
        // Selecciona la opción "No Facturada" para la inclusión
        //->inclusionPlanillaPage.seleccionarFacturadaSctr();
        // Hace clic en el botón "Siguiente" de Angular para avanzar al siguiente paso
        inclusionPlanillaPage.clickGenerarLink();
        // Procesa y valida la aparición del modal de lista de observaciones
        inclusionPlanillaPage.processAndAssertSuccessGenerarInclusion(60_000,500);
        // Toma una captura de pantalla como evidencia del modal de lista de observaciones
        //->ExtentEvidence.shot("Evidencia despues del modal Lista de Observaciones");
        // Hace clic en el botón "Aceptar" del modal de lista de observaciones
        //->inclusionPlanillaPage.clickAceptarListaObservacionesButton();
        // Procesa y valida la aparición del botón siguiente
        //->inclusionPlanillaPage.processAndAssertSuccessBotonSiguiente(40_000,500);
        inclusionPlanillaPage.processAndAssertSuccessListaDeObservacionesInclusion(60_000,500);
        inclusionPlanillaPage.processAndAssertSuccessGeneraInclusionModal(60_000,500);
    }
}
