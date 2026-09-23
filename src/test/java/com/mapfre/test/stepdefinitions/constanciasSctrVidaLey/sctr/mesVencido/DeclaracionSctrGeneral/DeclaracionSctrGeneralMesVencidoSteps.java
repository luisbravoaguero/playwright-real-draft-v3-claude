package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr.mesVencido.DeclaracionSctrGeneral;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.DeclaracionCargaAseguradoPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral.DeclaracionReporteConstacia;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.ClientesSctrGeneralPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.PerfilClienteSctrGeneralPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DeclaracionSctrGeneralMesVencidoSteps {
    private final ClientesSctrGeneralPage clientesSctrGeneralPage;
    private final PerfilClienteSctrGeneralPage perfilClienteSctrGeneralPage;
    private final DeclaracionCargaAseguradoPage declaracionCargaAseguradoPage;
    private final DeclaracionReporteConstacia declaracionReporteConstacia;

    public DeclaracionSctrGeneralMesVencidoSteps(PageProvider pageProvider) {
        this.clientesSctrGeneralPage = new ClientesSctrGeneralPage(pageProvider.get());
        this.perfilClienteSctrGeneralPage = new PerfilClienteSctrGeneralPage(pageProvider.get());
        this.declaracionCargaAseguradoPage = new DeclaracionCargaAseguradoPage(pageProvider.get());
        this.declaracionReporteConstacia = new DeclaracionReporteConstacia(pageProvider.get());
    }
    @And("en la pagina Clientes seleccionamos el numero de poliza {string} sctr general")
    public void enLaPaginaClientesSeleccionamosElNumeroDePolizaSctrGeneral(String numero_poliza) {
        clientesSctrGeneralPage.assertLoadedSctrGeneral();
        clientesSctrGeneralPage.fillFormularioClientesSctrGeneral(numero_poliza);
        clientesSctrGeneralPage.processAndAssertSuccessBusquedaNumeroPolizaSctrGeneral(60_000, 500);
        clientesSctrGeneralPage.processAndAssertSuccessSeleccionClienteSctrGeneral(60_000, 500);
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar sctr general mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararSctrGeneralMesVencido() {
        perfilClienteSctrGeneralPage.assertLoadedSctrGeneral();
        perfilClienteSctrGeneralPage.abrirPanelDeAplicacionesSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarAplicacionConEstadoNoDeclaradaParaDeclararMesVencidoSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarDeclararAplicacionButtonSctrGeneral();
        perfilClienteSctrGeneralPage.aceptarModalAlertaDeclaracionSiExiste();
        ExtentEvidence.shot("prueba");
    }

    @And("en la pagina Perfil Cliente seleccionamos la aplicacion {string} para declarar sctr general mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosLaAplicacionParaDeclararSctrGeneralMesVencido(String numeroAplicacion) {
        perfilClienteSctrGeneralPage.assertLoadedSctrGeneral();
        perfilClienteSctrGeneralPage.abrirPanelDeAplicacionesSctrGeneral();
        perfilClienteSctrGeneralPage.seleccionarAplicacionParaDeclararSctrGeneralMesVencido(numeroAplicacion);
        perfilClienteSctrGeneralPage.seleccionarDeclararAplicacionButtonSctrGeneral();
        perfilClienteSctrGeneralPage.aceptarModalAlertaDeclaracionSiExiste();
        ExtentEvidence.shot("prueba");
    }

    @And("en la pagina Perfil Cliente seccion Lista de Polizas declaramos la aplicacion seleccionada")
    public void enLaPaginaPerfilClienteSeccionListaDePolizasDeclaramosLaAplicacionSeleccionada() {
        declaracionCargaAseguradoPage.ingresarDatosDeclaracionMesVencido();
        declaracionCargaAseguradoPage.clickBtnCargaIndividual();
        declaracionCargaAseguradoPage.fillCargaIndividualDeclaracionMesVencido();
        declaracionCargaAseguradoPage.clickBtnSiguienteDeclaracionMesVencido();


    }
    @And("en la pagina Declaracion de planilla realizamos la carga individual para la generacion")
    public void enLaPaginaDeclaracionDePlanillaRealizamosLaCargaIndividualParaLaGeneracion() {
        declaracionCargaAseguradoPage.aceptarListaObservaciones();
        declaracionCargaAseguradoPage.clickBtnGenerar();
        declaracionCargaAseguradoPage.clickBtnConfirmarGenerarDeclaracion();
        declaracionCargaAseguradoPage.clickBtnOkDeclaracionGenerada();
    }

    @Then("en la pagina Declaracion de planilla se muestra el reporte constancia")
    public void enLaPaginaDeclaracionDePlanillaSeMuestraElReporteConstancia() {
        declaracionReporteConstacia.assertLoaded();

    }
}
