package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, String> {
    List<GoalEntity> findByTenantId(String tenantId);
    List<GoalEntity> findByTenantIdAndAgentId(String tenantId, String agentId);
}
