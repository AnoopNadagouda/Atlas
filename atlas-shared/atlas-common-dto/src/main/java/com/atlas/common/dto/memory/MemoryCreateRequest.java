package com.atlas.common.dto.memory;

import com.atlas.domain.memory.MemoryType;

import java.util.Map;

public class MemoryCreateRequest {
    private String agentId;
    private String conversationId;
    private String workflowId;
    private String key;
    private String content;
    private MemoryType type;
    private double importanceScore;
    private Map<String, Object> metadata;

    public MemoryCreateRequest() {
        this.importanceScore = 0.5;
        this.type = MemoryType.SHORT_TERM;
    }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MemoryType getType() { return type; }
    public void setType(MemoryType type) { this.type = type; }

    public double getImportanceScore() { return importanceScore; }
    public void setImportanceScore(double importanceScore) { this.importanceScore = importanceScore; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
