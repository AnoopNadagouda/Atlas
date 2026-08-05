package com.atlas.keywordsearch.connector;

import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorSchedulerTest {

    private ConnectorScheduler scheduler;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(new GitHubConnector()));
        registry.registerInjectedConnectors();
        scheduler = new ConnectorScheduler(registry, null);
    }

    @Test
    void testPauseAndResumeSchedule() {
        assertFalse(scheduler.isPaused("github"));
        scheduler.pauseSchedule("github");
        assertTrue(scheduler.isPaused("github"));
        scheduler.resumeSchedule("github");
        assertFalse(scheduler.isPaused("github"));
    }
}
