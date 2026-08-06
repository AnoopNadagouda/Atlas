package com.atlas.common.dto.memory;

public class ContextRestorationRequest {
    private String agentId;
    private String conversationId;
    private String workflowId;
    private String query;
    private int maxItems = 10;

    public ContextRestorationRequest() {}

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public int getMaxItems() { return maxItems; }
    public void setMaxItems(int maxItems) { this.maxItems = maxItems; }
}
