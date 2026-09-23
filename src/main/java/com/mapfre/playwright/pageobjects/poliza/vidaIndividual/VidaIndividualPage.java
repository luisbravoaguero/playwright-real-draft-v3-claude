package com.mapfre.playwright.pageobjects.poliza.vidaIndividual;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VidaIndividualPage extends BasePage {
    private final Locator lblVidaIndividual;
    private final Locator btnCotizarPolizaVidaIndividual;

    public VidaIndividualPage(Page page) {
        super(page);
        this.lblVidaIndividual = page.locator(".g-breadcrumb--desktop__active:has-text('Vida Individual')");
        this.btnCotizarPolizaVidaIndividual = page.locator("a[href='#/polizas/vida-individual/cotizar/1']");

    }
    public void validarPaginaVidaIndividual() {

        log.info("Validando página Vida Individual");
        UiSync.waitForAppIdle(page);
        assertThat(lblVidaIndividual).isVisible();
        log.info("Página Vida Individual validada correctamente");
    }

    public void ingresarACotizacionVidaIndividual() {

        log.info("Validando página Vida Individual");
        UiSync.waitForAppIdle(page);
        assertThat(lblVidaIndividual).isVisible();
        log.info("Seleccionando opción Cotizar póliza de vida individual");
        btnCotizarPolizaVidaIndividual.click();
        UiSync.waitForAppIdle(page);
        log.info("Ingreso a cotización de Vida Individual exitoso");
    }


}
