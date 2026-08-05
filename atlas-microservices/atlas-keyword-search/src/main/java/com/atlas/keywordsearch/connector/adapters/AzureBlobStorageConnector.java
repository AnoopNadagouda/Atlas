package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Azure Blob Storage Connector Adapter.
 */
@Component
public class AzureBlobStorageConnector extends AbstractConnector {

    public AzureBlobStorageConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("azure_blob")
                .name("Azure Blob Storage")
                .version("1.0.0")
                .vendor("Microsoft Azure")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("CONTAINER", "BLOB"))
                .permissions(List.of("Microsoft.Storage/storageAccounts/blobServices/containers/blobs/read"))
                .iconUrl("/assets/icons/azure.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("azure-blob-55")
                .title("[Azure Blob] container/documents/AI_Indexing_Policy.pdf")
                .fileType("PDF")
                .mimeType("application/pdf")
                .sizeBytes(3200000)
                .extractedText("Azure Blob Document: Enterprise indexing policy for Azure cloud infrastructure.")
                .createdAt(Instant.now())
                .metadata(Map.of("container", "documents", "blob", "AI_Indexing_Policy.pdf"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("azure-res-1")
                .title("Azure Blob: AI_Indexing_Policy.pdf")
                .snippet("Azure Blob container PDF document for enterprise cloud indexing rules.")
                .documentUrl("https://atlasstore.blob.core.windows.net/documents/AI_Indexing_Policy.pdf")
                .score(0.89)
                .sourceName("Azure Blob Storage")
                .connectorId("azure_blob")
                .repository("documents container")
                .workspace("Azure Subscription")
                .tenantId("default-tenant")
                .owner("CloudOps")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
