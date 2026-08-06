package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GoalEntity {
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private int priority; // 1 (Highest) - 5 (Lowest)
    private String status; // PENDING, IN_PROGRESS, COMPLETED, FAILED
    private List<String> targetCriteria = new ArrayList<>();
    private Instant createdAt;

    public GoalEntity() {
    }

    public GoalEntity(String id, String tenantId, String title, String description, int priority, String status, List<String> targetCriteria) {
        this.id = id;
        this.tenantId = tenantId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.targetCriteria = targetCriteria;
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

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getTargetCriteria() { return targetCriteria; }
    public void setTargetCriteria(List<String> targetCriteria) { this.targetCriteria = targetCriteria; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
