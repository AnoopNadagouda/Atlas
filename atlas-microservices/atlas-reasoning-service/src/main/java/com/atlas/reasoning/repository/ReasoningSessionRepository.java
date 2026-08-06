package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ReasoningSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasoningSessionRepository extends JpaRepository<ReasoningSessionEntity, String> {
    List<ReasoningSessionEntity> findByTenantId(String tenantId);
    List<ReasoningSessionEntity> findByTenantIdAndAgentId(String tenantId, String agentId);
}
