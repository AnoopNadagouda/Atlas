package com.atlas.memory.engine;

import com.atlas.common.dto.memory.MemoryCreateRequest;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryType;
import com.atlas.memory.integration.KafkaMemoryEventPublisher;
import com.atlas.memory.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MemoryEngineTest {

    @Mock private MemoryStore memoryStore;
    @Mock private MemorySearchService searchService;
    @Mock private MemoryConsolidationService consolidationService;
    @Mock private MemoryGraphBuilder graphBuilder;
    @Mock private MemoryAnalyticsService analyticsService;
    @Mock private MemorySyncService syncService;
    @Mock private KafkaMemoryEventPublisher eventPublisher;

    private MemoryEngine memoryEngine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memoryEngine = new MemoryEngineImpl(
            memoryStore, searchService, consolidationService,
            graphBuilder, analyticsService, syncService, eventPublisher, new ObjectMapper()
        );
    }

    @Test
    void testCreateMemory() {
        MemoryCreateRequest request = new MemoryCreateRequest();
        request.setKey("pref-theme");
        request.setContent("User prefers dark mode");
        request.setType(MemoryType.SHORT_TERM);

        Memory created = new Memory("mem-1", "agent-1", "pref-theme", "User prefers dark mode", MemoryType.SHORT_TERM);
        when(memoryStore.saveMemory(any())).thenReturn(created);

        Memory result = memoryEngine.createMemory("default-tenant", request);
        assertNotNull(result);
        assertEquals("mem-1", result.getId());
        assertEquals("pref-theme", result.getKey());
    }
}
