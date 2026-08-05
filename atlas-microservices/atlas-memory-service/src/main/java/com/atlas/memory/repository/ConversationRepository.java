package com.atlas.memory.repository;

import com.atlas.memory.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, String> {
    List<ConversationEntity> findByTenantId(String tenantId);
    List<ConversationEntity> findByTenantIdAndAgentId(String tenantId, String agentId);
}
