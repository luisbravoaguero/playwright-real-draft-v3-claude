package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.inclusionSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class InclusionPlanillaPage extends BasePage {
    private final Locator title;
    private final Locator policyCards;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator sueldoInput;
    private final Locator cargaIndividualButton;
    private final Locator siguienteButton;
    private final Locator aceptarModalLinkButton;
    private final Locator informacionInclusionLabel;
    private final Locator resultadoModalMensajeError;
    private final Locator facturadaRadioButton;
    private final Locator siguienteAngularButton;
    private final Locator aceptarModalAngularButton;
    private final Locator ocupacionInput;
    private final Locator tipoRiesgoSelect;
    private final Locator lugarExposicionInput;
    private final Locator sexoSelect;
    private final Locator procesarButton;
    private final Locator facturacionGroup;
    private final Locator facturadaRadioVidaLeyButton;
    private final Locator numeroAseguradosCargadosLabel;
    private final Locator iImportarPlanillaInput;
    private final Locator planillaCargadaExitosamenteMessage;
    private final Locator listaAseguradosContainer;
    private final Locator generarLink;
    private final Locator warningGeneralModal;
    private final Locator alertaTitulo;
    private final Locator aceptarButton;
    private final Locator infoGeneralModal;
    private final Locator generarButton;
    private final Locator successGeneralModal;
    private final Locator mensajeInclusionExitosa;

    private final Locator btnGenerar;
    private final Locator modalListaObservaciones;

    private final Locator modalGenerarInclusion;
    private final Locator modalInclusionExitosa;
    private final Locator lblNroMp;
    private final Locator lblNroDoc;
    private final Locator btnDescargar;

    public InclusionPlanillaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Inclusión de planilla"));
        this.policyCards = page.locator("div.g-box").filter(new Locator.FilterOptions().setHasText("Póliza"));
        this.tipoDocumentoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Tipo de documento"));
        this.numeroDocumentoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. documento"));
        this.nombreCompletoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nombre Completo"));
        //this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.fechaNacimientoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Fecha Nacimiento"));
        this.sueldoInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Sueldo"));
        this.cargaIndividualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.siguienteButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Siguiente")));
        this.aceptarModalLinkButton = page.locator("a.g-button.g-button--second-design").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.informacionInclusionLabel = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Información de Inclusión").setLevel(2));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.facturadaRadioButton = page.locator("oim-radio[label='Facturada'] input");
        this.siguienteAngularButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Siguiente"));
        this.aceptarModalAngularButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.ocupacionInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ocupación"));
        //this.tipoRiesgoSelect = page.locator("oim-select[label='Tipo de riesgo'] select");
        this.tipoRiesgoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName(Pattern.compile("Tipo de riesgo", Pattern.CASE_INSENSITIVE)));
        this.lugarExposicionInput = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Lugar de exposicion"));
        this.sexoSelect = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Sexo"));
        this.procesarButton = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Procesar"));
        this.facturacionGroup = page.locator("mat-radio-group[name='nOpcionFacturacion'][role='radiogroup']");
        this.facturadaRadioVidaLeyButton = facturacionGroup.getByText("Facturada", new Locator.GetByTextOptions().setExact(true));
        this.numeroAseguradosCargadosLabel = page.locator(".gH2 b");
        this.iImportarPlanillaInput = page.locator("input#iImportarPlanilla");
        this.planillaCargadaExitosamenteMessage = page.getByText("Planilla cargada exitosamente");
        this.listaAseguradosContainer = page.locator("div.gnContentAuto-bg");
        this.generarLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Generar",Pattern.CASE_INSENSITIVE)));
        this.warningGeneralModal = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']");
        this.alertaTitulo = warningGeneralModal.locator("h2.swal2-title");
        this.aceptarButton = warningGeneralModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Aceptar"));
        this.infoGeneralModal = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.generarButton = infoGeneralModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(Pattern.compile("Generar",Pattern.CASE_INSENSITIVE)));
        this.successGeneralModal = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.mensajeInclusionExitosa = successGeneralModal.locator("div.swal2-html-container");

        this.btnGenerar = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Generar"));
        this.modalListaObservaciones = page.locator("section.gSweetModal").filter(new Locator.FilterOptions().setHasText("Lista de observaciones"));
        this.modalGenerarInclusion =  page.locator("div.swal2-popup.swal2-icon-info").filter(new Locator.FilterOptions().setHasText("GENERAR INCLUSIÓN"));
        this.modalInclusionExitosa = page.locator("div.swal2-popup.swal2-icon-success").filter(new Locator.FilterOptions().setHasText("Se genero la inclusión con éxito"));

        this.lblNroMp = page.getByText("Nro. MP/");
        this.lblNroDoc = page.locator("div.col-sm-4.g-col-sm").filter(new Locator.FilterOptions().setHasText("Nro. Doc:"));
        this.btnDescargar = page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Descargar"));
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Inclusión de planilla DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
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
                log.info("[INCLUSION][ASEGURADO] Póliza de Pensión completada - Trabajadores: 1, Monto: 3000");
            }
            if (headerText.contains("Salud")) {
                fillPolicy(card, "1", "3000");
                log.info("[INCLUSION][ASEGURADO] Póliza de Salud completada - Trabajadores: 1, Monto: 3000");
            }
        }
    }

    public void fillAseguradosCargaIndividual(String numeroDocumento) {
        page.addLocatorHandler(warningGeneralModal, locator -> {
            log.info("[PERFIL_CLIENTE] Se detectó una popup de alerta general, se extrae el mensaje y se lanza una excepción");
            String mensajeAlerta = alertaTitulo.innerText();
            log.error("[PERFIL_CLIENTE] Mensaje de alerta: " + mensajeAlerta);
            if (mensajeAlerta.contains("Recibos Pendientes")) {
                clickAndSync(aceptarButton);
                log.info("[PERFIL_CLIENTE] Se hizo click en el botón Aceptar de la alerta de Recibos Pendientes");
            }
        });
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, numeroDocumento);
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(sueldoInput, "3000");
        //clickAndSync(siguienteButton);
        log.info("[INCLUSION][ASEGURADO] Asegurado cargado con exito");

    }
    public void clickGenerarLink() {
        clickAndSync(generarLink);
        log.info("[INCLUSION][ASEGURADO] Link Generar clickeado con exito");
    }
    public void fillAseguradosCargaIndividualVidaLey(String numeroDocumento) {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, numeroDocumento);
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(ocupacionInput, "INGENIERO");
        fillAndSync(sueldoInput, "3000");
        selectByOptionIfEnableAndSync(tipoRiesgoSelect,"EMPLEADOS");
        selectFromMatAutocompleteFailFast(lugarExposicionInput,"010101","Lugar de Exposición");
        assertAutocompleteSelected(lugarExposicionInput,"010101","Lugar de Exposición");
        selectByOptionIfEnableAndSync(sexoSelect,"MASCULINO");
        log.info("[INCLUSION][ASEGURADO] Formulario de la carga individual Vida Ley con exito");
    }
    public void seleccionarFacturadaSctr() {
        clickAndSync(facturadaRadioButton);
        log.info("[INCLUSION][ASEGURADO] Opción No Facturada seleccionada con exito");
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

    public void clickSiguienteAngularButton() {
        siguienteAngularButton.click();
        log.info("[INCLUSION][OBSERVACIONES] Botón Siguiente clickeado con exito");

    }

    public void processAndAssertSuccessListaDeObservaciones(long timeoutMs, long quietMs) {
        //siguienteButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][OBSERVACIONES] Lista de observaciones procesada con exito");

    }

    public void clickAceptarListaObservacionesButton() {
        //aceptarModalAngularButton.click();
    }

    public void processAndAssertSuccessBotonSiguiente(long timeoutMs, long quietMs) {
        aceptarModalLinkButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][ASEGURADO] Botón Siguiente procesado con exito");
    }

    public void processAndAssertSuccessBotonSiguienteVidaLey(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText() + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        log.info("[INCLUSION][ASEGURADO] Botón Siguiente Vida Ley procesado con exito");
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
    public void seleccionarFacturadaVidaLey() {
        clickAndSync(facturadaRadioVidaLeyButton);
        log.info("[INCLUSION][ASEGURADO] Opción No Facturada Vida Ley seleccionada con exito");
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

    public void assertLoadedImportarPlanillaMineria() {
        ElementAsserts.assertVisible(iImportarPlanillaInput, "Campo de importación de planilla DEBE SER VISIBLE");
        sync();
        waitForNetworkIdle();
        waitRandomBetween(1000);
        log.info("[INCLUSION][ASEGURADO SCTR MINERI] Seccion de importación de planilla cargada con éxito");
    }

    public void fillFormDatosDeLosAseguradosSctrMineria(Path template) {
        iImportarPlanillaInput.setInputFiles(template);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        ElementAsserts.assertVisible(planillaCargadaExitosamenteMessage, "Mensaje de planilla cargada exitosamente DEBE SER VISIBLE");
    }

    public void processAndAssertSuccessProcesarPlanillaAseguradoMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(procesarButton, "Botón Procesar DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                procesarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                listaAseguradosContainer,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se visualiza la seccion con la lista de asegurados");
    }

    public void validarAseguradosCargadosSctrMineria(String numeroDocumento) {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(2000);
        ElementAsserts.assertVisible(listaAseguradosContainer, "Sección con la lista de asegurados DEBE SER VISIBLE");
        Locator numeroDocumentoPlanillaLabel = listaAseguradosContainer.locator("li.tbl-item").filter(new Locator.FilterOptions().setHasText(Pattern.compile(numeroDocumento.trim(), Pattern.CASE_INSENSITIVE))).first();
        ElementAsserts.assertContainsText(numeroDocumentoPlanillaLabel,numeroDocumento.trim(),"Numero de documento " + numeroDocumento + " debe estar presente en la planilla cargada");
        log.info("[DECLARACION][ASEGURADO] Planilla cargada con exito con el numero de documento: {}", numeroDocumento);
    }

    public void processAndAssertSuccessBotonSiguienteMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(siguienteButton, "Botón Siguiente DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalAngularButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se visualiza la seccion con la lista de asegurados");
    }

    public void processAndAssertSuccessListaDeObservacionesMineria(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(aceptarModalAngularButton, "Botón Aceptar dentro del modal Lista de Observaciones DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalAngularButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                informacionInclusionLabel,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[INCLUSION][CARGA DE ASEGURADO MINERIA] Se procesó el modal de Lista de Observaciones con exito");
    }

    public void processAndAssertSuccessGenerarInclusion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalLinkButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[INCLUSION][ASEGURADO] Modal de Generar Inclusión procesado con exito");
    }

    public void processAndAssertSuccessListaDeObservacionesInclusion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarModalLinkButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                generarButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
    }

    public void processAndAssertSuccessGeneraInclusionModal(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                generarButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                successGeneralModal,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        ElementAsserts.assertContainsText(mensajeInclusionExitosa,"Se genero la inclusión con éxito","Mensaje de inclusión exitosa DEBE SER VISIBLE");
    }

    public void fillAseguradosCargaIndividualSctr() {
        clickAndSync(cargaIndividualButton);
        //datos en duro para la carga individual, se pueden parametrizar si se desea
        selectAndSync(tipoDocumentoSelect, "DNI");
        fillAndSync(numeroDocumentoInput, "45228114");
        fillAndSync(nombreCompletoInput, "JUAN PEREZ ROJAS");
        fillReadonlyDate(fechaNacimientoInput, "01/01/1990");
        fillAndSync(sueldoInput, "3000");
    }

    public void clickBtnGenerar() {

        log.info("[INCLUSION] Haciendo clic en el botón Generar.");
        ElementAsserts.assertVisible(btnGenerar,"El botón Generar debe ser visible.");
        clickAndSync(btnGenerar);
        log.info("[INCLUSION] Validando si se muestra el modal Lista de observaciones.");

        if (GestorModal.existeModal(modalListaObservaciones)) {

            log.info("[INCLUSION] Se mostró el modal Lista de observaciones.");
            Locator btnAceptarModal = modalListaObservaciones.locator("a").filter(new Locator.FilterOptions().setHasText("Aceptar"));
            clickAndSync(btnAceptarModal);
            log.info("[INCLUSION] Se hizo clic en Aceptar del modal Lista de observaciones.");
        }

        log.info("[INCLUSION] Validando si se muestra el modal GENERAR INCLUSIÓN.");
        boolean modalGeneracion = GestorModal.clickBotonSiExiste(modalGenerarInclusion,"GENERAR");

        if (modalGeneracion) {
            log.info("[INCLUSION] Se hizo clic en GENERAR del modal de confirmación.");
        }
        waitAppReady();
        log.info("[INCLUSION] Validando si se muestra el mensaje de inclusión exitosa.");

        boolean inclusionExitosa = GestorModal.clickBotonSiExiste(modalInclusionExitosa,"Ok");

        if (inclusionExitosa) {
            log.info("[INCLUSION] Se confirmó el mensaje 'Se genero la inclusión con éxito'.");
        } else {
            throw new FrameworkException("No se mostró el mensaje de inclusión exitosa.");
        }
        waitAppReady();
    }

    public void assertLoadedReporteConstanciaInclusion() {
        waitAppReady();
        log.info("[INCLUSION] Validando generación exitosa de la inclusión.");

        ElementAsserts.assertVisible(lblNroMp,"El número MP generado debe ser visible.");
        ElementAsserts.assertVisible(lblNroDoc,"El número de documento debe ser visible.");
        ElementAsserts.assertVisible(btnDescargar,"El botón Descargar debe ser visible.");
        log.info("[INCLUSION] Nro MP encontrado: {}", lblNroMp.innerText().trim());
        log.info("[INCLUSION] Nro Doc encontrado: {}", lblNroDoc.innerText().trim());
        log.info("[INCLUSION] Botón encontrado: {}", btnDescargar.innerText().trim());

    }



}