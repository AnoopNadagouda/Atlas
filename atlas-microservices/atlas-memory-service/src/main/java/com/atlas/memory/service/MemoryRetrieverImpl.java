package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryType;
import com.atlas.memory.entity.MemoryEntity;
import com.atlas.memory.repository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryRetrieverImpl implements MemoryRetriever {

    private final MemoryRepository memoryRepository;

    public MemoryRetrieverImpl(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public List<Memory> retrieveByConversationId(String tenantId, String conversationId) {
        return memoryRepository.findByTenantIdAndConversationId(tenantId, conversationId)
                .stream().map(this::mapToDomain).toList();
    }

    @Override
    public List<Memory> retrieveByWorkflowId(String tenantId, String workflowId) {
        return memoryRepository.findByTenantIdAndWorkflowId(tenantId, workflowId)
                .stream().map(this::mapToDomain).toList();
    }

    @Override
    public List<Memory> retrieveByAgentId(String tenantId, String agentId) {
        return memoryRepository.findByTenantIdAndAgentId(tenantId, agentId)
                .stream().map(this::mapToDomain).toList();
    }

    @Override
    public List<Memory> retrieveByType(String tenantId, MemoryType type) {
        return memoryRepository.findByTenantIdAndType(tenantId, type)
                .stream().map(this::mapToDomain).toList();
    }

    private Memory mapToDomain(MemoryEntity entity) {
        Memory memory = new Memory();
        memory.setId(entity.getId());
        memory.setTenantId(entity.getTenantId());
        memory.setAgentId(entity.getAgentId());
        memory.setConversationId(entity.getConversationId());
        memory.setWorkflowId(entity.getWorkflowId());
        memory.setKey(entity.getKey());
        memory.setContent(entity.getContent());
        memory.setType(entity.getType());
        memory.setState(entity.getState());
        memory.setImportanceScore(entity.getImportanceScore());
        memory.setDecayFactor(entity.getDecayFactor());
        memory.setAccessCount(entity.getAccessCount());
        memory.setCreatedAt(entity.getCreatedAt());
        memory.setUpdatedAt(entity.getUpdatedAt());
        memory.setLastAccessedAt(entity.getLastAccessedAt());
        memory.setExpiresAt(entity.getExpiresAt());
        return memory;
    }
}
