package com.atlas.memory.service;

import com.atlas.common.dto.memory.ContextRestorationRequest;
import com.atlas.common.dto.memory.ContextRestorationResponse;
import com.atlas.common.dto.memory.MemoryResponse;
import com.atlas.common.dto.memory.MemorySearchRequest;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemorySearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemorySyncServiceImpl implements MemorySyncService {

    private static final Logger log = LoggerFactory.getLogger(MemorySyncServiceImpl.class);

    private final MemoryStore memoryStore;
    private final MemorySearchService searchService;

    public MemorySyncServiceImpl(MemoryStore memoryStore, MemorySearchService searchService) {
        this.memoryStore = memoryStore;
        this.searchService = searchService;
    }

    @Override
    public ContextRestorationResponse restoreContext(String tenantId, ContextRestorationRequest request) {
        log.info("[MemorySyncService] Restoring cross-session context for agentId '{}', conversationId '{}'", request.getAgentId(), request.getConversationId());
        
        List<Memory> memories = new ArrayList<>();
        if (request.getConversationId() != null) {
            memories.addAll(memoryStore.findMemoriesByConversation(request.getConversationId()));
        }
        if (request.getWorkflowId() != null) {
            memories.addAll(memoryStore.findMemoriesByWorkflow(request.getWorkflowId()));
        }
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            MemorySearchRequest searchReq = new MemorySearchRequest();
            searchReq.setQuery(request.getQuery());
            searchReq.setAgentId(request.getAgentId());
            searchReq.setLimit(request.getMaxItems());
            List<MemorySearchResult> results = searchService.searchMemories(tenantId, searchReq);
            for (MemorySearchResult res : results) {
                if (memories.stream().noneMatch(m -> m.getId().equals(res.getMemory().getId()))) {
                    memories.add(res.getMemory());
                }
            }
        }

        List<MemoryResponse> responses = memories.stream().map(m -> {
            MemoryResponse resp = new MemoryResponse();
            resp.setId(m.getId());
            resp.setTenantId(m.getTenantId());
            resp.setAgentId(m.getAgentId());
            resp.setConversationId(m.getConversationId());
            resp.setWorkflowId(m.getWorkflowId());
            resp.setKey(m.getKey());
            resp.setContent(m.getContent());
            resp.setType(m.getType());
            resp.setState(m.getState());
            resp.setImportanceScore(m.getImportanceScore());
            resp.setCreatedAt(m.getCreatedAt());
            resp.setUpdatedAt(m.getUpdatedAt());
            return resp;
        }).collect(Collectors.toList());

        String summary = "Restored " + responses.size() + " memories for context.";

        ContextRestorationResponse response = new ContextRestorationResponse();
        response.setAgentId(request.getAgentId());
        response.setConversationId(request.getConversationId());
        response.setWorkflowId(request.getWorkflowId());
        response.setMemories(responses);
        response.setRestoredSummary(summary);
        return response;
    }

    @Override
    public void syncSessionState(String tenantId, String sessionId) {
        log.info("[MemorySyncService] Syncing session state for tenantId '{}', sessionId '{}'", tenantId, sessionId);
    }
}
