package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.RecoveryPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryPlanRepository extends JpaRepository<RecoveryPlanEntity, String> {
    List<RecoveryPlanEntity> findByTenantId(String tenantId);
    List<RecoveryPlanEntity> findBySessionId(String sessionId);
}
