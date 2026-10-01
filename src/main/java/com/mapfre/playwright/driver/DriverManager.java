package com.mapfre.playwright.driver;

import com.mapfre.config.ConfigManager;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Playwright/Browser viven por hilo (se reutilizan entre escenarios); BrowserContext/Page viven por escenario.
 * Todos los Playwright creados se registran para cerrarlos al final de la ejecucion con {@link #shutdownAll()}.
 */
public final class DriverManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<Playwright> TL_PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> TL_BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> TL_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> TL_PAGE = new ThreadLocal<>();

    private static final Queue<Playwright> ALL_PLAYWRIGHTS = new ConcurrentLinkedQueue<>();

    private DriverManager() {}

    static void setThreadBrowser(Playwright pw, Browser br) {
        TL_PLAYWRIGHT.set(pw);
        TL_BROWSER.set(br);
        ALL_PLAYWRIGHTS.add(pw);
    }

    static void setScenario(BrowserContext ctx, Page pg) {
        TL_CONTEXT.set(ctx);
        TL_PAGE.set(pg);
    }

    public static Playwright playwright() { return TL_PLAYWRIGHT.get(); }
    public static Browser browser() { return TL_BROWSER.get(); }
    public static BrowserContext context() { return TL_CONTEXT.get(); }
    public static Page page() { return TL_PAGE.get(); }

    /** Fin de escenario: cierra Page y Context. Nunca lanza excepcion (no debe ocultar el fallo real del test). */
    public static void cleanup() {
        try {
            closeQuietly("page", TL_PAGE.get());
            closeQuietly("context", TL_CONTEXT.get());
        } finally {
            TL_PAGE.remove();
            TL_CONTEXT.remove();
        }
        if (ConfigManager.isBrowserPerScenario()) {
            closeThreadBrowser();
        }
    }

    /** Cierra el Browser y Playwright del hilo actual (si existen). */
    static void closeThreadBrowser() {
        Playwright pw = TL_PLAYWRIGHT.get();
        try {
            closeQuietly("browser", TL_BROWSER.get());
            closeQuietly("playwright", pw);
        } finally {
            if (pw != null) ALL_PLAYWRIGHTS.remove(pw);
            TL_BROWSER.remove();
            TL_PLAYWRIGHT.remove();
        }
    }

    /** Fin de la ejecucion: cierra todos los Playwright (y sus navegadores) de todos los hilos. */
    public static void shutdownAll() {
        Playwright pw;
        while ((pw = ALL_PLAYWRIGHTS.poll()) != null) {
            closeQuietly("playwright", pw);
        }
    }

    private static void closeQuietly(String what, AutoCloseable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception e) {
            log.warn("No se pudo cerrar {}: {}", what, e.toString());
        }
    }
}
