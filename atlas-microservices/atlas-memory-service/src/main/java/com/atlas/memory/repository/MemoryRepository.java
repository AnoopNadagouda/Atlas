package com.atlas.memory.repository;

import com.atlas.domain.memory.MemoryState;
import com.atlas.domain.memory.MemoryType;
import com.atlas.memory.entity.MemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, String> {

    List<MemoryEntity> findByTenantId(String tenantId);

    List<MemoryEntity> findByTenantIdAndAgentId(String tenantId, String agentId);

    List<MemoryEntity> findByTenantIdAndConversationId(String tenantId, String conversationId);

    List<MemoryEntity> findByTenantIdAndWorkflowId(String tenantId, String workflowId);

    List<MemoryEntity> findByTenantIdAndType(String tenantId, MemoryType type);

    List<MemoryEntity> findByTenantIdAndState(String tenantId, MemoryState state);

    Optional<MemoryEntity> findByTenantIdAndKey(String tenantId, String key);

    long countByTenantId(String tenantId);

    long countByTenantIdAndState(String tenantId, MemoryState state);

    long countByTenantIdAndType(String tenantId, MemoryType type);
}
