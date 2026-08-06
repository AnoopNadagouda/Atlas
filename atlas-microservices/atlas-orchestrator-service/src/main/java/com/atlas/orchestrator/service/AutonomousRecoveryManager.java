package com.atlas.orchestrator.service;

public interface AutonomousRecoveryManager {
    void handleFailure(String missionId, String failureReason);
}
