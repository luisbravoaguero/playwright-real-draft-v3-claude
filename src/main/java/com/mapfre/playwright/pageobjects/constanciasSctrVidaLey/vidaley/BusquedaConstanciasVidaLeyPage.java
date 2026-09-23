package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class BusquedaConstanciasVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator polizaTab;
    private final Locator numeroPolizaInput;
    private final Locator numeroReciboInput;
    private final Locator buscarLink;
    private final Locator sinResultadosMensaje;
    private final Locator resultadoBusquedaLista;
    private final Locator verDetalleAnulacionLink;
    private final Locator detalleAnulacionModal;
    private final Locator errorGeneralModal;
    private final Locator successGeneralModal;
    private final Locator numeroConstanciaLabel;
    private final Locator fechaLabel;
    private final Locator usuarioLabel;
    private final Locator sistemaLabel;
    private final Locator motivoLabel;
    private final Locator menuAnulacionModal;
    private final Locator anularReciboCheckbox;
    private final Locator aceptarButton;
    private final Locator motivoInput;
    public BusquedaConstanciasVidaLeyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Búsqueda de constancias"));
        this.polizaTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Póliza"));
        this.numeroPolizaInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Póliza"));
        this.numeroReciboInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Nro. Recibo"));
        this.buscarLink = page.locator("a.g-button").filter(new Locator.FilterOptions().setHasText("Buscar"));
        this.sinResultadosMensaje = page.getByText("No hay resultados para los");
        this.resultadoBusquedaLista = page.locator("div.g-list-md.g-box");
        this.verDetalleAnulacionLink = page.locator("span.g-box-label + a");
        this.detalleAnulacionModal = page.locator("mat-dialog-container");
        this.errorGeneralModal = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.successGeneralModal = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.numeroConstanciaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Número de CONSTANCIA")));
        this.fechaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Fecha")));
        this.usuarioLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Usuario")));
        this.sistemaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Sistema")));
        this.motivoLabel =  detalleAnulacionModal.locator("div").filter(new Locator.FilterOptions().setHas(page.locator("p.fwBold").filter(new Locator.FilterOptions().setHasText("Motivo")))).first();
        this.menuAnulacionModal = page.locator("section.modalCreateApplication");
        this.anularReciboCheckbox = menuAnulacionModal.getByRole(AriaRole.CHECKBOX, new Locator.GetByRoleOptions().setName("Anular recibo"));
        this.aceptarButton = menuAnulacionModal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Aceptar"));
        this.motivoInput = menuAnulacionModal.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Motivo"));
    }
    public void assertLoadedVidaLey() {
        ElementAsserts.assertVisible(title, "TITULO Búsqueda de constancias DEBE SER VISIBLE");
    }

    public void seleccionarPolizaTabVidaLey() {
        clickAndSync(polizaTab);
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Pestaña Póliza seleccionada");
    }

    public void fillFormBusquedaConstanciaPolizaTabVidaLey(String numeroPoliza, String numeroRecibo) {
        fillAndSync(numeroPolizaInput, numeroPoliza);
        fillAndBlurSync(numeroReciboInput, numeroRecibo);
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Formulario con número de póliza y número de recibo completado con exito");
    }

    public void processAndAssertSuccessBotonBuscarVidaLey(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                buscarLink::click,                // Realizar click en el botón guardar
                sinResultadosMensaje,          // Elemento esperado: mensaje de error
                resultadoBusquedaLista.first(),          // Elemento esperado: mensaje de éxito
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
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Búsqueda del número de póliza y número de recibo completada con exito");
    }

    public void validarResultadoBusquedaParaAnularVidaLey(String numeroPoliza, String numeroRecibo) {
        ElementAsserts.assertVisible(resultadoBusquedaLista.first(), "RESULTADO DE BUSQUEDA DEBE SER VISIBLE");
        ElementAsserts.assertContainsText(resultadoBusquedaLista.first(), numeroPoliza, "RESULTADO DE BUSQUEDA DEBE CONTENER EL NUMERO DE POLIZA");
        ElementAsserts.assertContainsText(resultadoBusquedaLista.first(), numeroRecibo, "RESULTADO DE BUSQUEDA DEBE CONTENER EL NUMERO DE RECIBO");
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Resultado de búsqueda validado con exito para anular constancia");
    }

    public void processAndAssertSuccessAbrirModalAnulacionVidaLey(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(resultadoBusquedaLista.first(), "RESULTADO DE BUSQUEDA DEBE SER VISIBLE");
        Locator anularLink = resultadoBusquedaLista.first().locator(".gnIcon-lg");
        ElementAsserts.assertVisible(anularLink, "LINK DE ANULAR DEBE SER VISIBLE");
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Link de anular constancia visible, procediendo a abrir modal de detalle de anulación");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                anularLink::click,                // Realizar click en el botón guardar
                errorGeneralModal,          // Elemento esperado: mensaje de error
                menuAnulacionModal,          // Elemento esperado: mensaje de éxito
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
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Modal de detalle de anulación abierto con exito");
    }

    public void seleccionarReciboParaAnularVidaLey() {
        ElementAsserts.assertVisible(menuAnulacionModal, "MODAL DE ANULACION DEBE SER VISIBLE");
        Locator labels = menuAnulacionModal.locator("mat-checkbox label");
        ElementAsserts.assertVisible(labels.first(), "No se encontraron recibos en el modal de anulación");
        for (int i = 0; i < labels.count(); i++) {
            log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Etiqueta de recibo {}: {}", i, labels.nth(i).innerText().trim());
        }
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Se va a seleccionar el primer checkbox de recibo para anular");
        Locator primerCheckbox = menuAnulacionModal.getByRole(AriaRole.CHECKBOX).first();
        ElementAsserts.assertVisible(primerCheckbox, "PRIMER CHECKBOX DE RECIBO DEBE SER VISIBLE");
        primerCheckbox.check();
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Primer checkbox de recibo seleccionado para anular");
    }

    public void ingresarMotivoAnulacionVidaLey() {
        fillAndSync(motivoInput, "MOTIVO ANULACION PRUEBA VIDALEY");
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Motivo de anulación ingresado: MOTIVO ANULACION PRUEBA VIDALEY");
    }

    public void processAndAssertSuccessAnulacionVidaLey(long timeoutMs, long quietMs) {
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Se va a hacer click en el botón Aceptar para anular la constancia");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                aceptarButton::click,                // Realizar click en el botón guardar
                errorGeneralModal,          // Elemento esperado: mensaje de error
                successGeneralModal,          // Elemento esperado: mensaje de éxito
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
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Constancia anulada con exito");
    }
}
