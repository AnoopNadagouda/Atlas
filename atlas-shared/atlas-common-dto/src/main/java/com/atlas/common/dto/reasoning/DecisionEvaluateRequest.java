package com.atlas.common.dto.reasoning;

import java.util.ArrayList;
import java.util.List;

public class DecisionEvaluateRequest {
    private String sessionId;
    private String decisionPoint;
    private List<String> options = new ArrayList<>();

    public DecisionEvaluateRequest() {
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getDecisionPoint() { return decisionPoint; }
    public void setDecisionPoint(String decisionPoint) { this.decisionPoint = decisionPoint; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}
