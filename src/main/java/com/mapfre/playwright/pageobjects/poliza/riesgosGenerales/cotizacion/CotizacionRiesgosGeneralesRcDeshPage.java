package com.mapfre.playwright.pageobjects.poliza.riesgosGenerales.cotizacion;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CotizacionRiesgosGeneralesRcDeshPage extends BasePage {


    private final Locator cmbProducto;
    private final Locator modalAlerta;
    private final Locator tituloModalAlerta;
    private final Locator mensajeModalAlerta;

    private final Locator cmbTipoDocumento;
    private final Locator txtNumeroDocumento;
    private final Locator txtCorredor;

// DATOS DEL CONTRATO U OBRA
    private final Locator txtTrabajoObra;
    private final Locator cmbDepartamento;
    private final Locator cmbProvincia;
    private final Locator cmbDistrito;
    private final Locator txtDireccionRiesgo;
    private final Locator cmbRamo;
    private final Locator cmbMoneda;
    private final Locator txtNumeroTrabajadores;
    private final Locator txtValorContrato;
    private final Locator txtSumaAsegurada;
    private final Locator cmbCanal;

    private final Locator btnSiguiente;

    private final Locator lblTituloResultadoCotizacion;
    private final Locator lblNumeroCotizacion;

    private final Locator cmbRamoDeshonestidad;
    // Suma asegurada Deshonestidad
    private final Locator txtSumaAseguradaDesh;
    private final Locator cmbCanalDesh;

    public CotizacionRiesgosGeneralesRcDeshPage(Page page) {
        super(page);

        this.cmbProducto = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Producto"));
        this.modalAlerta = page.locator("div.swal2-popup.swal2-modal");
        this.tituloModalAlerta = page.locator(".swal2-title");
        this.mensajeModalAlerta = page.locator(".swal2-html-container");
        this.cmbTipoDocumento = page.getByLabel("Tipo de documento");
        this.txtNumeroDocumento = page.locator("input[name='numDocumento']");
        this.txtCorredor = page.locator("input[name='corredor']");
        this.txtTrabajoObra =page.locator("input[name='detalleTrabajoObra']");
        this.cmbDepartamento =page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Departamento"));
        this.cmbProvincia = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Provincia"));
        this.cmbDistrito =  page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Distrito"));
        this.txtDireccionRiesgo = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Dirección del riesgo"));
        this.cmbRamo = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Ramo"));
        this.cmbMoneda = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Moneda"));
        this.txtNumeroTrabajadores = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Numero de Trabajadores"));
        this.txtValorContrato = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Valor del contrato u obra"));
        this.txtSumaAsegurada = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Suma asegurada"));
        this.cmbCanal = page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Canal"));
        this.btnSiguiente =   page.locator("input[value='Siguiente']");
        this.lblTituloResultadoCotizacion = page.getByText("Cotización de riesgos generales");
        this.lblNumeroCotizacion = page.getByText("Nro de cotización:");
        this.cmbRamoDeshonestidad = page.locator("oim-select").filter(new Locator.FilterOptions().setHasText("Ramo Deshonestidad")).locator("select");
        this.txtSumaAseguradaDesh = page.locator("input[name='sumaAseguradaDesh']");
        this.cmbCanalDesh = page.locator("select:has-text('Enlace')");
    }

    public void seleccionarProducto(String producto) {
        selectOptionAndSync(cmbProducto, producto);
        waitAppReady();
        if (GestorModal.existeModal(modalAlerta)) {

            String titulo = GestorModal.obtenerTexto(tituloModalAlerta);
            String mensaje = GestorModal.obtenerTexto(mensajeModalAlerta);
            log.info("[RIESGOS GENERALES][RC DESH] Modal detectado. Titulo: '{}' Mensaje: '{}'", titulo, mensaje );

            GestorModal.clickBotonSiExiste(modalAlerta,"OK");
            log.info("[RIESGOS GENERALES][RC DESH] Se seleccionó el botón OK del modal");
        }

        log.info("[RIESGOS GENERALES][RC DESH] Producto seleccionado: {}",producto);
    }

    public void ingresarDatosContratante(String tipoDocumento, String numeroDocumento, String corredor)
    {
        selectByOptionIfEnableAndSync(cmbTipoDocumento,tipoDocumento);
        fillAndBlurSync(txtNumeroDocumento,numeroDocumento);
        fillAndBlurSync(txtCorredor,  corredor );
        log.info("[RIESGOS GENERALES][RC DESH] Datos del contratante completados. Tipo Documento: {}, Documento: {}, Corredor: {}",tipoDocumento, numeroDocumento,corredor);
    }

    public void ingresarDatosContrato(
            String trabajoObra,
            String departamento,
            String provincia,
            String distrito,
            String direccionRiesgo,
            String ramo,
            String moneda,
            String numeroTrabajadores,
            String valorContrato,
            String sumaAsegurada,
            String canal
    ) {

        fillAndBlurSync(txtTrabajoObra, trabajoObra);
        selectOptionAndSync(cmbDepartamento, departamento);
        selectOptionAndSync(cmbProvincia, provincia);
        selectOptionAndSync(cmbDistrito, distrito);
        fillAndBlurSync(txtDireccionRiesgo, direccionRiesgo);
        selectOptionAndSync(cmbRamo, ramo);
        waitAppReady();
        selectOptionAndSync(cmbMoneda, moneda);
        fillAndBlurSync(txtNumeroTrabajadores, numeroTrabajadores);
        fillAndBlurSync(txtValorContrato, valorContrato);
        fillAndBlurSync(txtSumaAsegurada, sumaAsegurada);
        selectOptionAndSync(cmbCanal, canal);

        log.info("[RIESGOS GENERALES][RC DESH] Datos del contrato u obra completados");
    }

    public void clicksiguiente() {
        clickAndSync(btnSiguiente);
        log.info("[RIESGOS GENERALES][RC DESH] clic siguiente realizado correctamente");
    }
    public String validarYObtenerNumeroCotizacion() {

        ElementAsserts.assertVisible(
                lblTituloResultadoCotizacion,
                "No se encontró la página Resultado de la cotización"
        );

        String numeroCotizacion = lblNumeroCotizacion.textContent().replace("Nro de cotización:", "").trim();

        log.info("[RIESGOS GENERALES][RC DESH] Número de cotización generado: {}", numeroCotizacion  );
        waitAppReady();
        return numeroCotizacion;

    }

    public void ingresarDatosContratoDeshonestidad(
            String trabajoObra,
            String departamento,
            String provincia,
            String distrito,
            String direccionRiesgo,
            String ramo,
            String ramoDeshonestidad,
            String moneda,
            String numeroTrabajadores,
            String valorContrato,
            String sumaAsegurada,
            String canal
    ) {

        fillAndBlurSync(txtTrabajoObra, trabajoObra);
        selectOptionAndSync(cmbDepartamento, departamento);
        selectOptionAndSync(cmbProvincia, provincia);
        selectOptionAndSync(cmbDistrito, distrito);
        fillAndBlurSync(txtDireccionRiesgo, direccionRiesgo);
        selectOptionAndSync(cmbRamo, ramo);
        waitAppReady();
        selectOptionAndSync(cmbRamoDeshonestidad,ramoDeshonestidad);
        waitAppReady();
        selectOptionAndSync(cmbMoneda, moneda);
        fillAndBlurSync(txtNumeroTrabajadores, numeroTrabajadores);
        fillAndBlurSync(txtValorContrato, valorContrato);
        fillAndBlurSync(txtSumaAseguradaDesh, sumaAsegurada);
        selectOptionAndSync(cmbCanalDesh, canal);
        waitAppReady();

        log.info("[RIESGOS GENERALES][RC DESH][Ramo Deshonestidad] Datos del contrato u obra completados");
    }

}