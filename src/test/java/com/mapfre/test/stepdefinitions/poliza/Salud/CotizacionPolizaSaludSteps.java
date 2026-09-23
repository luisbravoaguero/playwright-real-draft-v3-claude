package com.mapfre.test.stepdefinitions.poliza.Salud;

import com.mapfre.playwright.pageobjects.poliza.PolizaPage;
import com.mapfre.playwright.pageobjects.poliza.salud.MenuPolizaSaludPage;
import com.mapfre.playwright.pageobjects.poliza.salud.CotizacionPolizaSaludPage;


import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;


public class CotizacionPolizaSaludSteps {
    private final MenuPolizaSaludPage menuPolizaSaludPage;
    private final PolizaPage polizaPage;
    private final CotizacionPolizaSaludPage cotizacionPolizaSaludPage;


    public CotizacionPolizaSaludSteps(PageProvider pageProvider) {
        this.menuPolizaSaludPage =  new MenuPolizaSaludPage(pageProvider.get());
        this.polizaPage = new PolizaPage(pageProvider.get());
        this.cotizacionPolizaSaludPage = new CotizacionPolizaSaludPage(pageProvider.get());

            }

    @And("en la pagina Polizas selecciona la opcion Salud")
    public void enLaPaginaPolizasSeleccionaLaOpcionSalud() {
        polizaPage.clickSaludLink();
    }

    @And("en la pagina salud se debe seleccionar la opcion cotizar poliza de salud")
    public void enLaPaginaSaludSeDebeSeleccionarLaOpcionCotizarPolizaDeSalud() {
        menuPolizaSaludPage.assertLoadedPolizaSaludMenu();
        menuPolizaSaludPage.seleccionaBotonCotizacion();
    }

    @And("en la pagina cotizacion poliza salud se ingresa los campos producto {string} , agente {string}")
    public void enLaPaginaCotizacionPolizaSaludSeIngresaLosCamposProductoAgente(String producto, String agente) {
        cotizacionPolizaSaludPage.completarSeccionProducto(producto,agente);

    }

    @And("en la pagina cotizacion poliza salud se agrega un asegurado")
    public void enLaPaginaCotizacionPolizaSaludSeAgregaUnAsegurado() {
        cotizacionPolizaSaludPage.agregarAsegurado();


    }

    @And("en la pagina cotizacion poliza salud se agrega los datos tipo de documento {string}, numero documento {string}, nombres {string}, apellido paterno{string}, apellido materno {string}, fecha de nacimiento {string},tipo {string}, sexo {string}")
    public void enLaPaginaCotizacionPolizaSaludSeAgregaLosDatosTipoDeDocumentoNumeroDocumentoNombresApellidoPaternoApellidoMaternoFechaDeNacimientoTipoSexo(
            String tipoDocumento,
            String numeroDocumento,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String fechaNacimiento,
            String tipo,
            String sexo) {

        cotizacionPolizaSaludPage.completarDatosDocumento(
                tipoDocumento,
                numeroDocumento,
                nombres,
                apellidoPaterno,
                apellidoMaterno,
                fechaNacimiento,
                tipo,
                sexo);
        cotizacionPolizaSaludPage.confirmarAgregarAsegurado();
        cotizacionPolizaSaludPage.cotizarPoliza();



    }



    @Then("en la pagina cotizacion poliza salud se genera la cotizacion")
    public void enLaPaginaCotizacionPolizaSaludSeGeneraLaCotizacion() {
        cotizacionPolizaSaludPage.validarCotizacionGenerada();

    }


}
