package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GovernanceManagerImpl implements GovernanceManager {

    private static final Logger log = LoggerFactory.getLogger(GovernanceManagerImpl.class);

    @Override
    public void auditAction(String tenantId, String actor, String action, String resource) {
        log.info("[GovernanceManager] AUDIT LOG: tenant='{}', actor='{}', action='{}', resource='{}'", tenantId, actor, action, resource);
    }
}
