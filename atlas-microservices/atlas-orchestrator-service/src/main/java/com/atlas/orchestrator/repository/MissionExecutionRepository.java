package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.MissionExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionExecutionRepository extends JpaRepository<MissionExecutionEntity, String> {
    List<MissionExecutionEntity> findByMissionId(String missionId);
    List<MissionExecutionEntity> findByTenantId(String tenantId);
}
