package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.FederatedSearchRequest;
import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import com.atlas.keywordsearch.connector.adapters.ConfluenceConnector;
import com.atlas.keywordsearch.connector.adapters.SlackConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SearchAggregationTest {

    private FederatedSearchService federatedSearchService;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(
                new GitHubConnector(),
                new ConfluenceConnector(),
                new SlackConnector()
        ));
        registry.registerInjectedConnectors();
        AclFilterService aclFilterService = new AclFilterService();
        federatedSearchService = new FederatedSearchService(registry, null, aclFilterService);
    }

    @Test
    void testParallelSearchAggregation() {
        FederatedSearchRequest req = FederatedSearchRequest.builder()
                .query("Atlas")
                .size(10)
                .timeoutMs(2500)
                .targetConnectors(List.of("ALL"))
                .build();

        Map<String, Object> result = federatedSearchService.executeFederatedSearch(req);
        assertNotNull(result);
        assertTrue((int) result.get("totalResults") >= 3);
        Map<?, ?> perSource = (Map<?, ?>) result.get("perSourceLatency");
        assertTrue(perSource.containsKey("github"));
        assertTrue(perSource.containsKey("confluence"));
        assertTrue(perSource.containsKey("slack"));
    }
}
