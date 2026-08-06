package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class AutomationCompletedEvent {
    private String eventId;
    private String jobId;
    private String tenantId;
    private String jobName;
    private Instant timestamp;

    public AutomationCompletedEvent() {
    }

    public AutomationCompletedEvent(String eventId, String jobId, String tenantId, String jobName) {
        this.eventId = eventId;
        this.jobId = jobId;
        this.tenantId = tenantId;
        this.jobName = jobName;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
