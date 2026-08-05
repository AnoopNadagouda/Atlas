package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Notion Connector Adapter for Pages, Databases, and Blocks.
 */
@Component
public class NotionConnector extends AbstractConnector {

    public NotionConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("notion")
                .name("Notion Workspace")
                .version("1.0.0")
                .vendor("Notion Labs")
                .category("WIKI_DOCUMENTATION")
                .supportedObjectTypes(List.of("PAGE", "DATABASE", "BLOCK"))
                .permissions(List.of("read_content"))
                .iconUrl("/assets/icons/notion.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("notion-page-991")
                .title("[Notion] Atlas Product Roadmap & AI Search Feature Specs")
                .fileType("MARKDOWN")
                .mimeType("text/markdown")
                .sizeBytes(6200)
                .extractedText("Notion Page: Product Roadmap 2026 for Atlas Federated AI Search Platform.")
                .createdAt(Instant.now())
                .metadata(Map.of("workspace", "Product Org", "created_by", "VP Product"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("notion-res-1")
                .title("Notion: Atlas Product Roadmap 2026")
                .snippet("Notion product page detailing enterprise connector integrations and vector RAG capability.")
                .documentUrl("https://notion.so/company/Atlas-Roadmap-991")
                .score(0.91)
                .sourceName("Notion")
                .connectorId("notion")
                .repository("Product Database")
                .workspace("Product Org")
                .tenantId("default-tenant")
                .owner("VP Product")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
