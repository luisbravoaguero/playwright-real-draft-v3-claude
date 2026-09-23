package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;


import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class DeclaracionVidaLeyPage extends BasePage {

    private final Locator declaracionTitle;

    private final Locator procesarButton;
    private final Locator siguienteButton;
    private final Locator generarButton;

    private final Locator popupErrorListaPersonas;
    private final Locator popupListaObservaciones;

    private final Locator aceptarPopupListaObservacionesButton;

    private final Locator detalleRiesgosTitle;

    private final Locator popupGenerarDeclaracion;
    private final Locator confirmarGenerarButton;

    private final Locator popupDeclaracionExitosa;
    private final Locator aceptarPopupDeclaracionExitosaButton;

    private final Locator filasTrabajadoresPopup;

    private final Locator agregarFilaButton;


    private final CargaIndividualDeclaracionVidaLeyPage
            datosAseguradoDeclaracionVidaLeyPage;

    public DeclaracionVidaLeyPage(Page page) {

        super(page);

        this.declaracionTitle =
                page.getByRole(
                        AriaRole.HEADING,
                        new Page.GetByRoleOptions()
                                .setName(
                                        Pattern.compile(
                                                "Declaración|Inclusión de planilla",
                                                Pattern.CASE_INSENSITIVE)));

        this.procesarButton =
                page.locator("a.g-button.block")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Procesar"));

        this.siguienteButton =
                page.getByText(
                        "Siguiente",
                        new Page.GetByTextOptions()
                                .setExact(true));

        this.generarButton =
                page.getByText(
                        "Generar",
                        new Page.GetByTextOptions()
                                .setExact(true));

        this.popupErrorListaPersonas =
                page.getByText(
                        Pattern.compile(
                                "Existen trabajadores que fueron incluidos como no facturados",
                                Pattern.CASE_INSENSITIVE));
        this.filasTrabajadoresPopup =
                page.locator("table tbody tr");

        this.popupListaObservaciones =
                page.locator(".swal2-title")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Lista de observaciones"));



        this.aceptarPopupListaObservacionesButton =
                page.locator("a.g-button.g-button--second-design")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("ACEPTAR"));
        this.detalleRiesgosTitle =
                page.getByText(
                        Pattern.compile(
                                "Detalle de los Riesgos",
                                Pattern.CASE_INSENSITIVE));

        this.popupGenerarDeclaracion =
                page.locator(".swal2-title")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Generar"));

        this.confirmarGenerarButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName("Generar"));

        this.popupDeclaracionExitosa =
                page.locator(".swal2-html-container");

        this.aceptarPopupDeclaracionExitosaButton =
                page.getByRole(
                        AriaRole.BUTTON,
                        new Page.GetByRoleOptions()
                                .setName("OK"));

        this.agregarFilaButton =
                page.locator(":text('Agregar fila')");

        this.datosAseguradoDeclaracionVidaLeyPage =
                new CargaIndividualDeclaracionVidaLeyPage(page);


    }

    public void assertLoaded() {

        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();

        ElementAsserts.assertVisible(
                declaracionTitle,
                "La pantalla Declaración debe estar visible");

        ElementAsserts.assertVisible(
                procesarButton,
                "El botón Procesar debe estar visible");
    }

    public void seleccionarProcesar() {

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando botón Procesar");

        procesarButton.scrollIntoViewIfNeeded();

        ElementAsserts.assertVisible(
                procesarButton,
                "El botón Procesar debe ser visible");

        clickAndSync(procesarButton);
    }

    public void validarPopupErrorListaPersonas() {

        log.info(
                "[DECLARACION][VIDALEY] Validando popup Lista de personas");

        ElementAsserts.assertVisible(
                popupErrorListaPersonas,
                "Debe mostrarse el popup Lista de personas");
    }

    public void aceptarPopupErrorListaPersonas() {

        log.info(
                "[DECLARACION][VIDALEY] Aceptando popup Lista de personas");

        ElementAsserts.assertVisible(
                aceptarPopupListaObservacionesButton,
                "El botón Aceptar debe ser visible");

        clickAndSync(aceptarPopupListaObservacionesButton);
    }

    public void agregarFila() {

        log.info(
                "[DECLARACION][VIDALEY] Agregando nueva fila");

        agregarFilaButton.scrollIntoViewIfNeeded();

        waitRandomBetween(1000);

        ElementAsserts.assertVisible(
                agregarFilaButton,
                "El botón Agregar fila debe ser visible");

        clickAndSync(
                agregarFilaButton);

        waitRandomBetween(1000);
    }


    public void agregarAseguradosFaltantes() {

        int totalFilas = filasTrabajadoresPopup.count();

        for (int i = 0; i < totalFilas; i++) {

            Locator fila = filasTrabajadoresPopup.nth(i);

            String dni =
                    fila.locator("td").nth(0)
                            .innerText()
                            .replace("DNI", "")
                            .trim();

            String nombre =
                    fila.locator("td").nth(1)
                            .innerText()
                            .trim();

            agregarFila();

            datosAseguradoDeclaracionVidaLeyPage.completarPrimerTrabajador(
                    dni,
                    nombre);

            datosAseguradoDeclaracionVidaLeyPage.seleccionarLugarExposicionVidaLey();

            datosAseguradoDeclaracionVidaLeyPage.seleccionarSexoVidaLey();
        }
    }
    public void validarPopupListaObservaciones() {

        log.info(
                "[DECLARACION][VIDALEY] Validando popup Lista de observaciones");

        ElementAsserts.assertVisible(
                popupListaObservaciones,
                "Debe mostrarse el popup Lista de observaciones");
    }

    public void aceptarPopupListaObservaciones() {

        log.info(
                "[DECLARACION][VIDALEY] Aceptando popup Lista de observaciones");

        ElementAsserts.assertVisible(
                aceptarPopupListaObservacionesButton,
                "El botón Aceptar debe ser visible");

        clickAndSync(aceptarPopupListaObservacionesButton);
    }

    public void seleccionarSiguiente() {

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando botón Siguiente");

        siguienteButton.scrollIntoViewIfNeeded();

        ElementAsserts.assertVisible(
                siguienteButton,
                "El botón Siguiente debe ser visible");

        clickAndSync(siguienteButton);
    }

    public void validarDetalleRiesgos() {

        log.info(
                "[DECLARACION][VIDALEY] Validando detalle de riesgos");

        ElementAsserts.assertVisible(
                detalleRiesgosTitle,
                "Debe visualizarse el detalle de riesgos");
    }

    public void seleccionarGenerar() {

        log.info(
                "[DECLARACION][VIDALEY] Seleccionando botón Generar");

        generarButton.scrollIntoViewIfNeeded();

        ElementAsserts.assertVisible(
                generarButton,
                "El botón Generar debe ser visible");

        clickAndSync(generarButton);
    }

    public void validarPopupGenerarDeclaracion() {

        log.info(
                "[DECLARACION][VIDALEY] Validando popup Generar declaración");

        ElementAsserts.assertVisible(
                popupGenerarDeclaracion,
                "Debe mostrarse el popup Generar declaración");
    }

    public void confirmarGeneracionDeclaracion() {

        log.info(
                "[DECLARACION][VIDALEY] Confirmando generación de declaración");

        ElementAsserts.assertVisible(
                confirmarGenerarButton,
                "El botón Generar debe ser visible");

        clickAndSync(confirmarGenerarButton);
    }

    public void validarPopupGeneracionExitosa() {

        log.info(
                "[DECLARACION][VIDALEY] Validando popup declaración exitosa");

        ElementAsserts.assertVisible(
                popupDeclaracionExitosa,
                "Debe mostrarse el popup declaración exitosa");
    }

    public void aceptarPopupGeneracionExitosa() {

        log.info(
                "[DECLARACION][VIDALEY] Aceptando popup declaración exitosa");

        ElementAsserts.assertVisible(
                aceptarPopupDeclaracionExitosaButton,
                "El botón OK debe ser visible");

        clickAndSync(
                aceptarPopupDeclaracionExitosaButton);
    }
}