package com.atlas.reasoning.controller;

import com.atlas.common.dto.reasoning.*;
import com.atlas.domain.reasoning.*;
import com.atlas.reasoning.engine.ReasoningEngine;
import com.atlas.reasoning.service.GoalManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v22/reasoning")
@Tag(name = "Reasoning Engine API v22", description = "Enterprise AI Reasoning, Self-Reflection & Autonomous Decision Engine APIs")
public class ReasoningController {

    private final ReasoningEngine reasoningEngine;
    private final GoalManager goalManager;

    public ReasoningController(ReasoningEngine reasoningEngine, GoalManager goalManager) {
        this.reasoningEngine = reasoningEngine;
        this.goalManager = goalManager;
    }

    @PostMapping("/goals")
    @Operation(summary = "Formulate a new autonomous goal")
    public ResponseEntity<GoalEntity> createGoal(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody GoalCreateRequest request) {
        return ResponseEntity.ok(goalManager.createGoal(tenantId, request));
    }

    @GetMapping("/goals")
    @Operation(summary = "List goals for a tenant")
    public ResponseEntity<List<GoalEntity>> getGoals(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(goalManager.getGoals(tenantId));
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute autonomous multi-step reasoning session")
    public ResponseEntity<ReasoningResponse> executeReasoning(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody ReasoningExecuteRequest request) {
        return ResponseEntity.ok(reasoningEngine.executeReasoning(tenantId, request));
    }

    @PostMapping("/plan")
    @Operation(summary = "Generate goal execution plan steps")
    public ResponseEntity<List<String>> generatePlan(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody PlanGenerateRequest request) {
        return ResponseEntity.ok(reasoningEngine.generatePlan(tenantId, request));
    }

    @PostMapping("/alternatives")
    @Operation(summary = "Generate alternative candidate paths for decision making")
    public ResponseEntity<List<String>> generateAlternatives(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestParam("goalId") String goalId,
            @RequestParam(name = "context", required = false) String context) {
        return ResponseEntity.ok(reasoningEngine.generateAlternatives(tenantId, goalId, context));
    }

    @PostMapping("/evaluate-decision")
    @Operation(summary = "Evaluate and select optimal decision path")
    public ResponseEntity<DecisionRecord> evaluateDecision(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody DecisionEvaluateRequest request) {
        return ResponseEntity.ok(reasoningEngine.evaluateDecision(tenantId, request));
    }

    @PostMapping("/reflect")
    @Operation(summary = "Execute post-task self-reflection and extract lessons learned")
    public ResponseEntity<ReflectionRecord> reflectOnExecution(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody ReflectionRequest request) {
        return ResponseEntity.ok(reasoningEngine.reflectOnExecution(tenantId, request));
    }

    @PostMapping("/critique")
    @Operation(summary = "Critique output quality and detect hallucinations")
    public ResponseEntity<CritiqueRecord> critiqueExecution(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody CritiqueRequest request) {
        return ResponseEntity.ok(reasoningEngine.critiqueExecution(tenantId, request));
    }

    @PostMapping("/failure-analysis")
    @Operation(summary = "Analyze execution failure root cause")
    public ResponseEntity<Map<String, String>> failureAnalysis(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestParam(name = "sessionId", required = false) String sessionId,
            @RequestBody String errorLog) {
        String analysis = reasoningEngine.analyzeFailure(tenantId, errorLog, sessionId);
        return ResponseEntity.ok(Map.of("sessionId", sessionId != null ? sessionId : "none", "analysis", analysis));
    }

    @PostMapping("/recovery-plan")
    @Operation(summary = "Generate automatic failure recovery plan")
    public ResponseEntity<RecoveryPlan> recoveryPlan(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestBody RecoveryPlanRequest request) {
        return ResponseEntity.ok(reasoningEngine.generateRecoveryPlan(tenantId, request));
    }

    @GetMapping("/trace/{sessionId}")
    @Operation(summary = "Get step-by-step reasoning trace")
    public ResponseEntity<ReasoningTrace> getReasoningTrace(@PathVariable("sessionId") String sessionId) {
        return ResponseEntity.ok(reasoningEngine.getReasoningTrace(sessionId));
    }

    @GetMapping("/thought-graph/{sessionId}")
    @Operation(summary = "Get Thought Graph nodes for session")
    public ResponseEntity<List<ThoughtNode>> getThoughtGraph(@PathVariable("sessionId") String sessionId) {
        return ResponseEntity.ok(reasoningEngine.getThoughtGraph(sessionId));
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get aggregate reasoning platform analytics")
    public ResponseEntity<ReasoningAnalyticsResponse> getAnalytics(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(reasoningEngine.getAnalytics(tenantId));
    }

    @GetMapping("/decisions")
    @Operation(summary = "Get decision history for tenant")
    public ResponseEntity<List<DecisionRecord>> getDecisionHistory(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(reasoningEngine.getDecisionHistory(tenantId));
    }

    @GetMapping("/confidence-report")
    @Operation(summary = "Get confidence and risk report")
    public ResponseEntity<ConfidenceReportResponse> getConfidenceReport(
            @RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
            @RequestParam(name = "sessionId", required = false) String sessionId) {
        return ResponseEntity.ok(reasoningEngine.getConfidenceReport(tenantId, sessionId));
    }
}
