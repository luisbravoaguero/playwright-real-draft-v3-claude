package com.mapfre.playwright.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.mapfre.config.ConfigManager;

import java.util.List;

public final class BrowserOptions {
    private BrowserOptions() {}
    /**
     * Configura las opciones de lanzamiento del binario del navegador.
     *
     * @return LaunchOptions configuradas para el hilo actual.
     */
    public static BrowserType.LaunchOptions launchOptions() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(ConfigManager.isHeadless());

        // Opcional: "chromium" usa el headless nuevo (navegador completo) en lugar de chromium-headless-shell;
        // "chrome" usa el Google Chrome instalado
        String channel = ConfigManager.getDriver("browser.channel");
        if (channel != null && !channel.isBlank()) {
            options.setChannel(channel.trim());
        }

        if (ConfigManager.isMaximize()) {
            options.setArgs(List.of("--start-maximized"));
        }

        options.setArgs(mergeArgs(options.args, List.of(
                "--no-sandbox",
                "--disable-dev-shm-usage"
        )));
        return options;
    }

     //Configura las opciones del contexto del navegador (Sesión).
     //El contexto es lo que garantiza el aislamiento entre pruebas en paralelo.
    public static Browser.NewContextOptions contextOptions(Browser browser) {

        Browser.NewContextOptions options = new Browser.NewContextOptions();

        //if (ConfigManager.isMaximize()) {
            //options.setViewportSize(null);
        //} else {
            options.setViewportSize(1920, 1080);
        //}

        /*
         * Puntos de extensión (Puedes descomentar según necesites):
         *
         * options.setIgnoreHTTPSErrors(true);
         */
        options.setAcceptDownloads(true);

        // Opcionales (vacios por defecto): por ej. es-PE / America/Lima
        String locale = ConfigManager.getDriver("browser.locale");
        if (locale != null && !locale.isBlank()) options.setLocale(locale.trim());
        String timezone = ConfigManager.getDriver("browser.timezoneId");
        if (timezone != null && !timezone.isBlank()) options.setTimezoneId(timezone.trim());

        String userAgent = userAgent(browser);
        if (userAgent != null) options.setUserAgent(userAgent);

        return options;
    }

    /**
     * browser.userAgent explicito, o (browser.ua.hideHeadless=true en Chromium headless) el UA de un Chrome normal:
     * el UA por defecto incluye "HeadlessChrome" y un WAF/antibot puede ralentizar o bloquear la pagina.
     */
    private static String userAgent(Browser browser) {
        String explicit = ConfigManager.getDriver("browser.userAgent");
        if (explicit != null && !explicit.isBlank()) return explicit.trim();

        boolean hide = Boolean.parseBoolean(ConfigManager.getDriver("browser.ua.hideHeadless"));
        boolean chromium = browser != null && "chromium".equals(browser.browserType().name());
        if (!hide || !chromium || !ConfigManager.isHeadless()) return null;

        return "Mozilla/5.0 (" + plataforma() + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/"
                + browser.version() + " Safari/537.36";
    }

    private static String plataforma() {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) return "Windows NT 10.0; Win64; x64";
        if (os.contains("mac")) return "Macintosh; Intel Mac OS X 10_15_7";
        return "X11; Linux x86_64";
    }

    private static List<String> mergeArgs(List<String> current, List<String> extra) {
        if (current == null || current.isEmpty()) return extra;
        java.util.ArrayList<String> merged = new java.util.ArrayList<>(current);
        for (String a : extra) if (!merged.contains(a)) merged.add(a);
        return merged;
    }
}
