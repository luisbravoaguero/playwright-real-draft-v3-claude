package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ReporteConstanciaDeclaracionVidaLeyPage extends BasePage {

    private final Locator reporteConstanciaTitle;

    private final Locator numeroConstanciaGenerada;

    private final Locator certificadoDocumento;
    private final Locator cartaDocumento;
    private final Locator cargoDocumento;
    private final Locator descargarCertificadoButton;
    private final Locator modalErrorGeneral;

    public ReporteConstanciaDeclaracionVidaLeyPage(Page page) {

        super(page);

        this.reporteConstanciaTitle =
                page.getByText(
                        "REPORTE CONSTANCIA",
                        new Page.GetByTextOptions()
                                .setExact(false));

        this.numeroConstanciaGenerada =
                page.locator("div.g-text-right-sm > b");

        this.certificadoDocumento =
                page.locator("div.row.mx-0")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Certificado"));

        this.cartaDocumento =
                page.locator("div.row.mx-0")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Carta"));

        this.cargoDocumento =
                page.locator("div.row.mx-0")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Cargo"));


        this.descargarCertificadoButton =
                page.locator("div")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Certificado"))
                        .locator("a.g-button")
                        .first();
        this.modalErrorGeneral =
                page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
    }

    public void assertLoaded() {

        log.info(
                "[REPORTE CONSTANCIA][VIDALEY] Validando carga de página");

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                reporteConstanciaTitle,
                "Debe visualizarse el reporte de constancia");

        ElementAsserts.assertVisible(
                numeroConstanciaGenerada,
                "Debe visualizarse el número de constancia");
    }

    public void processAndAssertSuccessProcesarDeclaracion(long timeoutMs, long quietMs) {

        var result = FirstAppearanceRace.waitForFirst(

                page,

                null,               // action (coloca null si la accion click fue realizada)

                modalErrorGeneral,

                numeroConstanciaGenerada,

                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje

                timeoutMs,

                quietMs,                 // quietMs (stability)

                150                      // pollMs

        );
    }

        public void validarReporteGenerado () {

            log.info(
                    "[REPORTE CONSTANCIA][VIDALEY] Validando reporte generado");

            String numeroGenerado =
                    numeroConstanciaGenerada.innerText().trim();

            log.info(
                    "[REPORTE CONSTANCIA][VIDALEY] Número generado {}",
                    numeroGenerado);

            ElementAsserts.assertTrue(
                    numeroGenerado.contains("VLEY/"),
                    "El número de constancia debe contener VLEY/");
        }

        public void validarDocumentosAsociados () {

            log.info(
                    "[REPORTE CONSTANCIA][VIDALEY] Validando documentos asociados");

            ElementAsserts.assertVisible(
                    certificadoDocumento,
                    "Debe visualizarse el documento Certificado");

            ElementAsserts.assertVisible(
                    cartaDocumento,
                    "Debe visualizarse el documento Carta");

            ElementAsserts.assertVisible(
                    cargoDocumento,
                    "Debe visualizarse el documento Cargo");


            log.info(
                    "[REPORTE CONSTANCIA][VIDALEY] Documentos asociados validados correctamente");
        }
        public void descargarCertificado () {

            log.info(
                    "[REPORTE CERTIFICADO][VIDALEY] Descargando certificado");

            int cantidad =
                    descargarCertificadoButton.count();

            log.info(
                    "[REPORTE CERTIFICADO][VIDALEY] Botones encontrados: {}",
                    cantidad);

            ElementAsserts.assertTrue(
                    cantidad > 0,
                    "Debe existir al menos un botón Descargar para el certificado");

            descargarCertificadoButton.first()
                    .scrollIntoViewIfNeeded();

            ElementAsserts.assertVisible(
                    descargarCertificadoButton.first(),
                    "El botón Descargar Certificado debe ser visible");

            clickAndSync(
                    descargarCertificadoButton.first());

            log.info(
                    "[REPORTE CERTIFICADO][VIDALEY] Descarga iniciada correctamente");
        }
    }