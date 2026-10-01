package com.mapfre.utils.debug;

import com.microsoft.playwright.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DebugLocatorUtils {

    private static final Logger log = LoggerFactory.getLogger(DebugLocatorUtils.class);

    private DebugLocatorUtils() {}

    public static void debugLocator(String name, Locator loc) {
        log.info("\n---- " + name + " ----");

        int count;
        try {
            count = loc.count();
            log.info("count=" + count);
        } catch (Exception e) {
            log.info("count=ERROR: " + e.getMessage());
            return;
        }

        if (count == 0) return;

        Locator first = loc.first();

        try {
            log.info("visible=" + first.isVisible());
        } catch (Exception e) {
            log.info("visible=ERROR: " + e.getMessage());
        }

        try {
            log.info("display=" + first.evaluate("e => getComputedStyle(e).display"));
            log.info("visibility=" + first.evaluate("e => getComputedStyle(e).visibility"));
            log.info("opacity=" + first.evaluate("e => getComputedStyle(e).opacity"));
        } catch (Exception e) {
            log.info("computedStyle=ERROR: " + e.getMessage());
        }

        try {
            log.info("bbox=" + first.boundingBox()); // null often means not visible
        } catch (Exception e) {
            log.info("bbox=ERROR: " + e.getMessage());
        }

        try {
            log.info("textContent=" + first.textContent());
        } catch (Exception e) {
            log.info("textContent=ERROR: " + e.getMessage());
        }
    }
}