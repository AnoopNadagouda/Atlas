package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.GoalCreateRequest;
import com.atlas.domain.reasoning.GoalEntity;
import java.util.List;

public interface GoalManager {
    GoalEntity createGoal(String tenantId, GoalCreateRequest request);
    List<GoalEntity> getGoals(String tenantId);
    GoalEntity updateGoalStatus(String goalId, String status);
}
