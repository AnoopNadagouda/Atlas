package com.atlas.reasoning.engine;

import com.atlas.common.dto.reasoning.*;
import com.atlas.domain.reasoning.*;

import java.util.List;

public interface ReasoningEngine {
    ReasoningResponse executeReasoning(String tenantId, ReasoningExecuteRequest request);
    List<String> generatePlan(String tenantId, PlanGenerateRequest request);
    List<String> generateAlternatives(String tenantId, String goalId, String context);
    DecisionRecord evaluateDecision(String tenantId, DecisionEvaluateRequest request);
    ReflectionRecord reflectOnExecution(String tenantId, ReflectionRequest request);
    CritiqueRecord critiqueExecution(String tenantId, CritiqueRequest request);
    String analyzeFailure(String tenantId, String errorLog, String sessionId);
    RecoveryPlan generateRecoveryPlan(String tenantId, RecoveryPlanRequest request);
    ReasoningTrace getReasoningTrace(String sessionId);
    List<ThoughtNode> getThoughtGraph(String sessionId);
    ReasoningAnalyticsResponse getAnalytics(String tenantId);
    List<DecisionRecord> getDecisionHistory(String tenantId);
    ConfidenceReportResponse getConfidenceReport(String tenantId, String sessionId);
}
