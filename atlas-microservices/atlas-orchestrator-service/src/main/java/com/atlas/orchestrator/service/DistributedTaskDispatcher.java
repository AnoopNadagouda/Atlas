package com.atlas.orchestrator.service;

public interface DistributedTaskDispatcher {
    void dispatchTask(String taskId, String targetService, Object payload);
}
