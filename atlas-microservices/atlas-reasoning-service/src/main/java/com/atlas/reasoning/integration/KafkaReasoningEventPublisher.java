package com.atlas.reasoning.integration;

import com.atlas.kafka.events.reasoning.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaReasoningEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaReasoningEventPublisher.class);
    private static final String TOPIC_REASONING_EVENTS = "reasoning.events.lifecycle";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaReasoningEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStarted(ReasoningStartedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishCompleted(ReasoningCompletedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishDecisionGenerated(DecisionGeneratedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishReflectionCompleted(ReflectionCompletedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishCritiqueGenerated(CritiqueGeneratedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishRecoveryExecuted(RecoveryExecutedEvent event) {
        publish(event.getSessionId(), event);
    }

    public void publishGoalCompleted(GoalCompletedEvent event) {
        publish(event.getGoalId(), event);
    }

    public void publishGoalFailed(GoalFailedEvent event) {
        publish(event.getGoalId(), event);
    }

    private void publish(String key, Object event) {
        try {
            kafkaTemplate.send(TOPIC_REASONING_EVENTS, key, event);
            log.debug("[KafkaReasoningPublisher] Published event: {} key: {}", event.getClass().getSimpleName(), key);
        } catch (Exception e) {
            log.warn("[KafkaReasoningPublisher] Failed to publish reasoning event: {}", e.getMessage());
        }
    }
}
