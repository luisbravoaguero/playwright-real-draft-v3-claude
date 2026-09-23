package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class ReemplazarPlanillaVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator cargaMasivaButton;
    private final Locator escogerArchivoInput;
    private final Locator nombreArchivoLabel;
    private final Locator estadoCargaLabel;
    private final Locator cargaIndividualOption;
    private final Locator tipoDocumentoSelect;
    private final Locator numeroDocumentoInput;
    private final Locator apellidoPaternoInput;
    private final Locator apellidoMaternoInput;
    private final Locator nombreInput;
    private final Locator nombreCompletoInput;
    private final Locator fechaNacimientoInput;
    private final Locator sueldoInput;
    private final Locator mostrarPlanillaButton;
    private final Locator cargarArchivoInput;
    private final Locator aceptarButton;
    private final Locator confirmarReemplazarButton;
    private final Locator modalAlertaReniec;
    private final Locator modalAlertaReniecAceptar;
    private final Locator modalConfirmacionReemplazo;
    private final Locator resultadoModalMensajeError;
    private final Locator modalErrorGeneral;
    private final Locator modalWarningGeneral;
    private final Locator planillaReemplazoContainer;
    private final Locator aseguradosReemplazoContenedor;
    private final Locator aseguradosReemplazoFila;
    private final Locator reemplazarPlanillaButton;
    private final Locator equifaxModal;
    private final Locator modalInfoGeneral;
    private final Locator modalInfoTitulo;
    private final Locator modalInfoReemplazarButton;
    private final Locator modalSuccessGeneral;
    private final Locator seccionTrabajadorLabel;
    private static final int IDX_NRO_DOC = 1;

    public ReemplazarPlanillaVidaLeyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Reemplazar Planilla"));
        this.cargaMasivaButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Carga masiva"));
        this.escogerArchivoInput = page.locator("#iImportarPlanilla");
        this.nombreArchivoLabel = page.locator("div.clearfix.g-box").filter(new Locator.FilterOptions().setHasText("File_constancia_reemplazo_sctr.xls"));
        this.estadoCargaLabel = page.getByText("Planilla cargada exitosamente");
        this.cargaIndividualOption = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("Cargar individual")));
        this.tipoDocumentoSelect = page.locator("oim-select[label='Tipo de documento'] select");
        this.numeroDocumentoInput = page.locator("oim-input[label='Nro. Documento'] input");
        this.apellidoPaternoInput = page.locator("oim-input[label='Apellido Paterno'] input");
        this.apellidoMaternoInput = page.locator("oim-input[label='Apellido Materno'] input");
        this.nombreInput = page.locator("oim-input[label='Nombre'] input");
        this.nombreCompletoInput = page.locator("oim-input[label='Nombre Completo'] input");
        this.fechaNacimientoInput = page.locator("oim-datepicker[label='Fecha Nacimiento']").locator("input");
        this.sueldoInput = page.locator("oim-input[label='Sueldo'] input");
        this.mostrarPlanillaButton = page.locator("a").filter(new Locator.FilterOptions().setHasText("Mostrar Planilla"));
        this.cargarArchivoInput = page.locator("input#iImportarPlanilla");
        this.aceptarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.confirmarReemplazarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reemplazar"));
        this.modalAlertaReniec = page.locator("div.swal2-popup:has-text('RENIEC')");
        this.modalAlertaReniecAceptar = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar")).filter(new Locator.FilterOptions().setHasText("Aceptar"));
        this.modalConfirmacionReemplazo = page.locator("div.swal2-popup:has-text('Reemplazar')");
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalErrorGeneral = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.modalWarningGeneral = page.locator("div.swal2-popup.swal2-icon-warning[role='dialog']");
        this.planillaReemplazoContainer = page.locator("#iPayrollList");
        this.aseguradosReemplazoContenedor = page.locator("div.gnContentAuto-bg");
        this.aseguradosReemplazoFila = aseguradosReemplazoContenedor.locator("ul.g-tbl-row");
        this.reemplazarPlanillaButton = planillaReemplazoContainer.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Reemplazar planilla"));
        this.equifaxModal = page.locator("section.gSweetModal.modalReniecList");
        this.modalInfoGeneral = page.locator("div.swal2-popup.swal2-icon-info[role='dialog']");
        this.modalInfoTitulo = modalInfoGeneral.locator("h2.swal2-title");
        this.modalInfoReemplazarButton = modalInfoGeneral.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("REEMPLAZAR"));
        this.modalSuccessGeneral = page.locator("div.swal2-popup.swal2-icon-success[role='dialog']");
        this.seccionTrabajadorLabel = page.locator("nsctr-workers-information");
    }

    public void assertLoadedReemplazarPlanillaVidaLey() {
        ElementAsserts.assertVisible(title, "TITULO Reemplazar Planilla DEBE SER VISIBLE");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Página Reemplazar Planilla cargada correctamente");
    }

    public void fillFormDatosDeLosAseguradosVidaLey(Path template, String nombreArchivo) {
        clickAndSync(cargaMasivaButton);
        escogerArchivoInput.setInputFiles(template);
        ElementAsserts.assertVisible(estadoCargaLabel, "Estado de carga del archivo excel subido DEBE SER VISIBLE indicando éxito");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Archivo XLS cargado y procesado exitosamente: {}", nombreArchivo);
        //clickAndSync(mostrarPlanillaButton);
        //log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Planilla mostrada correctamente para verificación de datos");
    }



    public void clickMostrarPlanillaButtonVidaLey() {
        ElementAsserts.assertVisible(mostrarPlanillaButton, "Botón 'Mostrar Planilla' DEBE SER VISIBLE");
        page.addLocatorHandler(equifaxModal, locator -> {
            log.warn("[REEMPLAZAR PLANILLA][REEMPLAZO] Se detectó un modal de advertencia de Equifax. Se procederá a cerrarlo");
            Locator modalAceptarButton = equifaxModal.locator("a").filter(new Locator.FilterOptions().setHasText("Aceptar"));
            clickAndSync(modalAceptarButton);
            log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Modal de advertencia de Equifax cerrado correctamente");
        });
        scrollToBottom();
        waitRandomBetween(1000);
        mostrarPlanillaButton.click();
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        //page.mouse().move(0, 0);
        //mostrarPlanillaButton.describe("Botón 'Mostrar Planilla' clickeado");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Botón 'Mostrar Planilla' clickeado y planilla mostrada correctamente");
    }

    public void proccessAndAssertSuccessMostrarPlanillaVidaLey(long timeoutMs, long quietMs) {
        page.addLocatorHandler(equifaxModal, locator -> {
            log.warn("[REEMPLAZAR PLANILLA][REEMPLAZO] Se detectó un modal de advertencia de Equifax. Se procederá a cerrarlo");
            Locator modalAceptarButton = equifaxModal.locator("a").filter(new Locator.FilterOptions().setHasText("Aceptar"));
            clickAndSync(modalAceptarButton);
            log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Modal de advertencia de Equifax cerrado correctamente");
        });
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // Realizar click en el botón procesar
                modalErrorGeneral,          // Elemento esperado: mensaje de error
                seccionTrabajadorLabel,    // Elemento esperado:
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
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Se muestra la sección de trabajadores indicando que la planilla fue cargada correctamente");
    }

    public void validarListaPlanillaReemplazoContenedor(String numeroDocumento) {
        scrollToBottom();
        waitRandomBetween(1000);
        ElementAsserts.assertVisible(planillaReemplazoContainer, "Contenedor de la lista de planilla de reemplazo DEBE SER VISIBLE");
        ElementAsserts.assertVisible(aseguradosReemplazoContenedor, "Lista de asegurados de la planilla de reemplazo DEBE SER VISIBLE");
        for (int i = 0; i < aseguradosReemplazoFila.count(); i++) {
            Locator fila = aseguradosReemplazoFila.nth(i);
            Locator columnaNumeroDocumento = fila.locator("li").nth(IDX_NRO_DOC);
            String numeroDocum = columnaNumeroDocumento.textContent().trim();
            if (!numeroDocumento.equals(numeroDocum)) {
                throw new FrameworkException("[REEMPLAZAR PLANILLA][REEMPLAZO] La fila " + (i + 1) + " de la lista de asegurados no coincide con el número de documento esperado. Se esperaba: " + numeroDocumento + ", pero se encontró: " + numeroDocum);
            }
        }
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] El documento del asegurado a reemplazar {} se encuentra correctamente en la lista de planilla de reemplazo", numeroDocumento);
    }

    public void validarVisibilidadMensajeCargaAseguradosExitosaVidaLey() {
        ElementAsserts.assertVisible(mostrarPlanillaButton, "Botón 'Mostrar Planilla' DEBE SER VISIBLE");
        page.addLocatorHandler(equifaxModal, locator -> {
            log.warn("[REEMPLAZAR PLANILLA][REEMPLAZO] Se detectó un modal de advertencia de Equifax. Se procederá a cerrarlo");
            Locator modalAceptarButton = equifaxModal.locator("a").filter(new Locator.FilterOptions().setHasText("Aceptar"));
            clickAndSync(modalAceptarButton);
            log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Modal de advertencia de Equifax cerrado correctamente");
        });
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        waitRandomBetween(1000);
        ElementAsserts.assertVisible(seccionTrabajadorLabel,"Sección de trabajadores no es visible, la carga de asegurados podría haber fallado");
        ElementAsserts.assertTextMatches(seccionTrabajadorLabel, Pattern.compile(".*Se ha cargado un total de\\s+\\d+\\s+trabajador\\(es\\).*"),"Error al cargar trabajadores");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Mensaje de carga de asegurados exitosa verificado correctamente");
    }

    public void proccessAndAssertSuccessReemplazarPlanillaVidaLey(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(reemplazarPlanillaButton, "Botón Reemplazar Planilla DEBE SER VISIBLE");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                reemplazarPlanillaButton::click,               // Realizar click en el botón procesar
                modalErrorGeneral,          // Elemento esperado: mensaje de error
                modalInfoGeneral,    // Elemento esperado:
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
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Se muestra el modal un modal de tipo INFO");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Se validará que sea el modal 'REEMPLAZAR PLANILLA'");
        modalInfoGeneral.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        ElementAsserts.assertContainsTextIgonoreCase(modalInfoTitulo, "REEMPLAZAR PLANILLA", "El modal de información DEBE contener el título 'REEMPLAZAR PLANILLA'");
        ElementAsserts.assertVisible(modalInfoReemplazarButton, "Botón 'REEMPLAZAR' en el modal DEBE SER VISIBLE dentro del modal 'REEMPLAZAR PLANILLA'");
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO]Se muestra el modal de información de reemplazo de planilla");
    }

    public void proccessAndAssertSuccessReemplazarModalVidaLey(long timeoutMs, long quietMs) {
        ElementAsserts.assertVisible(modalInfoReemplazarButton, "Botón 'REEMPLAZAR' en el modal DEBE SER VISIBLE dentro del modal 'REEMPLAZAR PLANILLA'");
        var result = FirstAppearanceRace.waitForFirst(
                page,
                modalInfoReemplazarButton::click,               // Realizar click en el botón procesar
                modalErrorGeneral,          // Elemento esperado: mensaje de error
                modalSuccessGeneral,    // Elemento esperado:
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
        log.info("[REEMPLAZAR PLANILLA][REEMPLAZO] Reemplazo de planilla procesado exitosamente");
    }


}