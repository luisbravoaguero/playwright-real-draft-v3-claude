package com.mapfre.utils;

import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.driver.PageTimeouts;
import com.mapfre.utils.diagnostics.PageDiagnostics;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Navegacion por fases con un error distinto en cada una:
 * 1) NAVEGACION: el servidor entrega el HTML (DOMContentLoaded; no se espera "load", que depende de terceros).
 * 2) La SPA renderiza la señal de "lista" (ej. el campo usuario del login). Si no aparece, el error dice si la
 *    pantalla quedo en blanco, con spinner o con otro contenido, junto con la red y la consola.
 * 3) Terminan las olas de spinner ({@link UiSync#waitForAppIdle(Page)}).
 * Se registra cuanto tardo cada fase, para medir la lentitud aunque no falle.
 */
public final class AppReadiness {
    private static final Logger log = LoggerFactory.getLogger(AppReadiness.class);
    private static final double DEFAULT_READY_TIMEOUT_MS = 60000;

    private AppReadiness() {}

    /**
     * @param senalListo elemento que indica que la app termino de renderizar; null = solo DOMContentLoaded + spinner
     */
    public static void navegar(Page page, String url, Locator senalListo) {
        double navTimeoutMs = PageTimeouts.parseMs("ui.nav.timeoutMs", ConfigManager.getDefaultTimeoutMs());
        long inicio = System.currentTimeMillis();

        try {
            page.navigate(url, new Page.NavigateOptions()
                    .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                    .setTimeout(navTimeoutMs));
        } catch (PlaywrightException e) { // incluye TimeoutError
            throw new FrameworkException("[NAVEGACION] El servidor no entrego la pagina (DOMContentLoaded) en "
                    + (long) navTimeoutMs + " ms. Revisar red/VPN/DNS/servidor.\n" + PageDiagnostics.capturar(page), e);
        }
        long dom = System.currentTimeMillis();
        log.info("[NAVEGACION] DOMContentLoaded en {} ms", dom - inicio);

        if (senalListo != null) {
            double readyTimeoutMs = PageTimeouts.parseMs("ui.nav.readyTimeoutMs", DEFAULT_READY_TIMEOUT_MS);
            try {
                senalListo.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(readyTimeoutMs));
            } catch (PlaywrightException e) {
                PageDiagnostics.Diagnostico diag = PageDiagnostics.capturar(page);
                throw new FrameworkException("[" + diag.fase() + "] La aplicacion no mostro el elemento esperado en "
                        + (long) readyTimeoutMs + " ms despues de DOMContentLoaded.\n" + diag, e);
            }
            log.info("[RENDER] Elemento esperado visible en {} ms", System.currentTimeMillis() - dom);
        }

        long antesSpinner = System.currentTimeMillis();
        UiSync.waitForAppIdle(page);
        long fin = System.currentTimeMillis();
        log.info("[SPINNER] App inactiva en {} ms (total navegacion {} ms)", fin - antesSpinner, fin - inicio);
    }
}
