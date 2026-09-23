package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.asserts.ElementAsserts;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.util.regex.Pattern;

import static com.mapfre.utils.UiSync.waitForAppIdle;


public class DeclaracionCargaAseguradoPage extends BasePage {
    private final Locator titleDetallePolizasLabel;
    private final Locator titleDeclaracionLabel;
    private final Locator policyCards;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator sueldoInput;
    private final Locator cargaIndividualButton;
    private final Locator siguienteButton;
    private final Locator aceptarModalLinkButton;
    private final Locator informacionDeclaracionLabel;
    private final Locator resultadoModalMensajeError;
    private final Locator ocupacionInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;
    private final Locator procesarButton;
    private final Locator numeroAseguradosCargadosLabel;
    private final Locator titleInformacionDeclaracionLabel;
    private final Locator siguienteGButton;
    private final Locator cargaMasivaButton;
    private final Locator importarPlanillaButton;
    private final Locator planillaCargadaExitosamenteMessage;
    private final Locator formPlanillaAsegurados;

    private final Locator txtNroTrabajadores;
    private final Locator txtMonto;

    private final Locator txtApellidoPaterno;
    private final Locator txtApellidoMaterno;
    private final Locator txtNombres;
    private final Locator btnSiguiente;

    private final Locator modalListaObservaciones;
    private final Locator btnAceptarListaObservaciones;

    private final Locator btnGenerar;

    private final Locator modalGenerarDeclaracion;
    private final Locator btnConfirmarGenerarDeclaracion;

    private final Locator modalDeclaracionGeneradaExito;
    private final Locator btnOkDeclaracionGenerada;


    public DeclaracionCargaAseguradoPage(Page page) {
        super(page);
        this.titleDetallePolizasLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Detalles de Pólizas").setLevel(2));
        this.titleDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración").setLevel(1));
        this.titleInformacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.policyCards = page.locator("div.g-box").filter(new Locator.FilterOptions().setHasText("Póliza"));
        this.tipoDocumentoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Tipo de documento"));
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. documento"));
        this.nombreCompletoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre Completo"));
        this.ocupacionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ocupación"));
        this.tipoRiesgoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Tipo de riesgo"));
        this.lugarExposicionInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Lugar de exposicion"));
        this.sexoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Sexo"));
        //this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.fechaNacimientoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Fecha Nacimiento"));
        this.sueldoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Sueldo"));
        this.cargaIndividualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.siguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Siguiente")));
        this.aceptarModalLinkButton = page.locator("a.g-button.g-button--second-design").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.informacionDeclaracionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Declaración").setLevel(2));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.procesarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Procesar"));
        this.numeroAseguradosCargadosLabel = page.locator(".gH2 b");
        this.siguienteGButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Siguiente"));
        this.cargaMasivaButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Carga masiva"));
        this.importarPlanillaButton = page.locator("input#iImportarPlanilla");
        this.planillaCargadaExitosamenteMessage = page.getByText("Planilla cargada exitosamente");
        this.formPlanillaAsegurados = page.locator("div.container-table-trama");

        this.txtNroTrabajadores = page.locator("input[name^='nNroTrabajadores']");
        this.txtMonto = page.locator("input[name^='nMonto']");

        this.txtApellidoPaterno = page.locator("oim-input[label='Apellido Paterno'] input");
        this.txtApellidoMaterno = page.locator("oim-input[label='Apellido Materno'] input");
        this.txtNombres = page.locator("oim-input[label='Nombres'] input");
        this.btnSiguiente = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Siguiente"));

        this.modalListaObservaciones = page.locator("section.modalReniecList");
        this.btnAceptarListaObservaciones = modalListaObservaciones.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.btnGenerar = page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Generar"));
        this.modalGenerarDeclaracion = page.locator("div.swal2-popup").filter(new Locator.FilterOptions().setHasText("GENERAR DECLARACIÓN"));
        this.btnConfirmarGenerarDeclaracion = modalGenerarDeclaracion.locator("button.swal2-confirm");

        this.modalDeclaracionGeneradaExito = page.locator("div.swal2-popup.swal2-icon-success");
        this.btnOkDeclaracionGenerada =  modalDeclaracionGeneradaExito.locator("button.swal2-confirm");

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(titleDetallePolizasLabel, "TITULO Detalles de Pólizas DEBE SER VISIBLE");
    }
    public void assertDeclaracionLoaded() {
        ElementAsserts.assertVisible(titleDeclaracionLabel, "TITULO Declaracion DEBE SER VISIBLE");
    }

    public void fillFormDetallesPolizas() {
        int count = policyCards.count();
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        for (int i = 0; i < count; i++) {
            Locator card = policyCards.nth(i);
            // Detect policy name from header text
            String headerText = card.getByText("Póliza").first().innerText();
            if (headerText.contains("Pensión")) {
                fillPolicy(card, "1", "3000");
                log.info("[DECLARACION][ASEGURADO] Póliza de Pensión completada - Trabajadores: 1, Monto: 3000");
            }
            if (headerText.contains("Salud")) {
                fillPolicy(card, "1", "3000");
                log.info("[DECLARACION][ASEGURADO] Póliza de Salud completada - Trabajadores: 1, Monto: 3000");
            }
        }
    }

    public void fillAseguradosCargaIndividual() {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, "94646546");
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(sueldoInput, "3000");
        //clickAndSync(siguienteButton);
        log.info("[DECLARACION][ASEGURADO] Asegurado cargado con exito");

    }

    public void fillAseguradosVidaLeyCargaIndividual() {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, "94646582");
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(ocupacionInput, "INGENIERO");
        fillAndSync(sueldoInput, "3000");
        selectByOptionIfEnableAndSync(tipoRiesgoSelect,"EMPLEADOS");
        selectFromMatAutocompleteFailFast(lugarExposicionInput,"010101","Lugar de Exposición");
        assertAutocompleteSelected(lugarExposicionInput,"010101","Lugar de Exposición");
        selectByOptionIfEnableAndSync(sexoSelect,"MASCULINO");
        //clickAndSync(siguienteButton);
        log.info("[DECLARACION][ASEGURADO] Formulario de la carga individual Vida Ley con exito");

    }

    public void processAndAssertSuccessListaDeObservacionesVidaLey(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION][OBSERVACIONES] Lista de observaciones Vida Ley procesada con exito");

    }

    public void processAndAssertSuccessListaDeObservaciones(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION][OBSERVACIONES] Lista de observaciones procesada con exito");

    }

    protected void fillPolicy(Locator card, String trabajadores, String monto) {
        card.getByLabel(
                        "Nro. Trabajadores",
                        new Locator.GetByLabelOptions().setExact(false)
                )
                .fill(String.valueOf(trabajadores));

        card.getByLabel(
                        "Monto",
                        new Locator.GetByLabelOptions().setExact(false)
                )
                .fill(String.valueOf(monto));
    }

    public void clickAceptarListaObservacionesButton() {
        //clickAndSync(aceptarModalLinkButton);
    }

    public void processAndAssertSuccessBotonGenerar(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionDeclaracionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][GENERAR_CONSTANCIA] Constancia generada con exito");

    }

    public void processAndAssertSuccessCargaDeAsegurados(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                numeroAseguradosCargadosLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        ElementAsserts.assertTextMatches(numeroAseguradosCargadosLabel, Pattern.compile(".*Se ha cargado un total de\\s+\\d+\\s+trabajador\\(es\\).*"),"Error al cargar trabajadores");
        log.info("[DECLARACION][CARGA DE ASEGURADO] Carga de asegurados cargada con exito");

    }

    public void processAndAssertSuccessBotonSiguiente(long timeoutMs, long quietMs) {
        siguienteGButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteGButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                titleInformacionDeclaracionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
    }

    public void fillFormDatosDeLosAsegurados(Path template, String numeroDocumento) {
        clickAndSync(cargaMasivaButton);
        ElementAsserts.assertVisible(importarPlanillaButton, "Boton importar planilla DEBE SER VISIBLE");
        importarPlanillaButton.setInputFiles(template);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        ElementAsserts.assertVisible(planillaCargadaExitosamenteMessage, "Mensaje de planilla cargada exitosamente DEBE SER VISIBLE");
        Locator numeroDocumentoPlanillaLabel = formPlanillaAsegurados.locator("div.item-column").filter(new Locator.FilterOptions().setHasText(Pattern.compile(numeroDocumento.trim(), Pattern.CASE_INSENSITIVE)));
        ElementAsserts.assertContainsText(numeroDocumentoPlanillaLabel,numeroDocumento.trim(),"Numero de documento " + numeroDocumento + " debe estar presente en la planilla cargada");
        log.info("[DECLARACION][ASEGURADO] Planilla cargada con exito con el numero de documento: {}", numeroDocumento);
    }

    public void processAndAssertSuccessProcesarCargaMasivaAseguradosVidaLey(long timeoutMs, long quietMs) {
        procesarButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        ElementAsserts.assertVisible(procesarButton, "Boton procesar DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION][ASEGURADO] Carga masiva de asegurados Vida Ley procesada con exito");
    }

    public void ingresarDatosDeclaracionMesVencido() {

        log.info("[DECLARACION] Ingresando Nro. Trabajadores: 1 y Monto: 3000");
        ElementAsserts.assertVisible(txtNroTrabajadores,"El campo Nro. Trabajadores debe ser visible");
        fillAndSync(txtNroTrabajadores, "1");
        ElementAsserts.assertVisible(txtMonto,"El campo Monto debe ser visible");
        fillAndSync(txtMonto, "3000");
        log.info("[DECLARACION] Se ingresó Nro. Trabajadores y Monto");
    }

    public void clickBtnCargaIndividual() {

        log.info("[DECLARACION][PLANILLA] Haciendo clic en el botón Cargar individual.");
        ElementAsserts.assertVisible(cargaIndividualButton,"El botón CargaR individual debe ser visible");
        clickAndSync(cargaIndividualButton);
        log.info("[DECLARACION][PLANILLA] Se hizo clic en el botón Cargar individual.");
    }

    public void clickBtnSiguienteDeclaracionMesVencido() {

        log.info("[DECLARACION][PLANILLA] Haciendo clic en el botón Siguiente.");

        ElementAsserts.assertVisible(
                btnSiguiente,
                "El botón Siguiente debe ser visible.");

        clickAndSync(btnSiguiente);

        log.info("[DECLARACION][PLANILLA] Se hizo clic en el botón Siguiente.");
    }

    public void fillCargaIndividualDeclaracionMesVencido() {

        log.info("[DECLARACION][CARGA_INDIVIDUAL] Ingresando datos obligatorios.");

        ElementAsserts.assertVisible(tipoDocumentoSelect, "El combo Tipo de documento debe ser visible");

        selectAndSync(tipoDocumentoSelect, "DNI");
        ElementAsserts.assertVisible(numeroDocumentoInput,"El campo Nro. Documento debe ser visible");
        fillAndSync(numeroDocumentoInput, "45228115");
        fillAndSync(txtApellidoPaterno, "AUTOMATIZACION");
        fillAndSync(txtApellidoMaterno, "AUTOMATIZACION");
        fillAndSync(txtNombres, "AUTOMATIZADOR");
        ElementAsserts.assertVisible(sueldoInput,"El campo Sueldo debe ser visible");
        fillAndSync(sueldoInput, "3000");

        log.info("[DECLARACION][CARGA_INDIVIDUAL] Datos obligatorios ingresados correctamente.");
    }

    public void aceptarListaObservaciones() {

        log.info("[DECLARACION][OBSERVACIONES] Validando si se muestra la lista de observaciones.");
        ElementAsserts.assertVisible( modalListaObservaciones,  "La lista de observaciones debe ser visible.");
        ElementAsserts.assertVisible( btnAceptarListaObservaciones, "El botón Aceptar de la lista de observaciones debe ser visible.");
        clickAndSync(btnAceptarListaObservaciones);
        log.info("[DECLARACION][OBSERVACIONES] Se hizo clic en el botón Aceptar de la lista de observaciones.");
    }

    public void clickBtnGenerar() {

        waitForAppIdle();
        log.info("[DECLARACION][GENERACION] Haciendo clic en el botón Generar.");
        ElementAsserts.assertVisible(btnGenerar,"El botón Generar debe ser visible.");
        clickAndSync(btnGenerar);
        log.info("[DECLARACION][GENERACION] Se hizo clic en el botón Generar.");
    }

    public void clickBtnConfirmarGenerarDeclaracion() {

        log.info("[DECLARACION][GENERAR] Validando popup GENERAR DECLARACIÓN.");
        ElementAsserts.assertVisible(modalGenerarDeclaracion,"El popup GENERAR DECLARACIÓN debe ser visible.");
        ElementAsserts.assertVisible(btnConfirmarGenerarDeclaracion,"El botón GENERAR debe ser visible.");
        clickAndSync(btnConfirmarGenerarDeclaracion);
        log.info("[DECLARACION][GENERAR] Se confirmó la generación de la declaración.");
        waitForAppIdle();
    }

    public void clickBtnOkDeclaracionGenerada() {

        log.info("[DECLARACION][GENERAR] Validando mensaje de generación exitosa.");
        ElementAsserts.assertVisible(modalDeclaracionGeneradaExito,"El mensaje de declaración generada debe ser visible.");
        ElementAsserts.assertContainsText( modalDeclaracionGeneradaExito,"Se genero la declaración con éxito","Debe mostrarse el mensaje de generación exitosa.");
        ElementAsserts.assertVisible(btnOkDeclaracionGenerada,"El botón Ok debe ser visible.");
        clickAndSync(btnOkDeclaracionGenerada);
        log.info("[DECLARACION][GENERAR] Se confirmó el mensaje de generación exitosa.");
        waitAppReady();
    }

}
