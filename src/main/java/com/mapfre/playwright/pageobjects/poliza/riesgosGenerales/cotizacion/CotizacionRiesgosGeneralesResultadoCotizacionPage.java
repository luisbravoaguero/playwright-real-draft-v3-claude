package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.OptionalModalWait;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CotizacionRiesgosGeneralesResultadoCotizacionPage extends BasePage {
    private final Locator numeroCotizacion;
    private final Locator ubicacionRiesgoLabel;
    private final Locator ubigeoLabel;
    private final Locator siguienteButton;
    private final Locator modalErrorGeneral;
    public CotizacionRiesgosGeneralesResultadoCotizacionPage(Page page) {
        super(page);
        this.numeroCotizacion = page.locator("h2").filter(new Locator.FilterOptions().setHasText("Nro de cotización:"));
        this.ubicacionRiesgoLabel = page.locator("oim-input[label='Ubicación del riesgo'] input");
        this.ubigeoLabel = page.locator("p:text('Ubigeo') + p");
        this.siguienteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Siguiente"));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
    }

    public void assertLoaded() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertVisible(numeroCotizacion, "El número de cotización no se encuentra visible");
        ElementAsserts.assertFilled(numeroCotizacion, "El número de cotización no contiene texto");
        assertQuotationNumberIsValid();
    }

    public void assertLoadedHidrocarburosSoloRC(){
        ElementAsserts.assertVisible(ubicacionRiesgoLabel, "El label de ubicación del riesgo no se encuentra visible");
        scrollToBottom();
        waitRandomBetween(500);
        ElementAsserts.assertVisible(ubigeoLabel, "El label de ubigeo no se encuentra visible");
        esperarHastaQueAlMenosNFilled(5, "item-dato",60_000, 500);
        esperarHastaQueAlMenosNFilled(1, "text-xs",60_000, 500);
    }
    public void assertLoadedCarLite(){
        scrollToBottom();
        waitRandomBetween(500);
        esperarHastaQueAlMenosNFilled(5, "item-dato",60_000, 500);
    }

    public void assertLoadedCar() {
        scrollToBottom();
        waitRandomBetween(500);
        esperarHastaQueAlMenosNFilled(5, "item-dato",60_000, 500);
    }

    public void processAndAssertSuccessClickBotonSiguiente(long timeoutMs, long quietMs) {
        siguienteButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        log.info("[RIESGOS GENERALES][COTIZACION] Botón Siguiente visible, preparado para clickearlo");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                siguienteButton::click,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                numeroCotizacion,
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
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitForOptomalModal(modalErrorGeneral, 5_800, 500);
        log.info("[RIESGOS GENERALES][COTIZACION RESULTADO] Botón Siguiente clickeado exitosamente");
    }

    public void waitForOptomalModal(Locator locator, long timeoutMs, long quietMs) {
        boolean modalShown = OptionalModalWait.waitForOptionalModal(
                page,
                locator,
                timeoutMs,
                quietMs,
                150
        );

        if (modalShown) {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            ElementAsserts.assertUIMessage("Se detectó un modal de error: " + locator.innerText());
        }
    }

    public void esperarHastaQueAlMenosNFilled(int minimoLlenos, String valueAttribute, double timeoutMs, double quietMs) {
        Page.WaitForFunctionOptions options = new Page.WaitForFunctionOptions();
        options.setTimeout(timeoutMs);
        options.setPollingInterval(quietMs);

        page.waitForFunction(
                "(minimo) => {" +
                        "  const elementos = Array.from(document.querySelectorAll('div."+valueAttribute+"'));" +
                        "  const llenos = elementos.filter(el => el.textContent && el.textContent.trim().length > 0).length;" +
                        "  return llenos >= minimo;" +
                        "}",
                minimoLlenos,
                options
        );
        log.info("Al menos {} elementos div.{} tienen contenido.", valueAttribute, minimoLlenos);
    }

    private void assertQuotationNumberIsValid() {
        String rawText = numeroCotizacion.innerText().trim();
        String quotationNumber = extractQuotationNumber(rawText);

        if (quotationNumber.isEmpty()) {
            ElementAsserts.assertUIMessage(
                    "El número de cotización está vacío o no se pudo obtener. Valor obtenido: " + rawText
            );
            return;
        }

        if (!isNumeric(quotationNumber)) {
            ElementAsserts.assertUIMessage(
                    "El número de cotización no es correcto, se espera un valor numérico válido. Valor obtenido: " + rawText
            );
            return;
        }

        log.info("[RIESGOS GENERALES][COTIZACION RESULTADO] Número de cotización visible: {}", quotationNumber);
    }

    private String extractQuotationNumber(String rawText) {
        String prefix = "Nro de cotización:";
        return rawText.startsWith(prefix)
                ? rawText.substring(prefix.length()).trim()
                : rawText.trim();
    }

    private boolean isNumeric(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
