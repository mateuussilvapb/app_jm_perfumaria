package io.github.mateuussilvapb.app_jm_perfumaria.util;

import java.util.Comparator;
import java.util.function.Function;

public final class ComparatorsUtil {

    private ComparatorsUtil() {
        // Evita instanciação — classe utilitária
    }

    public static <T, K1 extends Comparable<? super K1>, K2 extends Comparable<? super K2>>
    Comparator<T> ordenarPorDoisCriterios(
            Function<T, K1> extratorChave1,
            Function<T, K2> extratorChave2) {

        return Comparator
                .comparing(extratorChave1, Comparator.reverseOrder())
                .thenComparing(extratorChave2);
    }
}
