package com.mapfre.playwright.pageobjects.poliza.salud;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MenuPolizaSaludPage extends BasePage {

    private final Locator lblQueQuieresHacer;
    private final Locator btnCotizarPolizaSalud;

    public MenuPolizaSaludPage(Page page) {
        super(page);

        this.lblQueQuieresHacer = page.getByText("¿Qué quieres hacer?");
        this.btnCotizarPolizaSalud = page.getByText("Cotizar póliza de Salud",new Page.GetByTextOptions().setExact(true));
    }

    public void assertLoadedPolizaSaludMenu() {
        ElementAsserts.assertVisible( lblQueQuieresHacer, "TITULO ¿Qué quieres hacer? DEBE SER VISIBLE");
        ElementAsserts.assertVisible( btnCotizarPolizaSalud,"BOTON Cotizar póliza de Salud DEBE SER VISIBLE");
    }
    public void seleccionaBotonCotizacion() {

        btnCotizarPolizaSalud.click();
    }
}