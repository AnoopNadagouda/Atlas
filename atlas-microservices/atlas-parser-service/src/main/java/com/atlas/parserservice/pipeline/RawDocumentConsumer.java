package com.atlas.parserservice.pipeline;

import com.atlas.common.utils.AtlasConstants;
import com.atlas.kafka.events.RawDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RawDocumentConsumer {

    private final DocumentCleanerPipeline pipeline;

    @KafkaListener(topics = AtlasConstants.TOPIC_CRAWL_RAW_DOCUMENTS, groupId = "${spring.kafka.consumer.group-id:atlas-parser-group}")
    public void consumeRawDocument(RawDocumentEvent rawEvent) {
        log.info("Kafka Consumer received RawDocumentEvent for URL: {}", rawEvent != null ? rawEvent.getNormalizedUrl() : "null");
        if (rawEvent != null) {
            pipeline.processRawDocument(rawEvent);
        }
    }
}
