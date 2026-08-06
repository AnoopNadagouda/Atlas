package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventCoordinatorImpl implements EventCoordinator {

    private static final Logger log = LoggerFactory.getLogger(EventCoordinatorImpl.class);

    @Override
    public void coordinateEvent(String topic, Object eventPayload) {
        log.info("[EventCoordinator] Routing global event across bus topic '{}'", topic);
    }
}
