package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.AgentAssignment;
import com.atlas.orchestrator.entity.AgentAssignmentEntity;
import com.atlas.orchestrator.repository.AgentAssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AgentCoordinatorImpl implements AgentCoordinator {

    private static final Logger log = LoggerFactory.getLogger(AgentCoordinatorImpl.class);

    private final AgentAssignmentRepository repository;

    public AgentCoordinatorImpl(AgentAssignmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public AgentAssignment assignAgent(String missionId, String agentId, String role) {
        log.info("[AgentCoordinator] Assigning agent '{}' to mission '{}' with role '{}'", agentId, missionId, role);
        AgentAssignmentEntity entity = new AgentAssignmentEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setMissionId(missionId);
        entity.setAgentId(agentId);
        entity.setRole(role);
        entity.setStatus("ASSIGNED");
        entity.setAssignedAt(Instant.now());

        repository.save(entity);

        AgentAssignment assignment = new AgentAssignment();
        assignment.setId(entity.getId());
        assignment.setMissionId(entity.getMissionId());
        assignment.setAgentId(entity.getAgentId());
        assignment.setRole(entity.getRole());
        assignment.setStatus(entity.getStatus());
        assignment.setAssignedAt(entity.getAssignedAt());
        return assignment;
    }

    @Override
    public List<AgentAssignment> getAssignments(String missionId) {
        return repository.findByMissionId(missionId).stream().map(e -> {
            AgentAssignment a = new AgentAssignment();
            a.setId(e.getId());
            a.setMissionId(e.getMissionId());
            a.setAgentId(e.getAgentId());
            a.setRole(e.getRole());
            a.setStatus(e.getStatus());
            a.setAssignedAt(e.getAssignedAt());
            return a;
        }).toList();
    }
}
