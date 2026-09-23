package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CotizacionRiesgosGeneralesRcDemolicionesPage extends BasePage {
    private final Locator txtFechaDesde;
    private final Locator txtFechaHasta;
    private final Locator txtAsegurado;
    private final Locator txtActividadRealizar;

    private final Locator cmbDepartamento;
    private final Locator cmbProvincia;
    private final Locator cmbDistrito;
    private final Locator txtDireccionRiesgo;
    private final Locator txtSumaAsegurada;

    private final Locator mdlTiempoMaximoDuracion;

    private final Locator btnSiguiente;

    public CotizacionRiesgosGeneralesRcDemolicionesPage(Page page) {
        super(page);
        this.txtFechaDesde =  page.locator("oim-datepicker[name='nFechaInicial'] input");
        this.txtFechaHasta =  page.locator("oim-datepicker[name='mFechaFinal'] input");
        this.txtAsegurado =   page.locator("input[name='asegurado']");
        this.txtActividadRealizar = page.locator("input[name='ActividadRealizar']");

        this.cmbDepartamento = page.locator("oim-select[name='mDepartamento'] select");
        this.cmbProvincia = page.locator("oim-select[name='mProvincia'] select");
        this.cmbDistrito = page.locator("oim-select[name='mDistrito'] select");

        this.txtDireccionRiesgo = page.locator("input[name='ubicacion']");
        this.txtSumaAsegurada = page.locator("input[name='SumaAsegurada']");

        this.mdlTiempoMaximoDuracion =page.locator(".swal2-popup").filter(new Locator.FilterOptions().setHasText("TIEMPO MÁXIMO DE DURACIÓN"));

        this.btnSiguiente = page.locator("input[value='Siguiente']");

    }
    public void ingresarDuracionEvento(
            String fechaDesde,
            String fechaHasta,
            String asegurado,
            String actividad) {

        log.info("Ingresando fecha desde: {}", fechaDesde);
        txtFechaDesde.click();
        txtFechaDesde.press("Control+A");
        txtFechaDesde.type(fechaDesde);
        txtFechaDesde.press("Tab");

        waitAppReady();

        log.info("Ingresando fecha hasta: {}", fechaHasta);
        txtFechaHasta.click();
        txtFechaHasta.press("Control+A");
        txtFechaHasta.type(fechaHasta);
        txtFechaHasta.press("Tab");

        waitAppReady();
        validarModalTiempoMaximoDuracion();

        log.info("Ingresando asegurado: {}", asegurado);
        txtAsegurado.fill(asegurado);
        txtAsegurado.press("Tab");

        waitAppReady();

        log.info("Ingresando actividad a realizar: {}", actividad);
        txtActividadRealizar.fill(actividad);
        txtActividadRealizar.press("Tab");

        waitAppReady();

        log.info("Datos de vigencia y actividad ingresados correctamente.");
    }

    public void ingresarDatosRiesgo(
            String direccionRiesgo,
            String sumaAsegurada) {

        log.info("Ingresando datos de ubicación del riesgo.");

        log.info("Seleccionando departamento: LIMA");
        selectAndSync(cmbDepartamento, "LIMA");
        waitAppReady();

        log.info("Esperando carga de provincias.");
        waitAppReady();

        log.info("Seleccionando provincia: LIMA");
        selectAndSync(cmbProvincia, "LIMA");
        waitAppReady();

        log.info("Esperando carga de distritos.");
        waitAppReady();

        log.info("Seleccionando distrito: MIRAFLORES");
        selectAndSync(cmbDistrito, "MIRAFLORES");
        waitAppReady();

        log.info("Ingresando dirección de riesgo: {}", direccionRiesgo);
        txtDireccionRiesgo.fill(direccionRiesgo);
        txtDireccionRiesgo.press("Tab");

        waitAppReady();

        log.info("Ingresando suma asegurada: {}", sumaAsegurada);
        txtSumaAsegurada.fill(sumaAsegurada);
        txtSumaAsegurada.press("Tab");

        waitAppReady();

        log.info("Datos de riesgo ingresados correctamente.");
    }

    private void validarModalTiempoMaximoDuracion() {

        boolean modalDetectado =
                GestorModal.clickBotonSiExiste(
                        mdlTiempoMaximoDuracion,
                        "REINTENTAR"
                );

        if (modalDetectado) {
            log.info("Se detectó el modal de tiempo máximo de duración y se presionó REINTENTAR.");
            waitAppReady();
        }
    }

    public void clickSiguiente() {

        log.info("Haciendo clic en el botón Siguiente.");
        btnSiguiente.click();
        UiSync.waitForAppIdle(page);
        log.info("Se avanzó correctamente al siguiente paso del cotizador.");
        btnSiguiente.click();
        UiSync.waitForAppIdle(page);
    }

}
