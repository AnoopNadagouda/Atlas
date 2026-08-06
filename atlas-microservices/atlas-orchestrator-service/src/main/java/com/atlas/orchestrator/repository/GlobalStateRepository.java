package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.GlobalStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalStateRepository extends JpaRepository<GlobalStateEntity, String> {
    List<GlobalStateEntity> findByTenantId(String tenantId);
    Optional<GlobalStateEntity> findByTenantIdAndStateKey(String tenantId, String stateKey);
}
