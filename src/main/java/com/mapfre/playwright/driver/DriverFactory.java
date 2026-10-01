package com.mapfre.playwright.driver;

import com.microsoft.playwright.*;
import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;

public final class DriverFactory {
    private DriverFactory() {}

    /**
     * Prepara el navegador del escenario actual.
     * Por defecto (browser.perScenario=true) crea Playwright, Browser, BrowserContext y Page nuevos por escenario.
     * Con browser.perScenario=false reutiliza el Playwright/Browser del hilo y solo crea un BrowserContext + Page
     * nuevos (aislamiento de cookies/storage por escenario).
     */
    public static void init() {
        ConfigManager.load();

        Browser browser = DriverManager.browser();
        boolean reusable = !ConfigManager.isBrowserPerScenario() && browser != null && browser.isConnected();
        if (!reusable) {
            DriverManager.closeThreadBrowser(); // descarta instancias previas/desconectadas del hilo
            Playwright pw = Playwright.create();
            DriverManager.setThreadBrowser(pw, launchBrowser(pw));
        }

        BrowserContext context = DriverManager.browser().newContext(BrowserOptions.contextOptions());
        Page page = context.newPage();
        page.setDefaultTimeout(ConfigManager.getDefaultTimeoutMs());

        DriverManager.setScenario(context, page);
    }

    private static Browser launchBrowser(Playwright pw) {
        String browserName = ConfigManager.getBrowser();
        BrowserType.LaunchOptions launchOptions = BrowserOptions.launchOptions();

        switch (browserName) {
            case "chromium":
            case "chrome":
                return pw.chromium().launch(launchOptions);
            case "firefox":
                return pw.firefox().launch(launchOptions);
            case "webkit":
                return pw.webkit().launch(launchOptions);
            default:
                pw.close();
                throw new FrameworkException("Navegador no compatible: " + browserName);
        }
    }
}
