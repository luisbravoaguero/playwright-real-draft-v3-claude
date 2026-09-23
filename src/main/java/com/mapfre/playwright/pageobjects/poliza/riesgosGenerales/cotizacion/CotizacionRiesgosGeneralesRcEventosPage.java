package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.UiSync;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.GestorModal;
import com.mapfre.utils.waits.OptionalModalWait;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CotizacionRiesgosGeneralesRcEventosPage extends BasePage {
    private final Locator cmbProducto;

    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtAsegurado;

    private final Locator cmbRamo;
    private final Locator cmbMoneda;
    private final Locator cmbCanal;

    private final Locator cmbDepartamento;
    private final Locator cmbProvincia;
    private final Locator cmbDistrito;
    private final Locator txtDireccionRiesgo;

    private final Locator txtFechaDesde;
    private final Locator txtFechaHasta;

    private final Locator cmbTipoEvento;
    private final Locator txtEvento;
    private final Locator txtAforo;
    private final Locator txtSeguridad;
    private final Locator txtSumaAsegurada;

    private final Locator btnSiguiente;

    private final Locator mdlAlertaCotizador;

    public CotizacionRiesgosGeneralesRcEventosPage(Page page) {
        super(page);
        this.cmbProducto = page.locator("oim-select[label='Producto'] select");

        this.cmbTipoDocumento = page.locator("oim-select[name='TipoDocumento'] select");
        this.txtNumeroDocumento = page.locator("input[name='numDocumento']");
        this.txtAsegurado = page.locator("input[name='asegurado']");

        this.cmbRamo = page.locator("oim-select[name='trabajoContratar'] select");
        this.cmbMoneda = page.locator("oim-select[name='moneda'] select");
        this.cmbCanal = page.locator("oim-select[name='canal'] select");

        this.cmbDepartamento = page.locator("oim-select[name='mDepartamento'] select");
        this.cmbProvincia = page.locator("oim-select[name='mProvincia'] select");
        this.cmbDistrito = page.locator("oim-select[name='mDistrito'] select");
        this.txtDireccionRiesgo = page.locator("input[name='ubicacion']");

        this.txtFechaDesde = page.locator("oim-datepicker[name='nFechaInicialDuracion'] input");
        this.txtFechaHasta = page.locator("oim-datepicker[name='mFechaFinalDuracion'] input");

        this.cmbTipoEvento = page.locator("oim-select[name='tipoEvento'] select");
        this.txtEvento = page.locator("input[name='evento']");
        this.txtAforo = page.locator("input[name='Aforo']");
        this.txtSeguridad = page.locator("input[name='Seguridad']");
        this.txtSumaAsegurada = page.locator("input[name='SumaAsegurada']");

        this.btnSiguiente = page.locator("input[type='button'][value='Siguiente']");

        this.mdlAlertaCotizador = page.locator(".swal2-popup:has-text('Alerta!')");

    }
    public void seleccionarProducto(String producto) {

        log.info("Seleccionando producto: {}", producto);
        selectAndSync(cmbProducto, producto);
        cerrarModalAlertaSiExiste();
        waitAppReady();
        log.info("Producto seleccionado correctamente: {}", producto);
    }
    public void ingresarDatosContratante(
            String tipoDocumento,
            String numeroDocumento,
            String asegurado) {

        log.info("Ingresando datos del contratante.");

        log.info("Seleccionando tipo de documento: {}", tipoDocumento);
        selectAndSync(cmbTipoDocumento, tipoDocumento);

        log.info("Ingresando numero de documento: {}", numeroDocumento);
        txtNumeroDocumento.fill(numeroDocumento);
        waitAppReady();

        log.info("Ingresando asegurado: {}", asegurado);
        txtAsegurado.fill(asegurado);

        log.info("Datos del contratante ingresados correctamente.");
        txtAsegurado.press("Tab");

    }
    public void ingresarDatosEvento(String ramo, String moneda, String canal) {

        log.info("Ingresando datos del evento.");
        log.info("Seleccionando ramo: {}", ramo);
        selectAndSync(cmbRamo, ramo);
        log.info("Seleccionando moneda: {}", moneda);
        selectAndSync(cmbMoneda, moneda);
        log.info("Seleccionando canal: {}", canal);
        selectAndSync(cmbCanal, canal);
        waitAppReady();
        log.info("Datos del evento ingresados correctamente.");
    }

    public void ingresarUbicacionRiesgo() {

        log.info("Ingresando ubicación del riesgo.");

        log.info("Seleccionando departamento: LIMA");
        selectAndSync(cmbDepartamento,  "LIMA" );
        waitAppReady();

        log.info("Seleccionando provincia: LIMA" );
        selectAndSync(cmbProvincia, "LIMA");
        waitAppReady();

        log.info("Seleccionando distrito: LINCE");
        selectAndSync(cmbDistrito, "LINCE");
        waitAppReady();

        txtDireccionRiesgo.fill("AV. PRINCIPAL");
        txtDireccionRiesgo.press("Tab");
        waitAppReady();
        log.info("Ubicación del riesgo ingresada correctamente.");
    }

    public void ingresarDuracionEvento(
            String fechaDesde,
            String fechaHasta) {

        log.info("Ingresando fecha desde: {}", fechaDesde);

        txtFechaDesde.click();
        txtFechaDesde.press("Control+A");
        txtFechaDesde.press("Delete");
        txtFechaDesde.type(fechaDesde);

        txtFechaDesde.press("Tab");

        waitAppReady();

        log.info("Ingresando fecha hasta: {}", fechaHasta);

        txtFechaHasta.click();
        txtFechaHasta.press("Control+A");
        txtFechaHasta.press("Delete");
        txtFechaHasta.type(fechaHasta);

        txtFechaHasta.press("Tab");

        waitAppReady();

        log.info("Fechas ingresadas correctamente.");
    }
    public void ingresarInformacionEvento(
            String tipoEvento,
            String evento,
            String aforo,
            String seguridad,
            String sumaAsegurada) {

        log.info("Ingresando información del evento.");

        log.info("Seleccionando tipo de evento: {}", tipoEvento);
        selectAndSync(cmbTipoEvento, tipoEvento);
        waitAppReady();

        log.info("Ingresando evento: {}", evento);
        txtEvento.fill(evento);
        txtEvento.press("Tab");

        log.info("Ingresando aforo: {}", aforo);
        txtAforo.fill(aforo);

        log.info("Ingresando seguridad: {}", seguridad);
        txtSeguridad.fill(seguridad);

        log.info("Ingresando suma asegurada: {}", sumaAsegurada);
        txtSumaAsegurada.fill(sumaAsegurada);
        txtSumaAsegurada.press("Tab");

        waitAppReady();

        log.info("Información del evento ingresada correctamente.");
    }

    public void clickBotonSiguiente() {

        log.info("Haciendo clic en el botón Siguiente.");

        btnSiguiente.click();

        waitAppReady();
        UiSync.waitForAppIdle(page);

        log.info("Se completó la navegación al siguiente paso.");
    }
    private void cerrarModalAlertaSiExiste() {

        boolean modalDetectado =
                GestorModal.clickBotonSiExiste(
                        mdlAlertaCotizador,
                        "OK"
                );

        if (modalDetectado) {
            log.info("Se detectó el modal de alerta del cotizador y se presionó OK.");
            waitAppReady();
        }
    }
}
