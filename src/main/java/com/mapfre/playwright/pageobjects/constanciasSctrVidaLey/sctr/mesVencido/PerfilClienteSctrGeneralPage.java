package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRaceMultiSuccess;
import com.mapfre.utils.waits.GestorModal;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PerfilClienteSctrGeneralPage extends BasePage {
    private enum RecibosPendientes { MODAL_RECIBOS_PENDIENTES, NUEVA_PAGINA_DECLARACION }
    private final Locator title;
    private final Locator filasAplicaciones;
    private final Locator saludNoDeclarada;
    private final Locator panelHeaderAplicacionesButton;
    private final Locator panelFooterAplicacionesButton;
    private final Locator declararButton;
    private final Locator aceptarModalButton;
    private final Locator headerDeclaracion;
    private final Locator resultadoModalMensajeError;
    private final Locator checkBoxConstanciaManual;
    private final Locator constanciaManualButton;
    private final Locator generarNuevaConstanciaModal;
    private final Locator vidaLeyNoDeclarada;
    private final Locator polizasResultList;
    private final Locator modalRecibosPendientes;
    private final Locator modalRecibosPendientesAceptarButton;
    private final Locator declaracionTittle;
    private final Locator noHayAplicacionesMensaje;
    private final Locator recibosPendienteLabel;
    private final Locator planillaButton;
    private final Locator titleConstancias;
    private final Locator recibosPendientesModal;
    private final Locator pnlAplicacionesSeleccionadas;
    private final Locator lblPolizaPensionSeleccionada;
    private final Locator btnExpandirAplicaciones;
    private final Locator btnIncluir;
    private final Locator modalAlertaInclusion;

    private final Locator modalAlertaDeclaracion;
    private final Locator btnAceptarModalDeclaracion;



    public PerfilClienteSctrGeneralPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Perfil Cliente"));
        this.filasAplicaciones = page.locator("div.col-md-12.g-list-sm");
        this.saludNoDeclarada = page.locator("ul:has-text('SALUD'):has-text('NO DECLARADA')").locator("li:has-text('SALUD')").locator("input[type='checkbox']").first();
        this.vidaLeyNoDeclarada = page.locator("ul:has-text('VIDA LEY'):has-text('NO DECLARADA')").locator("li:has-text('VIDA LEY')").locator("input[type='checkbox']").first();
        this.panelHeaderAplicacionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Colectivo Asegurado", Pattern.CASE_INSENSITIVE)));
        this.panelFooterAplicacionesButton = page.locator("div.gnSecResultsFixed.gBgcGray5.show");
        this.declararButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Declarar"));
        this.aceptarModalButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.headerDeclaracion = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración"));
        this.resultadoModalMensajeError = page.locator("div.swal2-popup.swal2-icon-error[role='dialog']");
        this.checkBoxConstanciaManual = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Colectivo Asegurado:", Pattern.CASE_INSENSITIVE))).getByLabel("");
        this.constanciaManualButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Constancia manual"));
        this.generarNuevaConstanciaModal = page.locator("mat-dialog-container:has-text('Generar Nueva Constancia Manual')");
        this.polizasResultList = page.locator("mat-expansion-panel");
        this.modalRecibosPendientes = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Recibos pendientes"));
        this.modalRecibosPendientesAceptarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.declaracionTittle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración"));
        this.noHayAplicacionesMensaje = page.getByText("No hay pólizas del mes actual");
        this.recibosPendienteLabel = page.locator("mat-dialog-container h1").filter(new Locator.FilterOptions().setHasText("Recibos pendientes"));
        this.planillaButton = panelFooterAplicacionesButton.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Planilla"));
        this.titleConstancias = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Constancias"));
        this.recibosPendientesModal = page.locator("mat-dialog-container h1").filter(new Locator.FilterOptions().setHasText("Recibos pendientes"));
        this.pnlAplicacionesSeleccionadas = page.locator("span.mat-content").filter(new Locator.FilterOptions().setHasText("Póliza Pensión:"));
        this.lblPolizaPensionSeleccionada = page.getByText("Póliza Pensión:");
        this.btnExpandirAplicaciones = page.locator("span.mat-expansion-indicator").last();
        this.btnIncluir = panelFooterAplicacionesButton.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Incluir"));
        this.modalAlertaInclusion = page.locator("div.swal2-popup").filter(new Locator.FilterOptions().setHasText("Solo ha seleccionado una póliza"));

        this.modalAlertaDeclaracion = page.locator("div.swal2-popup").filter(new Locator.FilterOptions().setHasText("Solo ha seleccionado una póliza"));
        this.btnAceptarModalDeclaracion = page.locator("button.swal2-confirm:has-text('Aceptar')");

    }

    public void assertLoadedSctrGeneral() {
        ElementAsserts.assertVisible(title, "TITULO Perfil Cliente DEBE SER VISIBLE");
    }

    public void abrirPanelDeAplicacionesSctrGeneral() {
        sync();
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        log.info("[PERFIL_CLIENTE] Abriendo panel de aplicaciones");
        ElementAsserts.assertVisible(panelHeaderAplicacionesButton, "Se esperaba que el panel de aplicaciones esté visible");
        panelHeaderAplicacionesButton.click();
        sync();
        waitRandomBetween(1000);
        waitForNetworkIdle();
    }

    public void seleccionarAplicacionConEstadoNoDeclaradaParaIncluirMesVencidoSctrGeneral() {
        log.info("[PERFIL_CLIENTE] Seleccionando aplicación con estado NO DECLARADA para incluir mes vencido");
        Locator cards = page.locator("div:has(> div.row.g-row h2:has-text('Vigencia'))");
        ElementAsserts.assertVisible(cards.first(), "Se esperaba que al menos una aplicación sea visible");
        int cantidadCards = cards.count();
        log.info("[PERFIL_CLIENTE] Se encontraron {} cards en el panel", cantidadCards);

        LocalDate fechaHoy = LocalDate.now();

        Locator cardVigente = null;
        Locator cardFuturaMasCercana = null;
        LocalDate fechaDesdeFuturaMasCercana = null;

        for (int i = 0; i < cantidadCards; i++) {
            Locator card = cards.nth(i);

            if (!tieneEstadoNODECLARADA(card)) {
                continue;
            }

            //Locator vigenciaLine = card.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')");
            Locator vigenciaLine = card.locator("div.col-md-auto:has-text('Desde:'):has-text('Hasta:')");

            String textoVigencia = vigenciaLine.innerText().trim();

            LocalDate[] rango = DateUtils.obtenerRangoFechasDesdeVigencia(textoVigencia);
            LocalDate fechaDesde = rango[0];
            LocalDate fechaHasta = rango[1];

            log.info("[PERFIL_CLIENTE] Card {} con estado NO DECLARADA. Vigencia detectada: {} | Desde: {} | Hasta: {}", i, textoVigencia, fechaDesde, fechaHasta);

            // Regla 1: vigente hoy (desde <= hoy <= hasta)
            boolean vigenteHoy = !fechaHoy.isBefore(fechaDesde) && !fechaHoy.isAfter(fechaHasta);
            if (vigenteHoy) {
                cardVigente = card;
                log.info("[PERFIL_CLIENTE] Se encontró card vigente para hoy ({}) en índice {}", fechaHoy, i);
                break;
            }

            // Regla 2 (fallback): futura más cercana (desde > hoy)
            if (fechaDesde.isAfter(fechaHoy)) {
                if (cardFuturaMasCercana == null || fechaDesde.isBefore(fechaDesdeFuturaMasCercana)) {
                    cardFuturaMasCercana = card;
                    fechaDesdeFuturaMasCercana = fechaDesde;
                    log.info("[PERFIL_CLIENTE] Se actualiza card futura más cercana. Índice {} con desde {}", i, fechaDesde);
                }
            }
        }

        Locator cardSeleccionada = cardVigente != null ? cardVigente : cardFuturaMasCercana;

        if (cardSeleccionada == null) {
            throw new FrameworkException("No se encontró ninguna aplicación NO DECLARADA vigente elegible para hoy ni futura. La inclusion solo puede realizarse en aplicaciones desde el mes actual hasta los siguientes meses.");
        }

        Locator checkboxNoDeclarada = cardSeleccionada.locator("input[type='checkbox']").first();
        ElementAsserts.assertVisible(checkboxNoDeclarada, "El checkbox de la aplicación seleccionada debe ser visible");

        //String vigenciaSeleccionada = cardSeleccionada.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')").innerText().trim();
        String vigenciaSeleccionada = cardSeleccionada.locator("div.col-md-auto:has-text('Desde:'):has-text('Hasta:')").innerText().trim();


        log.info("[PERFIL_CLIENTE] Aplicación seleccionada con estado NO DECLARADA y vigencia: {}", vigenciaSeleccionada);

        checkboxNoDeclarada.check();
        boolean seleccionado = checkboxNoDeclarada.isChecked();

        log.info("[PERFIL_CLIENTE] Estado del checkbox luego de seleccionar: {}",
                seleccionado ? "CHECKED" : "UNCHECKED");

        if (!seleccionado) {
            throw new FrameworkException(
                    "No se pudo seleccionar el checkbox de la aplicación con vigencia: "
                            + vigenciaSeleccionada);
        }

    }
    private boolean tieneEstadoNODECLARADA(Locator aplicacion) {
        try {
            Locator estadoLocator = aplicacion.locator("ul").getByText("NO DECLARADA", new Locator.GetByTextOptions().setExact(true)).first();
            return estadoLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void seleccionarAplicacionConEstadoNoDeclaradaParaDeclararMesVencidoSctrGeneral() {
        log.info("[PERFIL_CLIENTE] Seleccionando aplicación con estado NO DECLARADA para declarar mes vencido");
        Locator cards = page.locator("div:has(> div.row.g-row h2:has-text('Vigencia'))");
        ElementAsserts.assertVisible(cards.first(), "Se esperaba que al menos una aplicación sea visible");
        int cantidadCards = cards.count();

        log.info("[PERFIL_CLIENTE] Se encontraron {} cards en el panel", cantidadCards);


        LocalDate fechaHoy = LocalDate.now();
        boolean seEncontroNoDeclarada = false;

        for (int i = 0; i < cantidadCards; i++) {
            Locator aplicacion = cards.nth(i);

            if (!tieneEstadoNODECLARADA(aplicacion)) {
                continue;
            }

            seEncontroNoDeclarada = true;

            //Locator vigenciaLine = aplicacion.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')");
            Locator vigenciaLine = aplicacion.locator("div.col-md-auto:has-text('Desde:'):has-text('Hasta:')");

            String textoVigencia = vigenciaLine.innerText().trim();

            LocalDate[] rango = DateUtils.obtenerRangoFechasDesdeVigencia(textoVigencia);
            LocalDate fechaDesde = rango[0];
            LocalDate fechaHasta = rango[1];

            YearMonth mesAplicacion = YearMonth.from(fechaDesde);
            YearMonth mesActual = YearMonth.from(fechaHoy);

            boolean habilitadaDeclaracion;
            if (mesAplicacion.isBefore(mesActual)) {
                habilitadaDeclaracion = true;
            } else if (mesAplicacion.isAfter(mesActual)) {
                habilitadaDeclaracion = false;
            } else {
                LocalDate inicioVentana = fechaHasta.minusDays(15);
                habilitadaDeclaracion = !fechaHoy.isBefore(inicioVentana) && !fechaHoy.isAfter(fechaHasta);
            }

            if (habilitadaDeclaracion) {
                Locator checkboxNoDeclarada = aplicacion.locator("input[type='checkbox']").first();
                ElementAsserts.assertVisible(checkboxNoDeclarada, "El checkbox de la aplicación con estado NO DECLARADA debe ser visible");
                checkboxNoDeclarada.check();
                log.info("[PERFIL_CLIENTE] Aplicación seleccionada para Declaración con vigencia: {}", textoVigencia);
                return;
            }
        }

        if (!seEncontroNoDeclarada) {
            throw new FrameworkException("No se encontró ninguna aplicación con estado NO DECLARADA");
        }

        throw new FrameworkException("No se encontró ninguna aplicación NO DECLARADA vigente elegible para declarar mes vencido. La declaración solo puede realizarse en aplicaciones de meses anteriores al actual o en el mes actual dentro de los últimos 15 días de vigencia.");
    }

    public void seleccionarPrimeraAplicacionConEstadoDeclaradaConReciboParaConstanciaReemplazoSctrGeneral() {
        // Get all application cards with "Vigencia" label
        Locator cards = page.locator("div:has(> div.row.g-row h2:has-text('Vigencia'))");
        ElementAsserts.assertVisible(cards.first(), "Se esperaba que al menos una aplicación sea visible");
        int cantidadCards = cards.count();
        log.info("[PERFIL_CLIENTE] Se encontraron {} cards en el panel", cantidadCards);

        // Step 1: Verify at least one "DECLARADA" label exists
        List<Locator> cardsConDeclarada = new ArrayList<>();

        for (int i = 0; i < cantidadCards; i++) {
            Locator card = cards.nth(i);
            if (!tieneEstadoNODECLARADA(card)) {
                cardsConDeclarada.add(card);
                log.info("[PERFIL_CLIENTE] Card {} contiene estado DECLARADA", i);
            }
        }

        // Fail if no "DECLARADA" label found
        if (cardsConDeclarada.isEmpty()) {
            throw new FrameworkException("No se encontró ninguna aplicación con estado DECLARADA en la lista. Se requiere al menos una aplicación DECLARADA para completar la acción.");
        }

        log.info("[PERFIL_CLIENTE] Se encontraron {} card(s) con estado DECLARADA", cardsConDeclarada.size());

        // Step 2: Select the appropriate card
        Locator cardSeleccionada;

        if (cardsConDeclarada.size() > 1) {
            // If multiple "DECLARADA" labels, select the last one
            cardSeleccionada = cardsConDeclarada.get(cardsConDeclarada.size() - 1);
            log.info("[PERFIL_CLIENTE] Se selecciona la última card DECLARADA (posición {})", cardsConDeclarada.size() - 1);
        } else {
            // If only one "DECLARADA" label, select it
            cardSeleccionada = cardsConDeclarada.get(0);
            log.info("[PERFIL_CLIENTE] Se selecciona la única card DECLARADA encontrada");
        }

        // Step 3: Check the checkbox
        Locator checkboxDeclarada = cardSeleccionada.locator("input[type='checkbox']").first();
        ElementAsserts.assertVisible(checkboxDeclarada, "El checkbox de la aplicación seleccionada con estado DECLARADA debe ser visible");

        String vigenciaSeleccionada = cardSeleccionada.locator("div.col-md-auto:has-text('Desde:'):has-text('Hasta:')").innerText().trim();
        log.info("[PERFIL_CLIENTE] Aplicación seleccionada con estado DECLARADA y vigencia: {}", vigenciaSeleccionada);

        checkboxDeclarada.check();
        log.info("[PERFIL_CLIENTE] Checkbox de aplicación DECLARADA marcado exitosamente");
    }



    public void seleccionarDeclararAplicacionButtonSctrGeneral() {
        page.addLocatorHandler(recibosPendientesModal, locator -> {
            log.info("Se muestra el popup de Recibos Pendientes,");
            if(!modalRecibosPendientesAceptarButton.isVisible()){
                throw new FrameworkException("Se esperaba que el botón Aceptar del popup de Recibos Pendientes sea visible");
            }
            clickAndSync(modalRecibosPendientesAceptarButton);
            log.info("Se hizo click en el botón Aceptar del popup de Recibos Pendientes");
        });

        clickAndSync(panelFooterAplicacionesButton);
        clickAndSync(declararButton);
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][APLICACIONES] Botón declarar seleccionado");
    }

    public void assertLoadedSctrGeneralv2() {
        ElementAsserts.assertVisible(declaracionTittle, "TITULO Declaración DEBE SER VISIBLE");
    }

    public void processAndAssertSuccessMultiplesRecibosPendientesSctrGeneral(long timeoutMs, long quietMs) {
        Locator[] successLocators = new Locator[] { modalRecibosPendientesAceptarButton, headerDeclaracion };
        PerfilClienteSctrGeneralPage.RecibosPendientes[] successTypes = {
                PerfilClienteSctrGeneralPage.RecibosPendientes.MODAL_RECIBOS_PENDIENTES,
                PerfilClienteSctrGeneralPage.RecibosPendientes.NUEVA_PAGINA_DECLARACION
        };
        var result = FirstAppearanceRaceMultiSuccess.waitForFirst(
                page,
                null,
                resultadoModalMensajeError,
                successLocators,
                null,
                timeoutMs,
                quietMs,
                150
        );

        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }
        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout: no apareció ERROR ni ninguna alternativa SUCCESS dentro de "
                    + timeoutMs + " ms (después de " + result.elapsedMs() + " ms).");
        }

        if (result.outcome() == FirstAppearanceRaceMultiSuccess.Outcome.SUCCESS) {
            PerfilClienteSctrGeneralPage.RecibosPendientes successType = successTypeFromIndex(result.successIndex(), successTypes);
            ejecutarAccionPosteriorRecibosPendientes(successType);
        }
    }

    private PerfilClienteSctrGeneralPage.RecibosPendientes successTypeFromIndex(int successIndex, PerfilClienteSctrGeneralPage.RecibosPendientes[] successTypes) {
        if (successIndex < 0 || successIndex >= successTypes.length) {
            throw new IllegalStateException("Índice de success no contemplado: " + successIndex);
        }
        return successTypes[successIndex];
    }

    private void ejecutarAccionPosteriorRecibosPendientes(PerfilClienteSctrGeneralPage.RecibosPendientes successType) {
        switch (successType) {
            case MODAL_RECIBOS_PENDIENTES -> {
                log.info("[DECLARACION][PERFIL_CLIENTE] Se muestra el modal Recibos Pendientes");
                clickAndSync(modalRecibosPendientesAceptarButton);
                log.info("[DECLARACION][PERFIL_CLIENTE] Se hizo click en el boton Aceptar del modal Recibos Pendientes");
            }
            case NUEVA_PAGINA_DECLARACION -> {
                log.info("[DECLARACION][CARGA DE ASEGURADO] La página DECLARACIÓN es visible, no se mostró el modal de recibos pendientes");
                ElementAsserts.assertVisible(
                        declaracionTittle,
                        "[DECLARACION][CARGA DE ASEGURADO] Se esperaba que la página DECLARACIÓN sea visible"
                );
                log.info("DECLARACION][CARGA DE ASEGURADO] La página DECLARACIÓN cargó exitosamente");
            }
        }
    }
    public void validarAplicacionSeleccionada() {

        log.info("[PERFIL_CLIENTE] Expandiendo panel de aplicaciones seleccionadas.");

        btnExpandirAplicaciones.click();
        waitAppReady();
        log.info("[PERFIL_CLIENTE] Validando que se muestre la sección APLICACIONES SELECCIONADAS.");
        ElementAsserts.assertVisible(pnlAplicacionesSeleccionadas,"La sección APLICACIONES SELECCIONADAS debe mostrarse.");
        log.info("[PERFIL_CLIENTE] La aplicación seleccionada aparece correctamente en el panel.");
    }

    public void clickIncluirAplicacionSeleccionada() {

        log.info("[PERFIL_CLIENTE] Haciendo clic en el botón Incluir.");

        ElementAsserts.assertVisible( btnIncluir,"El botón Incluir debe ser visible.");
        clickAndSync(btnIncluir);
        log.info("[PERFIL_CLIENTE] Se hizo clic en el botón Incluir.");
    }
    public void aceptarModalAlertaInclusion() {

        log.info("[PERFIL_CLIENTE] Validando si se muestra la alerta de inclusión.");

        boolean modalProcesado = GestorModal.clickBotonSiExiste(
                modalAlertaInclusion,
                "Aceptar");

        if (modalProcesado) {
            log.info("[PERFIL_CLIENTE] Se mostró la alerta de inclusión y se hizo clic en Aceptar.");
        } else {
            log.info("[PERFIL_CLIENTE] No se mostró la alerta de inclusión.");
        }

        waitAppReady();
    }
    public void aceptarModalAlertaDeclaracionSiExiste() {

        log.info("[DECLARACION][PERFIL_CLIENTE] Validando si se muestra la alerta de una sola póliza.");

        if (modalAlertaDeclaracion.isVisible()) {

            log.info("[DECLARACION][PERFIL_CLIENTE] Se detectó la alerta 'Solo ha seleccionado una póliza'.");

            ElementAsserts.assertVisible(btnAceptarModalDeclaracion,"El botón Aceptar de la alerta debe ser visible");

            log.info("[DECLARACION][PERFIL_CLIENTE] Haciendo clic en Aceptar para continuar con la declaración.");
            //page.screenshot( new Page.ScreenshotOptions().setPath(Paths.get("modal_una_poliza.png")));
            clickAndSync(btnAceptarModalDeclaracion);
            log.info("[DECLARACION][PERFIL_CLIENTE] Finalizó el procesamiento posterior a la alerta de una sola póliza.");

        } else {

            log.info("[DECLARACION][PERFIL_CLIENTE] No se mostró la alerta de una sola póliza.");
        }
    }

    public void seleccionarAplicacionParaDeclararSctrGeneralMesVencido(String numeroAplicacionEsperada) {

        log.info("[PERFIL_CLIENTE] Buscando la aplicación {} para declarar mes vencido.", numeroAplicacionEsperada);

        Locator cards = page.locator("div:has(> div.row.g-row h2:has-text('Vigencia'))");
        ElementAsserts.assertVisible( cards.first(),"Se esperaba que al menos una aplicación sea visible");

        int cantidadCards = cards.count();
        LocalDate fechaHoy = LocalDate.now();

        for (int i = 0; i < cantidadCards; i++) {

            Locator aplicacion = cards.nth(i);

            String textoCard = aplicacion.innerText();

            if (!textoCard.contains("Aplicación:" + numeroAplicacionEsperada)
                    && !textoCard.contains("Aplicación: " + numeroAplicacionEsperada)) {
                continue;
            }

            log.info("[PERFIL_CLIENTE] Se encontró la aplicación {}.",
                    numeroAplicacionEsperada);

            if (!tieneEstadoNODECLARADA(aplicacion)) {
                throw new FrameworkException(String.format("La aplicación %s no tiene estado NO DECLARADA.", numeroAplicacionEsperada));
            }

            Locator vigenciaLine = aplicacion.locator("div.col-md-auto:has-text('Desde:'):has-text('Hasta:')");

            String textoVigencia = vigenciaLine.innerText().trim();

            LocalDate[] rango =  DateUtils.obtenerRangoFechasDesdeVigencia(textoVigencia);

            LocalDate fechaDesde = rango[0];
            LocalDate fechaHasta = rango[1];

            YearMonth mesAplicacion = YearMonth.from(fechaDesde);
            YearMonth mesActual = YearMonth.from(fechaHoy);

            boolean habilitadaDeclaracion;

            if (mesAplicacion.isBefore(mesActual)) {
                habilitadaDeclaracion = true;
            } else if (mesAplicacion.isAfter(mesActual)) {
                habilitadaDeclaracion = false;
            } else {
                LocalDate inicioVentana = fechaHasta.minusDays(15);

                habilitadaDeclaracion = !fechaHoy.isBefore(inicioVentana) && !fechaHoy.isAfter(fechaHasta);
            }

            if (!habilitadaDeclaracion) {
                throw new FrameworkException(
                        String.format(
                                "La aplicación %s no cumple la regla de negocio para declaración mes vencido.",
                                numeroAplicacionEsperada));
            }

            Locator chkAplicacion =
                    aplicacion.locator("input[type='checkbox']").first();

            ElementAsserts.assertVisible(
                    chkAplicacion,
                    "El checkbox de la aplicación debe ser visible");

            chkAplicacion.check();

            log.info( "[PERFIL_CLIENTE] Se seleccionó la aplicación {} con vigencia {}.",
                    numeroAplicacionEsperada,
                    textoVigencia);

            return;
        }

        throw new FrameworkException(
                String.format(
                        "No se encontró la aplicación %s en la pantalla.",
                        numeroAplicacionEsperada));
    }
    public void seleccionarPlanillaAplicacionButtonSctrGeneral() {
        page.addLocatorHandler(recibosPendientesModal, locator -> {
            log.info("Se muestra el popup de Recibos Pendientes,");
            if(!modalRecibosPendientesAceptarButton.isVisible()){
                throw new FrameworkException("Se esperaba que el botón Aceptar del popup de Recibos Pendientes sea visible");
            }
            clickAndSync(modalRecibosPendientesAceptarButton);
            log.info("Se hizo click en el botón Aceptar del popup de Recibos Pendientes");
        });
        clickAndSync(panelFooterAplicacionesButton);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        clickAndSync(planillaButton);
        log.info("[REEMPLAZO][PERFIL_CLIENTE] Botón Planilla seleccionado");
    }
}
