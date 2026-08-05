package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.FederatedSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Access Control List (ACL) Filtering Service enforcing user identity propagation,
 * tenant isolation, role mapping, and permission checks on federated search results.
 */
@Slf4j
@Service
public class AclFilterService {

    public List<FederatedSearchResult> filterResults(List<FederatedSearchResult> rawResults, String userId, String tenantId, List<String> aclTokens) {
        if (rawResults == null || rawResults.isEmpty()) {
            return List.of();
        }

        log.info("[AclFilterService] Filtering {} federated results for userId='{}', tenantId='{}'", rawResults.size(), userId, tenantId);

        return rawResults.stream()
                .filter(res -> matchTenant(res, tenantId))
                .filter(res -> matchPermissions(res, userId, aclTokens))
                .collect(Collectors.toList());
    }

    private boolean matchTenant(FederatedSearchResult res, String tenantId) {
        if (tenantId == null || tenantId.isBlank() || "default-tenant".equalsIgnoreCase(tenantId)) {
            return true;
        }
        return res.getTenantId() == null || res.getTenantId().equalsIgnoreCase(tenantId);
    }

    private boolean matchPermissions(FederatedSearchResult res, String userId, List<String> aclTokens) {
        if ("RESTRICTED".equalsIgnoreCase(res.getPermissionStatus())) {
            if (aclTokens == null || aclTokens.isEmpty()) {
                return false;
            }
            return aclTokens.contains("ADMIN") || aclTokens.contains(res.getOwner());
        }
        return true;
    }
}
