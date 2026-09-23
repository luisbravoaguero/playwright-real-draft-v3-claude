package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.regex.Pattern;

import static com.mapfre.utils.UiSync.waitForAppIdle;

public class InclusionPlanillaSctrMineriaPage extends BasePage {
    private final Locator lblInclusionPlanilla;
    private final Locator btnImportarPlanilla;
    private final Locator btnProcesar;
    private final Locator btnGenerar;
    private final Locator modalListaObservaciones;
    private final Locator btnAceptarListaObservaciones;


    public InclusionPlanillaSctrMineriaPage(Page page) {
        super(page);
        this.lblInclusionPlanilla =  page.getByRole( AriaRole.HEADING, new Page.GetByRoleOptions().setName("Inclusión de planilla"));
        this.btnImportarPlanilla =  page.locator("input#iImportarPlanilla");
        this.btnProcesar = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Procesar"));
        this.btnGenerar = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Generar"));
        this.modalListaObservaciones = page.locator("section.modalReniecList");
        this.btnAceptarListaObservaciones = modalListaObservaciones.locator("a.g-button") .filter(new Locator.FilterOptions().setHasText("Aceptar"));

    }

    public void assertLoadedInclusionPlanilla() {
        ElementAsserts.assertVisible( lblInclusionPlanilla,"El título Inclusión de planilla debe ser visible");
        log.info("[INCLUSION][PLANILLA] Página Inclusión de planilla cargada correctamente.");
    }
    public void cargarPlanillaInclusionMineria() {

        Path archivoPlanilla = Paths.get(
                "target",
                "test-classes",
                "testdata",
                "uploads",
                "templates",
                "Formato_Inclusion_SCTR_Mineria_Masiva.xls");

        log.info("[INCLUSION][MINERIA] Cargando archivo: {}", archivoPlanilla.toAbsolutePath());
        ElementAsserts.assertVisible(btnImportarPlanilla,"El input de importar planilla debe ser visible");
        btnImportarPlanilla.setInputFiles(archivoPlanilla);
        sync();
        waitForNetworkIdle();
        waitForAppIdle();
        log.info("[INCLUSION][MINERIA] Planilla cargada correctamente.");
    }

    public void clickBtnProcesar() {

        log.info(
                "[INCLUSION][MINERIA] Haciendo clic en el botón Procesar.");

        ElementAsserts.assertVisible(
                btnProcesar,
                "El botón Procesar debe ser visible.");

        clickAndSync(btnProcesar);

        log.info(
                "[INCLUSION][MINERIA] Se hizo clic en el botón Procesar.");

        waitForAppIdle();
    }

    public void clickBtnGenerar() {

        log.info("[INCLUSION][MINERIA] Haciendo clic en el botón Generar.");
        ElementAsserts.assertVisible(btnGenerar,"El botón Generar debe ser visible.");
        clickAndSync(btnGenerar);
        log.info( "[INCLUSION][MINERIA] Se hizo clic en el botón Generar.");
    }

    public void aceptarListaObservacionesInclusionMineria() {

        log.info("[INCLUSION][MINERIA] Validando lista de observaciones.");
        ElementAsserts.assertVisible(modalListaObservaciones,"La lista de observaciones debe ser visible.");
        ElementAsserts.assertVisible( btnAceptarListaObservaciones,"El botón Aceptar debe ser visible.");
        clickAndSync(btnAceptarListaObservaciones);

        log.info("[INCLUSION][MINERIA] Se aceptó la lista de observaciones.");
    }
}
