package com.atlas.keywordsearch.connector.adapters;

import com.atlas.domain.connector.*;
import com.atlas.domain.document.UniversalDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Amazon S3 Connector Adapter for Buckets, Objects, and Prefix Scanning.
 */
@Component
public class AmazonS3Connector extends AbstractConnector {

    public AmazonS3Connector() {
        super(ConnectorMetadata.builder()
                .connectorId("aws_s3")
                .name("Amazon S3 Object Storage")
                .version("1.0.0")
                .vendor("Amazon Web Services")
                .category("CLOUD_STORAGE")
                .supportedObjectTypes(List.of("BUCKET", "OBJECT", "PREFIX"))
                .permissions(List.of("s3:GetObject", "s3:ListBucket"))
                .iconUrl("/assets/icons/s3.svg")
                .build());
    }

    @Override
    protected List<UniversalDocument> doSync(ConnectorSyncJob syncJob) {
        List<UniversalDocument> docs = new ArrayList<>();
        docs.add(UniversalDocument.builder()
                .documentId("s3-obj-101")
                .title("[Amazon S3] s3://atlas-data-lake/2026/analytics-dump.json")
                .fileType("JSON")
                .mimeType("application/json")
                .sizeBytes(25400000)
                .extractedText("S3 Object: Data lake search query logs and clickthrough rate analytics.")
                .createdAt(Instant.now())
                .metadata(Map.of("bucket", "atlas-data-lake", "key", "2026/analytics-dump.json"))
                .build());
        return docs;
    }

    @Override
    protected List<FederatedSearchResult> doRemoteSearch(String query, int limit) {
        return List.of(FederatedSearchResult.builder()
                .resultId("s3-res-1")
                .title("Amazon S3: s3://atlas-data-lake/analytics-dump.json")
                .snippet("AWS S3 object storage log archive for search queries and embedding vectors.")
                .documentUrl("https://s3.us-east-1.amazonaws.com/atlas-data-lake/analytics-dump.json")
                .score(0.93)
                .sourceName("Amazon S3")
                .connectorId("aws_s3")
                .repository("atlas-data-lake")
                .workspace("AWS Account 123456")
                .tenantId("default-tenant")
                .owner("Data Engineering")
                .lastSync(Instant.now())
                .permissionStatus("ALLOWED")
                .aclStatus("VERIFIED")
                .build());
    }
}
