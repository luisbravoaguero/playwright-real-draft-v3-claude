package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.regex.Pattern;

public class BusquedaConstanciasSctrGeneralPage extends BasePage {
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
    private final Locator numeroConstanciaLabel;
    private final Locator fechaLabel;
    private final Locator usuarioLabel;
    private final Locator sistemaLabel;
    private final Locator motivoLabel;
    public BusquedaConstanciasSctrGeneralPage(Page page) {
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
        this.numeroConstanciaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Número de CONSTANCIA")));
        this.fechaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Fecha")));
        this.usuarioLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Usuario")));
        this.sistemaLabel = detalleAnulacionModal.locator("p").filter(new Locator.FilterOptions().setHasText(Pattern.compile("Sistema")));
        this.motivoLabel =  detalleAnulacionModal.locator("div").filter(new Locator.FilterOptions().setHas(page.locator("p.fwBold").filter(new Locator.FilterOptions().setHasText("Motivo")))).first();
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Búsqueda de constancias DEBE SER VISIBLE");
    }

    public void seleccionarPolizaTab() {
        clickAndSync(polizaTab);
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Pestaña Póliza seleccionada");
    }

    public void fillFormBusquedaConstanciaPolizaTab(String numeroPoliza, String numeroRecibo) {
        fillAndSync(numeroPolizaInput, numeroPoliza);
        fillAndBlurSync(numeroReciboInput, numeroRecibo);
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Formulario con número de póliza y número de recibo completado con exito");
    }

    public void processAndAssertSuccessBotonBuscar(long timeoutMs, long quietMs) {
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

    public void validarResultadoNumeroReciboAnulado(String numeroRecibo) {
        Locator resultadoItem = resultadoBusquedaLista.first();
        Locator numeroReciboLocator = resultadoItem.locator("div.row ").locator("a.g-link").filter(new Locator.FilterOptions().setHasText(numeroRecibo)).first();
        Locator etiquetaReciboAnulado = resultadoItem.locator("div.row ").locator("div").filter(new Locator.FilterOptions().setHasText("Anulado")).first();
        ElementAsserts.assertVisible(numeroReciboLocator, "RESULTADO DE BÚSQUEDA: Número de recibo " + numeroRecibo + " DEBE SER VISIBLE");
        ElementAsserts.assertVisible(etiquetaReciboAnulado, "RESULTADO DE BÚSQUEDA: Etiqueta Recibo Anulado DEBE SER VISIBLE para el número de recibo " + numeroRecibo);
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Resultado de búsqueda validado: Número de recibo {} y etiqueta Recibo Anulado visibles",numeroRecibo);
    }

    public String obtenerNumeroConstanciaDetalleBusqueda() {
        Locator numeroConstanciaValue = resultadoBusquedaLista.first().locator("div.g-col-md.cnt-item a.g-link b");
        String numeroConstancia = numeroConstanciaValue.innerText().trim();
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Número de constancia obtenido: {}", numeroConstancia);
        return numeroConstancia;
    }

    public void processAndAssertSuccessVerDetalleAnulacion(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                verDetalleAnulacionLink::click,                // Realizar click en el botón guardar
                errorGeneralModal,          // Elemento esperado: mensaje de error
                detalleAnulacionModal,          // Elemento esperado: mensaje de éxito
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
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Detalle de anulación del recibo abierto con éxito en un popup modal");
    }

    public void validarInformacionDetalleAnulacion(String numeroConstancia) {
        ElementAsserts.assertVisible(motivoLabel, "DETALLE DE ANULACIÓN: Motivo DEBE SER VISIBLE");
        Locator motivoValue = motivoLabel.locator("p").nth(1);
        ElementAsserts.assertVisible(numeroConstanciaLabel, "DETALLE DE ANULACIÓN: Número de constancia DEBE SER VISIBLE");
        ElementAsserts.assertContainsText(numeroConstanciaLabel, numeroConstancia, "DETALLE DE ANULACIÓN: Número de constancia DEBE COINCIDIR con el valor esperado");
        ElementAsserts.assertVisible(fechaLabel, "DETALLE DE ANULACIÓN: Fecha DEBE SER VISIBLE");
        ElementAsserts.assertVisible(usuarioLabel, "DETALLE DE ANULACIÓN: Usuario DEBE SER VISIBLE");
        ElementAsserts.assertVisible(sistemaLabel, "DETALLE DE ANULACIÓN: Sistema DEBE SER VISIBLE");
        ElementAsserts.assertVisible(motivoValue, "DETALLE DE ANULACIÓN: Valor del motivo DEBE SER VISIBLE");
        ElementAsserts.assertFilled(motivoValue, "DETALLE DE ANULACIÓN: Valor del motivo DEBE ESTAR LLENO");
        log.info("[BUSQUEDA DE CONSTANCIAS][PÓLIZA] Información del detalle de anulación del recibo validada con éxito");
    }

    public void validarResultadoPolizaNumeroReciboExclusion(String numeroPoliza, String numeroRecibo) {
        Locator resultadoItem = resultadoBusquedaLista.first();
        ElementAsserts.assertVisible(resultadoItem, "RESULTADO DE BÚSQUEDA: Resultado de búsqueda DEBE SER VISIBLE");
        ElementAsserts.assertContainsTextIgonoreCase(resultadoItem, "EXCLUSION", "RESULTADO DE BÚSQUEDA: Tipo de constancia DEBE SER EXCLUSION");
        ElementAsserts.assertContainsText(resultadoItem, numeroPoliza, "RESULTADO DE BÚSQUEDA: Número de póliza DEBE COINCIDIR con el valor esperado");
        ElementAsserts.assertContainsText(resultadoItem, numeroRecibo, "RESULTADO DE BÚSQUEDA: Número de recibo DEBE COINCIDIR con el valor esperado");
    }
}
