package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.ExecutionTimelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionTimelineRepository extends JpaRepository<ExecutionTimelineEntity, String> {
    List<ExecutionTimelineEntity> findByMissionIdOrderByTimestampAsc(String missionId);
}
