package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.FederatedSearchRequest;

import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import com.atlas.keywordsearch.connector.adapters.ConfluenceConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FederatedSearchServiceTest {

    private FederatedSearchService federatedSearchService;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(new GitHubConnector(), new ConfluenceConnector()));
        registry.registerInjectedConnectors();
        AclFilterService aclFilterService = new AclFilterService();
        federatedSearchService = new FederatedSearchService(registry, null, aclFilterService);
    }

    @Test
    void testExecuteFederatedSearch() {
        FederatedSearchRequest req = FederatedSearchRequest.builder()
                .query("architecture")
                .size(10)
                .timeoutMs(2000)
                .tenantId("default-tenant")
                .targetConnectors(List.of("ALL"))
                .build();

        Map<String, Object> result = federatedSearchService.executeFederatedSearch(req);
        assertNotNull(result);
        assertEquals("architecture", result.get("query"));
        assertTrue((int) result.get("totalResults") >= 1);
        assertNotNull(result.get("perSourceLatency"));
    }
}
