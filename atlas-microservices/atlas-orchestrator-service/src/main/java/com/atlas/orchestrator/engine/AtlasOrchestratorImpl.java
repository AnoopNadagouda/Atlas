package com.atlas.orchestrator.engine;

import com.atlas.domain.orchestrator.Mission;
import com.atlas.domain.orchestrator.MissionExecution;
import com.atlas.orchestrator.entity.MissionExecutionEntity;
import com.atlas.orchestrator.integration.KafkaOrchestratorEventPublisher;
import com.atlas.orchestrator.repository.MissionExecutionRepository;
import com.atlas.orchestrator.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AtlasOrchestratorImpl implements AtlasOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(AtlasOrchestratorImpl.class);

    private final MissionManager missionManager;
    private final ObjectivePlanner objectivePlanner;
    private final GlobalScheduler globalScheduler;
    private final AgentCoordinator agentCoordinator;
    private final ResourceAllocator resourceAllocator;
    private final ExecutionSupervisor executionSupervisor;
    private final PolicyEngine policyEngine;
    private final GovernanceManager governanceManager;
    private final ConflictResolver conflictResolver;
    private final DistributedTaskDispatcher taskDispatcher;
    private final GlobalStateManager globalStateManager;
    private final AutonomousRecoveryManager recoveryManager;
    private final MissionExecutionRepository executionRepository;
    private final KafkaOrchestratorEventPublisher eventPublisher;

    public AtlasOrchestratorImpl(MissionManager missionManager,
                                 ObjectivePlanner objectivePlanner,
                                 GlobalScheduler globalScheduler,
                                 AgentCoordinator agentCoordinator,
                                 ResourceAllocator resourceAllocator,
                                 ExecutionSupervisor executionSupervisor,
                                 PolicyEngine policyEngine,
                                 GovernanceManager governanceManager,
                                 ConflictResolver conflictResolver,
                                 DistributedTaskDispatcher taskDispatcher,
                                 GlobalStateManager globalStateManager,
                                 AutonomousRecoveryManager recoveryManager,
                                 MissionExecutionRepository executionRepository,
                                 KafkaOrchestratorEventPublisher eventPublisher) {
        this.missionManager = missionManager;
        this.objectivePlanner = objectivePlanner;
        this.globalScheduler = globalScheduler;
        this.agentCoordinator = agentCoordinator;
        this.resourceAllocator = resourceAllocator;
        this.executionSupervisor = executionSupervisor;
        this.policyEngine = policyEngine;
        this.governanceManager = governanceManager;
        this.conflictResolver = conflictResolver;
        this.taskDispatcher = taskDispatcher;
        this.globalStateManager = globalStateManager;
        this.recoveryManager = recoveryManager;
        this.executionRepository = executionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public MissionExecution executeMission(String missionId) {
        log.info("[AtlasOrchestrator] Executing autonomous enterprise mission '{}' across AIOS platform", missionId);
        long start = System.currentTimeMillis();

        Mission mission = missionManager.updateMissionStatus(missionId, "IN_PROGRESS");
        governanceManager.auditAction(mission.getTenantId(), "AIOS_ORCHESTRATOR", "EXECUTE_MISSION", missionId);

        policyEngine.validatePolicies(mission.getTenantId(), "EXECUTE_MISSION");
        objectivePlanner.planObjectives(mission.getDescription());
        resourceAllocator.balanceResources();

        globalScheduler.scheduleTask(missionId, "task-" + UUID.randomUUID().toString().substring(0, 8), mission.getPriority());
        agentCoordinator.assignAgent(missionId, "agent-primary", "LEAD_ORCHESTRATOR");
        conflictResolver.resolveConflicts(missionId);

        taskDispatcher.dispatchTask("step-1", "atlas-agent-service", "EXECUTE");
        taskDispatcher.dispatchTask("step-2", "atlas-reasoning-service", "REASON");
        taskDispatcher.dispatchTask("step-3", "atlas-workflow-service", "TRIGGER_DAG");
        taskDispatcher.dispatchTask("step-4", "atlas-memory-service", "CONSOLIDATE");

        executionSupervisor.superviseExecution(missionId);

        long end = System.currentTimeMillis();
        double duration = end - start;

        missionManager.updateMissionStatus(missionId, "COMPLETED");

        MissionExecutionEntity entity = new MissionExecutionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setMissionId(missionId);
        entity.setTenantId(mission.getTenantId());
        entity.setStatus("COMPLETED");
        entity.setExecutionTimeMs(duration);
        entity.setStartTime(Instant.ofEpochMilli(start));
        entity.setEndTime(Instant.ofEpochMilli(end));

        executionRepository.save(entity);
        eventPublisher.publishMissionCompleted(missionId, mission.getTenantId(), duration);

        MissionExecution me = new MissionExecution();
        me.setId(entity.getId());
        me.setMissionId(entity.getMissionId());
        me.setTenantId(entity.getTenantId());
        me.setStatus(entity.getStatus());
        me.setExecutionTimeMs(entity.getExecutionTimeMs());
        me.setStartTime(entity.getStartTime());
        me.setEndTime(entity.getEndTime());
        return me;
    }

    @Override
    public Mission pauseMission(String missionId) {
        log.info("[AtlasOrchestrator] Pausing mission '{}'", missionId);
        Mission mission = missionManager.updateMissionStatus(missionId, "PAUSED");
        eventPublisher.publishMissionPaused(missionId, mission.getTenantId());
        return mission;
    }

    @Override
    public Mission resumeMission(String missionId) {
        log.info("[AtlasOrchestrator] Resuming mission '{}'", missionId);
        Mission mission = missionManager.updateMissionStatus(missionId, "IN_PROGRESS");
        eventPublisher.publishMissionResumed(missionId, mission.getTenantId());
        return mission;
    }

    @Override
    public Mission cancelMission(String missionId) {
        log.info("[AtlasOrchestrator] Cancelling mission '{}'", missionId);
        return missionManager.updateMissionStatus(missionId, "CANCELLED");
    }
}
