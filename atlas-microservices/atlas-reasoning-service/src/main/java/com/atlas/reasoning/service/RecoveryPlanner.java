package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.RecoveryPlanRequest;
import com.atlas.domain.reasoning.RecoveryPlan;

public interface RecoveryPlanner {
    RecoveryPlan generateRecoveryPlan(String tenantId, RecoveryPlanRequest request);
}
