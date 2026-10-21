package com.mapfre.playwright.pageobjects.demo.letskodeit;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.playwright.driver.PageTimeouts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/** Pagina "All Courses" de LetsKodeIt, que se abre en una nueva pestaña desde {@link PracticaPage}. */
public class CursosPage extends BasePage {

    private final Locator titulo;
    private final Locator categoriaSelect;
    private final Locator buscarInput;
    private final Locator buscarButton;
    private final Locator titulosCursos;

    public CursosPage(Page page) {
        super(page);
        this.titulo = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        this.categoriaSelect = page.locator("select[name='categories']");
        this.buscarInput = page.getByPlaceholder("Search Course");
        this.buscarButton = page.locator("button.find-course");
        this.titulosCursos = page.locator("#course-list h4");
    }

    /**
     * Prepara la pestaña recien abierta (llamar una sola vez por pestaña, no en el constructor: los steps crean
     * un CursosPage nuevo en cada step). El sitio puede mostrar un modal de newsletter en cualquier momento;
     * si tapa una accion, Playwright lo cierra antes de continuar.
     */
    static CursosPage enNuevaPestana(Page popup) {
        Locator cerrarNewsletter = popup.locator("#zenPopupModalClose");
        popup.addLocatorHandler(cerrarNewsletter, modal -> cerrarNewsletter.click());
        return new CursosPage(popup);
    }

    public void assertLoaded() {
        ElementAsserts.assertUrl(page, Pattern.compile("/courses"), "LA NUEVA PESTAÑA DEBE ESTAR EN /courses");
        ElementAsserts.assertContainsText(titulo, "All Courses", "TITULO All Courses DEBE SER VISIBLE");
    }

    /** Elegir una categoria navega a /courses/category/... y cambia el titulo a "Category : X". */
    public void seleccionarCategoria(String categoria) {
        categoriaSelect.selectOption(new SelectOption().setLabel(categoria));
        page.waitForURL(Pattern.compile("/courses/category/"));
    }

    public void assertCategoriaSeleccionada(String categoria) {
        ElementAsserts.assertContainsText(titulo, "Category : " + categoria,
                "EL TITULO DEBE MOSTRAR LA CATEGORIA " + categoria);
        ElementAsserts.assertContainsText(categoriaSelect.locator("option:checked"), categoria,
                "EL COMBO CATEGORIA DEBE TENER SELECCIONADO " + categoria);
    }

    /** La busqueda filtra los cursos por AJAX (la URL no cambia). */
    public void buscarCurso(String curso) {
        buscarInput.fill(curso);
        buscarButton.click();
    }

    /** Espera (con re-intentos) a que todos los cursos listados contengan el texto, y que haya al menos uno. */
    public void assertResultadosContienen(String curso) {
        ElementAsserts.assertTextHasValue(buscarInput, curso, "EL CAMPO BUSCAR DEBE CONTENER " + curso);
        // timeout de la pestaña (popup), no el global
        double timeoutMs = PageTimeouts.assertTimeoutFor(titulosCursos);
        try {
            // filtro con String = subcadena sin distinguir mayusculas (no usar Pattern.quote: \Q..\E no existe en JS)
            assertThat(titulosCursos.filter(new Locator.FilterOptions().setHasNotText(curso)))
                    .hasCount(0, new LocatorAssertions.HasCountOptions().setTimeout(timeoutMs));
            assertThat(titulosCursos.first()).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeoutMs));
        } catch (AssertionError | RuntimeException e) {
            throw new AssertExceptions("LOS RESULTADOS DE LA BUSQUEDA DEBEN CONTENER SOLO CURSOS DE '" + curso + "'", e);
        }
        log.info("Cursos encontrados para '{}': {}", curso, titulosCursos.allInnerTexts());
    }
}
