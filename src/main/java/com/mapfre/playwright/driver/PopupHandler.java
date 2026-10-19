package com.mapfre.playwright.driver;

import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.utils.Checkpoint;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Manejo de popups / nuevas pestañas (Page.waitForPopup).
 * El popup capturado pasa a ser la pestaña activa de {@link DriverManager#page()}: PageProvider, UiSync y las
 * capturas automaticas de cada step trabajan sobre el popup hasta volver a la pestaña principal.
 */
public final class PopupHandler {
    private static final Logger log = LoggerFactory.getLogger(PopupHandler.class);
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    private PopupHandler() {}

    /**
     * Ejecuta la accion que abre el popup, lo espera y lo deja como pestaña activa.
     *
     * @param opener  pestaña desde la que se dispara el popup
     * @param trigger accion que lo abre (ej. {@code () -> boton.click()})
     * @return el popup ya cargado (DOMContentLoaded)
     */
    public static Page abrir(Page opener, Runnable trigger) {
        Objects.requireNonNull(opener, "opener");
        Objects.requireNonNull(trigger, "trigger");
        int timeoutMs = timeoutMs();

        Page popup;
        try {
            popup = opener.waitForPopup(new Page.WaitForPopupOptions().setTimeout(timeoutMs), trigger);
        } catch (TimeoutError e) {
            throw new FrameworkException("No se abrio la nueva pestaña en " + timeoutMs + " ms", e);
        }

        popup.setDefaultTimeout(ConfigManager.getDefaultTimeoutMs());
        popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
        // si la pestaña se cierra sola, volvemos a la principal
        popup.onClose(p -> {
            if (DriverManager.page() == p) {
                log.info("La pestaña activa se cerro; se vuelve a la pestaña principal");
                DriverManager.activateMain();
            }
        });

        DriverManager.activate(popup);
        log.info("Nueva pestaña abierta: url={} title={}", popup.url(), safeTitle(popup));
        Checkpoint.capture("Nueva pestaña abierta");
        return popup;
    }

    /** Cierra la pestaña activa (si no es la principal) y vuelve a la principal. */
    public static void cerrarYVolver() {
        Page active = DriverManager.page();
        Page main = DriverManager.mainPage();
        if (active != null && active != main && !active.isClosed()) {
            try {
                active.close();
            } catch (Exception e) {
                log.warn("No se pudo cerrar la pestaña activa: {}", e.toString());
            }
        }
        volverAPrincipal();
    }

    /** Vuelve a la pestaña principal sin cerrar el popup (para regresar a el despues con {@link #activar(Page)}). */
    public static void volverAPrincipal() {
        DriverManager.activateMain();
        Page main = DriverManager.mainPage();
        if (main != null) main.bringToFront();
    }

    /** Activa una pestaña ya abierta (ej. un popup guardado al llamar a {@link #abrir}). */
    public static void activar(Page pg) {
        Objects.requireNonNull(pg, "page");
        if (pg.isClosed()) {
            throw new FrameworkException("La pestaña que se quiere activar ya esta cerrada");
        }
        DriverManager.activate(pg);
        pg.bringToFront();
    }

    private static int timeoutMs() {
        String value = ConfigManager.getGlobal("ui.popup.timeoutMs");
        try {
            return value == null || value.isBlank() ? DEFAULT_TIMEOUT_MS : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("ui.popup.timeoutMs invalido ({}); se usa {} ms", value, DEFAULT_TIMEOUT_MS);
            return DEFAULT_TIMEOUT_MS;
        }
    }

    private static String safeTitle(Page pg) {
        try {
            return pg.title();
        } catch (Exception e) {
            return "?";
        }
    }
}
