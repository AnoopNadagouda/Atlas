package com.atlas.parserservice.pipeline;

import com.atlas.common.utils.AtlasConstants;
import com.atlas.kafka.events.CleanedDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanedDocumentProducer {

    private final KafkaTemplate<String, CleanedDocumentEvent> kafkaTemplate;

    public void publishCleanedDocument(CleanedDocumentEvent event) {
        if (event == null || event.getNormalizedUrl() == null) return;

        log.info("Publishing clean document to Kafka topic '{}': {}", AtlasConstants.TOPIC_DOCUMENT_CLEANED, event.getNormalizedUrl());
        kafkaTemplate.send(AtlasConstants.TOPIC_DOCUMENT_CLEANED, event.getNormalizedUrl(), event);
        kafkaTemplate.send(AtlasConstants.TOPIC_CRAWL_CLEANED_DOCUMENTS, event.getNormalizedUrl(), event);
    }
}
