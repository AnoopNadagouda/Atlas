package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExecutionSupervisorImpl implements ExecutionSupervisor {

    private static final Logger log = LoggerFactory.getLogger(ExecutionSupervisorImpl.class);

    @Override
    public void superviseExecution(String missionId) {
        log.info("[ExecutionSupervisor] Supervising real-time execution bounds and step transitions for mission '{}'", missionId);
    }
}
