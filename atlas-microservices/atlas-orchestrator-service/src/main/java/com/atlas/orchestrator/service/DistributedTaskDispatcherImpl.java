package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DistributedTaskDispatcherImpl implements DistributedTaskDispatcher {

    private static final Logger log = LoggerFactory.getLogger(DistributedTaskDispatcherImpl.class);

    @Override
    public void dispatchTask(String taskId, String targetService, Object payload) {
        log.info("[DistributedTaskDispatcher] Dispatching task '{}' to target service '{}'", taskId, targetService);
    }
}
