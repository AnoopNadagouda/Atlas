package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GitLab Connector Adapter for Projects, Issues, Merge Requests, and Snippets.
 */
@Component
public class GitLabConnector extends AbstractConnector {

    public GitLabConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("gitlab")
                .name("GitLab Enterprise")
                .version("1.0.0")
                .vendor("GitLab Inc.")
                .category("CODE_REPOSITORY")
                .supportedObjectTypes(List.of("PROJECT", "ISSUE", "MERGE_REQUEST", "SNIPPET"))
                .permissions(List.of("read_api", "read_repository"))
                .iconUrl("/assets/icons/gitlab.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("gitlab-mr-42")
                .title("[GitLab] MR !42: Add Microservices Circuit Breaker Resiliency")
                .fileType("MARKDOWN")
                .mimeType("text/markdown")
                .sizeBytes(2900)
                .extractedText("GitLab Merge Request: Resilience4j Circuit Breakers for API Gateway routing.")
                .createdAt(Instant.now())
                .metadata(Map.of("project", "atlas/gateway", "author", "gitlab-user", "state", "MERGED"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("gl-res-1")
                .title("GitLab: Resilience Architecture Spec")
                .snippet("GitLab project docs detailing gateway circuit breaker and fallback policies.")
                .documentUrl("https://gitlab.com/atlas/gateway/-/merge_requests/42")
                .score(0.92)
                .sourceName("GitLab")
                .connectorId("gitlab")
                .repository("atlas/gateway")
                .workspace("atlas-org")
                .tenantId("default-tenant")
                .owner("DevOps Team")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
