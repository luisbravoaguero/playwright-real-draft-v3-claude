package com.mapfre.utils.testdata;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Semilla de datos de prueba por escenario (por hilo).
 * Hooks llama a {@link #clear()} al inicio y fin de cada escenario, asi cada escenario obtiene
 * una semilla nueva. Con -Ddata.seed=N todos los escenarios reproducen los mismos datos.
 */
public final class SeedManager {
    private static final ThreadLocal<Long> TL_SEED = new ThreadLocal<>();
    private static final ThreadLocal<Random> TL_RANDOM = new ThreadLocal<>();

    private SeedManager() {}

    public static long seed() {
        Long s = TL_SEED.get();
        if (s != null) return s;

        String prop = System.getProperty("data.seed");
        long seed = (prop != null && !prop.isBlank())
                ? Long.parseLong(prop.trim())
                : ThreadLocalRandom.current().nextLong();

        TL_SEED.set(seed);
        return seed;
    }

    /** Secuencia aleatoria unica del escenario: cada llamada avanza la secuencia (valores distintos). */
    public static Random random() {
        Random r = TL_RANDOM.get();
        if (r == null) {
            r = new Random(seed());
            TL_RANDOM.set(r);
        }
        return r;
    }

    public static void clear() {
        TL_SEED.remove();
        TL_RANDOM.remove();
    }
}
