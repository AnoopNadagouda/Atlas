package com.atlas.workflow.engine;

import com.atlas.domain.workflow.StepType;
import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowInstance;
import com.atlas.domain.workflow.WorkflowState;
import com.atlas.domain.workflow.WorkflowStep;
import com.atlas.workflow.entity.WorkflowInstanceEntity;
import com.atlas.workflow.integration.AgentServiceClient;
import com.atlas.workflow.integration.KafkaWorkflowEventPublisher;
import com.atlas.workflow.repository.WorkflowEventRepository;
import com.atlas.workflow.repository.WorkflowInstanceRepository;
import com.atlas.workflow.repository.WorkflowStepRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkflowExecutorTest {

    private WorkflowInstanceRepository instanceRepository;
    private WorkflowStepRepository stepRepository;
    private WorkflowEventRepository eventRepository;
    private KafkaWorkflowEventPublisher eventPublisher;
    private AgentServiceClient agentServiceClient;
    private CheckpointManager checkpointManager;
    private MeterRegistry meterRegistry;
    private Counter counter;

    private WorkflowExecutorImpl executor;

    @BeforeEach
    void setUp() {
        instanceRepository = mock(WorkflowInstanceRepository.class);
        stepRepository = mock(WorkflowStepRepository.class);
        eventRepository = mock(WorkflowEventRepository.class);
        eventPublisher = mock(KafkaWorkflowEventPublisher.class);
        agentServiceClient = mock(AgentServiceClient.class);
        checkpointManager = mock(CheckpointManager.class);
        meterRegistry = mock(MeterRegistry.class);
        counter = mock(Counter.class);

        when(meterRegistry.counter(any(), any(String[].class))).thenReturn(counter);

        executor = new WorkflowExecutorImpl(
                instanceRepository, stepRepository, eventRepository, eventPublisher,
                agentServiceClient, checkpointManager, meterRegistry
        );
    }

    @Test
    void testExecuteWorkflowInitiation() {
        WorkflowDefinition def = WorkflowDefinition.builder()
                .id("def-001")
                .name("Simple Workflow")
                .steps(List.of(
                        WorkflowStep.builder().stepId("s1").type(StepType.SEQUENTIAL).build()
                ))
                .build();

        WorkflowInstance inst = executor.executeWorkflow(def, Map.of("param", "val"));
        assertNotNull(inst);
        assertNotNull(inst.getInstanceId());
        assertEquals("def-001", inst.getDefinitionId());
        assertEquals(WorkflowState.RUNNING, inst.getState());

        verify(instanceRepository, atLeastOnce()).save(any());
        verify(eventPublisher, atLeastOnce()).publishEvent(eq("WorkflowStarted"), any(), any());
    }

    @Test
    void testPauseWorkflow() {
        WorkflowInstanceEntity entity = WorkflowInstanceEntity.builder()
                .instanceId("inst-1")
                .definitionId("def-1")
                .state(WorkflowState.RUNNING.name())
                .build();

        when(instanceRepository.findById("inst-1")).thenReturn(Optional.of(entity));

        var res = executor.pauseWorkflow("inst-1");
        assertEquals(WorkflowState.PAUSED, res.getState());
        verify(instanceRepository).save(any());
    }
}
