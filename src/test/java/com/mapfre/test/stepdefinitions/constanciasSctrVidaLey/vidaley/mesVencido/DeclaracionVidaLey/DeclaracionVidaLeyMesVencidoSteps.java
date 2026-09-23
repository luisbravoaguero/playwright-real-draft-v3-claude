package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.vidaley.mesVencido.DeclaracionVidaLey;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.CargaIndividualDeclaracionVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.ClientesVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.PerfilClienteVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.DeclaracionVidaLeyPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido.ReporteConstanciaDeclaracionVidaLeyPage;


import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import com.mapfre.utils.testdata.RandomData;
import io.cucumber.java.an.E;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class DeclaracionVidaLeyMesVencidoSteps {
    private final ClientesVidaLeyPage clientesVidaLeyPage;
    private final PerfilClienteVidaLeyPage perfilClienteVidaLeyPage;
    private final DeclaracionVidaLeyPage declaracionVidaLeyPage;
    private final ReporteConstanciaDeclaracionVidaLeyPage reporteConstanciaDeclaracionVidaLeyPage;
    private final CargaIndividualDeclaracionVidaLeyPage cargaIndividualDeclaracionVidaLeyPage;

    public DeclaracionVidaLeyMesVencidoSteps(PageProvider pageProvider) {
        this.clientesVidaLeyPage = new ClientesVidaLeyPage(pageProvider.get());
        this.perfilClienteVidaLeyPage = new PerfilClienteVidaLeyPage(pageProvider.get());
        this.declaracionVidaLeyPage= new DeclaracionVidaLeyPage(pageProvider.get());
        this.cargaIndividualDeclaracionVidaLeyPage = new CargaIndividualDeclaracionVidaLeyPage(pageProvider.get());
        this.reporteConstanciaDeclaracionVidaLeyPage= new ReporteConstanciaDeclaracionVidaLeyPage(pageProvider.get());
    }
    @And("en la pagina Clientes seleccionamos el numero de poliza {string} vidaley")
    public void enLaPaginaClientesSeleccionamosElNumeroDePolizaVidaley(String numero_poliza) {
        clientesVidaLeyPage.assertLoadedVidaLey();
        clientesVidaLeyPage.fillFormularioClientesVidaLey(numero_poliza);
        clientesVidaLeyPage.processAndAssertSuccessBusquedaNumeroPolizaVidaLey(60_000, 500);
        clientesVidaLeyPage.processAndAssertSuccessSeleccionClienteVidaLey(60_000, 500);
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar vidaley mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararVidaleyMesVencido() {
        perfilClienteVidaLeyPage.assertLoadedVidaLey();
        perfilClienteVidaLeyPage.abrirPanelDeAplicacionesVidaLey();
        perfilClienteVidaLeyPage.seleccionarAplicacionConEstadoNoDeclaradaParaDeclararMesVencidoVidaLey();
        perfilClienteVidaLeyPage.seleccionarDeclararAplicacionButtonVidaLey();
        perfilClienteVidaLeyPage.assertLoadedVidaLeyv2();
    }
    @And("en la pagina Perfil Cliente seleccionamos el boton Declarar")
    public void seleccionarBotonDeclarar() {

        perfilClienteVidaLeyPage.seleccionarBotonDeclarar();
    }


    @And("en la pagina Declaracion completamos el formulario Datos de los asegurados vidaley mes vencido")
    public void enLaPaginaDeclaracionCompletamosElFormularioDatosDeLosAseguradosVidaleyMesVencido() {
        cargaIndividualDeclaracionVidaLeyPage.assertLoaded();
        cargaIndividualDeclaracionVidaLeyPage.seleccionarCargaIndividual();

        String numeroDocumento = RandomData.idDocumentPeru(8, 8);
        String nombreCompleto = "Prueba Declaracion Vida Ley";

        cargaIndividualDeclaracionVidaLeyPage.fillAseguradosCargaIndividualVidaLey(
                numeroDocumento,
                nombreCompleto);

        cargaIndividualDeclaracionVidaLeyPage.seleccionarLugarExposicionVidaLey();
        cargaIndividualDeclaracionVidaLeyPage.seleccionarSexoVidaLey();

    }

    @And("en la pagina Declaracion completamos los datos del asegurado")
    public void enLaPaginaDeclaracionCompletamoslosdatosdelasegurado() {
        cargaIndividualDeclaracionVidaLeyPage.assertLoaded();
        cargaIndividualDeclaracionVidaLeyPage.seleccionarCargaIndividual();

        String numeroDocumento = RandomData.idDocumentPeru(8, 8);
        String nombreCompleto = "Prueba Declaracion Vida Ley";
        cargaIndividualDeclaracionVidaLeyPage.fillAseguradosCargaIndividualVidaLey(
                numeroDocumento,
                nombreCompleto);
        cargaIndividualDeclaracionVidaLeyPage.seleccionarLugarExposicionVidaLey();
        cargaIndividualDeclaracionVidaLeyPage.seleccionarSexoVidaLey();
        ExtentEvidence.shot("Evidencia despues de completar los datos del asegurado en el formulario Datos de los asegurados");

        declaracionVidaLeyPage.seleccionarProcesar();
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton Procesar");
        declaracionVidaLeyPage.validarPopupListaObservaciones();
        ExtentEvidence.shot("Evidencia despues de validar el popup Lista de observaciones");
        declaracionVidaLeyPage.aceptarPopupListaObservaciones();
        ExtentEvidence.shot("Evidencia despues de aceptar el popup Lista de observaciones");
        declaracionVidaLeyPage.seleccionarSiguiente();
    }



    @And("en la pagina Declaracioon agregamos los asegurados que faltan en la lista de personas")
    public void enLaPaginaDeclaracionAgregamosLosAseguradosQueFaltanEnLaListaDePersonas() {
        declaracionVidaLeyPage.agregarFila();
        declaracionVidaLeyPage.agregarAseguradosFaltantes();
    }




    @Then("en la pagina Declaracion se muestra Detalle de los Riesgos")
    public void validarDetalleRiesgos() {

        declaracionVidaLeyPage.validarDetalleRiesgos();
    }

    @When("en la pagina Declaracion generamos la declaracion")
    public void seleccionarGenerar() {

        declaracionVidaLeyPage.seleccionarGenerar();
        ExtentEvidence.shot("Evidencia despues de seleccionar el boton Generar");
        declaracionVidaLeyPage.validarPopupGenerarDeclaracion();
        ExtentEvidence.shot("Evidencia despues de validar el popup Generar declaración");
        declaracionVidaLeyPage.confirmarGeneracionDeclaracion();
        ExtentEvidence.shot("Evidencia despues de confirmar la generacion de la declaracion");
        declaracionVidaLeyPage.validarPopupGeneracionExitosa();
        ExtentEvidence.shot("Evidencia despues de validar el popup Se genero la declaracion con éxito");
        declaracionVidaLeyPage.aceptarPopupGeneracionExitosa();
    }


    @Then("en la pagina Declaracion se muestra el Reporte constancia")
    public void validarReporteConstancia() {

        reporteConstanciaDeclaracionVidaLeyPage.assertLoaded();
        reporteConstanciaDeclaracionVidaLeyPage.processAndAssertSuccessProcesarDeclaracion(120_000, 500);

        reporteConstanciaDeclaracionVidaLeyPage.validarReporteGenerado();
    }

    @And("en la pagina Declaracion se muestran los documentos asociados a la constancia")
    public void validarDocumentosAsociados() {

        reporteConstanciaDeclaracionVidaLeyPage.validarDocumentosAsociados();
    }

    @And("en la pagina Declaracion se descarga el recibo")
    public void descargarConstancia() {

        reporteConstanciaDeclaracionVidaLeyPage.descargarCertificado();
    }
}