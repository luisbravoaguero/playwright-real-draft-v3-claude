package com.mapfre.test.stepdefinitions.constanciasSctrVidaLey.sctrMineria.mesVencido.DeclaracionSctrMineria;

import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.ClientesSctrMineriaPage;
import com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctrMineria.mesVencido.PerfilClienteSctrMineriaPage;
import com.mapfre.test.hooks.PageProvider;
import io.cucumber.java.en.And;

public class DeclaracionSctrMineriaMesVencidoSteps {
    private final ClientesSctrMineriaPage clientesSctrMineriaPage;
    private final PerfilClienteSctrMineriaPage perfilClienteSctrMineriaPage;
    public DeclaracionSctrMineriaMesVencidoSteps(PageProvider pageProvider) {
        this.clientesSctrMineriaPage = new ClientesSctrMineriaPage(pageProvider.get());
        this.perfilClienteSctrMineriaPage = new PerfilClienteSctrMineriaPage(pageProvider.get());
    }
    @And("en la pagina Clientes seleccionamos el numero de poliza {string} sctr mineria")
    public void enLaPaginaClientesSeleccionamosElNumeroDePolizaSctrMineria(String numero_poliza) {
        clientesSctrMineriaPage.assertLoadedSctrMineria();
        clientesSctrMineriaPage.fillFormularioClientesSctrMineria(numero_poliza);
        clientesSctrMineriaPage.processAndAssertSuccessBusquedaNumeroPolizaSctrMineria(60_000, 500);
        clientesSctrMineriaPage.processAndAssertSuccessSeleccionClienteSctrMineria(60_000, 500);
    }

    @And("en la pagina Perfil Cliente seleccionamos una aplicacion para declarar sctr mineria mes vencido")
    public void enLaPaginaPerfilClienteSeleccionamosUnaAplicacionParaDeclararSctrMineriaMesVencido() {
        perfilClienteSctrMineriaPage.assertLoadedSctrMineria();
        perfilClienteSctrMineriaPage.abrirPanelDeAplicacionesSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarAplicacionConEstadoNoDeclaradaParaDeclararMesVencidoSctrMineria();
        perfilClienteSctrMineriaPage.seleccionarDeclararAplicacionButtonSctrMineria();
    }
}
