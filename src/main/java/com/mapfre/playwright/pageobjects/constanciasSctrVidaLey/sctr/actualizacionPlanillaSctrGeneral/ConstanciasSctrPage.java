package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.actualizacionPlanillaSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DownloadVsErrorRace;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.FirstDisappearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConstanciasSctrPage extends BasePage {
    private final Locator sinResultadosMensaje;
    private final Locator contenedorTabla;
    private final Locator filas;
    private static final int IDX_EDITAR = 1;
    private static final int IDX_NRO_DOC = 3;
    private static final int IDX_ESTADO = 12;
    private static final int IDX_ESTADO_VIDALEY = 14;
    private static final int IDX_ESTADO_SCTR_MINERIA = 15;
    private final Locator filasTotales;
    private final Locator modalErrorGeneral;
    private final Locator modalActAsegurado;
    private final Locator modalActAseguradoEstado;
    private final Locator modalActAseguradoDarBajaButton;
    private final Locator modalAlertaExito;
    private final Locator modalAlertaExitoBajaExitosa;
    private final Locator modalAlertaExitoOkButton;
    private final Locator tablaContenedorDatosAsegurado;
    private final Locator generarConstanciaButton;
    private final Locator constanciaIndividualButton;
    private final Locator modalGenerarConstancia;
    private final Locator modalGenerarConstanciaInput;
    private final Locator modalGenerarConstanciaLink;
    private final Locator modalAlertaExitoDescargarConstanciaButton;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    public ConstanciasSctrPage(Page page) {
        super(page);
        this.sinResultadosMensaje = page.getByText("No hay resultados para los");
        this.contenedorTabla = page.locator("div.gnContentAuto-bg");
        this.filas = contenedorTabla.locator("ul.g-tbl-row");
        this.filasTotales = page.locator("ul.g-tbl-row");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalActAsegurado = page.locator("mat-dialog-container");
        this.modalActAseguradoEstado = modalActAsegurado.locator("span").filter(new Locator.FilterOptions().setHasText("Habilitado"));
        this.modalActAseguradoDarBajaButton = modalActAsegurado.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Dar baja"));
        this.modalAlertaExito = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.modalAlertaExitoBajaExitosa = modalAlertaExito.locator("#swal2-html-container");
        this.modalAlertaExitoOkButton = modalAlertaExito.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.tablaContenedorDatosAsegurado = page.locator(".g-tbl-wrapper");
        this.generarConstanciaButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Generar constancia"));
        this.constanciaIndividualButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Individual"));
        this.modalGenerarConstancia = page.locator("mat-dialog-container");
        this.modalGenerarConstanciaInput = modalGenerarConstancia.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Datos de Obra o Centro de Trabajo"));
        this.modalGenerarConstanciaLink = modalGenerarConstancia.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Generar constancia"));
        this.modalAlertaExitoDescargarConstanciaButton = modalAlertaExito.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Descargar constancia"));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR"));
        this.ventanaErrorDescripcion = page.getByText("Ocurrió un error inesperado.");
    }

    public void esperarHastaCargarTablaAsegurados() {
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        page.addLocatorHandler(sinResultadosMensaje, locator -> {
            throw new AssertExceptions("No se encontraron resultados en la tabla de asegurados");
        });
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        ElementAsserts.assertVisible(contenedorTabla, "Tabla de asegurados DEBE SER VISIBLE");
        ElementAsserts.assertTrue(filas.count() > 0, "Se esperaban filas en la tabla de movimientos");
        log.info("[CONSTANCIAS] Se muestra la tabla de asegurados");
    }

    public String obtenerPrimerDocumentoActivo() {
        esperarHastaCargarTablaAsegurados();

        int conteoFilas = filas.count();
        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");

            ElementAsserts.assertTrue(celdas.count() > IDX_ESTADO, "Fila " + i + " no contiene las columnas esperadas");

            String estado = celdas.nth(IDX_ESTADO).innerText().trim();

            if ("ACTIVO".equalsIgnoreCase(estado)) {
                String docActivo = celdas.nth(IDX_NRO_DOC).innerText().trim();

                ElementAsserts.assertNotNull(docActivo, "El documento ACTIVO no debe ser nulo");
                ElementAsserts.assertTrue(!docActivo.isBlank(), "El documento ACTIVO no debe estar vacío");

                log.info("[CONSTANCIAS] Asegurado con estado activo encontrado: {}", docActivo);
                return docActivo;
            }
        }

        throw new FrameworkException("No se encontró ningún documento con estado ACTIVO en la lista de asegurados, página Constancias");
    }

    public void seleccionarBotonEditarDeUnAseguradoActivo(String numeroDocumentoActivo) {
        int conteoFilas = filasTotales.count();
        ElementAsserts.assertTrue(conteoFilas > 0, "No se encontraron filas en la tabla");

        Locator checkboxCabecera = filasTotales.nth(0).locator("li").first().locator("input[type='checkbox']");
        ElementAsserts.assertTrue(checkboxCabecera.count() == 1, "Se esperaba un checkbox cabcera en la fila " + 0 + ", pero se encontraron: " + checkboxCabecera.count());
        checkboxCabecera.uncheck();
        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            String estado = celdas.nth(IDX_ESTADO).innerText().trim();
            String numeroDocumento = celdas.nth(IDX_NRO_DOC).innerText().trim();
            Locator editarButton = celdas.nth(IDX_EDITAR).locator("a");
            if (numeroDocumento.equals(numeroDocumentoActivo) && "ACTIVO".equalsIgnoreCase(estado)) {
                editarButton.click();
                log.info("[CONSTANCIAS] Boton editar clickeado del asegurado: {}", numeroDocumentoActivo);
                return;
            }
        }
    }

    public void processAndAssertSuccessEditarAseguradoButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                modalActAsegurado,
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
        log.info("[ACTUALIZACION][CONSTANCIAS] Se muestra el modal 'Actualizar Datos del Asegurado'");
    }

    public void validarEstadoHabilitadoDelAseguradoDentroDelModal(String numeroDocumentoActivo) {
        ElementAsserts.assertVisible(modalActAsegurado, "El modal 'Actualizar Datos del Asegurado' debe ser visible");
        ElementAsserts.assertContainsText(modalActAseguradoEstado.first(), "Habilitado", "El estado del asegurado "+numeroDocumentoActivo+" no es igual a 'Habilitado'");
        log.info("[ACTUALIZACION][CONSTANCIAS] Asegurado con estado habilitado listo para dar de baja");
    }

    public void processAndAssertSuccessDarBajaAseguradoButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalActAseguradoDarBajaButton::click,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                modalAlertaExito,
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
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertContainsText(modalAlertaExitoBajaExitosa,"Se dio de baja al asegurado", "No se mostró el mensaje 'Se dio de baja al asegurado' despues de dar de baja al asegurado");
        log.info("[ACTUALIZACION][MODAL][CONSTANCIAS] Se dio de baja al asegurado");
    }

    public void processAndAssertSuccessModalOkAseguradoButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(modalAlertaExito, "El modal 'Se dio de baja al asegurado' debe ser visible antes de dar click en el boton OK");
        var result = FirstDisappearanceRace.waitForFirst(
                page,
                modalAlertaExitoOkButton::click,               // action: ejecutar el click
                modalErrorGeneral,                   // errorRoot: si aparece este elemento = ERROR
                modalActAsegurado,               // successRoot: si desaparece este elemento = SUCCESS
                null,                                    // errorMessageLocator: null para usar errorRoot
                timeoutMs,                               // timeout máximo
                quietMs,                                 // quietMs (estabilidad)
                150                                      // pollMs (intervalo de sondeo)
        );

        if (result.outcome() == FirstDisappearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstDisappearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout. El elemento no desapareció dentro de " + timeoutMs
                    + " ms (después de " + result.elapsedMs() + " ms).");
        }
        // SUCCESS: continue
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        log.info("[ACTUALIZACION][MODAL][CONSTANCIAS] El modal 'Actualizar Datos del Asegurado' se ha cerrado exitosamente");
    }

    public void validarEstadoInactivoDelAsegurado(String numeroDocumentoAsegurado) {
        esperarHastaCargarTablaAsegurados();

        int conteoFilas = filasTotales.count();
        ElementAsserts.assertTrue(conteoFilas > 0, "No se encontraron filas en la tabla");

        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");

            String estado = celdas.nth(IDX_ESTADO).innerText().trim();
            String numeroDocumento = celdas.nth(IDX_NRO_DOC).innerText().trim();

            if (numeroDocumento.equals(numeroDocumentoAsegurado)) {
                ElementAsserts.assertTrue("INACTIVO".equalsIgnoreCase(estado), "Estado del asegurado " + numeroDocumentoAsegurado + " no es INACTIVO");
                log.info("[CONSTANCIAS] Se dio de baja satisfactoriamente al asegurado {}, ahora con estado INACTIVO", numeroDocumentoAsegurado);
                return;
            }
        }

        throw new FrameworkException("No se encontró el documento " + numeroDocumentoAsegurado + " en la lista de asegurados, página Constancias");
    }

    public void visualizarParteDerechaDeLContenido() {
        tablaContenedorDatosAsegurado.evaluate("(el) => { el.scrollLeft = el.scrollWidth; }");
        waitRandomBetween(1000);
        log.info("[ACTUALIZACION][CONSTANCIAS] Scroll realizado hacia la derecha");
    }

    public void processAndAssertSuccessGenerarConstaciaIndividualButton(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(generarConstanciaButton, "El botón 'Generar Constancia Individual' debe ser visible antes de dar click");
        clickAndSync(generarConstanciaButton);
        var result = FirstAppearanceRace.waitForFirst(
                page,
                constanciaIndividualButton::click,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                modalGenerarConstancia,
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
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        log.info("[REIMPRESION][CONSTANCIAS] Botón 'Generar Constancia Individual' procesado correctamente, resultado");
    }

    public void fillModalFormGenerarConstanciaIndividual() {
        fillAndSync(modalGenerarConstanciaInput, "MAPFRE OBRAS");
        log.info("[REIMPRESION][CONSTANCIAS] Modal 'Generar Constancia Individual' llenado correctamente");
    }

    public void processAndAssertSuccessModalGenerarConstanciaIndividualVidaLeyButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalGenerarConstanciaLink::click,               // action (coloca null si la accion click fue realizada)
                modalErrorGeneral,
                modalAlertaExito,
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
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        ElementAsserts.assertContainsPatternText(modalAlertaExitoBajaExitosa,"Se generó la Constancia VLEY/\\d+ con éxito", "No se mostró el mensaje 'Se generó la Constancia VLEY con éxito' despues de generar la constancia individual");
        log.info("[REIMPRESION][MODAL][CONSTANCIAS] Se generó la Reimpresion de la Constancia VLEY con éxito");
    }

    public void processAndAssertSuccessDownloadReimpresionConstanciaVidaLey(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(modalAlertaExito," El modal 'Se generó la Constancia Vida Ley con éxito' debe ser visible antes de dar click en el boton Descargar");
        ElementAsserts.assertVisible(modalAlertaExitoDescargarConstanciaButton,"El botón Descargar Constancia Vida Ley debe ser visible antes de dar click");

        DownloadVsErrorRace.Result r = DownloadVsErrorRace.race(
                page,
                modalAlertaExitoDescargarConstanciaButton::click,
                ventanaErrorTitulo,
                ventanaErrorDescripcion,
                timeoutMs, // timeoutMs
                quietMs, // quietMs (estabilidad para evitar falsos positivos por parpadeo)
                80 // pollMs
        );

        // 3) Decide outcome
        if (r.outcome() == DownloadVsErrorRace.Outcome.ERROR_UI_FIRST) {
            // best-effort evidence + cleanup
            ElementAsserts.assertUIMessage("Apareció un error modal antes de que finalizara la descarga. Mensaje: " + r.uiErrorText());
        }

        if (r.outcome() == DownloadVsErrorRace.Outcome.DOWNLOAD_FAILED) {
            throw new FrameworkException("La descarga finalizó pero FALLÓ. error=" + r.downloadFailure() +
                    " nombre_archivo=" + r.filename() + " url=" + r.url());
        }

        if (r.outcome() == DownloadVsErrorRace.Outcome.TIMEOUT) {
            throw new FrameworkException("Ni el error modal ni la finalización de la descarga ocurrieron dentro del tiempo de espera. Reimpresion Constancia Vida Ley Descargar");
        }

        // DOWNLOAD_SUCCESS -> continue
        log.info("[REIMPRESION][CONSTANCIAS] Descarga de Reimpresion de constancia Vida Ley completada exitosamente. Archivo: {}", r.filename());
    }
}
