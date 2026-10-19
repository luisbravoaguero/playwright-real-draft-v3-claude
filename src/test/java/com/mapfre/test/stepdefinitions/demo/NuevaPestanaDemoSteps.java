package com.mapfre.test.stepdefinitions.demo;

import com.mapfre.playwright.pageobjects.demo.letskodeit.CursosPage;
import com.mapfre.playwright.pageobjects.demo.letskodeit.PracticaPage;
import com.mapfre.reporting.ExtentEvidence;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Ejemplo de referencia de nueva pestaña (sitio demo LetsKodeIt).
 * Los page objects se crean dentro de cada step con pageProvider.get(), porque la pestaña activa cambia
 * a mitad del escenario (popup abierto / vuelta a la principal).
 */
public class NuevaPestanaDemoSteps {
    private final PageProvider pageProvider;

    public NuevaPestanaDemoSteps(PageProvider pageProvider) {
        this.pageProvider = pageProvider;
    }

    private PracticaPage practicaPage() {
        return new PracticaPage(pageProvider.get());
    }

    private CursosPage cursosPage() {
        return new CursosPage(pageProvider.get());
    }

    @Given("el usuario abre la pagina de practica de LetsKodeIt")
    public void elUsuarioAbreLaPaginaDePracticaDeLetsKodeIt() {
        PracticaPage practicaPage = practicaPage();
        practicaPage.abrir();
        practicaPage.assertLoaded();
    }

    @And("en la pagina de practica selecciona el auto {string}")
    public void enLaPaginaDePracticaSeleccionaElAuto(String auto) {
        PracticaPage practicaPage = practicaPage();
        practicaPage.seleccionarAuto(auto);
        practicaPage.assertAutoSeleccionado(auto);
    }

    @When("en la pagina de practica hace clic en Open Tab y se abre la pagina de cursos en una nueva pestaña")
    public void enLaPaginaDePracticaHaceClicEnOpenTabYSeAbreLaPaginaDeCursosEnUnaNuevaPestana() {
        practicaPage().abrirPestanaCursos();
    }

    @Then("en la nueva pestaña se muestra la pagina All Courses")
    public void enLaNuevaPestanaSeMuestraLaPaginaAllCourses() {
        cursosPage().assertLoaded();
    }

    @When("en la nueva pestaña selecciona la categoria {string}")
    public void enLaNuevaPestanaSeleccionaLaCategoria(String categoria) {
        CursosPage cursosPage = cursosPage();
        cursosPage.seleccionarCategoria(categoria);
        cursosPage.assertCategoriaSeleccionada(categoria);
    }

    @And("en la nueva pestaña busca el curso {string}")
    public void enLaNuevaPestanaBuscaElCurso(String curso) {
        cursosPage().buscarCurso(curso);
    }

    @Then("en la nueva pestaña los resultados contienen {string}")
    public void enLaNuevaPestanaLosResultadosContienen(String curso) {
        cursosPage().assertResultadosContienen(curso);
        ExtentEvidence.shot("Resultados de la busqueda '" + curso + "' en la nueva pestaña");
    }

    @Then("en la pagina de practica el auto seleccionado sigue siendo {string}")
    public void enLaPaginaDePracticaElAutoSeleccionadoSigueSiendo(String auto) {
        PracticaPage practicaPage = practicaPage();
        practicaPage.assertLoaded();
        practicaPage.assertAutoSeleccionado(auto);
    }

    @And("en la pagina de practica ingresa el nombre {string} y se muestra en el campo")
    public void enLaPaginaDePracticaIngresaElNombreYSeMuestraEnElCampo(String nombre) {
        PracticaPage practicaPage = practicaPage();
        practicaPage.ingresarNombre(nombre);
        practicaPage.assertNombre(nombre);
        ExtentEvidence.shot("Pagina de practica despues de volver de la nueva pestaña");
    }
}
