package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Microsoft Teams Connector Adapter for Teams, Channels, and Chat Messages.
 */
@Component
public class TeamsConnector extends AbstractConnector {

    public TeamsConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("teams")
                .name("Microsoft Teams")
                .version("1.0.0")
                .vendor("Microsoft")
                .category("CHAT_COLLABORATION")
                .supportedObjectTypes(List.of("TEAM", "CHANNEL", "CHAT_MESSAGE"))
                .permissions(List.of("ChannelMessage.Read.All"))
                .iconUrl("/assets/icons/teams.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("teams-msg-19")
                .title("[MS Teams] Architecture Channel: Release 5.4 Federation Sync")
                .fileType("TXT")
                .mimeType("text/plain")
                .sizeBytes(2200)
                .extractedText("MS Teams Message: Syncing external connectors with UniversalDocument pipeline.")
                .createdAt(Instant.now())
                .metadata(Map.of("team", "Search Org", "channel", "Architecture"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("teams-res-1")
                .title("Microsoft Teams: Release 5.4 Architecture Sync")
                .snippet("MS Teams chat thread covering external knowledge connector sync schedules.")
                .documentUrl("https://teams.microsoft.com/l/message/19:channel123")
                .score(0.90)
                .sourceName("Microsoft Teams")
                .connectorId("teams")
                .repository("Architecture Channel")
                .workspace("Search Org")
                .tenantId("default-tenant")
                .owner("Dev Lead")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
