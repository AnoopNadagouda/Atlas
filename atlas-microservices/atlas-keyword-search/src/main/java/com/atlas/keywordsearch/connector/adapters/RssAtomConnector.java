package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RSS/Atom Feed Connector Adapter.
 */
@Component
public class RssAtomConnector extends AbstractConnector {

    public RssAtomConnector() {
        super(ConnectorMetadata.builder()
                .connectorId("rss_atom")
                .name("RSS & Atom News Feeds")
                .version("1.0.0")
                .vendor("Standard Web Feed")
                .category("WIKI_DOCUMENTATION")
                .supportedObjectTypes(List.of("FEED_ITEM", "ARTICLE"))
                .permissions(List.of("public:read"))
                .iconUrl("/assets/icons/rss.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("rss-item-12")
                .title("[RSS Feed] Tech Blog: The Future of Cloud-Native Enterprise Search")
                .fileType("HTML")
                .mimeType("text/html")
                .sizeBytes(4200)
                .extractedText("RSS Feed Entry: Analysis of modern search engines using BM25 and vector embeddings.")
                .createdAt(Instant.now())
                .metadata(Map.of("feedUrl", "https://blog.company.com/rss.xml"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("rss-res-1")
                .title("RSS Feed: Future of Cloud-Native Enterprise Search")
                .snippet("RSS Atom feed article discussing parallel hybrid search architectures.")
                .documentUrl("https://blog.company.com/2026/08/future-of-search")
                .score(0.87)
                .sourceName("RSS/Atom Feed")
                .connectorId("rss_atom")
                .repository("Tech Blog Feed")
                .workspace("Public Feeds")
                .tenantId("default-tenant")
                .owner("Content Team")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
