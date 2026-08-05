package com.atlas.keywordsearch.tenant;

import com.atlas.domain.tenant.ApiKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiKeyServiceTest {

    private ApiKeyService apiKeyService;

    @BeforeEach
    void setUp() {
        apiKeyService = new ApiKeyService();
        apiKeyService.initSeedKeys();
    }

    @Test
    void testCreateAndRevokeApiKey() {
        ApiKey key = apiKeyService.createApiKey("key-test-1", "tenant-acme", "Test Key", "SEARCH", 30);
        assertNotNull(key);
        assertFalse(key.isRevoked());

        apiKeyService.revokeApiKey("key-test-1");
        List<ApiKey> list = apiKeyService.getApiKeysForTenant("tenant-acme");
        assertFalse(list.stream().anyMatch(k -> k.getKeyId().equals("key-test-1")));
    }
}
