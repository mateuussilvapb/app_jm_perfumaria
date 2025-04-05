package io.github.mateuussilvapb.app_jm_perfumaria.shared.tsid;


import io.micrometer.common.util.StringUtils;

import java.util.Optional;

public final class TsidNodeResolver {

    public static final String TSID_NODE_ID_PROPERTY = "tsid.node.id";
    public static final String TSID_NODE_ID_ENV = "TSID_NODE_ID";
    public static final String TSID_NODE_COUNT_PROPERTY = "tsid.node.count";
    public static final String TSID_NODE_COUNT_ENV = "TSID_NODE_COUNT";
    public static final int TSID_NODE_COUNT_DEFAULT = 1;
    public static final int TSID_NODE_ID_DEFAULT = 0;

    private TsidNodeResolver() {
        throw new UnsupportedOperationException();
    }

    public static int nodeCount() {
        return nodeCountProperty().or(TsidNodeResolver::nodeCountEnv).orElse(TSID_NODE_COUNT_DEFAULT);
    }

    public static int nodeId() {
        return nodeIdProperty().or(TsidNodeResolver::nodeIdEnv).orElse(TSID_NODE_ID_DEFAULT);
    }

    private static Optional<Integer> nodeCountProperty() {
        return property(TSID_NODE_COUNT_PROPERTY);
    }

    private static Optional<Integer> nodeCountEnv() {
        return env(TSID_NODE_COUNT_ENV);
    }

    private static Optional<Integer> nodeIdProperty() {
        return property(TSID_NODE_ID_PROPERTY);
    }

    private static Optional<Integer> nodeIdEnv() {
        return env(TSID_NODE_ID_ENV);
    }

    private static Optional<Integer> property(String property) {
        return Optional.ofNullable(System.getProperty(property))
                .filter(StringUtils::isNotBlank)
                .flatMap(TsidNodeResolver::asInteger);
    }

    private static Optional<Integer> env(String env) {
        return Optional.ofNullable(System.getenv(env))
                .filter(StringUtils::isNotBlank)
                .flatMap(TsidNodeResolver::asInteger);
    }

    private static Optional<Integer> asInteger(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}