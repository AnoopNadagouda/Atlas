package com.atlas.keywordsearch.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TenantContextTest {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void testTenantContextDefaultFallback() {
        assertEquals("default-tenant", TenantContextHolder.getTenantId());
    }

    @Test
    void testSetTenantContext() {
        TenantContextHolder.setTenantId("tenant-acme");
        assertEquals("tenant-acme", TenantContextHolder.getTenantId());
    }
}
