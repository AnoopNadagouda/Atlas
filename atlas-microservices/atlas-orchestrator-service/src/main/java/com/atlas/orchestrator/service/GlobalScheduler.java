package com.atlas.orchestrator.service;

public interface GlobalScheduler {
    void scheduleTask(String missionId, String taskId, int priority);
}
