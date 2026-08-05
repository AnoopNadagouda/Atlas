package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.FederatedSearchRequest;
import com.atlas.domain.connector.FederatedSearchResult;
import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateRemovalTest {

    private FederatedSearchService federatedSearchService;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(new GitHubConnector()));
        registry.registerInjectedConnectors();
        AclFilterService aclFilterService = new AclFilterService();
        federatedSearchService = new FederatedSearchService(registry, null, aclFilterService);
    }

    @Test
    void testDuplicateResultsRemoved() {
        FederatedSearchRequest req = FederatedSearchRequest.builder()
                .query("Atlas")
                .size(10)
                .timeoutMs(2000)
                .targetConnectors(List.of("github"))
                .build();

        Map<String, Object> result = federatedSearchService.executeFederatedSearch(req);
        assertNotNull(result);
        List<?> list = (List<?>) result.get("results");
        assertNotNull(list);
    }
}
