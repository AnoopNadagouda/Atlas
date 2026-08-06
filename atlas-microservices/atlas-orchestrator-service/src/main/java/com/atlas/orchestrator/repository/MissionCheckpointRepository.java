package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.MissionCheckpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionCheckpointRepository extends JpaRepository<MissionCheckpointEntity, String> {
    List<MissionCheckpointEntity> findByMissionId(String missionId);
}
