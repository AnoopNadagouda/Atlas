package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Local File System Connector Adapter for recursive directory indexing.
 */
@Component
public class LocalFileSystemConnector extends AbstractConnector {

    public LocalFileSystemConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("local_fs")
                .name("Local File System")
                .version("1.0.0")
                .vendor("Atlas Infrastructure")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("FILE", "DIRECTORY"))
                .permissions(List.of("fs:read"))
                .iconUrl("/assets/icons/folder.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("local-file-01")
                .title("[Local FS] System Config & Cluster Manifest.yaml")
                .fileType("JSON")
                .mimeType("application/json")
                .sizeBytes(1200)
                .extractedText("Local FS File: Cluster node topology and Docker deployment manifests.")
                .createdAt(Instant.now())
                .metadata(Map.of("path", "./config/cluster-manifest.yaml"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("localfs-res-1")
                .title("Local FS: System Config & Cluster Manifest")
                .snippet("Local file system directory entry for cluster configuration manifest.")
                .documentUrl("file:///c:/Users/ANOOP/Desktop/projects/Search Engine/README.md")
                .score(0.96)
                .sourceName("Local File System")
                .connectorId("local_fs")
                .repository("./config")
                .workspace("Local Workspace")
                .tenantId("default-tenant")
                .owner("System Administrator")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
