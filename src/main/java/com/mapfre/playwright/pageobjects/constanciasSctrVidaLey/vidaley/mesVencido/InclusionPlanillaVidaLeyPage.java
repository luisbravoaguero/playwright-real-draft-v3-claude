package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class InclusionPlanillaVidaLeyPage extends BasePage {

    private final Locator inclusionPlanillaTitle;

    private final Locator procesarButton;
    private final Locator generarButton;

    private final Locator popupListaObservaciones;
    private final Locator aceptarListaObservacionesButton;

    private final Locator popupGenerarInclusion;
    private final Locator confirmarGenerarButton;

    private final Locator popupInclusionExitosa;
    private final Locator aceptarPopupInclusionExitosaButton;


    public InclusionPlanillaVidaLeyPage(Page page) {
        super(page);

        this.inclusionPlanillaTitle =
                page.getByRole(
                        AriaRole.HEADING,
                        new Page.GetByRoleOptions()
                                .setName(
                                        Pattern.compile(
                                                "Inclusión de planilla",
                                                Pattern.CASE_INSENSITIVE)));


        this.procesarButton =
                page.locator("a.g-button.block")
                        .filter(new Locator.FilterOptions()
                                .setHasText("Procesar"));





        this.generarButton =
                page.getByText("Generar", new Page.GetByTextOptions().setExact(true));

        this.popupListaObservaciones =
                page.getByText(
                        Pattern.compile(
                                "Lista de observaciones",
                                Pattern.CASE_INSENSITIVE));



        this.aceptarListaObservacionesButton =
                page.locator("a.g-button.g-button--second-design")
                        .filter(new Locator.FilterOptions()
                                .setHasText("Aceptar"));



        this.confirmarGenerarButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName("Generar"));



        this.aceptarPopupInclusionExitosaButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName("OK"));
        this.popupGenerarInclusion =
                page.locator(".swal2-title")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Generar inclusión"));



        this.popupInclusionExitosa =
                page.locator(".swal2-html-container")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Se genero la inclusión con éxito"));

    }

    public void assertLoaded() {

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                inclusionPlanillaTitle,
                "TITULO Inclusión de planilla DEBE SER VISIBLE");
    }

    public void seleccionarBotonProcesar() {

        log.info("[INCLUSION][VIDALEY] Seleccionando botón Procesar");

        ElementAsserts.assertVisible(
                procesarButton,
                "El botón Procesar debe ser visible");

        clickAndSync(procesarButton);
    }

    public void validarPopupListaObservaciones() {

        log.info(
                "[INCLUSION][VIDALEY] Validando popup Lista de observaciones");

        popupListaObservaciones.waitFor(
                new Locator.WaitForOptions()
                        .setTimeout(10000));

        ElementAsserts.assertVisible(
                popupListaObservaciones,
                "Se esperaba visualizar el popup Lista de observaciones");
    }

    public void aceptarPopupListaObservaciones() {

        log.info("[INCLUSION][VIDALEY] Aceptando popup Lista de observaciones");

        ElementAsserts.assertVisible(
                aceptarListaObservacionesButton,
                "El botón Aceptar debe ser visible");

        clickAndSync(aceptarListaObservacionesButton);
    }
    public void seleccionarBotonGenerar() {

        log.info("[INCLUSION][VIDALEY] Seleccionando botón Generar");

        generarButton.evaluate(
                "element => element.scrollIntoView({block:'center'})"
        );

        waitRandomBetween(1000);

        ElementAsserts.assertVisible(
                generarButton,
                "El botón Generar debe ser visible");

        clickAndSync(generarButton);

        log.info("[INCLUSION][VIDALEY] Botón Generar seleccionado");
    }
    public void validarPopupGenerarInclusion() {

        log.info("[INCLUSION][VIDALEY] Validando popup Generar inclusión");

        ElementAsserts.assertVisible(
                popupGenerarInclusion,
                "Se esperaba visualizar el popup Generar inclusión");
    }

    public void confirmarGenerarInclusion() {

        log.info("[INCLUSION][VIDALEY] Confirmando generación de inclusión");

        ElementAsserts.assertVisible(
                confirmarGenerarButton,
                "El botón Generar del popup debe ser visible");

        clickAndSync(confirmarGenerarButton);

        waitForNetworkIdle();
    }



    public void validarPopupInclusionExitosa() {

        log.info("[INCLUSION][VIDALEY] Validando popup de inclusión exitosa");

        ElementAsserts.assertVisible(
                popupInclusionExitosa,
                "El popup de inclusión exitosa debe ser visible");
    }

    public void aceptarPopupInclusionExitosa() {

        log.info("[INCLUSION][VIDALEY] Aceptando popup de inclusión exitosa");

        ElementAsserts.assertVisible(
                aceptarPopupInclusionExitosaButton,
                "El botón OK debe ser visible");

        clickAndSync(aceptarPopupInclusionExitosaButton);
    }
}
