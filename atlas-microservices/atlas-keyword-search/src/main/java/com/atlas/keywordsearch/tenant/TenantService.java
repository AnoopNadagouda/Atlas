package com.atlas.keywordsearch.tenant;

import com.atlas.domain.tenant.Tenant;
import com.atlas.domain.tenant.TenantQuota;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TenantService {

    private final Map<String, Tenant> tenants = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedTenants() {
        log.info("Initializing Enterprise Multi-Tenant Service...");

        Tenant defaultTenant = Tenant.builder()
                .id("default-tenant")
                .name("Default Organization")
                .domain("atlas.search")
                .status("ACTIVE")
                .createdAt(Instant.now())
                .quota(TenantQuota.builder()
                        .maxStorageBytes(107374182400L) // 100 GB
                        .maxDocuments(1000000)
                        .maxMonthlyQueries(5000000)
                        .maxMonthlyCrawlPages(500000)
                        .build())
                .build();

        Tenant acmeTenant = Tenant.builder()
                .id("tenant-acme")
                .name("Acme Corporation")
                .domain("acme.org")
                .status("ACTIVE")
                .createdAt(Instant.now())
                .quota(TenantQuota.builder()
                        .maxStorageBytes(53687091200L) // 50 GB
                        .maxDocuments(500000)
                        .maxMonthlyQueries(2000000)
                        .maxMonthlyCrawlPages(200000)
                        .build())
                .build();

        createTenant(defaultTenant);
        createTenant(acmeTenant);
    }

    public synchronized Tenant createTenant(Tenant tenant) {
        if (tenant == null || tenant.getId() == null) return null;
        tenants.put(tenant.getId(), tenant);
        log.info("[TenantService] Created Tenant '{}' ({})", tenant.getName(), tenant.getId());
        return tenant;
    }

    public List<Tenant> getAllTenants() {
        return new ArrayList<>(tenants.values());
    }

    public Tenant getTenantById(String id) {
        return id != null ? tenants.get(id) : null;
    }

    public synchronized void deleteTenant(String id) {
        Tenant tenant = tenants.get(id);
        if (tenant != null) {
            tenant.setStatus("DELETED");
            log.info("[TenantService] Marked Tenant '{}' as DELETED", id);
        }
    }

    public Map<String, Object> getTenantStatistics(String id) {
        Tenant tenant = getTenantById(id);
        if (tenant == null) return Map.of();

        return Map.of(
                "tenantId", tenant.getId(),
                "name", tenant.getName(),
                "status", tenant.getStatus(),
                "currentStorageBytes", 1280000000L,
                "currentDocumentCount", 150000,
                "monthlyQueryCount", 45000,
                "monthlyCrawlPageCount", 12000
        );
    }
}
