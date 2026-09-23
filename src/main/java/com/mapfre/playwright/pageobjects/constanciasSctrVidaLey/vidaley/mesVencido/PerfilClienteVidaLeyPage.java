package com.mapfre.playwright.pageobjects.constanciasSctrVidaLey.vidaley.mesVencido;

import com.mapfre.asserts.ElementAsserts;
import com.mapfre.exceptions.FrameworkException;
import com.mapfre.playwright.pageobjects.BasePage;
import com.mapfre.utils.DateUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PerfilClienteVidaLeyPage extends BasePage {
    private final Locator title;
    private final Locator filasAplicaciones;
    private final Locator panelHeaderAplicacionesButton;
    private final Locator declararButton;
    private final Locator aceptarModalButton;
    private final Locator panelFooterAplicacionesButton;
    private final Locator recibosPendientesModal;
    private final Locator declaracionTittle;
    private final Locator modalRecibosPendientesAceptarButton;
    private final Locator planillaButton;
    private final Locator tipoDeclaracionMesVencido;
    private final Locator incluirButton;
    private final Locator declararButton2;
    public PerfilClienteVidaLeyPage(Page page) {
        super(page);
        this.title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Perfil Cliente"));
        this.filasAplicaciones = page.locator("div.col-md-12.g-list-sm");
        this.panelHeaderAplicacionesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Colectivo Asegurado", Pattern.CASE_INSENSITIVE)));
        this.declararButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Declarar"));
        this.aceptarModalButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.panelFooterAplicacionesButton = page.locator("div.gnSecResultsFixed.gBgcGray5.show");
        this.recibosPendientesModal = page.locator("mat-dialog-container h1").filter(new Locator.FilterOptions().setHasText("Recibos pendientes"));
        this.declaracionTittle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Declaración"));
        this.modalRecibosPendientesAceptarButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Aceptar"));
        this.planillaButton = panelFooterAplicacionesButton.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Planilla"));
        this.tipoDeclaracionMesVencido = page.getByText(Pattern.compile("mes vencido", Pattern.CASE_INSENSITIVE));
        this.incluirButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("^Incluir$")));
        this.declararButton2 = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("^Declarar$")));

    }

    public void assertLoadedVidaLey() {
        ElementAsserts.assertVisible(title, "TITULO Perfil Cliente DEBE SER VISIBLE");
    }

    public void abrirPanelDeAplicacionesVidaLey() {
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

    public void seleccionarAplicacionConEstadoNoDeclaradaParaIncluirMesVencidoVidaLey() {
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

            Locator vigenciaLine = card.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')");

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

        String vigenciaSeleccionada = cardSeleccionada.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')").innerText().trim();


        log.info("[PERFIL_CLIENTE] Aplicación seleccionada con estado NO DECLARADA y vigencia: {}", vigenciaSeleccionada);

        checkboxNoDeclarada.check();
        waitRandomBetween(2000);
    }
    private boolean tieneEstadoNODECLARADA(Locator aplicacion) {
        try {
            Locator estadoLocator = aplicacion.locator("ul").getByText("NO DECLARADA", new Locator.GetByTextOptions().setExact(true)).first();
            return estadoLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void seleccionarAplicacionConEstadoNoDeclaradaParaDeclararMesVencidoVidaLey() {
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

            Locator vigenciaLine = aplicacion.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')");

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

    public void seleccionarDeclararAplicacionButtonVidaLey() {
        page.addLocatorHandler(recibosPendientesModal, locator -> {
            log.info("Se muestra el popup de Recibos Pendientes,");
            if(!aceptarModalButton.isVisible()){
                throw new FrameworkException("Se esperaba que el botón Aceptar del popup de Recibos Pendientes sea visible");
            }
            clickAndSync(aceptarModalButton);
            log.info("Se hizo click en el botón Aceptar del popup de Recibos Pendientes");
        });

        clickAndSync(panelFooterAplicacionesButton);
        clickAndSync(declararButton);
        log.info("[DECLARACION - INCLUSION - CONSTANCIA MANUAL][APLICACIONES] Botón declarar seleccionado");
    }

    public void assertLoadedVidaLeyv2() {
        ElementAsserts.assertVisible(declaracionTittle, "TITULO Declaración DEBE SER VISIBLE");

    }

    public void seleccionarPrimeraAplicacionConEstadoDeclaradaConReciboParaConstanciaReemplazoVidaLey() {
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

        String vigenciaSeleccionada = cardSeleccionada.locator("div.g-col-sm.bottom:has-text('Desde:'):has-text('Hasta:')").innerText().trim();
        log.info("[PERFIL_CLIENTE] Aplicación seleccionada con estado DECLARADA y vigencia: {}", vigenciaSeleccionada);

        checkboxDeclarada.check();
        log.info("[PERFIL_CLIENTE] Checkbox de aplicación DECLARADA marcado exitosamente");
    }

    public void seleccionarPlanillaAplicacionButtonVidaLey() {
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
    public void assertTipoDeclaracionMesVencido() {
        ElementAsserts.assertVisible(
                tipoDeclaracionMesVencido,
                "Se esperaba visualizar el tipo de declaración Mes vencido"
        );
    }
    public void seleccionarBotonIncluir() {

        log.info("[PERFIL_CLIENTE] Seleccionando botón Incluir");

        ElementAsserts.assertVisible(
                panelFooterAplicacionesButton,
                "El panel de aplicaciones seleccionadas debe ser visible");

        clickAndSync(panelFooterAplicacionesButton);

        waitForNetworkIdle();
        sync();

        ElementAsserts.assertVisible(
                incluirButton,
                "El botón Incluir debe ser visible");

        clickAndSync(incluirButton);

        log.info("[PERFIL_CLIENTE] Botón Incluir seleccionado");
    }
    public void seleccionarBotonDeclarar() {

        log.info("[PERFIL_CLIENTE] Seleccionando botón Declarar");

        try {

            if (panelFooterAplicacionesButton.isVisible()) {

                log.info("[PERFIL_CLIENTE] Panel de aplicaciones visible");

                panelFooterAplicacionesButton.scrollIntoViewIfNeeded();

                clickAndSync(panelFooterAplicacionesButton);

                waitForNetworkIdle();
                sync();
            }

        } catch (Exception e) {

            log.info("[PERFIL_CLIENTE] Panel de aplicaciones no visible. Continuando flujo");
        }

        try {

            if (declararButton.isVisible()) {

                log.info("[PERFIL_CLIENTE] Botón Declarar visible");

                declararButton.scrollIntoViewIfNeeded();

                clickAndSync(declararButton);

                log.info("[PERFIL_CLIENTE] Botón Declarar seleccionado");

                return;
            }

        } catch (Exception e) {

            log.info("[PERFIL_CLIENTE] Botón Declarar no disponible");
        }

        log.info("[PERFIL_CLIENTE] Validando si ya se encuentra en la pantalla Declaración");

        if (declaracionTittle.isVisible()) {

            log.info("[PERFIL_CLIENTE] Ya se encuentra en la pantalla Declaración");

            return;
        }

        throw new AssertionError(
                "No se encontró el botón Declarar ni la pantalla Declaración");
    }
}
