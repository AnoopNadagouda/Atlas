package com.atlas.common.dto.orchestrator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MissionResponse {
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private String status;
    private int priority;
    private double progressPercentage;
    private List<String> steps = new ArrayList<>();
    private List<String> assignedAgentIds = new ArrayList<>();
    private Instant createdAt;

    public MissionResponse() {
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

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public List<String> getAssignedAgentIds() { return assignedAgentIds; }
    public void setAssignedAgentIds(List<String> assignedAgentIds) { this.assignedAgentIds = assignedAgentIds; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
