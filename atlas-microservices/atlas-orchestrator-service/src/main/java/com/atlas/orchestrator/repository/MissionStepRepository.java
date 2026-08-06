package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.MissionStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionStepRepository extends JpaRepository<MissionStepEntity, String> {
    List<MissionStepEntity> findByMissionId(String missionId);
}
