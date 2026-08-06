package com.atlas.common.dto.orchestrator;

import java.util.ArrayList;
import java.util.List;

public class MissionCreateRequest {
    private String title;
    private String description;
    private int priority = 1;
    private List<String> targetObjectives = new ArrayList<>();

    public MissionCreateRequest() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public List<String> getTargetObjectives() { return targetObjectives; }
    public void setTargetObjectives(List<String> targetObjectives) { this.targetObjectives = targetObjectives; }
}
