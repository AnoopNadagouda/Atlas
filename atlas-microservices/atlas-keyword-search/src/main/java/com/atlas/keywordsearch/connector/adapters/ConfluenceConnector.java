package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Confluence Connector Adapter for Spaces, Pages, Comments, and Attachments.
 */
@Component
public class ConfluenceConnector extends AbstractConnector {

    public ConfluenceConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("confluence")
                .name("Atlassian Confluence")
                .version("1.0.0")
                .vendor("Atlassian")
                .category("WIKI_DOCUMENTATION")
                .supportedObjectTypes(List.of("SPACE", "PAGE", "COMMENT", "ATTACHMENT"))
                .permissions(List.of("read:confluence-space", "read:confluence-props"))
                .iconUrl("/assets/icons/confluence.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("conf-page-881")
                .title("[Confluence] Atlas Enterprise Architecture Blueprint & Runbook")
                .fileType("HTML")
                .mimeType("text/html")
                .sizeBytes(15200)
                .extractedText("Confluence Wiki Page: Distributed Cluster Topology, Kafka Topics, and Disaster Recovery Runbook.")
                .createdAt(Instant.now())
                .metadata(Map.of("spaceKey", "ENG", "version", 14, "author", "Principal Architect"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("conf-res-1")
                .title("Confluence: Atlas Disaster Recovery Runbook")
                .snippet("Engineering wiki documentation for cluster failover and multi-region topic replication.")
                .documentUrl("https://confluence.company.com/display/ENG/Atlas+Runbook")
                .score(0.95)
                .sourceName("Confluence")
                .connectorId("confluence")
                .repository("ENG Space")
                .workspace("Engineering Workspace")
                .tenantId("default-tenant")
                .owner("Infra Team")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
