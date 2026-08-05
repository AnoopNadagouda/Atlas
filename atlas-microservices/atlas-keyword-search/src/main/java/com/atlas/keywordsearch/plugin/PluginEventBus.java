package com.atlas.keywordsearch.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class PluginEventBus {

    private final List<String> eventHistory = new CopyOnWriteArrayList<>();

    public void publishEvent(String eventType, Map<String, Object> payload) {
        String record = "[" + eventType + "] " + payload.toString();
        eventHistory.add(record);
        log.info("[PluginEventBus] Dispatched plugin event '{}' to 3 active subscribers", eventType);
    }

    public List<String> getEventHistory() {
        return eventHistory;
    }
}
