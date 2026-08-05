package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Slack Connector Adapter for Channels, Messages, and Threads.
 */
@Component
public class SlackConnector extends AbstractConnector {

    public SlackConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("slack")
                .name("Slack Enterprise Grid")
                .version("1.0.0")
                .vendor("Salesforce / Slack")
                .category("CHAT_COLLABORATION")
                .supportedObjectTypes(List.of("CHANNEL", "MESSAGE", "THREAD"))
                .permissions(List.of("channels:history", "groups:history"))
                .iconUrl("/assets/icons/slack.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("slack-msg-301")
                .title("[Slack] #proj-atlas: Discussion on 1B doc index partitioning")
                .fileType("TXT")
                .mimeType("text/plain")
                .sizeBytes(1800)
                .extractedText("Slack Thread: Recommendation to use 64 disk segment partitions for sub-10ms BM25 scoring.")
                .createdAt(Instant.now())
                .metadata(Map.of("channel", "#proj-atlas", "user", "U99120", "ts", "1722800000.001"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("slack-res-1")
                .title("Slack #proj-atlas: Shard Partitioning Thread")
                .snippet("Slack conversation regarding disk segment partitioning and low-latency hybrid search tuning.")
                .documentUrl("https://company.slack.com/archives/C12345/p1722800000")
                .score(0.95)
                .sourceName("Slack")
                .connectorId("slack")
                .repository("#proj-atlas")
                .workspace("Engineering Slack")
                .tenantId("default-tenant")
                .owner("Lead Search Engineer")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
