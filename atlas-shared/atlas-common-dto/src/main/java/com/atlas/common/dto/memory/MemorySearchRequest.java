package com.atlas.common.dto.memory;

import com.atlas.domain.memory.MemoryType;

public class MemorySearchRequest {
    private String query;
    private String agentId;
    private String conversationId;
    private String workflowId;
    private MemoryType type;
    private int limit;
    private double minImportance;

    public MemorySearchRequest() {
        this.limit = 10;
        this.minImportance = 0.0;
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public MemoryType getType() { return type; }
    public void setType(MemoryType type) { this.type = type; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public double getMinImportance() { return minImportance; }
    public void setMinImportance(double minImportance) { this.minImportance = minImportance; }
}
