package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.tenant.ApiKey;
import com.atlas.domain.tenant.Tenant;
import com.atlas.domain.tenant.TenantQuota;
import com.atlas.keywordsearch.tenant.ApiKeyService;
import com.atlas.keywordsearch.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v14/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;
    private final ApiKeyService apiKeyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tenant>>> getAllTenants() {
        log.info("Fetching all registered enterprise tenants");
        return ResponseEntity.ok(ApiResponse.success(tenantService.getAllTenants()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tenant>> createTenant(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String domain) {
        log.info("Creating new enterprise tenant: '{}' ({})", name, id);
        Tenant tenant = Tenant.builder()
                .id(id)
                .name(name)
                .domain(domain)
                .status("ACTIVE")
                .createdAt(Instant.now())
                .quota(TenantQuota.builder()
                        .maxStorageBytes(107374182400L)
                        .maxDocuments(1000000)
                        .maxMonthlyQueries(5000000)
                        .maxMonthlyCrawlPages(500000)
                        .build())
                .build();
        Tenant created = tenantService.createTenant(tenant);
        return ResponseEntity.ok(ApiResponse.success(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tenant>> getTenant(@PathVariable String id) {
        log.info("Fetching tenant details for ID: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(tenantService.getTenantById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTenant(@PathVariable String id) {
        log.info("Deleting tenant: '{}'", id);
        tenantService.deleteTenant(id);
        return ResponseEntity.ok(ApiResponse.success("Tenant marked as DELETED"));
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics(@PathVariable String id) {
        log.info("Fetching statistics for tenant: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(tenantService.getTenantStatistics(id)));
    }

    @GetMapping("/{id}/usage")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUsage(@PathVariable String id) {
        log.info("Fetching resource usage for tenant: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(tenantService.getTenantStatistics(id)));
    }

    @GetMapping("/{id}/quotas")
    public ResponseEntity<ApiResponse<TenantQuota>> getQuotas(@PathVariable String id) {
        log.info("Fetching quotas for tenant: '{}'", id);
        Tenant tenant = tenantService.getTenantById(id);
        TenantQuota quota = tenant != null ? tenant.getQuota() : null;
        return ResponseEntity.ok(ApiResponse.success(quota));
    }

    @PostMapping("/{id}/apikeys")
    public ResponseEntity<ApiResponse<ApiKey>> createApiKey(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam(defaultValue = "SEARCH") String role) {
        log.info("Creating API Key for tenant: '{}' (Role: {})", id, role);
        String keyId = "key-" + UUID.randomUUID().toString().substring(0, 8);
        ApiKey key = apiKeyService.createApiKey(keyId, id, name, role, 180);
        return ResponseEntity.ok(ApiResponse.success(key));
    }

    @DeleteMapping("/{id}/apikeys/{keyId}")
    public ResponseEntity<ApiResponse<String>> revokeApiKey(
            @PathVariable String id,
            @PathVariable String keyId) {
        log.info("Revoking API Key '{}' for tenant: '{}'", keyId, id);
        apiKeyService.revokeApiKey(keyId);
        return ResponseEntity.ok(ApiResponse.success("API Key revoked successfully"));
    }
}
