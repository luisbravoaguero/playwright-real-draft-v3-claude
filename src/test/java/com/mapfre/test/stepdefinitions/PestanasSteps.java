package com.mapfre.test.stepdefinitions;

import com.mapfre.playwright.driver.PopupHandler;
import com.mapfre.test.hooks.PageProvider;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.When;

/** Steps genericos para nuevas pestañas / popups. Tras abrirla, el resto de steps trabaja sobre ella. */
public class PestanasSteps {
    private final PageProvider pageProvider;

    public PestanasSteps(PageProvider pageProvider) {
        this.pageProvider = pageProvider;
    }

    @When("al hacer clic en el boton {string} se abre una nueva pestaña")
    public void alHacerClicEnElBotonSeAbreUnaNuevaPestana(String nombreBoton) {
        Page page = pageProvider.get();
        PopupHandler.abrir(page, () -> page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(nombreBoton)).click());
    }

    @When("cerramos la nueva pestaña y volvemos a la pestaña principal")
    public void cerramosLaNuevaPestanaYVolvemosALaPestanaPrincipal() {
        PopupHandler.cerrarYVolver();
    }

    @When("volvemos a la pestaña principal")
    public void volvemosALaPestanaPrincipal() {
        PopupHandler.volverAPrincipal();
    }
}
