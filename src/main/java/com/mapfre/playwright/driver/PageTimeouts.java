package com.mapfre.playwright.driver;

import com.mapfre.config.ConfigManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Timeout de aserciones por pestaña.
 * PlaywrightAssertions solo tiene un timeout global (estatico, para toda la JVM); aqui se registra un valor propio
 * para pestañas concretas (ej. popups lentos) sin afectar a las demas. Las pestañas sin valor propio usan
 * {@code ui.assert.timeoutMs}.
 */
public final class PageTimeouts {
    private static final Logger log = LoggerFactory.getLogger(PageTimeouts.class);
    private static final double DEFAULT_ASSERT_TIMEOUT_MS = 30000;
    private static final Map<Page, Double> ASSERT_TIMEOUTS = new ConcurrentHashMap<>();

    private PageTimeouts() {}

    /** Registra el timeout de aserciones de una pestaña; se elimina solo cuando la pestaña se cierra. */
    public static void set(Page page, double timeoutMs) {
        if (ASSERT_TIMEOUTS.put(page, timeoutMs) == null) {
            page.onClose(ASSERT_TIMEOUTS::remove);
        }
    }

    public static double assertTimeoutFor(Page page) {
        Double value = page == null ? null : ASSERT_TIMEOUTS.get(page);
        return value != null ? value : defaultAssertTimeoutMs();
    }

    public static double assertTimeoutFor(Locator locator) {
        return assertTimeoutFor(locator.page());
    }

    /** Timeout de aserciones de las pestañas normales ({@code ui.assert.timeoutMs}). */
    public static double defaultAssertTimeoutMs() {
        return parseMs("ui.assert.timeoutMs", DEFAULT_ASSERT_TIMEOUT_MS);
    }

    /** Lee una propiedad global en ms; si falta o es invalida devuelve el valor por defecto. */
    public static double parseMs(String key, double defaultMs) {
        String value = ConfigManager.getGlobal(key);
        try {
            return value == null || value.isBlank() ? defaultMs : Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            log.warn("{} invalido ({}); se usa {} ms", key, value, defaultMs);
            return defaultMs;
        }
    }
}
