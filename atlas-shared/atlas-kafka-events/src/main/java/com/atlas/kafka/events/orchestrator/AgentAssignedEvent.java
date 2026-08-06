package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class AgentAssignedEvent {
    private String eventId;
    private String missionId;
    private String agentId;
    private String role;
    private Instant timestamp;

    public AgentAssignedEvent() {
    }

    public AgentAssignedEvent(String eventId, String missionId, String agentId, String role) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.agentId = agentId;
        this.role = role;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
