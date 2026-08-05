package com.atlas.common.dto.memory;

import com.atlas.domain.memory.MemoryState;
import com.atlas.domain.memory.MemoryType;

import java.util.Map;

public class MemoryUpdateRequest {
    private String content;
    private MemoryType type;
    private MemoryState state;
    private Double importanceScore;
    private Map<String, Object> metadata;

    public MemoryUpdateRequest() {}

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MemoryType getType() { return type; }
    public void setType(MemoryType type) { this.type = type; }

    public MemoryState getState() { return state; }
    public void setState(MemoryState state) { this.state = state; }

    public Double getImportanceScore() { return importanceScore; }
    public void setImportanceScore(Double importanceScore) { this.importanceScore = importanceScore; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
