package com.atlas.indexbuilder.pipeline;

import com.atlas.common.utils.AtlasConstants;
import com.atlas.kafka.events.CleanedDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanedDocumentConsumer {

    private final IndexCoordinatorService coordinatorService;

    @KafkaListener(topics = AtlasConstants.TOPIC_DOCUMENT_CLEANED, groupId = "${spring.kafka.consumer.group-id:atlas-indexer-group}")
    public void consumeCleanedDocument(CleanedDocumentEvent event) {
        log.info("Kafka Indexer Consumer received CleanedDocumentEvent for URL: {}", event != null ? event.getNormalizedUrl() : "null");
        if (event != null) {
            coordinatorService.indexDocument(event);
        }
    }
}
