package com.mapfre.playwright.pageobjects.poliza.salud;

import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.mapfre.utils.UiSync;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CotizacionPolizaSaludPage extends BasePage {

    private final Locator cboProducto;
    private final Locator txtAgente;
    private final Locator btnAgregarAsegurado;
    private final Locator cboTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtNombres;
    private final Locator txtApellidoPaterno;
    private final Locator txtApellidoMaterno;
    private final Locator cboDia;
    private final Locator cboMes;
    private final Locator cboAnio;
    private final Locator cboTipo;
    private final Locator rbMasculino;
    private final Locator rbFemenino;
    private final Locator btnAgregarAseguradoModal;
    private final Locator cboFormaPago;
    private final Locator btnCotizar;
    private final Locator lblCotizacionGuardada;
    private final Locator lblNumeroCotizacion;



    public CotizacionPolizaSaludPage(Page page) {
        super(page);
        this.cboProducto = page.locator("#mat-input-4");
        this.txtAgente = page.locator("input[role='combobox'][aria-autocomplete='list']");
        this.btnAgregarAsegurado = page.getByText("Agregar asegurado",new Page.GetByTextOptions().setExact(true));

        this.cboTipoDocumento = page.locator("#mat-input-11");
        this.txtNumeroDocumento = page.locator("input[name='documentNumber']");
        this.txtNombres = page.locator("input[name='Nombre']");
        this.txtApellidoPaterno = page.locator("input[name='ApellidoPaterno']");
        this.txtApellidoMaterno = page.locator("input[name='ApellidoMaterno']");
        this.cboDia = page.locator("oim-select[name='day'] select");
        this.cboMes = page.locator("oim-select[name='month'] select");
        this.cboAnio = page.locator("oim-select[name='year'] select");
        this.cboTipo = page.locator("oim-select[name='tipoPersona'] select");
        this.rbMasculino = page.locator("input[type='radio'][value='1']");
        this.rbFemenino = page.locator("input[type='radio'][value='0']");
        this.btnAgregarAseguradoModal = page.getByText("AGREGAR ASEGURADO", new Page.GetByTextOptions().setExact(true));
        this.cboFormaPago = page.locator("oim-select[label='Financiamiento'] select");
        this.btnCotizar = page.locator("button:has-text('cotizar')");
        this.lblCotizacionGuardada =  page.getByText("Cotización Guardada");
        this.lblNumeroCotizacion = page.locator("div.g-myd-title:has-text('Nro. Cotización')").locator("b");

    }

    public void completarSeccionProducto(String producto, String agente) {

        assertThat(cboProducto).isVisible();
        assertThat(txtAgente).isVisible();

        cboProducto.selectOption(new SelectOption().setLabel(producto));
        txtAgente.fill(agente);
    }
    public void agregarAsegurado() {

        assertThat(btnAgregarAsegurado).isVisible();
        btnAgregarAsegurado.click();
        UiSync.waitForAppIdle(page);
    }

    public void completarDatosDocumento(
            String tipoDocumento,
            String numeroDocumento,
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String fechaNacimiento,
            String tipo,
            String sexo) {

        log.info("=== INICIO AGREGAR ASEGURADO ===");

        assertThat(cboTipoDocumento).isVisible();
        assertThat(txtNumeroDocumento).isVisible();

        log.info("Seleccionando tipo documento: {}", tipoDocumento);

        cboTipoDocumento.selectOption(new SelectOption().setLabel(tipoDocumento));
        log.info("Ingresando número documento: {}", numeroDocumento);
        txtNumeroDocumento.fill(numeroDocumento);
        log.info("Perdiendo foco para disparar autocompletado");
        txtNumeroDocumento.press("Tab");
        log.info("Esperando respuesta de autocompletado");
        UiSync.waitForAppIdle(page);

        boolean autocompleto = false;

        try {

            assertThat(txtNombres).hasValue(nombres);
            assertThat(txtApellidoPaterno).hasValue(apellidoPaterno);
            assertThat(txtApellidoMaterno).hasValue(apellidoMaterno);

            autocompleto = true;

        } catch (AssertionError e) {

            autocompleto = false;
        }

        log.info("¿Autocompletó información?: {}", autocompleto);

        if (autocompleto) {

            log.info("Datos autocompletados correctamente");
            log.info("Validando nombres: {}", nombres);
            assertThat(txtNombres).hasValue(nombres);

            log.info("Validando apellido paterno: {}", apellidoPaterno);
            assertThat(txtApellidoPaterno).hasValue(apellidoPaterno);

            log.info("Validando apellido materno: {}", apellidoMaterno);
            assertThat(txtApellidoMaterno).hasValue(apellidoMaterno);

            log.info("Validando sexo: {}", sexo);

            if ("Masculino".equalsIgnoreCase(sexo)) {
                assertThat(rbMasculino).isChecked();
            } else {
                assertThat(rbFemenino).isChecked();
            }

        } else {

            log.info("No se obtuvo autocompletado. Iniciando carga manual");

            if (txtNombres.isEnabled()) {
                log.info("Ingresando nombres: {}", nombres);
                txtNombres.fill(nombres);
            }

            if (txtApellidoPaterno.isEnabled()) {
                log.info("Ingresando apellido paterno: {}", apellidoPaterno);
                txtApellidoPaterno.fill(apellidoPaterno);
            }

            if (txtApellidoMaterno.isEnabled()) {
                log.info("Ingresando apellido materno: {}", apellidoMaterno);
                txtApellidoMaterno.fill(apellidoMaterno);
            }

            String[] fecha = fechaNacimiento.split("/");
            log.info("Seleccionando fecha nacimiento: {}", fechaNacimiento);

            cboDia.selectOption(new SelectOption().setLabel(fecha[0]));
            cboMes.selectOption(new SelectOption().setLabel(fecha[1]));
            cboAnio.selectOption(new SelectOption().setLabel(fecha[2]));
        }

        log.info("Seleccionando tipo de asegurado: {}", tipo);
        cboTipo.selectOption(new SelectOption().setLabel(tipo));

        log.info("=== FIN AGREGAR ASEGURADO ===");
        UiSync.waitForAppIdle(page);
    }

    public void confirmarAgregarAsegurado() {

        log.info("Confirmando agregado de asegurado");
        assertThat(btnAgregarAseguradoModal).isVisible();
        btnAgregarAseguradoModal.click();
        log.info("Esperando procesamiento de asegurado");
        UiSync.waitForAppIdle(page);
        log.info("Asegurado agregado correctamente");
    }

    public void cotizarPoliza() {

        log.info("=== INICIO COTIZACION ===");
        log.info("Seleccionando forma de pago: ,  Pago al Contado ");
        cboFormaPago.selectOption(new SelectOption().setLabel("Pago al Contado"));

        log.info("Esperando estabilización de la pantalla");
        UiSync.waitForAppIdle(page);

        log.info("Haciendo clic en botón Cotizar");
        assertThat(btnCotizar).isVisible();

        btnCotizar.click();
        log.info("Esperando generación de cotización");
        UiSync.waitForAppIdle(page);
        log.info("=== FIN COTIZACION ===");
    }

    public void validarCotizacionGenerada() {

        log.info("Validando pantalla de cotización generada");
        UiSync.waitForAppIdle(page);
        assertThat(lblCotizacionGuardada).isVisible();
        String numeroCotizacion = lblNumeroCotizacion.textContent().trim();
        log.info("Número de cotización generado: {}", numeroCotizacion);

        if (numeroCotizacion.isEmpty()) {
            throw new RuntimeException(
                    "No se obtuvo el número de cotización");
        }

        log.info("Cotización generada correctamente");
        UiSync.waitForAppIdle(page);
    }
}