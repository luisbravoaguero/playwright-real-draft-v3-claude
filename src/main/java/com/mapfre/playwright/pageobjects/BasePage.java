package com.mapfre.playwright.pageobjects;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.config.ConfigManager;
import com.mapfre.config.EnvironmentConfig;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.driver.PopupHandler;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mapfre.utils.UiSync;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected final Page page;
    protected final Logger log;

    protected BasePage(Page page) {
        this.page = page;
        this.log = LoggerFactory.getLogger(this.getClass());
    }
    protected void waitAppReady() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        UiSync.waitForAppIdle(this.page);
    }

    public void navigate() {
        page.navigate(EnvironmentConfig.getAppUrl());
        waitAppReady();
    }

    protected void clickAndSync(Locator locator) {
        locator.click();
        UiSync.waitForAppIdle(page);
    }

    /**
     * Hace clic en un elemento que abre una nueva pestaña y la devuelve ya activa
     * (los steps siguientes trabajan sobre ella via PageProvider). Ej:
     * {@code return new DocumentoPopupPage(clickAndCapturePopup(btnVerDocumento));}
     */
    protected Page clickAndCapturePopup(Locator trigger) {
        return PopupHandler.abrir(page, trigger::click);
    }

    protected void clickForcedAndSync(Locator locator) {
        locator.click(new Locator.ClickOptions().setForce(true));
        UiSync.waitForAppIdle(page);
    }

    protected void hoverAndSync(Locator locator) {
        locator.hover();
        UiSync.waitForAppIdle(page);
    }

    protected void hoverForcedAndSync(Locator locator) {
        locator.hover(new Locator.HoverOptions().setForce(true));
        UiSync.waitForAppIdle(page);
    }

    protected void checkfEnabledAndSync(Locator locator) {
        if(isInputEnabled(locator) && locator.isVisible()) {
            locator.check();
            UiSync.waitForAppIdle(page);
        }
    }
    protected void clickIfEnabledAndSync(Locator locator) {
        if(isInputEnabled(locator) && locator.isVisible()) {
            locator.click();
            UiSync.waitForAppIdle(page);
        }
    }
    protected void fillAndSync(Locator locator, String value) {
        locator.fill(value);
        UiSync.waitForAppIdle(page);
    }
    protected void fillAndBlurSync(Locator locator, String value) {
        locator.fill(value);
        locator.blur();
        UiSync.waitForAppIdle(page);
    }
    protected void fillBlurAndFailIfErrorModalAppears(Locator locator, String value, long modalTimeoutMs, Locator modalError) {
        if (isInputEnabled(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);

            try {
                modalError.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(modalTimeoutMs));

                throw new FrameworkException(
                        "Se muestra un modal de error inesperado con el mensaje: " + modalError.innerText()
                );
            } catch (TimeoutError e) {
                // no modal appeared, continue
            }
        }
    }


    protected void pressSequentiallyAndBlurSync(Locator locator, String value) {
        locator.pressSequentially(value);
        locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void pressSequentiallyDelayAndSync(Locator locator, String value) {
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        //locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void cleanPressSequentiallyDelayBlurAndSync(Locator locator, String value) {
        locator.clear();
        waitRandomBetween(250);
        locator.fill("");
        waitRandomBetween(250);
        locator.pressSequentially(value, new Locator.PressSequentiallyOptions().setDelay(100));
        //locator.blur();
        UiSync.waitForAppIdle(page);
    }

    protected void fillInputIfEmptyAndSync(Locator locator, String value) {
        if (isInputEmpty(locator)) {
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void fillInputIfEmptyBlurAndSync(Locator locator, String value) {
        if (isInputEmpty(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputNewValueIfEnabledAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.clear();
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputValueIfEnabledAndEmptyAndSync(Locator locator, String value) {
        if (isInputEnabled(locator) && isInputEmpty(locator)){
            locator.fill(value);
        }
        UiSync.waitForAppIdle(page);
    }

    public void clearAndFill(Locator locator, String value){
        locator.fill("");
        locator.fill(value);
        UiSync.waitForAppIdle(page);
    }

    protected void selectOptionIfEnabledAndSync(Locator locator, String option) {
        if (isInputEnabled(locator)) {
            locator.selectOption(option);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void selectOptionIfEnabledAndEmptyAndSync(Locator locator, String option) {
        if (isInputEnabled(locator) && isInputEmpty(locator)) {
            locator.selectOption(option);
            UiSync.waitForAppIdle(page);
        }
    }
    protected void selectIndexIfEmptyAndSync(Locator locator, int index) {
        if (isInputEmpty(locator)) {
            locator.selectOption(new SelectOption().setIndex(index));
            UiSync.waitForAppIdle(page);
        }
    }
    public void selectIndexOptionIfEnabledAndSync(Locator locator, int index) {
        if (isInputEnabled(locator)) {
            locator.selectOption(new SelectOption().setIndex(index));
            UiSync.waitForAppIdle(page);
        }
    }
    public void selectBornDateFromStringAndSync(Locator dayLocator, Locator monthLocator, Locator yearLocator, String date) {
        if (isInputEnabled(dayLocator) && isInputEmpty(dayLocator)) {
            dayLocator.selectOption(date.split("/")[0]);
            UiSync.waitForAppIdle(page);
        }
        if (isInputEnabled(monthLocator) && isInputEmpty(monthLocator)) {
            monthLocator.selectOption(date.split("/")[1]);
            UiSync.waitForAppIdle(page);
        }
        if (isInputEnabled(yearLocator) && isInputEmpty(yearLocator)) {
            yearLocator.selectOption(date.split("/")[2]);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputIfEnabledAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.clear();
            UiSync.waitForAppIdle(page);
            locator.fill(value);
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillInputIfEnabledBlurAndSync(Locator locator, String value) {
        if (isInputEnabled(locator)) {
            locator.fill(value);
            locator.blur();
            UiSync.waitForAppIdle(page);
        }
    }

    protected void fillReadonlyDate(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.evaluate(" (el, dateValue) => {" +
                "  el.value = dateValue;" +
                "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "  el.blur();" +
                "}", date);
    }

    protected void fillReadonlyDateIfEmpty(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        if (isInputEmpty(locator)){
            locator.evaluate(" (el, dateValue) => {" +
                    "  el.value = dateValue;" +
                    "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "  el.blur();" +
                    "}", date);
        }
    }

    protected void fillReadonlyIfFieldIsNotOptional(Locator locator, String date) {
        UiSync.waitForAppIdle(page);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        if (!isFieldOptional(date)) {
            locator.evaluate(" (el, dateValue) => {" +
                    "  el.value = dateValue;" +
                    "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "  el.blur();" +
                    "}", date);
        }
    }

    public void setReadonlyInputValue(Locator input, String value) {
        input.evaluate(
                "(el, value) => {" +
                        "  const nativeInputValueSetter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;" +
                        "  nativeInputValueSetter.call(el, value);" +
                        "  el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "}",
                value
        );

        log.info("Se estableció el valor '{}' en el input readonly.", value);
    }

    public void setDateByTyping(String label, String value) {
        Locator input = page.locator("oim-datepicker[label='" + label + "'] input");

        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.evaluate("(el) => el.removeAttribute('readonly')");
        input.click();
        input.fill(value);
        input.press("Tab");

        log.info("Se ingresó la fecha '{}' en el campo '{}'.", value, label);
    }

    public void setValueWithJsAndCommit(Locator input, String value) {
        UiSync.waitForAppIdle(page);
        input.evaluate(
                "(el, v) => {" +
                        "const setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(el, v);" +
                        "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "el.dispatchEvent(new FocusEvent('blur', { bubbles: true }));" +
                        "}", value
        );
        input.blur();
    }

    protected void selectAndSync(Locator locator, String option) {
        locator.selectOption(option);
        UiSync.waitForAppIdle(page);
    }

    protected void selectOptionAndSync(Locator select, String optionValue) {
        try {
            select.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Locator option = select.locator("option").filter(new Locator.FilterOptions().setHasText(Pattern.compile(optionValue, Pattern.CASE_INSENSITIVE)));
            option.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
            String value = option.first().getAttribute("value");
            select.selectOption(value);
            UiSync.waitForAppIdle(page);

        } catch (Exception e) {
            log.info("Falló la seleccion usando case-insensitive match.", e);
        }
    }

    protected void selectOptionEnabledAndSync(Locator select, String optionValue) {
        try {
            select.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        } catch (Exception e) {
            log.error("El locator {} no está visible.", select, e);
            return;
        }

        if (!select.isEnabled()) {
            log.info("El locator {} no está habilitado; no se seleccionó ninguna opción.", select);
            return;
        }

        Locator option = select.locator("option").filter(new Locator.FilterOptions().setHasText(Pattern.compile(optionValue, Pattern.CASE_INSENSITIVE))).first();

        try {
            option.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        } catch (Exception e) {
            log.info("No se encontró la opción '{}' en el locator {}; no se seleccionó ninguna opción.", optionValue, select);
            return;
        }

        if (!option.isEnabled()) {
            log.info("La opción '{}' del locator {} no está habilitada; no se seleccionó ninguna opción.", optionValue, select);
            return;
        }

        String value = option.getAttribute("value");

        if (value == null) {
            log.info("La opción '{}' del locator {} no tiene atributo value; no se seleccionó ninguna opción.", optionValue, select);
            return;
        }

        select.selectOption(value);
        UiSync.waitForAppIdle(page);
    }

    protected void selectByOptionIfEnableAndSync(Locator select, String optionValue) {
        try {
            if(select.isVisible() && isInputEnabled(select)){
                Locator option = select.locator("option").filter(new Locator.FilterOptions().setHasText(Pattern.compile(optionValue, Pattern.CASE_INSENSITIVE)));
                option.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
                String value = option.first().getAttribute("value");
                select.selectOption(value);
                UiSync.waitForAppIdle(page);
            }
        } catch (Exception e) {
            log.info("Falló la seleccion usando case-insensitive match.", e);
        }
    }

    protected void selectIndexOptionAndSync(Locator locator, int index) {
        locator.selectOption(new SelectOption().setIndex(index));
        UiSync.waitForAppIdle(page);
    }

    protected void scrollIntoView(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    protected void scrollPageBy(int deltaX, int deltaY) {
        page.mouse().wheel(deltaX, deltaY);
    }

    protected void scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
    }

    protected void scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }

    // Use after navigation or page transition
    public void sync() {
        UiSync.waitForAppIdle(page);
    }


    protected boolean isInputEmpty(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        UiSync.waitForAppIdle(page);
        String value = locator.inputValue();
        return value == null || value.trim().isEmpty();
    }

    // Positivo: implementación completa para comprobar que un input está realmente habilitado y editable.
    // No es un wrapper: devolvemos true sólo si podemos confirmar que el elemento es visible, enabled
    // y editable. En caso de excepciones devolvemos false (conservador) para no permitir acciones sobre
    // elementos cuya disponibilidad no haya sido verificada.
    protected boolean isInputEnabled(Locator locator) {
        try {
            // Asegurar que el locator está presente y visible
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            UiSync.waitForAppIdle(page);

            // Si explícitamente está marcado como disabled -> no está habilitado
            try {
                if (locator.isDisabled()) return false;
            } catch (Exception ignored) {
                // Si falla la comprobación, seguimos con otras verificaciones
            }

            // Si no está 'enabled' según Playwright -> no está habilitado
            try {
                if (!locator.isEnabled()) return false;
            } catch (Exception ignored) {
                // Si no pudimos consultar isEnabled, consideramos que no está garantizado
                return false;
            }

            // Finalmente, comprobar si es editable (no readonly, acepta texto)
            try {
                if (!locator.isEditable()) return false;
            } catch (Exception ignored) {
                // Si no es aplicable (por ejemplo no es un input de texto) o falla la comprobación,
                // conservadoramente devolvemos false para evitar escribir en elementos no verificados.
                return false;
            }

            // Si llegamos aquí, el elemento aparenta estar visible, enabled y editable
            return true;
        } catch (Exception e) {
            // En cualquier excepción inesperada devolvemos false (no confiamos en que esté habilitado)
            return false;
        }
    }


    public void forceTypeTextOnce(Locator input) {
        UiSync.waitForAppIdle(page);
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        input.evaluate("el => el.setAttribute('type','text')");
    }

    protected boolean closeSwalIfVisible(double timeoutMs) {
        Locator modal = page.locator("div.swal2-popup.swal2-modal[role='dialog']");

        try {
            modal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeoutMs));
        } catch (TimeoutError e) {
            return false; // not visible within timeout => nothing to close
        }

        // If it's visible, grab useful text for logs
        String title = safeInnerText(modal.locator("#swal2-title"));
        String msg   = safeInnerText(modal.locator("#swal2-html-container"));

        log.info("Swal modal detected and will be closed: {} - {}", title, msg);


        // Click OK / Aceptar if present
        Locator okButton = modal.getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName(Pattern.compile("^(OK|Ok|Aceptar|ACEPTAR)$")));

        if (okButton.count() > 0) {
            okButton.first().click();
        } else {
            // Fallback to CSS
            Locator cssOk = modal.locator("button.swal2-confirm");
            if (cssOk.count() > 0) cssOk.first().click();
        }

        // Wait for modal to disappear so it doesn't block next actions
        try {
            modal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.HIDDEN)
                    .setTimeout(5000));
        } catch (TimeoutError ignored) {
            // Don't fail; at worst the next action will reveal it’s still blocking
        }

        return true;
    }

    private String safeInnerText(Locator loc) {
        try {
            String t = loc.innerText();
            return t == null ? "" : t.replaceAll("\\s+", " ").trim();
        } catch (Exception e) {
            return "";
        }
    }


    protected boolean isFieldOptional(String valor) {
        return valor == null ||
                valor.isBlank() ||
                valor.equalsIgnoreCase("OPCIONAL");
    }

    protected void fillIfFieldIsNotOptional(Locator locator, String valor) {
        if(!isFieldOptional(valor)){
            locator.fill(valor);
        }
    }

    protected void selectFromMatAutocompleteFailFast(Locator locator, String valueToType, String fieldName) {
        // 1. Focus and type (Angular Material requires real key events)
        locator.click();
        locator.pressSequentially(valueToType);

        // 2. Autocomplete options live in Angular CDK overlay
        Locator options = page.locator(".cdk-overlay-pane mat-option");

        // 3. FAIL FAST if no option becomes visible
        boolean hasResults = ElementAsserts.becomesVisible(options.first(), 2000);

        if (!hasResults) {
            throw new AssertExceptions("Autocomplete sin resultados en el campo '" + fieldName + "'. Valor ingresado: [" + valueToType + "]");
        }

        // 4. Extra guard: ensure count > 0
        int count = options.count();
        if (count == 0) {
            throw new AssertExceptions("El autocomplete del campo '" + fieldName + "' se abrió pero no contiene opciones seleccionables. " + "Valor ingresado: [" + valueToType + "]");
        }

        // 5. Select first option
        options.first().click();

        UiSync.waitForAppIdle(page);
    }

    protected void selectFromMatAutocompleteFailFastIfEmpty(Locator locator, String valueToType, String fieldName, String locatorOptions) {
        if(!isInputEmpty(locator)){
            log.info("El campo '{}' ya tiene un valor [{}], se omite selección en autocomplete", fieldName, locator.inputValue());
            return;
        }
        // 1. Focus and type (Angular Material requires real key events)
        locator.click();
        locator.pressSequentially(valueToType);

        // 2. Autocomplete options live in Angular CDK overlay
        Locator options = page.locator(locatorOptions);

        // 3. FAIL FAST if no option becomes visible
        boolean hasResults = ElementAsserts.becomesVisible(options.first(), 3000);

        if (!hasResults) {
            throw new AssertExceptions("Autocomplete sin resultados en el campo '" + fieldName + "'. Valor ingresado: [" + valueToType + "]");
        }

        // 4. Extra guard: ensure count > 0
        int count = options.count();
        if (count == 0) {
            throw new AssertExceptions("El autocomplete del campo '" + fieldName + "' se abrió pero no contiene opciones seleccionables. " + "Valor ingresado: [" + valueToType + "]");
        }

        // 5. Select first option
        options.first().click();

        UiSync.waitForAppIdle(page);
    }

    protected void assertAutocompleteSelected(Locator locator, String expectedKey, String fieldName) {
        // Assert filled
        ElementAsserts.assertFilled(locator, "El campo '" + fieldName + "' no fue completado");

        // Assert correct value
        String actualValue = locator.inputValue();
        if (!actualValue.contains(expectedKey)) {
            throw new AssertExceptions("Valor incorrecto en el campo '" + fieldName + "'. Esperado que contenga: [" + expectedKey + "], pero fue: [" + actualValue + "]");
        }
    }

    public void verifyCheckboxesIsChecked(Locator locator, String fieldName) {
        int count = locator.count();
        for (int i = 0; i < count; i++) {
            ElementAsserts.assertChecked(locator.nth(i),"El checkbox #" + (i+1) + " de '" + fieldName + "' no está marcado después de hacer click en 'Seleccionar toda la lista'");
        }
        log.info("[{}] ✓ Todos los checkboxes {} estan activados", fieldName, count);
    }

    protected void clickCheckboxIfNotChecked(Locator checkbox) {
        if (!checkbox.isChecked()) {
            clickAndSync(checkbox);
        }
    }

    protected void clickAndVerifyCheckboxIfNotChecked(Locator checkbox, String fieldName) {
        if (!checkbox.isChecked()) {
            clickAndSync(checkbox);
        }
        verifyCheckboxesIsChecked(checkbox, fieldName);
    }

    public void clickCheckBoxList(Locator locator) {
        try {
            // Get count
            int checkboxCount = locator.count();
            log.warn("Numero de checkboxes encontrados para seleccionar: {}", checkboxCount);

            if (checkboxCount == 0) {
                log.warn("No se encontrados documentos Obligatorios para seleccionar");
                return;
            }
            for (int i = 0; i < checkboxCount; i++) {
                Locator checkbox = locator.nth(i);
                checkbox.click();
                page.waitForTimeout(100);
                if(checkboxCount > 10 && i > checkboxCount - 5){
                    page.evaluate("el => el.scrollIntoView(true)", checkbox.elementHandle());
                    page.waitForTimeout(300);
                }
            }
        } catch (Exception e) {
            throw new AssertExceptions("No es posible seleccionar los documentos requeridos: " + e.getMessage(), e);
        }
    }


    public void clickCheckBoxList(Locator locator, Locator checkLocator) {
        try {
            // Get count
            int checkboxCount = locator.count();
            log.warn("Numero de checkboxes encontrados para seleccionar: {}", checkboxCount);

            if (checkboxCount == 0) {
                log.warn("No se encontrados documentos Obligatorios para seleccionar");
                return;
            }

            for (int i = 0; i < checkboxCount; i++) {
                Locator checkbox = checkLocator.nth(i);
                page.waitForTimeout(300);
                checkbox.click();
            }

        } catch (Exception e) {
            throw new AssertExceptions(
                    "No es posible seleccionar los documentos requeridos: " + e.getMessage(), e
            );
        }
    }

    public void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void waitForDOMContentLoaded() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void clickMenuOption(String menuName) {
        // Esperar a que la opcion exista en el DOM (en vez de una espera fija)
        page.locator("inspec-menu li.link a")
                .filter(new Locator.FilterOptions().setHasText(menuName))
                .first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        // Click via JS: el nombre se pasa como argumento (no se concatena dentro del script)
        Object clicked = page.evaluate(
                "(name) => { " +
                        "  const menuItems = document.querySelectorAll('inspec-menu li.link a'); " +
                        "  for (const item of menuItems) { " +
                        "    if (item.textContent.includes(name)) { item.click(); return true; } " +
                        "  } " +
                        "  return false; " +
                        "}",
                menuName
        );
        if (!Boolean.TRUE.equals(clicked)) {
            throw new FrameworkException("No se encontró la opción de menú: " + menuName);
        }

        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        UiSync.waitForAppIdle(page);

        log.info("{} menu clicked", menuName);
    }

    /**
     * Espera fija heredada (antes era aleatoria entre 30% y 100% de largestValueMs).
     * Ahora es determinista: largestValueMs * ui.legacyWait.scale (por defecto 1.0).
     * Pendiente: reemplazar cada uso por una espera de estado (locator visible, spinner, etc.).
     */
    public void waitRandomBetween(long largestValueMs) {
        page.waitForTimeout(Math.round(largestValueMs * legacyWaitScale()));
    }

    private static double legacyWaitScale() {
        String value = ConfigManager.getGlobal("ui.legacyWait.scale");
        if (value == null || value.isBlank()) return 1.0;
        try {
            double scale = Double.parseDouble(value.trim());
            if (scale < 0) throw new NumberFormatException("negativo");
            return scale;
        } catch (NumberFormatException e) {
            throw new FrameworkException("Valor inválido para ui.legacyWait.scale: " + value, e);
        }
    }

    protected void uploadFile(Locator locator, Path filePath) {
        locator.setInputFiles(filePath);
        UiSync.waitForAppIdle(page);
    }

    public void clickMenuPrincipal(String menuName) {
        Locator menuOption = page.locator("inspec-menu ul.g-menu-hrz").nth(1)
                .locator("li.link a:has-text('" + menuName + "')");

        menuOption.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        menuOption.click();
        UiSync.waitForAppIdle(page);
        log.info("Se hizo click en la opción del menú: {}", menuName);
    }

    public void waitUntilItemDatoAttributeIsNFilled(int minimoLlenos) {
        page.waitForFunction(
                "(minimo) => {" +
                        "  const elementos = Array.from(document.querySelectorAll('div.item-dato'));" +
                        "  const llenos = elementos.filter(el => el.textContent && el.textContent.trim().length > 0).length;" +
                        "  return llenos >= minimo;" +
                        "}",
                minimoLlenos
        );

        log.info("Al menos {} elementos div.item-dato tienen contenido.", minimoLlenos);
    }

    public void dismissAnyOverlay() {
        page.keyboard().press("Escape");
        page.waitForTimeout(300);
        page.locator("h1, .page-title, .form-header").first().click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
    public void clickUsingJS(Locator locator) {
        try {
            locator.evaluate("element => element.click()");
            log.info("✅ Click ejecutado con JS sobre: {}", locator);
        } catch (Exception e) {
            log.error("❌ No se pudo dar click usando JavaScript sobre: {}", locator);
            throw new RuntimeException("FAIL: No se pudo dar click usando JavaScript sobre el elemento: " + locator, e);
        }
    }

    protected void selectOptionByTextInsensitive(Locator select, String campo, String textoVisible) {

        try {

            select.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            String target = normalize(textoVisible);

            Locator matched = null;

            long timeoutMs = 10000;
            long pollMs = 250;
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < timeoutMs) {

                Locator options = select.locator("option:not([disabled])");
                int count = options.count();

                for (int i = 0; i < count; i++) {

                    Locator option = options.nth(i);

                    String label = safe(option.innerText());
                    String value = safe(option.getAttribute("value"));

                    if (normalize(label).equals(target) || normalize(value).equals(target)) {
                        matched = option;
                        break;
                    }
                }

                if (matched != null) {
                    break;
                }

                page.waitForTimeout(pollMs);
            }

            if (matched == null) {

                Locator options = select.locator("option");

                log.error(
                        "[{}] No se encontró la opción [{}]. Opciones disponibles: {}",
                        campo,
                        textoVisible,
                        options.count()
                );

                for (int i = 0; i < options.count(); i++) {

                    log.error(
                            "[{}] Opción disponible [{}]: [{}]",
                            campo,
                            i,
                            safe(options.nth(i).innerText()).trim()
                    );
                }

                throw new AssertExceptions(
                        "No se encontró opción para " + campo + ": " + textoVisible
                );
            }

            String label = safe(matched.innerText()).trim();
            String value = safe(matched.getAttribute("value"));

            if (value.isBlank()) {
                throw new AssertExceptions(
                        "No se encontró value para " + campo + ": " + textoVisible
                );
            }
            select.selectOption(value);
            UiSync.waitForAppIdle(page);
            log.info("[{}] Opción seleccionada correctamente: {}", campo,label);

        } catch (AssertExceptions e) {

            throw e;

        } catch (Exception e) {

            throw new AssertExceptions(
                    "Error seleccionando " + campo + ": " + textoVisible,
                    e
            );
        }
    }

    private void esperarOpciones(Locator select) {
        select.waitFor();
        // Wait until at least one option exists (no nth hardcode)
        Locator options = select.locator("option");
        options.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    }

    private static String normalize(String s) {
        if (s == null) return "";
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase(java.util.Locale.ROOT);
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    protected void seleccionarOpcionPorTexto(Locator select, String campo, String textoVisible) {
        try {
            esperarOpciones(select);

            Locator opcion = select.locator("option")
                    .filter(new Locator.FilterOptions().setHasText(textoVisible))
                    .first();

            opcion.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED));

            String value = opcion.getAttribute("value");

            if (value == null || value.isBlank()) {
                throw new AssertExceptions("No se encontró value para " + campo + ": " + textoVisible);
            }

            select.selectOption(value);
            UiSync.waitForAppIdle();
            //log.info("[{}] Opción Seleccionada: {}", campo, textoVisible);

        } catch (Exception e) {
            throw new AssertExceptions("Error seleccionando " + campo + ": " + textoVisible, e);
        }
    }
}
