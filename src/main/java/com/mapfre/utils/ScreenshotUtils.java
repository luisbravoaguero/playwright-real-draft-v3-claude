package com.mapfre.utils;

import com.mapfre.playwright.driver.DriverManager;
import com.mapfre.playwright.driver.PageTimeouts;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;

import java.util.Base64;

public final class ScreenshotUtils {
    private static final double DEFAULT_TIMEOUT_MS = 10000;

    private ScreenshotUtils() {}

    // Timeout propio (ui.screenshot.timeoutMs): screenshot() espera a las fuentes web y, sin limite, una fuente
    // colgada bloquea cada captura el timeout completo de la pagina
    public static String screenshotBase64() {
        byte[] bytes = DriverManager.page().screenshot(new Page.ScreenshotOptions()
                .setTimeout(PageTimeouts.parseMs("ui.screenshot.timeoutMs", DEFAULT_TIMEOUT_MS))
                .setAnimations(ScreenshotAnimations.DISABLED));
        return Base64.getEncoder().encodeToString(bytes);
    }
}
