package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryType;

import java.util.List;

public interface MemoryRetriever {
    List<Memory> retrieveByConversationId(String tenantId, String conversationId);
    List<Memory> retrieveByWorkflowId(String tenantId, String workflowId);
    List<Memory> retrieveByAgentId(String tenantId, String agentId);
    List<Memory> retrieveByType(String tenantId, MemoryType type);
}
