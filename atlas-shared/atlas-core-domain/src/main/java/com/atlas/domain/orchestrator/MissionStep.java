package com.atlas.domain.orchestrator;

import java.time.Instant;

public class MissionStep {
    private String id;
    private String missionId;
    private String stepName;
    private String targetService;
    private String status; // PENDING, RUNNING, COMPLETED, FAILED, SKIPPED
    private int stepOrder;
    private Instant createdAt;

    public MissionStep() {
    }

    public MissionStep(String id, String missionId, String stepName, String targetService, int stepOrder) {
        this.id = id;
        this.missionId = missionId;
        this.stepName = stepName;
        this.targetService = targetService;
        this.status = "PENDING";
        this.stepOrder = stepOrder;
        this.createdAt = Instant.now();
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
