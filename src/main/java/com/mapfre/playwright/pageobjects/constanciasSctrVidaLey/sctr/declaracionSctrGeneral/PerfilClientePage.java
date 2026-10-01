package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.sctr.declaracionSctrGeneral;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.AssertExceptions;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.models.RegistroFechaVigenciaPolizaConstancias;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.mapfre.utils.waits.FirstAppearanceRace;
import com.mapfre.utils.waits.FirstAppearanceRaceMultiSuccess;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class PerfilClientePage extends BasePage {


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

    public PerfilClientePage(Page page) {
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

    }

    public void assertLoaded() {
        ElementAsserts.assertVisible(title, "TITULO Perfil Cliente DEBE SER VISIBLE");
    }
    public void seleccionarAplicacionConEstadoNoDeclaradaCheckBox() {
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        panelHeaderAplicacionesButton.click();
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        sync();
        if(saludNoDeclarada.isChecked()){
            log.info("El checkbox de salud con estado NO DECLARADA ya está seleccionado");
        }else{
            clickAndSync(saludNoDeclarada);
        }
        log.info("[DECLARACION][PERFIL_CLIENTE] Aplicación con estado NO DECLARADA seleccionado");

    }

    public void seleccionarDeclararAplicacionButton() {
        clickAndSync(panelFooterAplicacionesButton);
        clickAndSync(declararButton);
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][APLICACIONES] Botón declarar seleccionado");
    }

    public void processAndAssertSuccessMultiplesRecibosPendientes(long timeoutMs, long quietMs) {
        Locator[] successLocators = new Locator[] { modalRecibosPendientesAceptarButton, headerDeclaracion };
        RecibosPendientes[] successTypes = {
                RecibosPendientes.MODAL_RECIBOS_PENDIENTES,
                RecibosPendientes.NUEVA_PAGINA_DECLARACION
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
            RecibosPendientes successType = successTypeFromIndex(result.successIndex(), successTypes);
            ejecutarAccionPosteriorRecibosPendientes(successType);
        }
    }

    private RecibosPendientes successTypeFromIndex(int successIndex, RecibosPendientes[] successTypes) {
        if (successIndex < 0 || successIndex >= successTypes.length) {
            throw new IllegalStateException("Índice de success no contemplado: " + successIndex);
        }
        return successTypes[successIndex];
    }

    private void ejecutarAccionPosteriorRecibosPendientes(RecibosPendientes successType) {
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

    public void processAndAssertSuccessRecibosPendientes(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                aceptarModalButton,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][MODAL_RECIBOS] Modal de recibos procesado exitosamente");
    }

    public void clickAceptarRecibosPendientesButton() {
        clickAndSync(aceptarModalButton);
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][MODAL_RECIBOS] Modal de recibos pendientes aceptado");
    }

    public void processAndAssertSuccessDeclaracionButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                headerDeclaracion,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][PERFIL CLIENTE] Pantalla de declaración cargada exitosamente");
    }

    public void activarCheckboxBarraConstaciaManual() {
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        clickAndSync(checkBoxConstanciaManual);
        waitRandomBetween(500);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        clickAndSync(panelFooterAplicacionesButton);
        waitRandomBetween(500);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        log.info("[CONSTANCIA MANUAL][PERFIL CLIENTE] Checkbox para generar constancia manual activado");
    }

    public void processAndAssertSuccessConstanciaManualButton(long timeoutMs, long quietMs) {
        constanciaManualButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var result = FirstAppearanceRace.waitForFirst(
                page,
                constanciaManualButton::click,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                generarNuevaConstanciaModal,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + resultadoModalMensajeError.innerText()+ result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[CONSTANCIA MANUAL][MODAL_GENERAR_CONSTANCIA] Modal Nueva Constancia Manual visible con exito");
    }

    public void seleccionarAplicacionConEstadoNoDeclaradaVidaLeyCheckBox() {
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        panelHeaderAplicacionesButton.click();
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
        sync();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        sync();
        if(vidaLeyNoDeclarada.isChecked()){
            log.info("El checkbox de VIDA LEY con estado NO DECLARADA ya está seleccionado");
        }else{
            clickAndSync(vidaLeyNoDeclarada);
        }
        log.info("[DECLARACION][PERFIL_CLIENTE] Aplicación VIDA LEY con estado NO DECLARADA seleccionado");
    }

    public void validarPolizaVigente() {
        polizasResultList.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Set<String> polizasProcesadas = new HashSet<>();
        Locator visiblePolicies = polizasResultList.filter(new Locator.FilterOptions().setHasText("Póliza Salud"));

        int count = visiblePolicies.count();
        log.info("Cantidad de pólizas visibles encontradas: {}", count);

        for (int i = 0; i < count; i++) {
            Locator policy = visiblePolicies.nth(i);
            String numPolizaSalud = policy.locator("li:has-text('Póliza Salud') .item-dato").innerText().trim();

            // Guard against DOM duplicates
            if (!polizasProcesadas.add(numPolizaSalud)) {
                log.warn("Póliza duplicada ignorada: {}", numPolizaSalud);
                continue;
            }

            String finVigenciaText = policy.locator("li:has-text('Fin Vigencia') .item-dato").innerText().trim();
            RegistroFechaVigenciaPolizaConstancias registro = new RegistroFechaVigenciaPolizaConstancias(numPolizaSalud, finVigenciaText);
            LocalDate finVigenciaDate = DateUtils.parseDate(registro.finVigencia());

            if (finVigenciaDate.isBefore(LocalDate.now())) {
                throw new AssertExceptions("¡Prueba Fallida! La póliza no está vigente. " + "Póliza: " + registro.numeroPoliza() + " | Fin Vigencia: " + registro.finVigencia());
            }

            log.info("[VALIDACION POLIZA VIGENTE][POLIZA: {}] Fin de vigencia {} es posterior a hoy.", registro.numeroPoliza(), registro.finVigencia());
        }
    }

    public void seleccionarAplicacionParaActualizarPlanilla() {
        page.addLocatorHandler(noHayAplicacionesMensaje, locator -> {
            log.info("[PERFIL_CLIENTE] Se detectó el mensaje de No hay pólizas del mes actual");
            throw new FrameworkException("No se muestra la sección de aplicaciones de polizas");
        });
        log.info("[PERFIL_CLIENTE] Buscando una aplicación con estado DECLARADA para actualizar la planilla");
        sync();
        log.info("[PERFIL_CLIENTE] Sincronizando la página antes de buscar aplicaciones");
        waitForNetworkIdle();
        log.info("[PERFIL_CLIENTE] Esperando a que la red esté inactiva antes de buscar aplicaciones");
        waitForDOMContentLoaded();
        log.info("[PERFIL_CLIENTE] Esperando a que el DOM esté completamente cargado antes de buscar aplicaciones");
        panelHeaderAplicacionesButton.click();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        log.info("[PERFIL_CLIENTE] Se encontraron {} aplicaciones en el panel", filasAplicaciones.count());
        int aplicacionesCount = filasAplicaciones.count();
        for (int i = 0; i < aplicacionesCount; i++) {
            Locator aplicacion = filasAplicaciones.nth(i);

            if (tieneEstadoDECLARADA(aplicacion)) {
                Locator checkboxDeclarada = aplicacion.locator("input[type='checkbox']").first();
                ElementAsserts.assertVisible(checkboxDeclarada, "El checkbox de la aplicación con estado DECLARADA debe ser visible");
                clickAndSync(checkboxDeclarada);
                log.info("[PERFIL_CLIENTE] Aplicación con estado DECLARADA seleccionada para actualizacion");
                return;
            }
        }
        throw new FrameworkException("No se encontró ninguna aplicación con estado DECLARADA");
    }

    private boolean tieneEstadoDECLARADA(Locator aplicacion) {
        try {
            Locator estadoLocator = aplicacion.locator("ul").filter(new Locator.FilterOptions().setHasText("DECLARADA")).first();
            return estadoLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void seleccionarBotonPlanillaDelPanelFooter() {
        page.addLocatorHandler(recibosPendienteLabel, locator -> {
            log.info("[ACTUALIZACION][PERFIL_CLIENTE] Se detectó el modal de Recibos Pendientes, click boton Aceptar");
            clickAndSync(modalRecibosPendientesAceptarButton);
        });

        clickAndSync(panelFooterAplicacionesButton);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        clickAndSync(planillaButton);
        sync();
        waitForNetworkIdle();
        waitForDOMContentLoaded();
        //waitRandomBetween(5000);
        log.info("[ACTUALIZACION][PERFIL CLIENTE] Se seleccionó el boton Planilla de una aplicacion");
    }

    public void processAndAssertSuccessPlanillaButton(long timeoutMs, long quietMs) {
        var result = FirstAppearanceRace.waitForFirst(
                page,
                null,               // action (coloca null si la accion click fue realizada)
                resultadoModalMensajeError,
                titleConstancias,
                null,       // coloca null si el mensaje del error esta en resultadoNoEncontradoMensaje
                timeoutMs,
                quietMs,                 // quietMs (stability)
                150                      // pollMs
        );

        if (result.outcome() == FirstAppearanceRace.Outcome.ERROR) {
            ElementAsserts.assertUIMessage("Proceso falló. UI error: " + result.errorMessage()
                    + " (después de " + result.elapsedMs() + " ms)");
        }

        if (result.outcome() == FirstAppearanceRace.Outcome.TIMEOUT) {
            ElementAsserts.assertUIMessage("Error de timeout ni SUCCESS or ERROR encontrado, Apareció dentro de " + timeoutMs
                    + " ms (después de" + result.elapsedMs() + " ms).");
        }
        log.info("[ACTUALIZACION][PERFIL CLIENTE] Se muestra la pagina Constancias. Duracion {}", result.elapsedMs());
    }

    public void seleccionarAplicacionConEstadoNoDeclaradoParaInclusionDeMesVencido() {
        page.addLocatorHandler(noHayAplicacionesMensaje, locator -> {
            log.info("[PERFIL_CLIENTE] Se detectó el mensaje de No hay pólizas del mes actual");
            throw new FrameworkException("No se muestra la sección de aplicaciones de polizas");
        });
        log.info("[PERFIL_CLIENTE] Buscando una aplicación con estado NO DECLARADA para realizar la Inclusion de mes vencido");
        sync();
        log.info("[PERFIL_CLIENTE] Sincronizando la página antes de buscar aplicaciones");
        waitForNetworkIdle();
        log.info("[PERFIL_CLIENTE] Esperando a que la red esté inactiva antes de buscar aplicaciones");
        waitForDOMContentLoaded();
        log.info("[PERFIL_CLIENTE] Esperando a que el DOM esté completamente cargado antes de buscar aplicaciones");
        panelHeaderAplicacionesButton.click();
        filasAplicaciones.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        log.info("[PERFIL_CLIENTE] Se encontraron {} aplicaciones en el panel", filasAplicaciones.count());
        int aplicacionesCount = filasAplicaciones.count();
        for (int i = 0; i < aplicacionesCount; i++) {
            Locator aplicacion = filasAplicaciones.nth(i);

            if (tieneEstadoNODECLARADA(aplicacion)) {
                Locator checkboxNoDeclarada = aplicacion.locator("input[type='checkbox']").first();
                ElementAsserts.assertVisible(checkboxNoDeclarada, "El checkbox de la aplicación con estado NO DECLARADA debe ser visible");
                clickAndSync(checkboxNoDeclarada);
                scrollToBottom();
                waitRandomBetween(1000);
                log.info("[PERFIL_CLIENTE] Aplicación con estado NO DECLARADA seleccionada para realizar la inclusion");
                return;
            }
        }
        throw new FrameworkException("No se encontró ninguna aplicación con estado NO DECLARADA");
    }

    private boolean tieneEstadoNODECLARADA(Locator aplicacion) {
        try {
            //Locator estadoLocator = aplicacion.locator("ul").filter(new Locator.FilterOptions().setHasText("NO DECLARADA")).first();
            Locator estadoLocator = aplicacion.locator("ul").getByText("NO DECLARADA", new Locator.GetByTextOptions().setExact(true)).first();
            return estadoLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean tieneEstadoDECLARADAv2(Locator aplicacion) {
        try {
            //Locator estadoLocator = aplicacion.locator("ul").filter(new Locator.FilterOptions().setHasText("NO DECLARADA")).first();
            Locator estadoLocator = aplicacion.locator("ul").getByText("DECLARADA", new Locator.GetByTextOptions().setExact(true)).first();
            return estadoLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void buscarAplicacionConEstadoNoDeclaradoParaInclusionDeMesVencido() {
        log.info("[PERFIL_CLIENTE] Buscando una aplicación con estado NO DECLARADA para inclusión de mes vencido");
        sync();
        log.info("[PERFIL_CLIENTE] Sincronizando la página antes de buscar aplicaciones");
        waitForNetworkIdle();
        log.info("[PERFIL_CLIENTE] Esperando a que la red esté inactiva antes de buscar aplicaciones");
        waitForDOMContentLoaded();
        log.info("[PERFIL_CLIENTE] Esperando a que el DOM esté completamente cargado antes de buscar aplicaciones");

        panelHeaderAplicacionesButton.click();
        sync();
        waitRandomBetween(1000);
        waitForDOMContentLoaded();
        waitForNetworkIdle();
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

            Locator vigenciaLine = card.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')");
            String textoVigencia = vigenciaLine.innerText().trim();

            LocalDate[] rango = DateUtils.obtenerRangoFechasDesdeVigencia(textoVigencia);
            LocalDate fechaDesde = rango[0];
            LocalDate fechaHasta = rango[1];

            log.info("[PERFIL_CLIENTE] Card {} con estado NO DECLARADA. Vigencia detectada: {} | Desde: {} | Hasta: {}",
                    i, textoVigencia, fechaDesde, fechaHasta);

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
            throw new FrameworkException("No se encontró ninguna aplicación NO DECLARADA vigente para hoy ni futura");
        }

        Locator checkboxNoDeclarada = cardSeleccionada.locator("input[type='checkbox']").first();
        ElementAsserts.assertVisible(checkboxNoDeclarada, "El checkbox de la aplicación seleccionada debe ser visible");

        String vigenciaSeleccionada = cardSeleccionada
                .locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')")
                .innerText()
                .trim();

        log.info("[PERFIL_CLIENTE] Aplicación seleccionada con estado NO DECLARADA y vigencia: {}", vigenciaSeleccionada);

        // Si corresponde en tu flujo:
        checkboxNoDeclarada.check();
        //clickAndSync(checkboxNoDeclarada);
    }
}
