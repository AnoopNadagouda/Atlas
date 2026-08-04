package com.atlas.logging;

import com.atlas.common.utils.AtlasConstants;
import org.slf4j.MDC;

import java.util.UUID;

public final class CorrelationIdContext {

    public static final String MDC_KEY = "correlationId";

    private CorrelationIdContext() {}

    public static String getCorrelationId() {
        String id = MDC.get(MDC_KEY);
        if (id == null || id.isBlank()) {
            id = generateNewCorrelationId();
            setCorrelationId(id);
        }
        return id;
    }

    public static void setCorrelationId(String correlationId) {
        if (correlationId != null && !correlationId.isBlank()) {
            MDC.put(MDC_KEY, correlationId);
        } else {
            MDC.put(MDC_KEY, generateNewCorrelationId());
        }
    }

    public static String generateNewCorrelationId() {
        return "atl-" + UUID.randomUUID().toString().substring(0, 13);
    }

    public static void clear() {
        MDC.remove(MDC_KEY);
    }
}
