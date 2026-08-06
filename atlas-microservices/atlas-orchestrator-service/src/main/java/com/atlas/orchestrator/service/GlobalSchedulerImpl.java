package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GlobalSchedulerImpl implements GlobalScheduler {

    private static final Logger log = LoggerFactory.getLogger(GlobalSchedulerImpl.class);

    @Override
    public void scheduleTask(String missionId, String taskId, int priority) {
        log.info("[GlobalScheduler] Scheduled task '{}' for mission '{}' with priority P{}", taskId, missionId, priority);
    }
}
