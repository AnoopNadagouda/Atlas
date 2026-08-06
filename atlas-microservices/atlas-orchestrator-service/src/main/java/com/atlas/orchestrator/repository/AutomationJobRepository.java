package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.AutomationJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationJobRepository extends JpaRepository<AutomationJobEntity, String> {
    List<AutomationJobEntity> findByTenantId(String tenantId);
}
