package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Jira Connector Adapter for Projects, Issues, and Components.
 */
@Component
public class JiraConnector extends AbstractConnector {

    public JiraConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("jira")
                .name("Atlassian Jira")
                .version("1.0.0")
                .vendor("Atlassian")
                .category("WIKI_DOCUMENTATION")
                .supportedObjectTypes(List.of("PROJECT", "ISSUE", "COMPONENT"))
                .permissions(List.of("read:jira-work"))
                .iconUrl("/assets/icons/jira.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("jira-issue-ATLAS-504")
                .title("[Jira] ATLAS-504: Enterprise Connectors & Federated Search Release")
                .fileType("TXT")
                .mimeType("text/plain")
                .sizeBytes(4100)
                .extractedText("Jira Ticket: Implement production connectors for GitHub, Slack, Notion, and S3.")
                .createdAt(Instant.now())
                .metadata(Map.of("projectKey", "ATLAS", "assignee", "Anoop Nadagouda", "priority", "HIGHEST"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("jira-res-1")
                .title("Jira ATLAS-504: Enterprise Connectors & Federated Search")
                .snippet("Jira Issue tracking release 5.4.0 deliverables, connector adapters, and ACL filtering.")
                .documentUrl("https://jira.company.com/browse/ATLAS-504")
                .score(0.97)
                .sourceName("Jira")
                .connectorId("jira")
                .repository("ATLAS Project")
                .workspace("Core Engineering")
                .tenantId("default-tenant")
                .owner("Anoop Nadagouda")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
