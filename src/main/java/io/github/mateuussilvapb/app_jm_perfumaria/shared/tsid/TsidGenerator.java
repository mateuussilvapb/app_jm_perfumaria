package io.github.mateuussilvapb.app_jm_perfumaria.shared.tsid;

import io.hypersistence.tsid.TSID;
import org.slf4j.Logger;

public final class TsidGenerator {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TsidGenerator.class);

    public static final TSID.Factory TSID_FACTORY = factory(
            TsidNodeResolver.nodeCount(),
            TsidNodeResolver.nodeId()
    );

    private TsidGenerator() {
        throw new UnsupportedOperationException();
    }

    private static TSID.Factory factory(int nodeCount, int nodeId) {
        log.info("Instanciando o TSID.Factory... Node count: {}, Node ID: {}", nodeCount, nodeId);
        final var factory = TSID.Factory.builder()
                .withRandomFunction(TSID.Factory.THREAD_LOCAL_RANDOM_FUNCTION)
                .withNodeBits(nodeBits(nodeCount))
                .withNode(nodeId)
                .build();
        log.info("TSID.Factory instanciado!");
        return factory;
    }

    public static TSID generate() {
        return TSID_FACTORY.generate();
    }

    public static Long generateLong() {
        return generate().toLong();
    }

    public static String generateString() {
        return generate().toString();
    }

    private static int nodeBits(int nodeCount) {
        return (int) (Math.log(nodeCount) / Math.log(2));
    }
}