package com.atlas.domain.orchestrator;

import java.time.Instant;

public class PolicyRule {
    private String id;
    private String ruleName;
    private String category; // SECURITY, RESOURCE, GOVERNANCE, COMPLIANCE
    private String conditionExpression;
    private String action; // ALLOW, DENY, AUDIT, THROTTLE
    private boolean enabled;
    private Instant createdAt;

    public PolicyRule() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getConditionExpression() { return conditionExpression; }
    public void setConditionExpression(String conditionExpression) { this.conditionExpression = conditionExpression; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
