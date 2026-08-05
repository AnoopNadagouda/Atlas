package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Microsoft OneDrive Connector Adapter.
 */
@Component
public class OneDriveConnector extends AbstractConnector {

    public OneDriveConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("onedrive")
                .name("Microsoft OneDrive")
                .version("1.0.0")
                .vendor("Microsoft")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("FILE", "FOLDER", "DOCUMENT"))
                .permissions(List.of("Files.Read.All"))
                .iconUrl("/assets/icons/onedrive.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("onedrive-file-12")
                .title("[OneDrive] Quarterly Security Audit & Compliance Report.docx")
                .fileType("DOCX")
                .mimeType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .sizeBytes(1450000)
                .extractedText("OneDrive Document: ISO-27001 and SOC-2 Type II audit compliance verification for Atlas platform.")
                .createdAt(Instant.now())
                .metadata(Map.of("drive", "Personal Business", "folder", "Compliance"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("onedrive-res-1")
                .title("OneDrive: Quarterly Security Audit Report")
                .snippet("OneDrive Word document covering SOC2 certification and encryption key security.")
                .documentUrl("https://onedrive.live.com/view.aspx?id=12")
                .score(0.89)
                .sourceName("Microsoft OneDrive")
                .connectorId("onedrive")
                .repository("Compliance Folder")
                .workspace("Microsoft 365")
                .tenantId("default-tenant")
                .owner("SecOps Team")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
