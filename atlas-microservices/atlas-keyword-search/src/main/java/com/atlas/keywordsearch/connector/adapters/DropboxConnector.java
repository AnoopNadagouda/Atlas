package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dropbox Connector Adapter for Files and Folders.
 */
@Component
public class DropboxConnector extends AbstractConnector {

    public DropboxConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("dropbox")
                .name("Dropbox Business")
                .version("1.0.0")
                .vendor("Dropbox Inc.")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("FILE", "FOLDER"))
                .permissions(List.of("files.content.read"))
                .iconUrl("/assets/icons/dropbox.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("dbx-file-77")
                .title("[Dropbox] Financial Forecast Model Q3 2026.xlsx")
                .fileType("XLSX")
                .mimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .sizeBytes(2100000)
                .extractedText("Dropbox Spreadsheet: Revenue projections, infrastructure cloud credits, and R&D budget.")
                .createdAt(Instant.now())
                .metadata(Map.of("path", "/Finance/2026/Q3_Forecast.xlsx"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("dbx-res-1")
                .title("Dropbox: Financial Forecast Model Q3 2026")
                .snippet("Dropbox Excel workbook outlining cloud infrastructure expenditure and AI cluster sizing.")
                .documentUrl("https://dropbox.com/home/Finance/Q3_Forecast.xlsx")
                .score(0.88)
                .sourceName("Dropbox")
                .connectorId("dropbox")
                .repository("/Finance")
                .workspace("Finance Team")
                .tenantId("default-tenant")
                .owner("CFO Office")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
