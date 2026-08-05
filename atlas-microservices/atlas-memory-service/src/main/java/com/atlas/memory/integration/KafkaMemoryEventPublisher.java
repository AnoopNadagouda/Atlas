package com.atlas.memory.integration;

import com.atlas.kafka.events.memory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMemoryEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaMemoryEventPublisher.class);
    private static final String TOPIC_MEMORY_EVENTS = "memory.events.lifecycle";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaMemoryEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(MemoryCreatedEvent event) {
        publish(event.memoryId(), event);
    }

    public void publishUpdated(MemoryUpdatedEvent event) {
        publish(event.memoryId(), event);
    }

    public void publishDeleted(MemoryDeletedEvent event) {
        publish(event.memoryId(), event);
    }

    public void publishRetrieved(MemoryRetrievedEvent event) {
        publish(event.memoryId(), event);
    }

    public void publishConsolidated(MemoryConsolidatedEvent event) {
        publish(event.consolidatedMemoryId(), event);
    }

    public void publishExpired(MemoryExpiredEvent event) {
        publish(event.memoryId(), event);
    }

    public void publishImported(MemoryImportedEvent event) {
        publish(event.eventId(), event);
    }

    public void publishExported(MemoryExportedEvent event) {
        publish(event.eventId(), event);
    }

    private void publish(String key, Object event) {
        try {
            kafkaTemplate.send(TOPIC_MEMORY_EVENTS, key, event);
            log.debug("[KafkaMemoryPublisher] Published event type: {} with key: {}", event.getClass().getSimpleName(), key);
        } catch (Exception e) {
            log.warn("[KafkaMemoryPublisher] Failed to publish memory event: {}", e.getMessage());
        }
    }
}
