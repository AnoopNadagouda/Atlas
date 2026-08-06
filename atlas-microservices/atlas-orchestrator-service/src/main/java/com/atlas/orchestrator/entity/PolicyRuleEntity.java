package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_policy_rules")
public class PolicyRuleEntity {

    @Id
    private String id;

    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @Column(name = "category")
    private String category;

    @Column(name = "condition_expression", length = 1024)
    private String conditionExpression;

    @Column(name = "action")
    private String action;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public PolicyRuleEntity() {
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
