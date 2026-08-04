package com.atlas.keywordsearch.vector;

import com.atlas.common.utils.AtlasConstants;
import com.atlas.kafka.events.CleanedDocumentEvent;
import com.atlas.kafka.events.EmbeddedDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanedDocumentEmbeddingConsumer {

    private final EmbeddingService embeddingService;
    private final InMemHnswVectorStore vectorStore;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = AtlasConstants.TOPIC_DOCUMENT_CLEANED, groupId = "${spring.kafka.consumer.group-id:atlas-embedding-group}")
    public void consumeAndEmbed(CleanedDocumentEvent event) {
        if (event == null || event.getDocumentId() == null || event.isDuplicate()) {
            return;
        }

        log.info("Generating 384-dim dense vector embedding for docId: '{}', URL: {}", event.getDocumentId(), event.getNormalizedUrl());

        String textToEmbed = (event.getTitle() != null ? event.getTitle() + " " : "") +
                (event.getCleanText() != null ? event.getCleanText() : "");

        float[] embedding = embeddingService.generateEmbedding(textToEmbed);

        Map<String, Object> payload = Map.of(
                "title", event.getTitle() != null ? event.getTitle() : "",
                "url", event.getNormalizedUrl() != null ? event.getNormalizedUrl() : "",
                "language", event.getLanguage() != null ? event.getLanguage() : "en"
        );

        // Store vector in HNSW Vector Store
        vectorStore.storeVector(event.getDocumentId(), embedding, payload);

        // Publish EmbeddedDocumentEvent to Kafka
        EmbeddedDocumentEvent embeddedEvent = EmbeddedDocumentEvent.builder()
                .documentId(event.getDocumentId())
                .normalizedUrl(event.getNormalizedUrl())
                .modelName("all-MiniLM-L6-v2")
                .dimension(384)
                .vector(embedding)
                .metadata(payload)
                .embeddedAt(Instant.now())
                .build();

        try {
            kafkaTemplate.send(AtlasConstants.TOPIC_DOCUMENT_EMBEDDED, event.getDocumentId(), embeddedEvent);
            log.info("Published EmbeddedDocumentEvent for docId '{}' to Kafka topic '{}'", event.getDocumentId(), AtlasConstants.TOPIC_DOCUMENT_EMBEDDED);
        } catch (Exception e) {
            log.error("Failed to publish EmbeddedDocumentEvent: {}", e.getMessage());
        }
    }
}
