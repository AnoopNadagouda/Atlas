package com.atlas.orchestrator.controller;

import com.atlas.common.dto.orchestrator.*;
import com.atlas.domain.orchestrator.*;
import com.atlas.orchestrator.engine.AtlasOrchestrator;
import com.atlas.orchestrator.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v23/orchestrator")
@Tag(name = "Atlas AIOS Orchestrator API", description = "Version 23 Endpoints for Atlas AI Operating System (AIOS) & Autonomous Orchestration")
public class OrchestratorController {

    private final MissionManager missionManager;
    private final AtlasOrchestrator atlasOrchestrator;
    private final AgentCoordinator agentCoordinator;
    private final GlobalStateManager globalStateManager;
    private final EnterpriseAutomationEngine automationEngine;
    private final PlatformHealthManager healthManager;
    private final ClusterCoordinator clusterCoordinator;

    public OrchestratorController(MissionManager missionManager,
                                  AtlasOrchestrator atlasOrchestrator,
                                  AgentCoordinator agentCoordinator,
                                  GlobalStateManager globalStateManager,
                                  EnterpriseAutomationEngine automationEngine,
                                  PlatformHealthManager healthManager,
                                  ClusterCoordinator clusterCoordinator) {
        this.missionManager = missionManager;
        this.atlasOrchestrator = atlasOrchestrator;
        this.agentCoordinator = agentCoordinator;
        this.globalStateManager = globalStateManager;
        this.automationEngine = automationEngine;
        this.healthManager = healthManager;
        this.clusterCoordinator = clusterCoordinator;
    }

    @PostMapping("/missions")
    @Operation(summary = "Create Mission", description = "Formulates a new autonomous enterprise mission")
    public ResponseEntity<Mission> createMission(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
                                                 @RequestBody MissionCreateRequest request) {
        return ResponseEntity.ok(missionManager.createMission(tenantId, request));
    }

    @GetMapping("/missions")
    @Operation(summary = "List Missions", description = "Retrieves all missions for the tenant")
    public ResponseEntity<List<Mission>> getMissions(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(missionManager.getMissions(tenantId));
    }

    @PostMapping("/missions/{missionId}/execute")
    @Operation(summary = "Execute Mission", description = "Executes an autonomous mission across AIOS platform components")
    public ResponseEntity<MissionExecution> executeMission(@PathVariable String missionId) {
        return ResponseEntity.ok(atlasOrchestrator.executeMission(missionId));
    }

    @PostMapping("/missions/{missionId}/pause")
    @Operation(summary = "Pause Mission", description = "Pauses an active mission execution")
    public ResponseEntity<Mission> pauseMission(@PathVariable String missionId) {
        return ResponseEntity.ok(atlasOrchestrator.pauseMission(missionId));
    }

    @PostMapping("/missions/{missionId}/resume")
    @Operation(summary = "Resume Mission", description = "Resumes a paused mission execution")
    public ResponseEntity<Mission> resumeMission(@PathVariable String missionId) {
        return ResponseEntity.ok(atlasOrchestrator.resumeMission(missionId));
    }

    @PostMapping("/missions/{missionId}/cancel")
    @Operation(summary = "Cancel Mission", description = "Cancels a mission execution")
    public ResponseEntity<Mission> cancelMission(@PathVariable String missionId) {
        return ResponseEntity.ok(atlasOrchestrator.cancelMission(missionId));
    }

    @GetMapping("/missions/{missionId}")
    @Operation(summary = "Get Mission Status", description = "Retrieves mission status and details by mission ID")
    public ResponseEntity<Mission> getMissionStatus(@PathVariable String missionId) {
        return ResponseEntity.ok(missionManager.getMissionById(missionId));
    }

    @GetMapping("/missions/{missionId}/timeline")
    @Operation(summary = "Mission Timeline", description = "Retrieves chronological execution timeline for a mission")
    public ResponseEntity<List<ExecutionTimeline>> getMissionTimeline(@PathVariable String missionId) {
        return ResponseEntity.ok(missionManager.getMissionTimeline(missionId));
    }

    @GetMapping("/missions/{missionId}/analytics")
    @Operation(summary = "Mission Analytics", description = "Retrieves execution analytics for a mission")
    public ResponseEntity<java.util.Map<String, Object>> getMissionAnalytics(@PathVariable String missionId) {
        return ResponseEntity.ok(missionManager.getMissionAnalytics(missionId));
    }

    @PostMapping(value = {"/missions/{missionId}/agents", "/agents/assign"})
    @Operation(summary = "Assign Agent", description = "Assigns an AI agent to an active mission")
    public ResponseEntity<AgentAssignment> assignAgent(@PathVariable(required = false) String missionId,
                                                      @RequestBody AgentAssignmentRequest request) {
        String targetMissionId = missionId != null ? missionId : request.getMissionId();
        return ResponseEntity.ok(agentCoordinator.assignAgent(targetMissionId, request.getAgentId(), request.getRole()));
    }

    @GetMapping("/health")
    @Operation(summary = "Platform Health", description = "Returns aggregated cluster health status across all 19 Atlas modules")
    public ResponseEntity<PlatformHealthResponse> getPlatformHealth() {
        return ResponseEntity.ok(healthManager.getHealth());
    }

    @GetMapping("/statistics")
    @Operation(summary = "Platform Statistics", description = "Returns platform execution statistics and telemetry metrics")
    public ResponseEntity<PlatformStatisticsResponse> getPlatformStatistics() {
        return ResponseEntity.ok(healthManager.getStatistics());
    }

    @GetMapping("/cluster")
    @Operation(summary = "Cluster Overview", description = "Returns multi-node topology and resource utilization overview")
    public ResponseEntity<ClusterOverviewResponse> getClusterOverview() {
        return ResponseEntity.ok(clusterCoordinator.getClusterOverview());
    }

    @PostMapping(value = {"/state", "/global-state"})
    @Operation(summary = "Set Global State", description = "Sets a key-value pair in cluster global state")
    public ResponseEntity<GlobalState> setGlobalState(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
                                                      @RequestParam String key,
                                                      @RequestParam String value) {
        return ResponseEntity.ok(globalStateManager.setState(tenantId, key, value));
    }

    @GetMapping(value = {"/state", "/global-state"})
    @Operation(summary = "Get Global State", description = "Retrieves global cluster state for tenant")
    public ResponseEntity<List<GlobalState>> getGlobalState(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(globalStateManager.getGlobalState(tenantId));
    }

    @PostMapping(value = {"/automation-jobs", "/automation/jobs"})
    @Operation(summary = "Schedule Automation Job", description = "Schedules an enterprise background automation job")
    public ResponseEntity<AutomationJob> scheduleJob(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId,
                                                     @RequestParam String jobName,
                                                     @RequestParam String cronExpression) {
        return ResponseEntity.ok(automationEngine.scheduleJob(tenantId, jobName, cronExpression));
    }

    @GetMapping(value = {"/automation-jobs", "/automation/jobs"})
    @Operation(summary = "List Automation Jobs", description = "Lists active automation jobs for tenant")
    public ResponseEntity<List<AutomationJob>> getJobs(@RequestHeader(value = "X-Tenant-ID", defaultValue = "default-tenant") String tenantId) {
        return ResponseEntity.ok(automationEngine.getJobs(tenantId));
    }
}

