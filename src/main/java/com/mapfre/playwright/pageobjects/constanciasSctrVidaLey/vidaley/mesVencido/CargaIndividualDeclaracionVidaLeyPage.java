package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CargaIndividualDeclaracionVidaLeyPage extends BasePage {

    private final Locator declaracionTitle;

    private final Locator cargaIndividualButton;
    private final Locator agregarFilaButton;

    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator ocupacionInput;
    private final Locator sueldoInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;

    public CargaIndividualDeclaracionVidaLeyPage(Page page) {

        super(page);

        this.declaracionTitle =
                page.getByRole(
                        AriaRole.HEADING,
                        new Page.GetByRoleOptions()
                                .setName("Declaración"));

        this.cargaIndividualButton =
                page.getByRole(
                        AriaRole.LINK,
                        new Page.GetByRoleOptions()
                                .setName("Cargar individual"));

        this.agregarFilaButton =
                page.getByText(
                        "Agregar fila");

        this.tipoDocumentoSelect =
                page.locator("select").first();

        this.numeroDocumentoInput =
                page.getByLabel("Nro. Documento");

        this.nombreCompletoInput =
                page.locator("input[name='nNombreCompleto0']");

        this.fechaNacimientoInput =
                page.locator(
                        "oim-datepicker[label='Fecha Nacimiento'] input");

        this.ocupacionInput =
                page.locator("[name='nOcupacion0']");;

        this.sueldoInput =
                page.locator("input[name='nSueldo0']");


        this.tipoRiesgoSelect =
                page.locator("select").nth(1);

        this.lugarExposicionInput =
                page.getByRole(
                                AriaRole.COMBOBOX,
                                new Page.GetByRoleOptions()
                                        .setName("Lugar de Exposicion"))
                        .last();

        this.sexoSelect =
                page.locator("select").last();
    }

    public void assertLoaded() {

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                declaracionTitle,
                "La pantalla Declaración debe ser visible");
    }

    public void seleccionarCargaIndividual() {

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando carga individual");

        cargaIndividualButton.scrollIntoViewIfNeeded();

        clickAndSync(
                cargaIndividualButton);
    }

    public void fillAseguradosCargaIndividualVidaLey(
            String numeroDocumento,
            String nombreCompleto) {

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando tipo documento");

        selectAndSync(
                tipoDocumentoSelect,
                "DNI");

        fillAndSync(
                numeroDocumentoInput,
                numeroDocumento);

        fillAndSync(
                nombreCompletoInput,
                nombreCompleto);

        fillReadonlyDate(
                fechaNacimientoInput,
                "01/01/1990");

        fillAndSync(
                ocupacionInput,
                "INGENIERA");

        fillAndSync(
                sueldoInput,
                "9000");

        selectByOptionIfEnableAndSync(
                tipoRiesgoSelect,
                "Empleados");
    }

    public void seleccionarLugarExposicionVidaLey() {

        log.info(
                "[VIDALEY][ASEGURADO] Seleccionando lugar de exposición");

        lugarExposicionInput.scrollIntoViewIfNeeded();

        waitRandomBetween(1000);

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

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando sexo");

        selectByOptionIfEnableAndSync(
                sexoSelect,
                "FEMENINO");

        log.info(
                "[DECLARACION][VIDALEY] Sexo FEMENINO seleccionado");
    }
    public void completarPrimerTrabajador(
            String numeroDocumento,
            String nombreCompleto) {

        assertLoaded();

        seleccionarCargaIndividual();

        fillAseguradosCargaIndividualVidaLey(
                numeroDocumento,
                nombreCompleto);

        seleccionarLugarExposicionVidaLey();

        seleccionarSexoVidaLey();
    }

}