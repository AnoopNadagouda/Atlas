package com.atlas.common.dto.reasoning;

import java.util.ArrayList;
import java.util.List;

public class PlanGenerateRequest {
    private String goalDescription;
    private List<String> constraints = new ArrayList<>();

    public PlanGenerateRequest() {
    }

    public String getGoalDescription() { return goalDescription; }
    public void setGoalDescription(String goalDescription) { this.goalDescription = goalDescription; }

    public List<String> getConstraints() { return constraints; }
    public void setConstraints(List<String> constraints) { this.constraints = constraints; }
}
