package com.atlas.memory.controller;

import com.atlas.common.dto.memory.*;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryAnalytics;
import com.atlas.domain.memory.MemoryRelation;
import com.atlas.domain.memory.MemorySearchResult;
import com.atlas.memory.engine.MemoryEngine;
import com.atlas.memory.service.MemoryRelationshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v21/memory")
@Tag(name = "Memory Platform API v21", description = "Enterprise AI Memory, Long-Term Context & Knowledge Learning Platform APIs")
public class MemoryController {

    private final MemoryEngine memoryEngine;
    private final MemoryRelationshipService relationshipService;

    public MemoryController(MemoryEngine memoryEngine, MemoryRelationshipService relationshipService) {
        this.memoryEngine = memoryEngine;
        this.relationshipService = relationshipService;
    }

    @PostMapping
    @Operation(summary = "Create a new memory entry")
    public ResponseEntity<MemoryResponse> createMemory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody MemoryCreateRequest request) {
        Memory memory = memoryEngine.createMemory(tenantId, request);
        return ResponseEntity.ok(mapToResponse(memory));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing memory entry")
    public ResponseEntity<MemoryResponse> updateMemory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @PathVariable("id") String id,
            @RequestBody MemoryUpdateRequest request) {
        Memory memory = memoryEngine.updateMemory(tenantId, id, request);
        return ResponseEntity.ok(mapToResponse(memory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a memory entry")
    public ResponseEntity<Map<String, String>> deleteMemory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @PathVariable("id") String id) {
        memoryEngine.deleteMemory(tenantId, id);
        return ResponseEntity.ok(Map.of("message", "Memory deleted successfully", "id", id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get memory by ID")
    public ResponseEntity<MemoryResponse> getMemory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @PathVariable("id") String id) {
        Memory memory = memoryEngine.getMemory(tenantId, id);
        return ResponseEntity.ok(mapToResponse(memory));
    }

    @GetMapping
    @Operation(summary = "List all memories for a tenant")
    public ResponseEntity<List<MemoryResponse>> listMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        List<MemoryResponse> list = memoryEngine.listMemories(tenantId).stream()
                .map(this::mapToResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/search")
    @Operation(summary = "Search memories semantically or lexically")
    public ResponseEntity<List<MemoryResponse>> searchMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody MemorySearchRequest request) {
        List<MemorySearchResult> results = memoryEngine.searchMemories(tenantId, request);
        List<MemoryResponse> list = results.stream().map(res -> {
            MemoryResponse resp = mapToResponse(res.getMemory());
            resp.setScore(res.getRelevanceScore());
            return resp;
        }).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/semantic-search")
    @Operation(summary = "Perform vector-based semantic search across agent memories")
    public ResponseEntity<List<MemoryResponse>> semanticSearch(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody MemorySearchRequest request) {
        return searchMemories(tenantId, request);
    }

    @GetMapping("/conversation/{conversationId}")
    @Operation(summary = "Get memory history for a specific conversation session")
    public ResponseEntity<List<MemoryResponse>> getConversationHistory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @PathVariable("conversationId") String conversationId) {
        List<MemoryResponse> list = memoryEngine.getConversationHistory(tenantId, conversationId).stream()
                .map(this::mapToResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/workflow/{workflowId}")
    @Operation(summary = "Get memory history for a specific workflow execution")
    public ResponseEntity<List<MemoryResponse>> getWorkflowHistory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @PathVariable("workflowId") String workflowId) {
        List<MemoryResponse> list = memoryEngine.getWorkflowHistory(tenantId, workflowId).stream()
                .map(this::mapToResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/consolidate")
    @Operation(summary = "Consolidate multiple memories into a summary memory")
    public ResponseEntity<MemoryResponse> consolidateMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody List<String> memoryIds) {
        Memory memory = memoryEngine.consolidateMemories(tenantId, memoryIds);
        return ResponseEntity.ok(mapToResponse(memory));
    }

    @PostMapping("/relationships")
    @Operation(summary = "Create relationship link between two memories")
    public ResponseEntity<MemoryRelation> createRelationship(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestParam("sourceId") String sourceId,
            @RequestParam("targetId") String targetId,
            @RequestParam(name = "relationType", defaultValue = "ASSOCIATED_WITH") String relationType,
            @RequestParam(name = "weight", defaultValue = "1.0") double weight) {
        MemoryRelation relation = relationshipService.createRelationship(tenantId, sourceId, targetId, relationType, weight);
        return ResponseEntity.ok(relation);
    }

    @PostMapping("/relation")
    @Operation(summary = "Link two memories in Knowledge Graph")
    public ResponseEntity<MemoryRelation> linkMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestParam("sourceId") String sourceId,
            @RequestParam("targetId") String targetId,
            @RequestParam(name = "relationType", defaultValue = "ASSOCIATED_WITH") String relationType,
            @RequestParam(name = "weight", defaultValue = "1.0") double weight) {
        return createRelationship(tenantId, sourceId, targetId, relationType, weight);
    }

    @GetMapping("/{id}/relationships")
    @Operation(summary = "Get relationship links for a memory")
    public ResponseEntity<List<MemoryRelation>> getRelationships(@PathVariable("id") String id) {
        return ResponseEntity.ok(relationshipService.getRelationships(id));
    }

    @GetMapping("/{id}/relations")
    @Operation(summary = "Get relations for a memory")
    public ResponseEntity<List<MemoryRelation>> getRelations(@PathVariable("id") String id) {
        return getRelationships(id);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get memory platform statistics")
    public ResponseEntity<MemoryAnalyticsResponse> getStatistics(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return getAnalytics(tenantId);
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get aggregate memory platform analytics")
    public ResponseEntity<MemoryAnalyticsResponse> getAnalytics(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        MemoryAnalytics analytics = memoryEngine.getAnalytics(tenantId);
        MemoryAnalyticsResponse response = new MemoryAnalyticsResponse();
        response.setTotalMemories(analytics.getTotalMemories());
        response.setActiveMemories(analytics.getActiveMemories());
        response.setConsolidatedMemories(analytics.getConsolidatedMemories());
        response.setDecayedMemories(analytics.getDecayedMemories());
        response.setAverageImportanceScore(analytics.getAverageImportanceScore());
        response.setCountByType(analytics.getCountByType());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/restore-context")
    @Operation(summary = "Restore cross-session context for an AI agent")
    public ResponseEntity<ContextRestorationResponse> restoreContext(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody ContextRestorationRequest request) {
        return ResponseEntity.ok(memoryEngine.restoreContext(tenantId, request));
    }

    @GetMapping("/export")
    @Operation(summary = "Export tenant memory data as JSON")
    public ResponseEntity<String> exportMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(memoryEngine.exportMemories(tenantId));
    }

    @PostMapping("/import")
    @Operation(summary = "Import tenant memory data from JSON")
    public ResponseEntity<Map<String, Object>> importMemories(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody String jsonData) {
        int imported = memoryEngine.importMemories(tenantId, jsonData);
        return ResponseEntity.ok(Map.of("message", "Memories imported successfully", "count", imported));
    }

    private MemoryResponse mapToResponse(Memory memory) {
        MemoryResponse response = new MemoryResponse();
        response.setId(memory.getId());
        response.setTenantId(memory.getTenantId());
        response.setAgentId(memory.getAgentId());
        response.setConversationId(memory.getConversationId());
        response.setWorkflowId(memory.getWorkflowId());
        response.setKey(memory.getKey());
        response.setContent(memory.getContent());
        response.setType(memory.getType());
        response.setState(memory.getState());
        response.setImportanceScore(memory.getImportanceScore());
        response.setDecayFactor(memory.getDecayFactor());
        response.setAccessCount(memory.getAccessCount());
        response.setCreatedAt(memory.getCreatedAt());
        response.setUpdatedAt(memory.getUpdatedAt());
        response.setLastAccessedAt(memory.getLastAccessedAt());
        response.setMetadata(memory.getMetadata());
        return response;
    }
}
