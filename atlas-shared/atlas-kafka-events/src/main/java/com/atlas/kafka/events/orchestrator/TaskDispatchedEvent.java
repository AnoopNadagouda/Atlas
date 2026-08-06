package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class TaskDispatchedEvent {
    private String eventId;
    private String taskId;
    private String targetService;
    private Instant timestamp;

    public TaskDispatchedEvent() {
    }

    public TaskDispatchedEvent(String eventId, String taskId, String targetService) {
        this.eventId = eventId;
        this.taskId = taskId;
        this.targetService = targetService;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getTargetService() { return targetService; }
    public void setTargetService(String targetService) { this.targetService = targetService; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
