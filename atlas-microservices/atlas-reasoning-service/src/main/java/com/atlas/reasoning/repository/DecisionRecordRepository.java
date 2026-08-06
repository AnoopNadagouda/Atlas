package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.DecisionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionRecordRepository extends JpaRepository<DecisionRecordEntity, String> {
    List<DecisionRecordEntity> findByTenantId(String tenantId);
    List<DecisionRecordEntity> findBySessionId(String sessionId);
}
