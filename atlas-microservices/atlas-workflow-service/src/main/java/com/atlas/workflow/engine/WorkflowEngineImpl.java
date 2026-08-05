package com.atlas.workflow.engine;

import com.atlas.common.utils.JsonUtils;
import com.atlas.domain.workflow.*;
import com.atlas.workflow.entity.WorkflowDefinitionEntity;
import com.atlas.workflow.entity.WorkflowInstanceEntity;
import com.atlas.workflow.repository.WorkflowDefinitionRepository;
import com.atlas.workflow.repository.WorkflowInstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowEngineImpl implements WorkflowEngine {

    private final WorkflowDefinitionRepository definitionRepository;
    private final WorkflowInstanceRepository instanceRepository;
    private final WorkflowExecutor executor;
    private final DAGConverter dagConverter;
    private final HumanApprovalManager approvalManager;
    private final WorkflowScheduler scheduler;

    @Override
    public WorkflowDefinition createWorkflow(WorkflowDefinition definition) {
        if (definition.getId() == null) {
            definition.setId(UUID.randomUUID().toString());
        }
        if (definition.getState() == null) {
            definition.setState(WorkflowState.ACTIVE);
        }

        WorkflowDefinitionEntity entity = WorkflowDefinitionEntity.builder()
                .id(definition.getId())
                .name(definition.getName())
                .description(definition.getDescription())
                .version(definition.getVersion() != null ? definition.getVersion() : "1.0.0")
                .state(definition.getState().name())
                .definitionJson(JsonUtils.toJson(definition))
                .cronSchedule(definition.getCronSchedule())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        definitionRepository.save(entity);
        log.info("[WorkflowEngine] Saved Workflow Definition ID: '{}', Name: '{}'", definition.getId(), definition.getName());

        if (definition.getCronSchedule() != null) {
            scheduler.scheduleWorkflow(definition.getId(), definition.getCronSchedule());
        }

        return definition;
    }

    @Override
    public WorkflowDefinition validateWorkflow(String rawContent, boolean isYaml) {
        return dagConverter.parseDefinition(rawContent, isYaml);
    }

    @Override
    public WorkflowInstance executeWorkflow(String definitionId, Map<String, Object> inputValues) {
        Optional<WorkflowDefinitionEntity> opt = definitionRepository.findById(definitionId);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Workflow Definition not found: " + definitionId);
        }
        WorkflowDefinition def = JsonUtils.fromJson(opt.get().getDefinitionJson(), WorkflowDefinition.class);
        return executor.executeWorkflow(def, inputValues);
    }

    @Override
    public WorkflowResult pauseWorkflow(String instanceId) {
        return executor.pauseWorkflow(instanceId);
    }

    @Override
    public WorkflowResult resumeWorkflow(String instanceId) {
        return executor.resumeWorkflow(instanceId);
    }

    @Override
    public WorkflowResult cancelWorkflow(String instanceId) {
        return executor.cancelWorkflow(instanceId);
    }

    @Override
    public WorkflowResult restartWorkflow(String instanceId) {
        return executor.restartWorkflow(instanceId);
    }

    @Override
    public boolean processApproval(String instanceId, String stepId, boolean approved, String approverId, String comments) {
        return approvalManager.processApproval(instanceId, stepId, approved, approverId, comments);
    }

    @Override
    public WorkflowInstance getWorkflowStatus(String instanceId) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
        }
        WorkflowInstanceEntity e = opt.get();
        return WorkflowInstance.builder()
                .instanceId(e.getInstanceId())
                .definitionId(e.getDefinitionId())
                .state(WorkflowState.valueOf(e.getState()))
                .currentStepId(e.getCurrentStepId())
                .errorMessage(e.getErrorMessage())
                .startTime(e.getStartTime())
                .endTime(e.getEndTime())
                .executionTimeMs(e.getExecutionTimeMs())
                .build();
    }

    @Override
    public List<WorkflowInstance> getWorkflowHistory(String definitionId) {
        return instanceRepository.findByDefinitionId(definitionId).stream()
                .map(e -> WorkflowInstance.builder()
                        .instanceId(e.getInstanceId())
                        .definitionId(e.getDefinitionId())
                        .state(WorkflowState.valueOf(e.getState()))
                        .startTime(e.getStartTime())
                        .endTime(e.getEndTime())
                        .build())
                .toList();
    }

    @Override
    public Map<String, Object> getWorkflowStatistics() {
        long totalDefinitions = definitionRepository.count();
        long totalInstances = instanceRepository.count();
        long runningInstances = instanceRepository.findByState(WorkflowState.RUNNING.name()).size();
        long completedInstances = instanceRepository.findByState(WorkflowState.COMPLETED.name()).size();
        long failedInstances = instanceRepository.findByState(WorkflowState.FAILED.name()).size();

        return Map.of(
                "totalDefinitions", totalDefinitions,
                "totalInstances", totalInstances,
                "runningInstances", runningInstances,
                "completedInstances", completedInstances,
                "failedInstances", failedInstances,
                "successRate", totalInstances > 0 ? (double) completedInstances / totalInstances * 100.0 : 100.0
        );
    }
}
