package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.FederatedSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AclFilterTest {

    private AclFilterService aclFilterService;

    @BeforeEach
    void setUp() {
        aclFilterService = new AclFilterService();
    }

    @Test
    void testPublicResultsPass() {
        FederatedSearchResult res = FederatedSearchResult.builder()
                .resultId("res-1")
                .permissionStatus("PUBLIC")
                .tenantId("default-tenant")
                .build();

        List<FederatedSearchResult> filtered = aclFilterService.filterResults(List.of(res), "user1", "default-tenant", List.of());
        assertEquals(1, filtered.size());
    }

    @Test
    void testRestrictedResultsFilteredWithoutToken() {
        FederatedSearchResult res = FederatedSearchResult.builder()
                .resultId("res-2")
                .permissionStatus("RESTRICTED")
                .owner("admin-user")
                .tenantId("default-tenant")
                .build();

        List<FederatedSearchResult> filtered = aclFilterService.filterResults(List.of(res), "user1", "default-tenant", List.of());
        assertEquals(0, filtered.size());

        List<FederatedSearchResult> allowed = aclFilterService.filterResults(List.of(res), "user1", "default-tenant", List.of("ADMIN"));
        assertEquals(1, allowed.size());
    }
}
