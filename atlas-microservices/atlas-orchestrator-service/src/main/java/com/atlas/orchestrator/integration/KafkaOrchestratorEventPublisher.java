package com.atlas.orchestrator.integration;

import com.atlas.kafka.events.orchestrator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaOrchestratorEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaOrchestratorEventPublisher.class);
    private static final String TOPIC = "orchestrator.events.lifecycle";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaOrchestratorEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMissionCreated(String missionId, String tenantId, String title) {
        MissionCreatedEvent event = new MissionCreatedEvent(UUID.randomUUID().toString(), missionId, tenantId, title);
        send(event);
    }

    public void publishMissionStarted(String missionId, String tenantId) {
        MissionStartedEvent event = new MissionStartedEvent(UUID.randomUUID().toString(), missionId, tenantId);
        send(event);
    }

    public void publishMissionCompleted(String missionId, String tenantId, double durationMs) {
        MissionCompletedEvent event = new MissionCompletedEvent(UUID.randomUUID().toString(), missionId, tenantId, durationMs);
        send(event);
    }

    public void publishMissionFailed(String missionId, String tenantId, String failureReason) {
        MissionFailedEvent event = new MissionFailedEvent(UUID.randomUUID().toString(), missionId, tenantId, failureReason);
        send(event);
    }

    public void publishMissionPaused(String missionId, String tenantId) {
        MissionPausedEvent event = new MissionPausedEvent(UUID.randomUUID().toString(), missionId, tenantId);
        send(event);
    }

    public void publishMissionResumed(String missionId, String tenantId) {
        MissionResumedEvent event = new MissionResumedEvent(UUID.randomUUID().toString(), missionId, tenantId);
        send(event);
    }

    public void publishAgentAssigned(String missionId, String agentId, String role) {
        AgentAssignedEvent event = new AgentAssignedEvent(UUID.randomUUID().toString(), missionId, agentId, role);
        send(event);
    }

    public void publishTaskDispatched(String taskId, String targetService, String action) {
        TaskDispatchedEvent event = new TaskDispatchedEvent(UUID.randomUUID().toString(), taskId, targetService, action);
        send(event);
    }

    public void publishRecoveryTriggered(String missionId, String tenantId, String recoveryStrategy) {
        RecoveryTriggeredEvent event = new RecoveryTriggeredEvent(UUID.randomUUID().toString(), missionId, tenantId, recoveryStrategy);
        send(event);
    }

    public void publishAutomationCompleted(String jobId, String tenantId, String resultStatus) {
        AutomationCompletedEvent event = new AutomationCompletedEvent(UUID.randomUUID().toString(), jobId, tenantId, resultStatus);
        send(event);
    }

    private void send(Object event) {
        try {
            kafkaTemplate.send(TOPIC, event);
            log.info("[KafkaOrchestratorEventPublisher] Published event '{}' to topic '{}'", event.getClass().getSimpleName(), TOPIC);
        } catch (Exception e) {
            log.warn("[KafkaOrchestratorEventPublisher] Exception publishing event to Kafka: {}", e.getMessage());
        }
    }
}

