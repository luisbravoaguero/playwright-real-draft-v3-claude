package com.mapfre.playwright.pageobjects.poliza.vidaIndividual;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CotizacionPolizaVidaIndividualPage extends BasePage {

    private final Locator lblTituloCotizacionPolizaVidaIndividual;

    // Contratante
    private final Locator cboTipoDocumentoContratante;
    private final Locator txtNumeroDocumentoContratante;
    private final Locator txtNombresContratante;
    private final Locator txtApellidoPaternoContratante;
    private final Locator txtApellidoMaternoContratante;
    private final Locator txtCorreoContratante;
    private final Locator btnCotizar;

    // Asegurado
    private final Locator chkMismoContratante;

    // Características del Seguro
    private final Locator cboProducto;
    private final Locator rdbSoles;
    private final Locator rdbDolares;

    private final Locator lblTituloCotizacionFondoUniversitario;
    private final Locator btnGuardarCotizacion;
    private final Locator mdlGuardarCotizacion;

    private final Locator lblTituloCotizacionGuardada;
    private final Locator lblNumeroCotizacion;

    private final Locator cboDuracionSeguro;
    private final Locator cboPorcentajeDevolucion;

    private final Locator txtSumaAsegurada;

    public CotizacionPolizaVidaIndividualPage(Page page) {
        super(page);

        this.lblTituloCotizacionPolizaVidaIndividual = page.locator("h1.g-page-title__title");

        // Contratante
        this.cboTipoDocumentoContratante = page.locator("person-vida[name='contratante'] oim-select[name='documentType'] select");
        this.txtNumeroDocumentoContratante = page.locator("person-vida[name='contratante'] input[name='documentNumber']");
        this.txtNombresContratante = page.locator("person-vida[name='contratante'] input[name='Nombre']");
        this.txtApellidoPaternoContratante = page.locator("person-vida[name='contratante'] input[name='ApellidoPaterno']");
        this.txtApellidoMaternoContratante = page.locator("person-vida[name='contratante'] input[name='ApellidoMaterno']");
        this.txtCorreoContratante = page.locator("person-vida[name='contratante'] input[name='CorreoElectronico']");

        // Asegurado
        this.chkMismoContratante = page.locator("oim-checkbox[name='IgualAsegurado'] label");

        // Características del Seguro
        this.cboProducto = page.locator("oim-select[name='nProducto'] select");
        this.rdbSoles = page.getByText("Soles", new Page.GetByTextOptions().setExact(true));
        this.rdbDolares = page.getByText("Dólares", new Page.GetByTextOptions().setExact(true));

        this.btnCotizar = page.locator("a[ng-reflect-ga-label='Botón: Cotizar']");

        this.lblTituloCotizacionFondoUniversitario = page.locator("h1.g-page-title__title");
        this.btnGuardarCotizacion = page.getByText("Guardar Cotizacion", new Page.GetByTextOptions().setExact(true));
        this.mdlGuardarCotizacion = page.locator("div.swal2-popup");

        this.lblTituloCotizacionGuardada = page.locator("h1");
        this.lblNumeroCotizacion = page.locator("div.col-12.mb-2:has-text('Nro. Cotización:')");

        this.cboDuracionSeguro = page.locator("oim-select[name='nDuracionSeguro'] select");
        this.cboPorcentajeDevolucion = page.locator("oim-select[name='nDevolucion'] select");

        this.txtSumaAsegurada = page.locator("input[name='MontoCobertura']");

    }

    public void validarPaginaCotizacionPolizaVidaIndividual() {

        log.info("Validando página Cotización póliza Vida Individual");
        UiSync.waitForAppIdle(page);
        assertThat(lblTituloCotizacionPolizaVidaIndividual).containsText("Cotización póliza Vida Individual");
        log.info("Página Cotización póliza Vida Individual validada correctamente");
    }

    public void ingresarDatosContratante(
            String tipoDocumento,
            String nroDocumento,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String correo) {

        log.info("Ingresando datos del contratante");
        cboTipoDocumentoContratante.selectOption(new SelectOption().setLabel(tipoDocumento));
        txtNumeroDocumentoContratante.fill(nroDocumento);
        txtNumeroDocumentoContratante.press("Tab");
        UiSync.waitForAppIdle(page);
        validarOCompletarDatosContratante(nombres, apellidoPaterno, apellidoMaterno, correo);
    }

    private void validarOCompletarDatosContratante(
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String correo) {

        log.info("Validando autocompletado de contratante");

        boolean autocompletado =
                !txtNombresContratante.inputValue().trim().isEmpty()
                        && !txtApellidoPaternoContratante.inputValue().trim().isEmpty()
                        && !txtApellidoMaternoContratante.inputValue().trim().isEmpty();

        if (autocompletado) {

            log.info("Contratante encontrado y autocompletado por el sistema");

            log.info("Nombre: {}", txtNombresContratante.inputValue());
            log.info("Apellido paterno: {}", txtApellidoPaternoContratante.inputValue());
            log.info("Apellido materno: {}", txtApellidoMaternoContratante.inputValue());
            log.info("Correo: {}", txtCorreoContratante.inputValue());

            if (txtCorreoContratante.inputValue().trim().isEmpty()) {
                txtCorreoContratante.fill(correo);
            }

            return;
        }

        log.info("Contratante no encontrado. Completando datos manualmente");

        txtNombresContratante.fill(nombres);
        txtApellidoPaternoContratante.fill(apellidoPaterno);
        txtApellidoMaternoContratante.fill(apellidoMaterno);
        txtCorreoContratante.fill(correo);

        log.info("Datos del contratante ingresados manualmente");
    }

    public void seleccionarContratante() {

        log.info("Seleccionando checkbox mismo contratante");
        chkMismoContratante.click();
        UiSync.waitForAppIdle(page);
        log.info("Checkbox seleccionado correctamente");
    }

    public void ingresarCaracteristicasSeguroFondoUniversitario(String producto) {

        log.info("Ingresando características del seguro");
        cboProducto.selectOption(producto);
        UiSync.waitForAppIdle(page);
        rdbSoles.click();
        rdbSoles.press("Tab");
        UiSync.waitForAppIdle(page);
        log.info("Producto Fondo Universitario y moneda Soles seleccionados correctamente");
    }

    public void ingresarCaracteristicasSeguroDevolucion(String producto, String duracion, String devolucion) {

        log.info("Ingresando características del seguro. Producto: {}, Duración: {}, Devolución: {}", producto, duracion,devolucion);
        cboProducto.selectOption(producto);
        UiSync.waitForAppIdle(page);
        rdbSoles.click();
        rdbSoles.press("Tab");
        UiSync.waitForAppIdle(page);
        cboDuracionSeguro.selectOption(duracion);
        UiSync.waitForAppIdle(page);
        cboPorcentajeDevolucion.selectOption(devolucion);
        UiSync.waitForAppIdle(page);
        log.info("Características del seguro ingresadas correctamente");
    }
    public void ingresarCoberturas(String sumaAsegurada) {

        log.info("Ingresando suma asegurada: {}", sumaAsegurada);
        txtSumaAsegurada.clear();
        txtSumaAsegurada.fill(sumaAsegurada);
        txtSumaAsegurada.press("Tab");
        UiSync.waitForAppIdle(page);
        log.info("Suma asegurada ingresada correctamente");
    }
    public void cotizar() {

            log.info("Realizando cotización");
            assertThat(btnCotizar).isVisible();
            btnCotizar.click();
            UiSync.waitForAppIdle(page);
            log.info("Cotización ejecutada correctamente");

    }
    public void validarResultadoCotizacion() {

        log.info("Validando pantalla resultado cotización Fondo Universitario");
        UiSync.waitForAppIdle(page);
        assertThat(lblTituloCotizacionFondoUniversitario).containsText("Cotización Fondo Universitario");
        assertThat(btnGuardarCotizacion).isVisible();
        log.info("Pantalla resultado cotización validada correctamente");

    }

    public void validarResultadoCotizacionDevolucionAhorro() {

        log.info("Validando pantalla resultado cotización Devolucion Ahorro");
        UiSync.waitForAppIdle(page);
        assertThat(lblTituloCotizacionFondoUniversitario).containsText("Cotización Vida Devolución Ahorro OC");
        assertThat(btnGuardarCotizacion).isVisible();
        log.info("Pantalla resultado cotización validada correctamente");

    }

    public void guardarCotizacion() {

        log.info("Guardando cotización");
        btnGuardarCotizacion.click();
        boolean modalAparecio = GestorModal.clickBotonSiExiste(
                mdlGuardarCotizacion,
                "GUARDAR");

        log.info("¿Modal apareció?: {}", modalAparecio);
        UiSync.waitForAppIdle(page);
        log.info("Cotización guardada correctamente");
    }

    public String ObtenerNumeroCotizacionGuardada() {

        log.info("Validando pantalla Cotización Guardada");
        UiSync.waitForAppIdle(page);
        assertThat(lblTituloCotizacionGuardada).containsText("Cotización Guardada");
        String textoNumeroCotizacion = lblNumeroCotizacion.textContent().trim();
        String numeroCotizacion =  textoNumeroCotizacion.replace("Nro. Cotización:", "").trim();
        log.info("Número de cotización generado: {}", numeroCotizacion);
        return numeroCotizacion;
    }


}