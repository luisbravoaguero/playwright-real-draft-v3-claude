package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ReporteConstanciaInclusionVidaLeyPage extends BasePage {

    private final Locator constanciaNumeroDocumento;

    private final Locator certificadoDocumento;
    private final Locator cartaDocumento;
    private final Locator cargoDocumento;
    private final Locator constanciaDocumento;

    private final Locator descargarConstanciaButton;
    private final Locator modalErrorGeneral;

    public ReporteConstanciaInclusionVidaLeyPage(Page page) {

        super(page);


        this.constanciaNumeroDocumento =
                page.locator("b")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Nro. VLEY/")
                        );

        this.certificadoDocumento =
                page.locator("div.g-box-attachment")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Certificado"));

        this.cartaDocumento =
                page.locator("div.g-box-attachment")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Carta"));

        this.cargoDocumento =
                page.locator("div.g-box-attachment")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Cargo"));

        this.constanciaDocumento =
                page.locator("div.g-box-attachment")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Constancia"));
        this.descargarConstanciaButton =
                constanciaDocumento
                        .locator("xpath=ancestor::div[contains(@class,'row')]")
                        .getByText("Descargar");
        this.modalErrorGeneral =
                page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");

    }

    public void assertLoaded() {

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                constanciaNumeroDocumento,
                "Debe visualizarse el número de constancia generado");
    }

    public void processAndAssertSuccessProcesarInclusion(long timeoutMs, long quietMs) {

        var result = FirstAppearanceRace.waitForFirst(

                page,

                null,               // action (coloca null si la accion click fue realizada)

                modalErrorGeneral,

                constanciaNumeroDocumento,

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

        log.info("");

    }


    public void validarReporteGenerado() {

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Validando reporte generado");

        ElementAsserts.assertVisible(
                constanciaNumeroDocumento,
                "Debe visualizarse el número de constancia generado");

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Número generado {}",
                constanciaNumeroDocumento.textContent());
    }

    public void validarDocumentosAsociados() {

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Validando documentos asociados");

        constanciaDocumento.scrollIntoViewIfNeeded();

        ElementAsserts.assertVisible(
                certificadoDocumento,
                "Debe visualizarse el documento Certificado");

        ElementAsserts.assertVisible(
                cartaDocumento,
                "Debe visualizarse el documento Carta");

        ElementAsserts.assertVisible(
                cargoDocumento,
                "Debe visualizarse el documento Cargo");


    }

    public void descargarConstancia() {

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Iniciando descarga de constancia");

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Elementos encontrados: {}",
                descargarConstanciaButton.count());

        descargarConstanciaButton.first().scrollIntoViewIfNeeded();

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Scroll realizado hacia botón Descargar");

        ElementAsserts.assertVisible(
                descargarConstanciaButton.first(),
                "El botón Descargar Constancia debe ser visible");

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Botón Descargar visible correctamente");

        clickAndSync(
                descargarConstanciaButton.first());

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Click realizado sobre Descargar Constancia");
    }

}