package com.atlas.workflow.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaWorkflowEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${atlas.kafka.topics.workflow-events:workflow.events.execution}")
    private String topicName;

    public void publishEvent(String eventType, String instanceId, Object eventPayload) {
        try {
            log.info("[WorkflowEvent] Publishing event '{}' for instance: {}", eventType, instanceId);
            kafkaTemplate.send(topicName, instanceId, eventPayload);
        } catch (Exception e) {
            log.warn("[WorkflowEvent] Failed to publish event '{}' to Kafka: {}", eventType, e.getMessage());
        }
    }
}
