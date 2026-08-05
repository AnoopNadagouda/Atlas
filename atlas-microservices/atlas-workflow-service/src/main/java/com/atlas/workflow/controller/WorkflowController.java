package com.atlas.workflow.controller;

import com.atlas.common.dto.workflow.*;
import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowInstance;
import com.atlas.domain.workflow.WorkflowResult;
import com.atlas.workflow.engine.WorkflowEngine;
import com.atlas.workflow.entity.WorkflowTemplateEntity;
import com.atlas.workflow.repository.WorkflowTemplateRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v20/workflows")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Workflow Automation API", description = "Enterprise AI Workflow Automation Platform REST Endpoints")
public class WorkflowController {

    private final WorkflowEngine workflowEngine;
    private final WorkflowTemplateRepository templateRepository;

    @PostMapping
    @Operation(summary = "Create Workflow Definition", description = "Register a new workflow definition topology")
    public ResponseEntity<WorkflowDefinition> createWorkflow(@RequestBody WorkflowCreateRequest request) {
        log.info("[WorkflowController] REST POST /api/v20/workflows - Name: {}", request.getName());
        WorkflowDefinition def = WorkflowDefinition.builder()
                .name(request.getName())
                .description(request.getDescription())
                .version(request.getVersion() != null ? request.getVersion() : "1.0.0")
                .cronSchedule(request.getCronSchedule())
                .inputParameters(request.getInputParameters())
                .build();
        return ResponseEntity.ok(workflowEngine.createWorkflow(def));
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate Workflow DAG", description = "Validate YAML or JSON workflow DAG representation")
    public ResponseEntity<WorkflowDefinition> validateWorkflow(@RequestBody String rawContent,
                                                               @RequestParam(name = "isYaml", defaultValue = "false") boolean isYaml) {
        log.info("[WorkflowController] REST POST /api/v20/workflows/validate (YAML: {})", isYaml);
        return ResponseEntity.ok(workflowEngine.validateWorkflow(rawContent, isYaml));
    }

    @PostMapping("/{id}/execute")
    @Operation(summary = "Execute Workflow", description = "Trigger a new execution instance of a workflow definition")
    public ResponseEntity<WorkflowInstance> executeWorkflow(@PathVariable("id") String id,
                                                             @RequestBody(required = false) WorkflowExecuteRequest request) {
        log.info("[WorkflowController] REST POST /api/v20/workflows/{}/execute", id);
        Map<String, Object> inputVals = request != null ? request.getInputValues() : Map.of();
        return ResponseEntity.ok(workflowEngine.executeWorkflow(id, inputVals));
    }

    @PostMapping("/instances/{instanceId}/pause")
    @Operation(summary = "Pause Workflow Instance", description = "Pause an active workflow execution instance")
    public ResponseEntity<WorkflowResult> pauseWorkflow(@PathVariable("instanceId") String instanceId) {
        return ResponseEntity.ok(workflowEngine.pauseWorkflow(instanceId));
    }

    @PostMapping("/instances/{instanceId}/resume")
    @Operation(summary = "Resume Workflow Instance", description = "Resume a paused workflow execution instance")
    public ResponseEntity<WorkflowResult> resumeWorkflow(@PathVariable("instanceId") String instanceId) {
        return ResponseEntity.ok(workflowEngine.resumeWorkflow(instanceId));
    }

    @PostMapping("/instances/{instanceId}/cancel")
    @Operation(summary = "Cancel Workflow Instance", description = "Cancel an active workflow execution instance")
    public ResponseEntity<WorkflowResult> cancelWorkflow(@PathVariable("instanceId") String instanceId) {
        return ResponseEntity.ok(workflowEngine.cancelWorkflow(instanceId));
    }

    @PostMapping("/instances/{instanceId}/restart")
    @Operation(summary = "Restart Workflow Instance", description = "Restart a failed or cancelled workflow instance")
    public ResponseEntity<WorkflowResult> restartWorkflow(@PathVariable("instanceId") String instanceId) {
        return ResponseEntity.ok(workflowEngine.restartWorkflow(instanceId));
    }

    @PostMapping("/approval")
    @Operation(summary = "Process Human Approval", description = "Approve or reject a workflow step waiting for approval")
    public ResponseEntity<Map<String, Object>> processApproval(@RequestBody WorkflowApprovalRequest request) {
        boolean result = workflowEngine.processApproval(request.getInstanceId(), request.getStepId(),
                request.isApproved(), request.getApproverId(), request.getComments());
        return ResponseEntity.ok(Map.of("success", result, "instanceId", request.getInstanceId(), "stepId", request.getStepId()));
    }

    @GetMapping("/instances/{instanceId}/status")
    @Operation(summary = "Get Workflow Status", description = "Retrieve current state and output values for a workflow instance")
    public ResponseEntity<WorkflowStatusResponse> getStatus(@PathVariable("instanceId") String instanceId) {
        WorkflowInstance inst = workflowEngine.getWorkflowStatus(instanceId);
        return ResponseEntity.ok(WorkflowStatusResponse.builder()
                .instanceId(inst.getInstanceId())
                .definitionId(inst.getDefinitionId())
                .state(inst.getState().name())
                .currentStepId(inst.getCurrentStepId())
                .errorMessage(inst.getErrorMessage())
                .startTime(inst.getStartTime())
                .endTime(inst.getEndTime())
                .executionTimeMs(inst.getExecutionTimeMs())
                .build());
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get Workflow History", description = "Retrieve list of all execution instances for a workflow definition")
    public ResponseEntity<List<WorkflowInstance>> getHistory(@PathVariable("id") String id) {
        return ResponseEntity.ok(workflowEngine.getWorkflowHistory(id));
    }

    @GetMapping("/{id}/graph")
    @Operation(summary = "Get Workflow Graph", description = "Retrieve DAG topology graph for visualization")
    public ResponseEntity<Map<String, Object>> getGraph(@PathVariable("id") String id) {
        return ResponseEntity.ok(Map.of("definitionId", id, "graphType", "DAG", "nodes", List.of()));
    }

    @GetMapping("/templates")
    @Operation(summary = "List Workflow Templates", description = "List reusable enterprise workflow templates")
    public ResponseEntity<List<WorkflowTemplateEntity>> getTemplates() {
        return ResponseEntity.ok(templateRepository.findAll());
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get Workflow Statistics", description = "Get aggregate system statistics for workflow executions")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(workflowEngine.getWorkflowStatistics());
    }
}
