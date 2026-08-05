package com.atlas.keywordsearch.tenant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContextHolder {

    private static final String DEFAULT_TENANT_ID = "default-tenant";
    private static final ThreadLocal<String> CURRENT_TENANT = ThreadLocal.withInitial(() -> DEFAULT_TENANT_ID);

    public static void setTenantId(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            CURRENT_TENANT.set(DEFAULT_TENANT_ID);
        } else {
            CURRENT_TENANT.set(tenantId.trim());
        }
        log.debug("[TenantContextHolder] Tenant context set to: '{}'", CURRENT_TENANT.get());
    }

    public static String getTenantId() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
