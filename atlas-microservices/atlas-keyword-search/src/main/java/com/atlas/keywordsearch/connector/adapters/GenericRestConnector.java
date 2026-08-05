package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generic REST API Connector Adapter for custom internal REST endpoints.
 */
@Component
public class GenericRestConnector extends AbstractConnector {

    public GenericRestConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("generic_rest")
                .name("Generic REST API Endpoint")
                .version("1.0.0")
                .vendor("Atlas Framework")
                .category("DB_OBJECT")
                .supportedObjectTypes(List.of("ENDPOINT", "JSON_ITEM"))
                .permissions(List.of("http:get"))
                .iconUrl("/assets/icons/api.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("rest-item-88")
                .title("[Generic REST] External CRM Customer Knowledge Record #88")
                .fileType("JSON")
                .mimeType("application/json")
                .sizeBytes(1500)
                .extractedText("Generic REST JSON item: Customer account profile and enterprise search preferences.")
                .createdAt(Instant.now())
                .metadata(Map.of("endpoint", "https://api.crm.company.com/v1/records/88"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("rest-res-1")
                .title("Generic REST API: CRM Customer Record #88")
                .snippet("REST API endpoint result containing enterprise customer metadata.")
                .documentUrl("https://api.crm.company.com/v1/records/88")
                .score(0.85)
                .sourceName("Generic REST API")
                .connectorId("generic_rest")
                .repository("CRM API Endpoint")
                .workspace("Internal Apps")
                .tenantId("default-tenant")
                .owner("CRM Admin")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
