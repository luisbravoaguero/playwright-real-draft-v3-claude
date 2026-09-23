package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctr;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido.ClientesSctrGeneralPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class ClientesSctrGeneralSteps {
    private final ClientesSctrGeneralPage clientesSctrGeneralPage;

    public ClientesSctrGeneralSteps(PageProvider pageProvider){
        this.clientesSctrGeneralPage = new ClientesSctrGeneralPage(pageProvider.get());
    }
    @And("en la pagina Clientes seleccionamos la opcion Constancia dentro del menu Consultas")
    public void enLaPaginaClientesSeleccionamosLaOpcionConstanciaDentroDelMenuConsultas() {
        clientesSctrGeneralPage.assertLoaded();
        clientesSctrGeneralPage.seleccionarConstanciaMenuConsultas();
    }
}
