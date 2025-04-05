package io.github.mateuussilvapb.app_jm_perfumaria.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ObjectUtil {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static Optional<Number> ifNumber(Object obj) {
        return (obj instanceof Number intObj) ? Optional.of(intObj) : Optional.empty();
    }

    public static Optional<Integer> ifInteger(Object obj) {
        return (obj instanceof Integer intObj) ? Optional.of(intObj) : Optional.empty();
    }

    public static Optional<String> ifString(Object obj) {
        return (obj instanceof String strObj) ? Optional.of(strObj) : Optional.empty();
    }

    public static Optional<Boolean> ifBoolean(Object obj) {
        return (obj instanceof Boolean boolObj) ? Optional.of(boolObj) : Optional.empty();
    }

    @SuppressWarnings("unused")
    public static <K> Optional<K> ifCast(Object object, Class<K> clazz) {
        try {
            return Optional.of(cast(object));
        } catch (ClassCastException ex) {
            return Optional.empty();
        }
    }
}