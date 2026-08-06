package com.atlas.domain.orchestrator;

import java.time.Instant;

public class MissionCheckpoint {
    private String id;
    private String missionId;
    private int completedSteps;
    private String stateSnapshot;
    private Instant createdAt;

    public MissionCheckpoint() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public int getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(int completedSteps) { this.completedSteps = completedSteps; }

    public String getStateSnapshot() { return stateSnapshot; }
    public void setStateSnapshot(String stateSnapshot) { this.stateSnapshot = stateSnapshot; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
