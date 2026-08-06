package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_mission_steps")
public class MissionStepEntity {

    @Id
    private String id;

    @Column(name = "mission_id", nullable = false)
    private String missionId;

    @Column(name = "step_name", nullable = false)
    private String stepName;

    @Column(name = "target_service")
    private String targetService;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "step_order")
    private int stepOrder;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public MissionStepEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getStepName() { return stepName; }
    public void setStepName(String stepName) { this.stepName = stepName; }

    public String getTargetService() { return targetService; }
    public void setTargetService(String targetService) { this.targetService = targetService; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getStepOrder() { return stepOrder; }
    public void setStepOrder(int stepOrder) { this.stepOrder = stepOrder; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
