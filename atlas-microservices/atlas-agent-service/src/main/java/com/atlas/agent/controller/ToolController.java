package com.atlas.agent.controller;

import com.atlas.agent.tool.executor.ToolExecutor;
import com.atlas.agent.tool.registry.ToolRegistry;
import com.atlas.domain.agent.tool.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/v1/tools", "/api/v18/tools"})
@RequiredArgsConstructor
@Tag(name = "Tool SDK Management API", description = "Endpoints for discovering, validating, and executing Agent Tools.")
public class ToolController {

    private final ToolRegistry toolRegistry;
    private final ToolExecutor toolExecutor;

    @GetMapping
    @Operation(summary = "List registered Agent Tools", description = "Retrieves metadata for all registered tools, optionally filtered by category.")
    public ResponseEntity<List<ToolMetadata>> listTools(@RequestParam(required = false) ToolCategory category) {
        Collection<AgentTool> tools = (category != null) ? toolRegistry.getToolsByCategory(category) : toolRegistry.getAllTools();
        List<ToolMetadata> metadataList = tools.stream()
                .map(AgentTool::getMetadata)
                .collect(Collectors.toList());
        return ResponseEntity.ok(metadataList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Tool Metadata by ID", description = "Retrieves full metadata and schema definition for a specific tool ID.")
    public ResponseEntity<ToolMetadata> getToolById(@PathVariable String id) {
        return toolRegistry.getTool(id)
                .map(tool -> ResponseEntity.ok(tool.getMetadata()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute Agent Tool", description = "Validates input arguments and executes a tool with permission and timeout checks.")
    public ResponseEntity<ToolExecutionResult> executeTool(@RequestBody ToolExecutionRequest request) {
        // Default caller permission if omitted for testing
        if (request.getCallerPermissions() == null || request.getCallerPermissions().isEmpty()) {
            request.setCallerPermissions(Set.of(ToolPermission.SEARCH_EXECUTE, ToolPermission.CRAWL_EXECUTE, ToolPermission.PARSE_EXECUTE, ToolPermission.SYSTEM_EXECUTE, ToolPermission.ADMIN_EXECUTE));
        }
        ToolExecutionResult result = toolExecutor.executeTool(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories")
    @Operation(summary = "List Tool Categories", description = "Returns all available functional categories for Agent Tools.")
    public ResponseEntity<ToolCategory[]> listCategories() {
        return ResponseEntity.ok(ToolCategory.values());
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get Tool Statistics", description = "Returns total registered tool counts and enabled tool status counts.")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTools", toolRegistry.getToolCount());
        stats.put("categories", ToolCategory.values().length);
        stats.put("health", toolRegistry.checkHealth());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/health")
    @Operation(summary = "Tool Health Status", description = "Returns individual health check statuses for every registered tool.")
    public ResponseEntity<Map<String, Boolean>> getHealth() {
        return ResponseEntity.ok(toolRegistry.checkHealth());
    }
}
