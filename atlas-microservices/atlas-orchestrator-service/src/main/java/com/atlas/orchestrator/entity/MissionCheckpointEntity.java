package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_mission_checkpoints")
public class MissionCheckpointEntity {

    @Id
    private String id;

    @Column(name = "mission_id", nullable = false)
    private String missionId;

    @Column(name = "completed_steps")
    private int completedSteps;

    @Column(name = "state_snapshot", length = 4096)
    private String stateSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public MissionCheckpointEntity() {
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
