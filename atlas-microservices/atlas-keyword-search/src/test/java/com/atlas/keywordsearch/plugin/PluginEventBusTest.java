package com.atlas.keywordsearch.plugin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PluginEventBusTest {

    private PluginEventBus pluginEventBus;

    @BeforeEach
    void setUp() {
        pluginEventBus = new PluginEventBus();
    }

    @Test
    void testPublishEvent() {
        pluginEventBus.publishEvent("DocumentIndexed", Map.of("docId", "doc-101", "tenantId", "tenant-acme"));
        assertFalse(pluginEventBus.getEventHistory().isEmpty());
        assertTrue(pluginEventBus.getEventHistory().get(0).contains("DocumentIndexed"));
    }
}
