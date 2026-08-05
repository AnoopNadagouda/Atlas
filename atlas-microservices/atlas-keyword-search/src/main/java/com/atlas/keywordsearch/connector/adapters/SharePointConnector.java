package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SharePoint Connector Adapter for Document Libraries, Lists, and Sites.
 */
@Component
public class SharePointConnector extends AbstractConnector {

    public SharePointConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("sharepoint")
                .name("Microsoft SharePoint")
                .version("1.0.0")
                .vendor("Microsoft")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("DOCUMENT_LIBRARY", "LIST", "SITE", "FILE"))
                .permissions(List.of("Sites.Read.All"))
                .iconUrl("/assets/icons/sharepoint.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("sp-doc-909")
                .title("[SharePoint] Global Enterprise IT Policy & Infrastructure.pptx")
                .fileType("PPTX")
                .mimeType("application/vnd.openxmlformats-officedocument.presentationml.presentation")
                .sizeBytes(5800000)
                .extractedText("SharePoint Presentation: Corporate IT policy for multi-tenant search indexing and data residency.")
                .createdAt(Instant.now())
                .metadata(Map.of("site", "IT-Portal", "library", "Shared Documents"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("sp-res-1")
                .title("SharePoint: Global Enterprise IT Policy")
                .snippet("SharePoint presentation deck detailing corporate search data residency guidelines.")
                .documentUrl("https://company.sharepoint.com/sites/IT-Portal/DocLib/Policy.pptx")
                .score(0.93)
                .sourceName("SharePoint")
                .connectorId("sharepoint")
                .repository("IT-Portal / Shared Documents")
                .workspace("SharePoint Online")
                .tenantId("default-tenant")
                .owner("CIO Office")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
