package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_agent_assignments")
public class AgentAssignmentEntity {

    @Id
    private String id;

    @Column(name = "mission_id", nullable = false)
    private String missionId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    public AgentAssignmentEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Instant assignedAt) { this.assignedAt = assignedAt; }
}
