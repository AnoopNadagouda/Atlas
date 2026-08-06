package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.GoalCreateRequest;
import com.atlas.domain.reasoning.GoalEntity;
import com.atlas.reasoning.repository.GoalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class GoalManagerImpl implements GoalManager {

    private static final Logger log = LoggerFactory.getLogger(GoalManagerImpl.class);

    private final GoalRepository goalRepository;

    public GoalManagerImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public GoalEntity createGoal(String tenantId, GoalCreateRequest request) {
        log.info("[GoalManager] Formulating new goal for tenant '{}': {}", tenantId, request.getTitle());
        com.atlas.reasoning.entity.GoalEntity entity = new com.atlas.reasoning.entity.GoalEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTenantId(tenantId);
        entity.setAgentId("agent-01");
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setStatus("ACTIVE");
        entity.setPriority(request.getPriority() > 0 ? request.getPriority() : 1);
        entity.setSuccessProbability(0.85);
        entity.setCreatedAt(Instant.now());

        goalRepository.save(entity);
        return mapToDomain(entity);
    }

    @Override
    public List<GoalEntity> getGoals(String tenantId) {
        return goalRepository.findByTenantId(tenantId).stream().map(this::mapToDomain).toList();
    }

    @Override
    public GoalEntity updateGoalStatus(String goalId, String status) {
        com.atlas.reasoning.entity.GoalEntity entity = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found: " + goalId));
        entity.setStatus(status);
        if ("COMPLETED".equalsIgnoreCase(status)) {
            entity.setCompletedAt(Instant.now());
        }
        goalRepository.save(entity);
        return mapToDomain(entity);
    }

    private GoalEntity mapToDomain(com.atlas.reasoning.entity.GoalEntity entity) {
        GoalEntity goal = new GoalEntity();
        goal.setId(entity.getId());
        goal.setTenantId(entity.getTenantId());
        goal.setTitle(entity.getTitle());
        goal.setDescription(entity.getDescription());
        goal.setStatus(entity.getStatus());
        goal.setPriority(entity.getPriority());
        goal.setTargetCriteria(List.of("Criteria 1", "Criteria 2"));
        goal.setCreatedAt(entity.getCreatedAt());
        return goal;
    }
}
