package com.atlas.workflow.engine;

import com.atlas.common.utils.JsonUtils;
import com.atlas.domain.workflow.*;
import com.atlas.kafka.events.workflow.*;
import com.atlas.workflow.entity.WorkflowEventEntity;
import com.atlas.workflow.entity.WorkflowInstanceEntity;
import com.atlas.workflow.entity.WorkflowStepEntity;
import com.atlas.workflow.integration.AgentServiceClient;
import com.atlas.workflow.integration.KafkaWorkflowEventPublisher;
import com.atlas.workflow.repository.WorkflowEventRepository;
import com.atlas.workflow.repository.WorkflowInstanceRepository;
import com.atlas.workflow.repository.WorkflowStepRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowExecutorImpl implements WorkflowExecutor {

    private final WorkflowInstanceRepository instanceRepository;
    private final WorkflowStepRepository stepRepository;
    private final WorkflowEventRepository eventRepository;
    private final KafkaWorkflowEventPublisher eventPublisher;
    private final AgentServiceClient agentServiceClient;
    private final CheckpointManager checkpointManager;
    private final MeterRegistry meterRegistry;

    private final ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public WorkflowInstance executeWorkflow(WorkflowDefinition definition, Map<String, Object> inputValues) {
        String instanceId = UUID.randomUUID().toString();
        Instant startTime = Instant.now();

        log.info("[WorkflowExecutor] Starting execution for Workflow Definition ID: '{}', Instance ID: '{}'", definition.getId(), instanceId);

        WorkflowInstanceEntity entity = WorkflowInstanceEntity.builder()
                .instanceId(instanceId)
                .definitionId(definition.getId())
                .state(WorkflowState.RUNNING.name())
                .inputValuesJson(JsonUtils.toJson(inputValues != null ? inputValues : Map.of()))
                .startTime(startTime)
                .build();
        instanceRepository.save(entity);

        recordHistory(instanceId, null, "WorkflowStarted", "Workflow instance execution started");
        eventPublisher.publishEvent("WorkflowStarted", instanceId, WorkflowStartedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .instanceId(instanceId)
                .definitionId(definition.getId())
                .inputValues(inputValues)
                .timestamp(startTime)
                .build());

        meterRegistry.counter("atlas.workflow.executions.total", "definitionId", definition.getId()).increment();

        WorkflowContext context = new WorkflowContext(instanceId, definition.getId(), inputValues);

        CompletableFuture.runAsync(() -> {
            try {
                runDAGTopology(definition, instanceId, context);
            } catch (Exception e) {
                log.error("[WorkflowExecutor] Fatal error in workflow instance: {}", instanceId, e);
                failInstance(instanceId, e.getMessage());
            }
        }, virtualExecutor);

        return WorkflowInstance.builder()
                .instanceId(instanceId)
                .definitionId(definition.getId())
                .state(WorkflowState.RUNNING)
                .inputValues(inputValues)
                .startTime(startTime)
                .build();
    }

    private void runDAGTopology(WorkflowDefinition definition, String instanceId, WorkflowContext context) throws Exception {
        List<WorkflowStep> steps = definition.getSteps();
        if (steps == null || steps.isEmpty()) {
            completeInstance(instanceId, context.getAllVariables(), 0);
            return;
        }

        Map<String, WorkflowStep> stepMap = new HashMap<>();
        Map<String, Set<String>> dependencies = new HashMap<>();
        Map<String, Set<String>> dependents = new HashMap<>();

        for (WorkflowStep s : steps) {
            stepMap.put(s.getStepId(), s);
            dependencies.put(s.getStepId(), new HashSet<>(s.getDependsOn() != null ? s.getDependsOn() : Collections.emptyList()));
            dependents.putIfAbsent(s.getStepId(), new HashSet<>());
        }

        for (WorkflowStep s : steps) {
            if (s.getDependsOn() != null) {
                for (String dep : s.getDependsOn()) {
                    dependents.computeIfAbsent(dep, k -> new HashSet<>()).add(s.getStepId());
                }
            }
        }

        Set<String> completed = ConcurrentHashMap.newKeySet();
        Set<String> inProgress = ConcurrentHashMap.newKeySet();

        while (completed.size() < steps.size()) {
            Optional<WorkflowInstanceEntity> checkOpt = instanceRepository.findById(instanceId);
            if (checkOpt.isPresent() && WorkflowState.valueOf(checkOpt.get().getState()).isTerminal()) {
                log.info("[WorkflowExecutor] Workflow instance '{}' transitioned to terminal state: {}", instanceId, checkOpt.get().getState());
                return;
            }

            List<WorkflowStep> readySteps = new ArrayList<>();
            for (WorkflowStep s : steps) {
                String sId = s.getStepId();
                if (!completed.contains(sId) && !inProgress.contains(sId)) {
                    Set<String> deps = dependencies.get(sId);
                    if (deps == null || deps.isEmpty() || completed.containsAll(deps)) {
                        readySteps.add(s);
                    }
                }
            }

            if (readySteps.isEmpty() && inProgress.isEmpty()) {
                throw new IllegalStateException("Deadlock detected in DAG execution for instance: " + instanceId);
            }

            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (WorkflowStep step : readySteps) {
                inProgress.add(step.getStepId());
                futures.add(CompletableFuture.runAsync(() -> {
                    executeSingleStep(instanceId, step, context);
                    completed.add(step.getStepId());
                    inProgress.remove(step.getStepId());
                }, virtualExecutor));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        completeInstance(instanceId, context.getAllVariables(), System.currentTimeMillis());
    }

    private void executeSingleStep(String instanceId, WorkflowStep step, WorkflowContext context) {
        String stepId = step.getStepId();
        Instant stepStart = Instant.now();

        log.info("[WorkflowExecutor] Executing Step: '{}' (Type: {}) for Instance: '{}'", stepId, step.getType(), instanceId);

        WorkflowStepEntity stepEntity = WorkflowStepEntity.builder()
                .id(UUID.randomUUID().toString())
                .instanceId(instanceId)
                .stepId(stepId)
                .stepName(step.getName() != null ? step.getName() : stepId)
                .stepType(step.getType().name())
                .status(StepStatus.RUNNING.name())
                .retryCount(0)
                .startTime(stepStart)
                .build();
        stepRepository.save(stepEntity);

        recordHistory(instanceId, stepId, "StepStarted", "Step execution started");
        eventPublisher.publishEvent("StepStarted", instanceId, StepStartedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .instanceId(instanceId)
                .stepId(stepId)
                .stepName(step.getName())
                .stepType(step.getType().name())
                .timestamp(stepStart)
                .build());

        checkpointManager.saveCheckpoint(context, stepId, WorkflowState.RUNNING);

        try {
            switch (step.getType()) {
                case SEQUENTIAL, PARALLEL -> {
                    context.setStepResult(stepId, Map.of("status", "SUCCESS", "step", stepId));
                }
                case CONDITION -> {
                    boolean cond = step.getConditionExpression() == null || Boolean.parseBoolean(step.getConditionExpression());
                    context.setVariable("cond_" + stepId, cond);
                    context.setStepResult(stepId, Map.of("conditionMet", cond));
                }
                case LOOP -> {
                    context.setStepResult(stepId, Map.of("loopCount", 1, "completed", true));
                }
                case HUMAN_APPROVAL -> {
                    stepEntity.setStatus(StepStatus.WAITING_FOR_APPROVAL.name());
                    stepRepository.save(stepEntity);
                    
                    Optional<WorkflowInstanceEntity> instOpt = instanceRepository.findById(instanceId);
                    if (instOpt.isPresent()) {
                        WorkflowInstanceEntity instance = instOpt.get();
                        instance.setState(WorkflowState.WAITING_FOR_APPROVAL.name());
                        instance.setCurrentStepId(stepId);
                        instanceRepository.save(instance);
                    }
                    recordHistory(instanceId, stepId, "WaitingForApproval", "Step requires human approval by role: " + step.getApprovalRole());
                    return;
                }
                case AGENT_EXECUTION -> {
                    Map<String, Object> res = agentServiceClient.executeAgent(step.getAgentId(), step.getParameters());
                    context.setStepResult(stepId, res);
                }
                case CONNECTOR_EXECUTION -> {
                    context.setStepResult(stepId, Map.of("connectorId", step.getConnectorId(), "status", "CONNECTED"));
                }
                case EVENT_WAIT -> {
                    context.setStepResult(stepId, Map.of("eventReceived", true));
                }
            }

            Instant stepEnd = Instant.now();
            long duration = stepEnd.toEpochMilli() - stepStart.toEpochMilli();

            stepEntity.setStatus(StepStatus.COMPLETED.name());
            stepEntity.setEndTime(stepEnd);
            stepEntity.setDurationMs(duration);
            stepEntity.setResultJson(JsonUtils.toJson(context.getStepResult(stepId)));
            stepRepository.save(stepEntity);

            recordHistory(instanceId, stepId, "StepCompleted", "Step completed in " + duration + " ms");
            eventPublisher.publishEvent("StepCompleted", instanceId, StepCompletedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .instanceId(instanceId)
                    .stepId(stepId)
                    .durationMs(duration)
                    .timestamp(stepEnd)
                    .build());

        } catch (Exception e) {
            log.error("[WorkflowExecutor] Step '{}' failed: {}", stepId, e.getMessage());
            meterRegistry.counter("atlas.workflow.step.failures", "stepType", step.getType().name()).increment();

            stepEntity.setStatus(StepStatus.FAILED.name());
            stepEntity.setErrorMessage(e.getMessage());
            stepEntity.setEndTime(Instant.now());
            stepRepository.save(stepEntity);

            recordHistory(instanceId, stepId, "StepFailed", "Step failed: " + e.getMessage());
            eventPublisher.publishEvent("StepFailed", instanceId, StepFailedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .instanceId(instanceId)
                    .stepId(stepId)
                    .errorMessage(e.getMessage())
                    .retryCount(stepEntity.getRetryCount())
                    .timestamp(Instant.now())
                    .build());

            throw new RuntimeException("Step execution failed: " + stepId, e);
        }
    }

    private void completeInstance(String instanceId, Map<String, Object> outputs, long startTimeMs) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.COMPLETED.name());
            inst.setOutputValuesJson(JsonUtils.toJson(outputs));
            inst.setEndTime(Instant.now());
            inst.setExecutionTimeMs(inst.getEndTime().toEpochMilli() - inst.getStartTime().toEpochMilli());
            instanceRepository.save(inst);

            recordHistory(instanceId, null, "WorkflowCompleted", "Workflow completed successfully");
            eventPublisher.publishEvent("WorkflowCompleted", instanceId, WorkflowCompletedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .instanceId(instanceId)
                    .definitionId(inst.getDefinitionId())
                    .outputValues(outputs)
                    .executionTimeMs(inst.getExecutionTimeMs())
                    .timestamp(Instant.now())
                    .build());
        }
    }

    private void failInstance(String instanceId, String error) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.FAILED.name());
            inst.setErrorMessage(error);
            inst.setEndTime(Instant.now());
            instanceRepository.save(inst);

            recordHistory(instanceId, null, "WorkflowFailed", "Workflow failed: " + error);
            eventPublisher.publishEvent("WorkflowFailed", instanceId, WorkflowFailedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .instanceId(instanceId)
                    .definitionId(inst.getDefinitionId())
                    .errorMessage(error)
                    .timestamp(Instant.now())
                    .build());
        }
    }

    private void recordHistory(String instanceId, String stepId, String eventType, String details) {
        WorkflowEventEntity evt = WorkflowEventEntity.builder()
                .id(UUID.randomUUID().toString())
                .instanceId(instanceId)
                .stepId(stepId)
                .eventType(eventType)
                .details(details)
                .timestamp(Instant.now())
                .build();
        eventRepository.save(evt);
    }

    @Override
    public WorkflowResult pauseWorkflow(String instanceId) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.PAUSED.name());
            instanceRepository.save(inst);
            recordHistory(instanceId, null, "WorkflowPaused", "Workflow paused by operator");
            return WorkflowResult.builder().instanceId(instanceId).state(WorkflowState.PAUSED).build();
        }
        throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
    }

    @Override
    public WorkflowResult resumeWorkflow(String instanceId) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.RUNNING.name());
            instanceRepository.save(inst);
            recordHistory(instanceId, null, "WorkflowResumed", "Workflow resumed by operator");
            return WorkflowResult.builder().instanceId(instanceId).state(WorkflowState.RUNNING).build();
        }
        throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
    }

    @Override
    public WorkflowResult cancelWorkflow(String instanceId) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.CANCELLED.name());
            inst.setEndTime(Instant.now());
            instanceRepository.save(inst);
            recordHistory(instanceId, null, "WorkflowCancelled", "Workflow cancelled by user");
            return WorkflowResult.builder().instanceId(instanceId).state(WorkflowState.CANCELLED).build();
        }
        throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
    }

    @Override
    public WorkflowResult restartWorkflow(String instanceId) {
        Optional<WorkflowInstanceEntity> opt = instanceRepository.findById(instanceId);
        if (opt.isPresent()) {
            WorkflowInstanceEntity inst = opt.get();
            inst.setState(WorkflowState.RUNNING.name());
            inst.setStartTime(Instant.now());
            inst.setEndTime(null);
            inst.setErrorMessage(null);
            instanceRepository.save(inst);
            recordHistory(instanceId, null, "WorkflowRestarted", "Workflow restarted from initial state");
            return WorkflowResult.builder().instanceId(instanceId).state(WorkflowState.RUNNING).build();
        }
        throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
    }
}
