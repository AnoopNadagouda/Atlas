package com.atlas.memory.controller;

import com.atlas.common.dto.memory.MemoryCreateRequest;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryType;
import com.atlas.memory.engine.MemoryEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemoryController.class)
class MemoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemoryEngine memoryEngine;

    @Test
    void testCreateMemory() throws Exception {
        MemoryCreateRequest request = new MemoryCreateRequest();
        request.setKey("test-key");
        request.setContent("User prefers dark mode");
        request.setType(MemoryType.SHORT_TERM);

        Memory memory = new Memory("mem-123", "agent-1", "test-key", "User prefers dark mode", MemoryType.SHORT_TERM);

        when(memoryEngine.createMemory(eq("default-tenant"), any())).thenReturn(memory);

        mockMvc.perform(post("/api/v21/memory")
                .header("X-Tenant-ID", "default-tenant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("mem-123"))
                .andExpect(jsonPath("$.key").value("test-key"));
    }

    @Test
    void testGetMemory() throws Exception {
        Memory memory = new Memory("mem-123", "agent-1", "test-key", "User prefers dark mode", MemoryType.SHORT_TERM);

        when(memoryEngine.getMemory("default-tenant", "mem-123")).thenReturn(memory);

        mockMvc.perform(get("/api/v21/memory/mem-123")
                .header("X-Tenant-ID", "default-tenant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("mem-123"));
    }
}
