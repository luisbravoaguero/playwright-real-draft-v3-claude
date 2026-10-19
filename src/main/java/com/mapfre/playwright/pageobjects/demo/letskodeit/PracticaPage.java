package com.mapfre.playwright.pageobjects.demo.letskodeit;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

/** Pagina de practica de LetsKodeIt (sitio demo publico). Su boton "Open Tab" abre la pagina de cursos en una nueva pestaña. */
public class PracticaPage extends BasePage {

    private final Locator titulo;
    private final Locator autoSelect;
    private final Locator nombreInput;
    private final Locator openTabLink;

    public PracticaPage(Page page) {
        super(page);
        this.titulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Practice Page"));
        this.autoSelect = page.locator("#carselect");
        // hay dos inputs con id="name" en la pagina (el otro es del newsletter): se ubica por placeholder
        this.nombreInput = page.getByPlaceholder("Enter Your Name");
        this.openTabLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Open Tab"));
    }

    public void abrir() {
        String url = ConfigManager.getGlobal("demo.url.letskodeit");
        if (url == null || url.isBlank()) {
            throw new FrameworkException("Falta el key global.properties: demo.url.letskodeit");
        }
        page.navigate(url);
    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(titulo, "TITULO Practice Page DEBE SER VISIBLE");
    }

    public void seleccionarAuto(String auto) {
        autoSelect.selectOption(new SelectOption().setLabel(auto));
    }

    public void assertAutoSeleccionado(String auto) {
        ElementAsserts.assertTextHasValue(autoSelect, auto.toLowerCase(), "EL AUTO SELECCIONADO DEBE SER " + auto);
    }

    public void ingresarNombre(String nombre) {
        nombreInput.fill(nombre);
    }

    public void assertNombre(String nombre) {
        ElementAsserts.assertTextHasValue(nombreInput, nombre, "EL CAMPO NOMBRE DEBE CONTENER " + nombre);
    }

    /** Hace clic en "Open Tab" y devuelve la pagina de cursos abierta en la nueva pestaña (ya activa). */
    public CursosPage abrirPestanaCursos() {
        return CursosPage.enNuevaPestana(clickAndCapturePopup(openTabLink));
    }
}
