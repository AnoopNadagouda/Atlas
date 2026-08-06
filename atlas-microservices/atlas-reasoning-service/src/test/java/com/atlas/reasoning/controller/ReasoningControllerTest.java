package com.atlas.reasoning.controller;

import com.atlas.common.dto.reasoning.ReasoningExecuteRequest;
import com.atlas.common.dto.reasoning.ReasoningResponse;
import com.atlas.domain.reasoning.ReasoningMode;
import com.atlas.reasoning.engine.ReasoningEngine;
import com.atlas.reasoning.service.GoalManager;
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

@WebMvcTest(ReasoningController.class)
class ReasoningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReasoningEngine reasoningEngine;

    @MockBean
    private GoalManager goalManager;

    @Test
    void testExecuteReasoning() throws Exception {
        ReasoningExecuteRequest request = new ReasoningExecuteRequest();
        request.setGoalId("goal-query-opt");
        request.setMode(ReasoningMode.TREE_OF_THOUGHTS);

        ReasoningResponse response = new ReasoningResponse();
        response.setId("session-999");
        response.setTenantId("default-tenant");
        response.setMode(ReasoningMode.TREE_OF_THOUGHTS);
        response.setOverallConfidence(0.95);

        when(reasoningEngine.executeReasoning(eq("default-tenant"), any())).thenReturn(response);

        mockMvc.perform(post("/api/v22/reasoning/execute")
                .header("X-Tenant-ID", "default-tenant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("session-999"))
                .andExpect(jsonPath("$.overallConfidence").value(0.95));
    }
}
