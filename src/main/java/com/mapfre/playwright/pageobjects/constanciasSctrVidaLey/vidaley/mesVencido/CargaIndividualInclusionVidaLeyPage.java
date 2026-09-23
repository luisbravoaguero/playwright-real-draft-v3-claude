package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CargaIndividualInclusionVidaLeyPage extends BasePage {

    private final Locator informacionInclusionTitle;

    private final Locator cargaIndividualButton;

    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator ocupacionInput;
    private final Locator sueldoInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;

    public CargaIndividualInclusionVidaLeyPage(Page page) {
        super(page);

        this.informacionInclusionTitle =
                page.getByText("Información de Inclusión");

        this.cargaIndividualButton =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName("Cargar individual"));

        // Reemplazar por locators reales cuando se inspeccione el HTML
        this.tipoDocumentoSelect =
                page.locator("#mat-input-18"); // SELECT

        this.numeroDocumentoInput =
                page.locator("input[name='nNroDocumento0']"); // INPUT
        this.nombreCompletoInput = page.locator("//input[@name='nNombreCompleto0']");

        this.fechaNacimientoInput =
                page.locator("oim-datepicker[label='Fecha Nacimiento'] input");

        this.ocupacionInput = page.locator("#mat-input-15");

        this.tipoRiesgoSelect =
                page.locator("select[id='mat-input-19']");

        this.lugarExposicionInput =
                page.locator("input[id='mat-input-17']");


        this.sueldoInput = page.locator("#mat-input-16");
        this.sexoSelect =
                page.locator("#mat-input-20");

    }

    public void assertLoaded() {

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                informacionInclusionTitle,
                "TITULO Información de Inclusión DEBE SER VISIBLE");
    }

    public void fillAseguradosCargaIndividualVidaLey(String numeroDocumento) {

        assertLoaded();

        log.info("[VIDALEY][ASEGURADO] Iniciando carga individual");

        ElementAsserts.assertVisible(
                cargaIndividualButton,
                "El botón Cargar individual debe ser visible");

        clickAndSync(cargaIndividualButton);

        log.info("[VIDALEY][ASEGURADO] Seleccionando tipo de documento");
        selectAndSync(tipoDocumentoSelect, "DNI");

        log.info("[VIDALEY][ASEGURADO] Ingresando número de documento");
        fillAndSync(numeroDocumentoInput, numeroDocumento);

        log.info("[VIDALEY][ASEGURADO] Ingresando nombre completo");
        fillAndSync(nombreCompletoInput, "ROSARIO CRISOSTOMO MEZA");

        log.info("[VIDALEY][ASEGURADO] Ingresando fecha de nacimiento");
        fillReadonlyDate(fechaNacimientoInput, "15/11/1994");

        log.info("[VIDALEY][ASEGURADO] Ingresando ocupación");
        fillAndSync(ocupacionInput, "INGENIERA");

        log.info("[VIDALEY][ASEGURADO] Ingresando sueldo");
        fillAndSync(sueldoInput, "9000");

        log.info("[VIDALEY][ASEGURADO] Realizando scroll hacia la sección de riesgo");
        page.mouse().wheel(0, 500);

        waitRandomBetween(1000);

        log.info("[VIDALEY][ASEGURADO] Seleccionando tipo de riesgo");
        selectByOptionIfEnableAndSync(
                tipoRiesgoSelect,
                "Obreros");

        log.info("[VIDALEY][ASEGURADO] Seleccionando lugar de exposición");
        selectFromMatAutocompleteFailFast(
                lugarExposicionInput,
                "150116",
                "Lugar de Exposición");

        assertAutocompleteSelected(
                lugarExposicionInput,
                "150116",
                "Lugar de Exposición");

        log.info("[VIDALEY][ASEGURADO] Seleccionando sexo");
        selectByOptionIfEnableAndSync(
                sexoSelect,
                "Femenino");

        log.info(
                "[VIDALEY][ASEGURADO] Formulario completado correctamente para documento {}",
                numeroDocumento);
    }
    public void seleccionarLugarExposicionVidaLey() {


        lugarExposicionInput.scrollIntoViewIfNeeded();

        page.mouse().wheel(0, 500);

        waitForNetworkIdle();
        sync();

        selectFromMatAutocompleteFailFast(
                lugarExposicionInput,
                "150116",
                "Lugar de Exposición");

        assertAutocompleteSelected(
                lugarExposicionInput,
                "150116",
                "Lugar de Exposición");

        log.info(
                "[VIDALEY][ASEGURADO] Lugar de exposición seleccionado correctamente");
    }

    public void seleccionarSexoVidaLey() {

        log.info("[VIDALEY][ASEGURADO] Seleccionando sexo");

        selectByOptionIfEnableAndSync(
                sexoSelect,
                "FEMENINO");
    }

}