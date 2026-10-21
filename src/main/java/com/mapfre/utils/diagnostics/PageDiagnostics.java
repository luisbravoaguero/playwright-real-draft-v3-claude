package com.mapfre.utils.diagnostics;

import com.mapfre.config.ConfigManager;
import com.microsoft.playwright.Page;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Foto del estado de una pestaña cuando algo no aparece a tiempo. Clasifica el problema en una fase
 * (pantalla en blanco, spinner atascado, contenido inesperado, pestaña sin respuesta) y adjunta la actividad
 * de red y consola registrada por {@link ActivityRecorder}.
 */
public final class PageDiagnostics {
    // Por debajo de esto la pagina se considera "en blanco" (la SPA no llego a renderizar)
    private static final int MAX_ELEMENTOS_EN_BLANCO = 30;
    private static final Pattern RUTA_HASH_SEGURA = Pattern.compile("#/[\\w/-]*");

    public enum Fase { PAGINA_SIN_RESPUESTA, APP_NO_RENDERIZO, SPINNER_ATASCADO, CONTENIDO_INESPERADO }

    public record Diagnostico(Fase fase, String detalle) {
        @Override
        public String toString() {
            return "Diagnostico: " + fase + "\n" + detalle;
        }
    }

    private static final String SCRIPT = """
            (xpath) => {
              const body = document.body;
              const texto = body ? body.innerText.trim() : '';
              let spinners = 0, visibles = 0;
              if (xpath) {
                const r = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
                spinners = r.snapshotLength;
                for (let i = 0; i < r.snapshotLength; i++) {
                  const n = r.snapshotItem(i);
                  const st = getComputedStyle(n);
                  if (n.getClientRects().length > 0 && st.visibility !== 'hidden' && st.display !== 'none') visibles++;
                }
              }
              return {
                readyState: document.readyState,
                url: location.href,
                titulo: document.title,
                largoTexto: texto.length,
                muestraTexto: texto.slice(0, 300),
                elementos: body ? body.getElementsByTagName('*').length : 0,
                spinners: spinners,
                spinnersVisibles: visibles
              };
            }""";

    private PageDiagnostics() {}

    /** Nunca lanza excepcion: si la pestaña no responde, lo indica en el diagnostico. */
    @SuppressWarnings("unchecked")
    public static Diagnostico capturar(Page page) {
        String actividad = ActivityRecorder.resumen(page);
        Map<String, Object> estado;
        try {
            estado = (Map<String, Object>) page.evaluate(SCRIPT, ConfigManager.getGlobal("ui.spinner.xpath"));
        } catch (Exception e) {
            return new Diagnostico(Fase.PAGINA_SIN_RESPUESTA,
                    "No se pudo evaluar la pagina: " + ActivityRecorder.recortar(e.getMessage(), 200) + "\n" + actividad);
        }

        int largoTexto = numero(estado.get("largoTexto"));
        int elementos = numero(estado.get("elementos"));
        int spinnersVisibles = numero(estado.get("spinnersVisibles"));

        Fase fase;
        if (spinnersVisibles > 0) {
            fase = Fase.SPINNER_ATASCADO;
        } else if (largoTexto == 0 && elementos <= MAX_ELEMENTOS_EN_BLANCO) {
            fase = Fase.APP_NO_RENDERIZO;
        } else {
            fase = Fase.CONTENIDO_INESPERADO;
        }

        String detalle = "Pagina: readyState=" + estado.get("readyState")
                + ", url=" + urlPagina(String.valueOf(estado.get("url")))
                + ", titulo='" + ActivityRecorder.recortar(String.valueOf(estado.get("titulo")), 100) + "'"
                + ", elementos=" + elementos
                + ", largoTexto=" + largoTexto
                + ", spinners=" + numero(estado.get("spinners")) + " (visibles=" + spinnersVisibles + ")"
                + (largoTexto > 0 ? "\nTexto visible: '" + ActivityRecorder.recortar(String.valueOf(estado.get("muestraTexto")), 300) + "'" : "")
                + "\n" + actividad;
        return new Diagnostico(fase, detalle);
    }

    // Conserva la ruta de la SPA (#/login) solo si no lleva parametros (un fragmento puede llevar tokens)
    private static String urlPagina(String url) {
        String base = ActivityRecorder.url(url);
        int h = url.indexOf('#');
        if (h >= 0) {
            var m = RUTA_HASH_SEGURA.matcher(url.substring(h));
            if (m.lookingAt()) base += m.group();
        }
        return base;
    }

    private static int numero(Object valor) {
        return valor instanceof Number n ? n.intValue() : 0;
    }
}
