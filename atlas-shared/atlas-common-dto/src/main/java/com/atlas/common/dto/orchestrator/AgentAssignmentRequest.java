package com.atlas.common.dto.orchestrator;

public class AgentAssignmentRequest {
    private String missionId;
    private String agentId;
    private String role;

    public AgentAssignmentRequest() {
    }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
