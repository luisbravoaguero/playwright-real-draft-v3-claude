package com.mapfre.utils.diagnostics;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Registra la actividad de red y de consola de un BrowserContext (incluye popups) para explicar los timeouts:
 * peticiones pendientes, peticiones fallidas, respuestas HTTP >= 400, errores de consola y errores JS no capturados.
 * Las URLs se guardan sin query string ni fragmento (pueden llevar tokens) y todos los buffers estan acotados.
 */
public final class ActivityRecorder {
    private static final int MAX_ENTRADAS = 20;
    private static final int MAX_PENDIENTES_MOSTRADAS = 10;
    private static final int MAX_URL = 150;
    private static final int MAX_MENSAJE = 200;
    private static final Map<BrowserContext, ActivityRecorder> RECORDERS = new ConcurrentHashMap<>();

    private final Map<Request, Long> pendientes = new ConcurrentHashMap<>();
    private final Deque<Entrada> fallidas = new ConcurrentLinkedDeque<>();
    private final Deque<Entrada> erroresHttp = new ConcurrentLinkedDeque<>();
    private final Deque<Entrada> erroresConsola = new ConcurrentLinkedDeque<>();
    private final Deque<Entrada> erroresJs = new ConcurrentLinkedDeque<>();

    private record Entrada(Page page, String texto) {}

    private ActivityRecorder() {}

    /** Registra los listeners una sola vez por contexto; se elimina solo cuando el contexto se cierra. */
    public static void attach(BrowserContext ctx) {
        if (RECORDERS.containsKey(ctx)) return;
        ActivityRecorder rec = new ActivityRecorder();
        RECORDERS.put(ctx, rec);
        ctx.onClose(RECORDERS::remove);

        ctx.onRequest(r -> rec.pendientes.put(r, System.currentTimeMillis()));
        ctx.onRequestFinished(rec.pendientes::remove);
        ctx.onRequestFailed(r -> {
            rec.pendientes.remove(r);
            agregar(rec.fallidas, new Entrada(pageDe(r), r.method() + " " + url(r.url()) + " [" + r.resourceType() + "] -> " + r.failure()));
        });
        ctx.onResponse(resp -> {
            if (resp.status() >= 400) {
                agregar(rec.erroresHttp, new Entrada(pageDe(resp), resp.status() + " " + resp.request().method() + " " + url(resp.url())));
            }
        });
        ctx.onConsoleMessage(msg -> {
            if ("error".equals(msg.type())) {
                agregar(rec.erroresConsola, new Entrada(msg.page(), recortar(msg.text(), MAX_MENSAJE)));
            }
        });
        ctx.onWebError(err -> agregar(rec.erroresJs, new Entrada(err.page(), recortar(err.error(), MAX_MENSAJE))));
    }

    /** Resumen legible de la actividad de la pestaña (o "sin registro" si su contexto no tiene recorder). */
    public static String resumen(Page page) {
        ActivityRecorder rec = page == null ? null : RECORDERS.get(page.context());
        if (rec == null) return "Actividad de red: sin registro para esta pestaña";

        long ahora = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();

        List<Map.Entry<Request, Long>> pend = new ArrayList<>();
        for (Map.Entry<Request, Long> e : rec.pendientes.entrySet()) {
            if (pageDe(e.getKey()) == page) pend.add(e);
        }
        pend.sort(Map.Entry.comparingByValue()); // las mas antiguas primero
        sb.append("Peticiones pendientes (").append(pend.size()).append("):");
        if (pend.isEmpty()) sb.append(" ninguna");
        for (int i = 0; i < Math.min(pend.size(), MAX_PENDIENTES_MOSTRADAS); i++) {
            Request r = pend.get(i).getKey();
            sb.append("\n  - ").append(ahora - pend.get(i).getValue()).append(" ms ")
                    .append(r.method()).append(' ').append(url(r.url())).append(" [").append(r.resourceType()).append(']');
        }
        seccion(sb, "Peticiones fallidas", rec.fallidas, page);
        seccion(sb, "Respuestas HTTP >= 400", rec.erroresHttp, page);
        seccion(sb, "Errores de consola", rec.erroresConsola, page);
        seccion(sb, "Errores JS no capturados", rec.erroresJs, page);
        return sb.toString();
    }

    private static void seccion(StringBuilder sb, String titulo, Deque<Entrada> entradas, Page page) {
        List<String> textos = new ArrayList<>();
        for (Entrada e : entradas) {
            if (e.page() == null || e.page() == page) textos.add(e.texto());
        }
        sb.append('\n').append(titulo).append(" (").append(textos.size()).append("):");
        if (textos.isEmpty()) sb.append(" ninguno");
        for (String t : textos) sb.append("\n  - ").append(t);
    }

    private static void agregar(Deque<Entrada> deque, Entrada entrada) {
        deque.addLast(entrada);
        while (deque.size() > MAX_ENTRADAS) deque.pollFirst();
    }

    // Las peticiones de service workers no tienen frame: frame() lanza excepcion
    private static Page pageDe(Request r) {
        try {
            return r.frame().page();
        } catch (Exception e) {
            return null;
        }
    }

    private static Page pageDe(Response r) {
        try {
            return r.frame().page();
        } catch (Exception e) {
            return null;
        }
    }

    /** URL sin query string ni fragmento (pueden contener tokens), recortada. */
    static String url(String url) {
        if (url == null) return "";
        int corte = url.length();
        int q = url.indexOf('?');
        int h = url.indexOf('#');
        if (q >= 0) corte = q;
        if (h >= 0 && h < corte) corte = h;
        return recortar(url.substring(0, corte), MAX_URL);
    }

    static String recortar(String texto, int max) {
        if (texto == null) return "";
        String limpio = texto.replaceAll("\\s+", " ").trim();
        return limpio.length() <= max ? limpio : limpio.substring(0, max) + "...";
    }
}
