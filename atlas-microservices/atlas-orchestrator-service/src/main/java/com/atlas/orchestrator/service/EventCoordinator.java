package com.atlas.orchestrator.service;

public interface EventCoordinator {
    void coordinateEvent(String topic, Object eventPayload);
}
