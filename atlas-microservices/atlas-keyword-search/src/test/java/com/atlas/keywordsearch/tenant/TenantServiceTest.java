package com.atlas.keywordsearch.tenant;

import com.atlas.domain.tenant.Tenant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TenantServiceTest {

    private TenantService tenantService;

    @BeforeEach
    void setUp() {
        tenantService = new TenantService();
        tenantService.initSeedTenants();
    }

    @Test
    void testGetAllTenants() {
        List<Tenant> list = tenantService.getAllTenants();
        assertNotNull(list);
        assertTrue(list.size() >= 2);
    }

    @Test
    void testGetTenantById() {
        Tenant tenant = tenantService.getTenantById("default-tenant");
        assertNotNull(tenant);
        assertEquals("Default Organization", tenant.getName());
    }
}
