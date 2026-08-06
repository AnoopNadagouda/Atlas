package com.atlas.domain.orchestrator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private String status; // CREATED, IN_PROGRESS, PAUSED, COMPLETED, FAILED, CANCELLED
    private int priority;
    private double progressPercentage;
    private List<MissionStep> steps = new ArrayList<>();
    private List<AgentAssignment> assignedAgents = new ArrayList<>();
    private Instant createdAt;
    private Instant completedAt;

    public Mission() {
    }

    public Mission(String id, String tenantId, String title, String description, String status, int priority) {
        this.id = id;
        this.tenantId = tenantId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.progressPercentage = 0.0;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }

    public List<MissionStep> getSteps() { return steps; }
    public void setSteps(List<MissionStep> steps) { this.steps = steps; }

    public List<AgentAssignment> getAssignedAgents() { return assignedAgents; }
    public void setAssignedAgents(List<AgentAssignment> assignedAgents) { this.assignedAgents = assignedAgents; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
}
