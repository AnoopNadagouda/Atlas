package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<MissionEntity, String> {
    List<MissionEntity> findByTenantId(String tenantId);
}
