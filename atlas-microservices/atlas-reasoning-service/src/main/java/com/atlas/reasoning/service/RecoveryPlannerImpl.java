package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.RecoveryPlanRequest;
import com.atlas.domain.reasoning.RecoveryPlan;
import com.atlas.reasoning.entity.RecoveryPlanEntity;
import com.atlas.reasoning.repository.RecoveryPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RecoveryPlannerImpl implements RecoveryPlanner {

    private static final Logger log = LoggerFactory.getLogger(RecoveryPlannerImpl.class);

    private final RecoveryPlanRepository recoveryRepository;

    public RecoveryPlannerImpl(RecoveryPlanRepository recoveryRepository) {
        this.recoveryRepository = recoveryRepository;
    }

    @Override
    public RecoveryPlan generateRecoveryPlan(String tenantId, RecoveryPlanRequest request) {
        log.info("[RecoveryPlanner] Formulating recovery strategy for session '{}'", request.getSessionId());

        RecoveryPlanEntity entity = new RecoveryPlanEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSessionId(request.getSessionId() != null ? request.getSessionId() : UUID.randomUUID().toString());
        entity.setFailureReason(request.getFailureReason() != null ? request.getFailureReason() : "Unexpected error during execution step");
        entity.setRootCause("TRANSIENT_DEPENDENCY_TIMEOUT");
        entity.setRecoveryStrategy("FALLBACK_ALTERNATIVE_EXECUTION");
        entity.setExecutedSuccessfully(true);
        entity.setTimestamp(Instant.now());

        recoveryRepository.save(entity);
        return mapToDomain(entity);
    }

    private RecoveryPlan mapToDomain(RecoveryPlanEntity entity) {
        RecoveryPlan plan = new RecoveryPlan();
        plan.setId(entity.getId());
        plan.setSessionId(entity.getSessionId());
        plan.setFailureReason(entity.getFailureReason());
        plan.setRootCause(entity.getRootCause());
        plan.setRecoveryStrategy(entity.getRecoveryStrategy());
        plan.setRecoverySteps(List.of("Step 1: Increase timeout limit", "Step 2: Execute secondary web search tool", "Step 3: Resume main pipeline"));
        plan.setExecutedSuccessfully(entity.isExecutedSuccessfully());
        plan.setTimestamp(entity.getTimestamp());
        return plan;
    }
}
