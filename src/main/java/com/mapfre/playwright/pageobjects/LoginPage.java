package com.mapfre.playwright.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mapfre.config.ConfigManager;
import com.mapfre.exceptions.FrameworkException;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;


    public LoginPage(Page page) {
        super(page);
        this.usernameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ingrese su usuario"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Ingresa tu contraseña"));
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ingresar"));
    }

    public void loginOim() {
        usernameInput.fill(requiredCredential("user.email"));
        passwordInput.fill(requiredCredential("user.password"));
        clickAndSync(loginButton);
    }

    private static String requiredCredential(String key) {
        String value = ConfigManager.getGlobal(key);
        if (value == null || value.isBlank()) {
            String envKey = key.toUpperCase().replace('.', '_');
            throw new FrameworkException("Falta la credencial '" + key + "'. Definirla con -D" + key + "=..., "
                    + "la variable de entorno " + envKey + " o en config/local/global.properties");
        }
        return value;
    }


}
