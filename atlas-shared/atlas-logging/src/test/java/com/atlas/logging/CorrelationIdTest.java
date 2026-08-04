package com.atlas.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrelationIdTest {

    @Test
    void testCorrelationIdContext() {
        CorrelationIdContext.clear();
        String id1 = CorrelationIdContext.getCorrelationId();
        assertNotNull(id1);
        assertTrue(id1.startsWith("atl-"));

        CorrelationIdContext.setCorrelationId("custom-cid-123");
        assertEquals("custom-cid-123", CorrelationIdContext.getCorrelationId());

        CorrelationIdContext.clear();
    }
}
