package com.atlas.orchestrator.service;

public interface GovernanceManager {
    void auditAction(String tenantId, String actor, String action, String resource);
}
