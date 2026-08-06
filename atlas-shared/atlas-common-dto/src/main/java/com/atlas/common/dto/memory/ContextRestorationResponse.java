package com.atlas.common.dto.memory;

import java.util.List;

public class ContextRestorationResponse {
    private String agentId;
    private String conversationId;
    private String workflowId;
    private List<MemoryResponse> memories;
    private String restoredSummary;

    public ContextRestorationResponse() {}

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public List<MemoryResponse> getMemories() { return memories; }
    public void setMemories(List<MemoryResponse> memories) { this.memories = memories; }

    public String getRestoredSummary() { return restoredSummary; }
    public void setRestoredSummary(String restoredSummary) { this.restoredSummary = restoredSummary; }
}
