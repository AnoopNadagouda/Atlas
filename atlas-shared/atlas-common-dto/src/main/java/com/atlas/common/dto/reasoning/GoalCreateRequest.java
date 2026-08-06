package com.atlas.common.dto.reasoning;

import java.util.ArrayList;
import java.util.List;

public class GoalCreateRequest {
    private String title;
    private String description;
    private int priority = 3;
    private List<String> targetCriteria = new ArrayList<>();

    public GoalCreateRequest() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public List<String> getTargetCriteria() { return targetCriteria; }
    public void setTargetCriteria(List<String> targetCriteria) { this.targetCriteria = targetCriteria; }
}
