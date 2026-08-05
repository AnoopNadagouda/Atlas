package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GitHub Connector Adapter for Repos, Issues, Pull Requests, Code, and Wikis.
 */
@Component
public class GitHubConnector extends AbstractConnector {

    public GitHubConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("github")
                .name("GitHub Enterprise")
                .version("1.0.0")
                .vendor("GitHub / Microsoft")
                .category("CODE_REPOSITORY")
                .supportedObjectTypes(List.of("REPOSITORY", "ISSUE", "PULL_REQUEST", "CODE", "WIKI"))
                .permissions(List.of("repo:read", "read:org"))
                .iconUrl("/assets/icons/github.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("github-issue-101")
                .title("[GitHub] Issue #101: Optimize Parallel Vector Shard Query Fan-Out")
                .fileType("MARKDOWN")
                .mimeType("text/markdown")
                .sizeBytes(3400)
                .extractedText("GitHub Issue: Fan-out optimization for parallel HNSW vector shards in Atlas search coordinator.")
                .createdAt(Instant.now())
                .metadata(Map.of("repo", "AnoopNadagouda/Atlas", "author", "octocat", "state", "OPEN"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("gh-res-1")
                .title("GitHub: Atlas Distributed Coordinator Architecture")
                .snippet("GitHub code & wiki documentation for Atlas distributed coordinator shard dispatch.")
                .documentUrl("https://github.com/AnoopNadagouda/Atlas/blob/main/README.md")
                .score(0.96)
                .sourceName("GitHub")
                .connectorId("github")
                .repository("AnoopNadagouda/Atlas")
                .workspace("AnoopNadagouda")
                .tenantId("default-tenant")
                .owner("AnoopNadagouda")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
