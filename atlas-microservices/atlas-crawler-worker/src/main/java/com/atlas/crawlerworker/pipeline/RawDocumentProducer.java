package com.atlas.crawlerworker.pipeline;

import com.atlas.common.utils.AtlasConstants;
import com.atlas.kafka.events.RawDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RawDocumentProducer {

    private final KafkaTemplate<String, RawDocumentEvent> kafkaTemplate;

    public void publishRawDocument(RawDocumentEvent event) {
        if (event == null || event.getNormalizedUrl() == null) return;
        
        log.info("Publishing raw HTML document to Kafka topic '{}': {}", AtlasConstants.TOPIC_CRAWL_RAW_DOCUMENTS, event.getNormalizedUrl());
        kafkaTemplate.send(AtlasConstants.TOPIC_CRAWL_RAW_DOCUMENTS, event.getNormalizedUrl(), event);
    }
}
