package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.AgentAssignment;
import java.util.List;

public interface AgentCoordinator {
    AgentAssignment assignAgent(String missionId, String agentId, String role);
    List<AgentAssignment> getAssignments(String missionId);
}
