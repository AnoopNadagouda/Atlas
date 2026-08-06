package com.atlas.reasoning.engine;

import com.atlas.common.dto.reasoning.ReasoningExecuteRequest;
import com.atlas.common.dto.reasoning.ReasoningResponse;
import com.atlas.domain.reasoning.ReasoningMode;
import com.atlas.domain.reasoning.ReasoningTrace;
import com.atlas.domain.reasoning.ThoughtNode;
import com.atlas.reasoning.integration.KafkaReasoningEventPublisher;
import com.atlas.reasoning.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

class ReasoningEngineTest {

    @Mock private TaskDecomposer taskDecomposer;
    @Mock private AlternativeGenerator alternativeGenerator;
    @Mock private DecisionEngine decisionEngine;
    @Mock private SelfReflectionEngine reflectionEngine;
    @Mock private CriticEngine criticEngine;
    @Mock private FailureAnalyzer failureAnalyzer;
    @Mock private RecoveryPlanner recoveryPlanner;
    @Mock private ThoughtGraphBuilder thoughtGraphBuilder;
    @Mock private ReasoningTraceStore traceStore;
    @Mock private ReasoningAnalyticsService analyticsService;
    @Mock private ConfidenceEstimator confidenceEstimator;
    @Mock private KafkaReasoningEventPublisher eventPublisher;

    private ReasoningEngine reasoningEngine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reasoningEngine = new ReasoningEngineImpl(
            taskDecomposer, alternativeGenerator, decisionEngine, reflectionEngine,
            criticEngine, failureAnalyzer, recoveryPlanner, thoughtGraphBuilder,
            traceStore, analyticsService, confidenceEstimator, eventPublisher
        );
    }

    @Test
    void testExecuteReasoning() {
        ReasoningExecuteRequest request = new ReasoningExecuteRequest();
        request.setGoalId("goal-mig-01");
        request.setMode(ReasoningMode.CHAIN_OF_THOUGHT);
        request.setAgentId("agent-reasoning-01");

        when(taskDecomposer.decomposeGoal(any(), any())).thenReturn(List.of("Step 1", "Step 2"));
        when(thoughtGraphBuilder.addThoughtNode(any(), any(), any(), anyDouble())).thenReturn(new ThoughtNode());
        when(traceStore.saveTrace(any())).thenReturn(new ReasoningTrace());

        ReasoningResponse response = reasoningEngine.executeReasoning("default-tenant", request);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("default-tenant", response.getTenantId());
        assertEquals(ReasoningMode.CHAIN_OF_THOUGHT, response.getMode());
        assertTrue(response.getOverallConfidence() > 0.8);
    }
}
