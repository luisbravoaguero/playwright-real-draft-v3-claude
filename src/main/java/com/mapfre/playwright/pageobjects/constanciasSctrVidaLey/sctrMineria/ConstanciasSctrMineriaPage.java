package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class ConstanciasSctrMineriaPage extends BasePage {
    private final Locator title;
    private final Locator contenedorTabla;
    private final Locator filasTabla;
    private final Locator filasTotales;
    private final Locator botonesReemplazar;
    private final Locator modalAlertaExito;
    private final Locator modalAlertaExitoTexto;
    private final Locator filas;
    private static final int IDX_EDITAR = 1;
    private static final int IDX_NRO_DOC = 3;
    private static final int IDX_ESTADO = 15;
    private static final int IDX_TIPO_MOVIMIENTO = 8;
    private final Locator reemplazarPlanillaButton;
    private final Locator reemplazarPlanillaTitle;
    private final Locator cargaMasivaButton;
    private final Locator escogerArchivoInput;
    private final Locator nombreArchivoLabel;
    private final Locator estadoCargaLabel;
    private final Locator modalErrorGeneral;
    private final Locator sinResultadosMensaje;
    private final Locator modalActAsegurado;
    private final Locator modalActAseguradoTitulo;
    private final Locator modalActAseguradoEstado;
    private final Locator modalActAseguradoDarBajaButton;
    private final Locator modalAlertaExitoBajaExitosa;
    private final Locator modalAlertaExitoOkButton;
    private final Locator tablaContenedorDatosAsegurado;
    private final Locator modalActAseguradoRehabilitarButton;
    private final Locator generarConstanciaButton;
    private final Locator constanciaIndividualButton;
    private final Locator modalGenerarConstancia;
    private final Locator modalGenerarConstanciaTitulo;
    private final Locator modalGenerarConstanciaInput;
    private final Locator modalGenerarConstanciaLink;
    private final Locator ventanaErrorTitulo;
    private final Locator ventanaErrorDescripcion;
    private final Locator modalAlertaExitoDescargarConstanciaButton;
    public ConstanciasSctrMineriaPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Constancias"));
        this.contenedorTabla = page.locator("div.gnContentAuto-bg");
        this.filasTabla = page.locator("table tbody tr");
        this.filasTotales = page.locator("ul.g-tbl-row");
        this.botonesReemplazar = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Reemplazar")));
        this.modalAlertaExito = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.modalAlertaExitoTexto = page.locator("div.swal2-popup.swal2-icon-success h2, div.swal2-popup.swal2-icon-success div.swal2-title");
        this.filas = contenedorTabla.locator("ul.g-tbl-row");
        this.reemplazarPlanillaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Reemplazar planilla",Pattern.CASE_INSENSITIVE)));
        this.reemplazarPlanillaTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Reemplazar Planilla"));
        this.cargaMasivaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Carga masiva"));
        this.escogerArchivoInput = page.locator("#iImportarPlanilla");
        this.nombreArchivoLabel = page.locator("div.clearfix.g-box").filter(new Locator.FilterOptions().setHasText("File_constancia_reemplazo_sctr.xls"));
        this.estadoCargaLabel = nombreArchivoLabel.getByText("Planilla cargada exitosamente", new Locator.GetByTextOptions().setExact(true));
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.sinResultadosMensaje = page.getByText("No hay resultados para los");
        this.modalActAsegurado = page.locator("mat-dialog-container");
        this.modalActAseguradoTitulo = modalActAsegurado.locator("h2").filter(new Locator.FilterOptions().setHasText("Actualizar datos del asegurado"));
        this.modalActAseguradoEstado = modalActAsegurado.locator("span").filter(new Locator.FilterOptions().setHasText("Habilitado"));
        this.modalActAseguradoDarBajaButton = modalActAsegurado.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Dar baja"));
        this.modalAlertaExitoBajaExitosa = modalAlertaExito.locator("#swal2-html-container");
        this.modalAlertaExitoOkButton = modalAlertaExito.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Ok"));
        this.tablaContenedorDatosAsegurado = page.locator(".g-tbl-wrapper");
        this.modalActAseguradoRehabilitarButton = modalActAsegurado.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Rehabilitar"));
        this.generarConstanciaButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Generar constancia"));
        this.constanciaIndividualButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Individual"));
        this.modalGenerarConstancia = page.locator("mat-dialog-container");
        this.modalGenerarConstanciaTitulo = modalGenerarConstancia.locator("h1").filter(new Locator.FilterOptions().setHasText("Generar Constancia"));
        this.modalGenerarConstanciaInput = modalGenerarConstancia.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Datos de Obra o Centro de Trabajo"));
        this.modalGenerarConstanciaLink = modalGenerarConstancia.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Generar constancia"));
        this.modalAlertaExitoDescargarConstanciaButton = modalAlertaExito.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Descargar constancia"));
        this.ventanaErrorTitulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("ERROR"));
        this.ventanaErrorDescripcion = page.getByText("Ocurrió un error inesperado.");
    }

    public void assertLoadedSctrMineria() {
        ElementAsserts.assertVisible(title, "La página de Constancias SCTR General DEBE ESTAR VISIBLE");
        log.info("[CONSTANCIAS] Página Constancias SCTR General cargada correctamente");
    }

    public void proccessAndAssertSuccessBotonPlanillaSctrMineria(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // Realizar click en el botón procesar
                modalErrorGeneral,          // Elemento esperado: mensaje de error
                title,    // Elemento esperado: lista de observaciones
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
        log.info("[CONSTANCIAS][REEMPLAZO] Botón 'Planilla' procesado correctamente, resultado");
    }

    public void validarVisualizacionTablaAseguradosSctrMineria() {
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
        scrollToBottom();
        log.info("[CONSTANCIAS] Se muestra la tabla de asegurados");
    }

    public String obtenerPrimerDocumentoActivoUnicoSctrMineria() {
        validarVisualizacionTablaAseguradosSctrMineria();

        String docActivo = null;

        int conteoFilas = filas.count();
        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            ElementAsserts.assertTrue(celdas.count() > IDX_ESTADO,
                    "Fila " + i + " no contiene las columnas esperadas");

            String estado = celdas.nth(IDX_ESTADO).innerText().trim();
            String nroDoc = celdas.nth(IDX_NRO_DOC).innerText().trim();

            if ("ACTIVO".equalsIgnoreCase(estado)) {
                docActivo = nroDoc;
                break;
            }
        }

        ElementAsserts.assertNotNull(docActivo, "El documento ACTIVO no debe ser nulo");
        ElementAsserts.assertTrue(!docActivo.isBlank(), "El documento ACTIVO no debe estar vacío");
        log.info("[CONSTANCIAS] Asegurado con estado activo encontrado: {}", docActivo);
        return docActivo;
    }

    public String obtenerSueldoAseguradoActivoPorNumeroDocumentoSctrMineria(String numeroDocumentoActivo) {
        String sueldoAsegurado = null;

        int conteoFilas = filas.count();
        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            String nroDoc = celdas.nth(IDX_NRO_DOC).innerText().trim();
            if (numeroDocumentoActivo.equalsIgnoreCase(nroDoc)) {
                Locator editarButton = celdas.nth(IDX_EDITAR).locator("a");
                clickAndSync(editarButton);
                Locator detalleAseguradoModal = page.locator("mat-dialog-container[role='dialog']").filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Actualizar datos del asegurado").setLevel(2))));
                ElementAsserts.assertVisible(detalleAseguradoModal, "El modal de detalle del asegurado DEBE SER VISIBLE");
                Locator sueldoAseguradoInput = detalleAseguradoModal.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Sueldo S/."));
                sueldoAsegurado = sueldoAseguradoInput.inputValue().trim();
                Locator cerrarButton = detalleAseguradoModal.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Cancelar"));
                clickAndSync(cerrarButton);
                log.info("[CONSTANCIAS] Sueldo del asegurado con documento {} obtenido: {}", numeroDocumentoActivo, sueldoAsegurado);
                break;
            }
        }
        return sueldoAsegurado;
    }

    public void seleccionarAseguradoActivoParaReemplazoSctrMineria(String numeroDocumentoActivo) {
        obtenerPrimerDocumentoActivoUnicoSctrMineria();

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
            Locator checkbox = fila.locator("li").first().locator("input[type='checkbox']");
            ElementAsserts.assertTrue(checkbox.count() == 1,
                    "Se esperaba un checkbox en la fila " + i + ", pero se encontraron: " + checkbox.count());

            if (numeroDocumento.equals(numeroDocumentoActivo) && "ACTIVO".equalsIgnoreCase(estado)) {
                checkbox.check();
                waitRandomBetween(1000);
                validarUnaSolaFilaActivaSeleccionadaSctrMineria();
                return;
            }
        }
    }

    private void validarUnaSolaFilaActivaSeleccionadaSctrMineria() {
        int conteoActivosSeleccionados = 0;
        int conteoFilas = filas.count();

        for (int i = 0; i < conteoFilas; i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            String estado = celdas.nth(IDX_ESTADO).innerText().trim();
            Locator checkbox = fila.locator("li").first().locator("input[type='checkbox']");

            if ("ACTIVO".equalsIgnoreCase(estado) && checkbox.isChecked()) {
                conteoActivosSeleccionados++;
            }
        }

        ElementAsserts.assertTrue(conteoActivosSeleccionados == 1,
                "Se esperaba exactamente una fila ACTIVO seleccionada, pero se encontraron: " + conteoActivosSeleccionados);
        log.info("[CONSTANCIAS][REEMPLAZO] Validación de una sola fila ACTIVO seleccionada completada correctamente");
    }

    public void proccessAndAssertSuccessReemplazarPlanillaSctrMineria(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                reemplazarPlanillaButton::click,               // Realizar click en el botón procesar
                modalErrorGeneral,          // Elemento esperado: mensaje de error
                reemplazarPlanillaTitle,    // Elemento esperado:
                null,       // coloca null si el mensaje del error esta en modalErrorGeneral
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
        log.info("[CONSTANCIAS][REEMPLAZO] Botón 'Reemplazar Planilla' procesado correctamente, resultado");
    }

    public void validarAseguradoReemplazantePorNumeroDeDocumentoSctrMineria(String numeroDocumento) {
        validarVisualizacionTablaAseguradosSctrMineria();
        //ElementAsserts.assertVisible(filas, "La tabla de asegurados DEBE SER VISIBLE");
        boolean encontrado = false;

        for (int i = 0; i < filas.count(); i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            String nroDoc = celdas.nth(IDX_NRO_DOC).innerText().trim();
            String estado = celdas.nth(IDX_ESTADO).innerText().trim();
            String tipoMovimiento = celdas.nth(IDX_TIPO_MOVIMIENTO).innerText().trim();
            if (numeroDocumento.equalsIgnoreCase(nroDoc) && "ACTIVO".equalsIgnoreCase(estado) && "REEMPLAZO".equalsIgnoreCase(tipoMovimiento)) {
                encontrado = true;
                break;
            }
        }

        ElementAsserts.assertTrue(encontrado, "No se encontró el asegurado reemplazante con número de documento: " + numeroDocumento + " y estado ACTIVO");
        log.info("[CONSTANCIAS][REEMPLAZO] Asegurado reemplazante con número de documento {} con tipo de movimiento REEMPLAZO y estado ACTIVO validado correctamente", numeroDocumento);
    }

    public void validarAseguradoReemplazadoConEstadoInactivoSctrMineria(String numeroDocumentoAseguradoReemplazado) {
        validarVisualizacionTablaAseguradosSctrMineria();
        //ElementAsserts.assertVisible(filas, "La tabla de asegurados DEBE SER VISIBLE");
        boolean encontrado = false;

        for (int i = 0; i < filas.count(); i++) {
            Locator fila = filas.nth(i);
            Locator celdas = fila.locator("li");
            String nroDoc = celdas.nth(IDX_NRO_DOC).innerText().trim();
            String estado = celdas.nth(IDX_ESTADO).innerText().trim();

            if (numeroDocumentoAseguradoReemplazado.equalsIgnoreCase(nroDoc) && "INACTIVO".equalsIgnoreCase(estado)) {
                encontrado = true;
                break;
            }
        }
        ElementAsserts.assertTrue(encontrado, "No se encontró el asegurado reemplazado con número de documento: " + numeroDocumentoAseguradoReemplazado + " y estado INACTIVO");
        log.info("[CONSTANCIAS][REEMPLAZO] Asegurado reemplazado con número de documento {} y estado INACTIVO validado correctamente", numeroDocumentoAseguradoReemplazado);
    }
}
