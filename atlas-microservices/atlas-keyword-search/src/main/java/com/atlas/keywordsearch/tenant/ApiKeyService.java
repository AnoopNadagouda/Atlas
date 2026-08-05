package com.atlas.keywordsearch.tenant;

import com.atlas.domain.tenant.ApiKey;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ApiKeyService {

    private final Map<String, ApiKey> apiKeys = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedKeys() {
        log.info("Initializing Tenant API Key Service...");
        createApiKey("key-admin-001", "default-tenant", "Admin Integration Key", "ADMIN", 365);
        createApiKey("key-acme-001", "tenant-acme", "Acme Search Key", "SEARCH", 180);
    }

    public synchronized ApiKey createApiKey(String keyId, String tenantId, String name, String role, int validDays) {
        ApiKey key = ApiKey.builder()
                .keyId(keyId)
                .tenantId(tenantId)
                .name(name)
                .secretKey("atl_sk_" + UUID.randomUUID().toString().replace("-", ""))
                .role(role)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(validDays, ChronoUnit.DAYS))
                .revoked(false)
                .build();

        apiKeys.put(keyId, key);
        log.info("[ApiKeyService] Created API Key '{}' for Tenant '{}' (Role: {})", keyId, tenantId, role);
        return key;
    }

    public synchronized void revokeApiKey(String keyId) {
        ApiKey key = apiKeys.get(keyId);
        if (key != null) {
            key.setRevoked(true);
            log.info("[ApiKeyService] Revoked API Key '{}'", keyId);
        }
    }

    public List<ApiKey> getApiKeysForTenant(String tenantId) {
        List<ApiKey> list = new ArrayList<>();
        for (ApiKey key : apiKeys.values()) {
            if (key.getTenantId().equalsIgnoreCase(tenantId) && !key.isRevoked()) {
                list.add(key);
            }
        }
        return list;
    }
}
