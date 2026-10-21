package com.mapfre.asserts;

import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.driver.PageTimeouts;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PageAssertions;
import java.util.regex.Pattern;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

    /**
     *ElementAsserts no es una Excepción. ElementAsserts es una clase de utilidad (una caja de herramientas) que lanza (throws) excepciones.
     *ElementAsserts: Es una clase que contiene lógica para verificar elementos de la web. Su función es ejecutar la validación y, si falla, crear e iniciar una excepción.
     */

public final class ElementAsserts {
        // El timeout de cada aserción es el de la pestaña del locator (PageTimeouts): ui.popup.pageTimeoutMs en
        // popups abiertos con PopupHandler, ui.assert.timeoutMs en el resto. No cambiar el timeout global.

        private ElementAsserts() {
        }

        private static double timeout(Locator locator) {
            return PageTimeouts.assertTimeoutFor(locator);
        }

        public static void assertVisible(Locator locator, String message) {
            try {
                // Playwright hará re-try automático hasta que sea visible o expire el timeout
                assertThat(locator).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                // Transformamos el error técnico en una excepción de negocio
                throw new AssertExceptions(message + " - El elemento no se hizo visible.", e);
            }
        }

        public static void assertContainsText(Locator locator, String text, String message) {
            try {
                assertThat(locator).containsText(text,
                        new LocatorAssertions.ContainsTextOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento no contiene el texto: '" + text + "'", e);
            }
        }

        public static boolean assertVisible(Locator locator, int timeoutMs) {
            try {
                // Usamos assertThat para aprovechar los re-intentos automáticos de Playwright
                // .setVisible(true) asegura que la aserción espere la visibilidad
                assertThat(locator).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeoutMs));
                return true;
            } catch (AssertionError | Exception e) {
                // Si falla la aserción o hay un timeout, retornamos false en lugar de lanzar excepción
                return false;
            }
        }

        public static boolean becomesVisible(Locator locator, int timeoutMs) {
            try {
                assertThat(locator).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeoutMs));
                return true;
            } catch (AssertionError | RuntimeException ignored) {
                return false;
            }
        }


        public static boolean becomesHidden(Locator locator, int timeoutMs) {
            try {
                assertThat(locator).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(timeoutMs));
                return true;
            } catch (AssertionError | RuntimeException ignored) {
                return false;
            }
        }


        public static void assertHidden(Locator locator, String message) {
            try {
                assertThat(locator).isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento debería estar oculto pero permanece visible.", e);
            }
        }

        public static void assertEnabled(Locator locator, String message) {
            try {
                assertThat(locator).isEnabled(new LocatorAssertions.IsEnabledOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento está deshabilitado (Disabled).", e);
            }
        }

        public static void assertFilled(Locator locator, String message) {
            try {
                assertThat(locator).not().isEmpty(new LocatorAssertions.IsEmptyOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento está vacío.", e);
            }
        }

        public static boolean isElementDisabled(Locator locator, int timeoutMs) {
            try {
                assertThat(locator).isDisabled(new LocatorAssertions.IsDisabledOptions().setTimeout(timeoutMs));
                return true;
            } catch (Exception | AssertionError e) {
                return false;
            }
        }

        public static void assertTextContains(Locator locator, String expectedPart, String message) {
            try {
                assertThat(locator).containsText(expectedPart,
                        new LocatorAssertions.ContainsTextOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " | No se encontró el texto esperado: " + expectedPart, e);
            }
        }

        public static void fail(String message) {
            throw new AssertExceptions("Error de Negocio: " + message);
        }

        public static void assertVisibileUIMessage(Locator locator, String message) {
            String uiError = locator.innerText().trim();
            throw new AssertExceptions(message + ": " + uiError);
        }

        public static void assertUIMessage(String message) {
            throw new AssertExceptions(message);
        }

        public static void assertTextMatches(Locator locator, Pattern pattern, String message) {
            try {
                assertThat(locator).hasText(pattern, new LocatorAssertions.HasTextOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(
                        message + " - El texto de la UI no cumple el patrón esperado: " + pattern.pattern(), e
                );
            }
        }

        public static void assertTextHasValue(Locator actualValue, String expectedValue, String message) {
            try {
                assertThat(actualValue).hasValue(expectedValue,
                        new LocatorAssertions.HasValueOptions().setTimeout(timeout(actualValue)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(
                        message + " - El elemento no tiene el valor: " + expectedValue, e
                );
            }
        }

        public static void assertLocatorValuesAreEqual(Locator actualValue, Locator expectedValue, String message) {
            try {
                String value1 = actualValue.inputValue();
                String value2 = expectedValue.inputValue();

                if (!value1.equals(value2)) {
                    throw new AssertionError("Los valores no son iguales: '" + value1 + "' != '" + value2 + "'");
                }
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - Valores diferentes: " + e.getMessage(), e);
            }
        }

        public static void assertChecked(Locator locator, String message) {
            try {
                assertThat(locator).isChecked(new LocatorAssertions.IsCheckedOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El checkbox no está marcado.", e);
            }
        }

        public static void assertIsNotChecked(Locator locator, String message) {
            try {
                assertThat(locator).not().isChecked(new LocatorAssertions.IsCheckedOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El checkbox debe estar desmarcado.", e);
            }
        }

        public static void assertCellNotEmpty(Locator cellLocator, String cellDescription) {
            try {
                assertThat(cellLocator).not().isEmpty(
                        new LocatorAssertions.IsEmptyOptions().setTimeout(timeout(cellLocator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(cellDescription + " - La celda está vacía, se esperaba un valor.", e);
            }
        }

        public static void assertTrue(boolean found, String s) {
            if (!found) {
                throw new AssertExceptions(s);
            }
        }

        public static <T> void assertNotEmpty(java.util.Collection<T> collection, String message) {
            try {
                if (collection == null || collection.isEmpty()) {
                    throw new AssertionError("La colección está vacía.");
                }
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - Se esperaba al menos un elemento en la colección.", e);
            }
        }

        public static <T> void assertNotNull(T object, String message) {
            try {
                if (object == null) {
                    throw new AssertionError("El objeto es nulo.");
                }
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - Se esperaba un objeto no nulo.", e);
            }
        }

        public static void assertContainsPatternText(Locator locator, String text, String message) {
            try {
                assertThat(locator).containsText(Pattern.compile(text),
                        new LocatorAssertions.ContainsTextOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento no contiene el texto: '" + text + "'", e);
            }
        }

        public static void assertUrl(Page page, Pattern pattern, String message) {
            try {
                assertThat(page).hasURL(pattern,
                        new PageAssertions.HasURLOptions().setTimeout(PageTimeouts.assertTimeoutFor(page)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - La URL no cumple el patrón esperado: " + pattern.pattern(), e);
            }
        }

        public static void assertContainsTextIgonoreCase(Locator locator, String text, String message) {
            try {
                assertThat(locator).containsText(Pattern.compile(text, Pattern.CASE_INSENSITIVE),
                        new LocatorAssertions.ContainsTextOptions().setTimeout(timeout(locator)));
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - El elemento no contiene el texto: '" + text + "'", e);
            }
        }

        public static void assertConstainsClass(Locator locator, String className, String message) {
            try {
                String classes = locator.getAttribute("class");
                if (classes == null || !classes.contains(className)) {
                    throw new AssertionError("El elemento no contiene la clase: '" + className + "'");
                }
            } catch (Exception | AssertionError e) {
                throw new AssertExceptions(message + " - " + e.getMessage(), e);
            }
        }

    }

