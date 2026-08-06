package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.AgentAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentAssignmentRepository extends JpaRepository<AgentAssignmentEntity, String> {
    List<AgentAssignmentEntity> findByMissionId(String missionId);
    List<AgentAssignmentEntity> findByAgentId(String agentId);
}
