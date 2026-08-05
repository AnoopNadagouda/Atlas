package com.atlas.workflow.controller;

import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowInstance;
import com.atlas.domain.workflow.WorkflowState;
import com.atlas.workflow.engine.WorkflowEngine;
import com.atlas.workflow.repository.WorkflowTemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkflowController.class)
class WorkflowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkflowEngine workflowEngine;

    @MockBean
    private WorkflowTemplateRepository templateRepository;

    @Test
    void testCreateWorkflowEndpoint() throws Exception {
        WorkflowDefinition def = WorkflowDefinition.builder()
                .id("def-123")
                .name("Rest Test Workflow")
                .version("1.0.0")
                .state(WorkflowState.ACTIVE)
                .build();

        when(workflowEngine.createWorkflow(any())).thenReturn(def);

        mockMvc.perform(post("/api/v20/workflows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Rest Test Workflow",
                                  "description": "API Test",
                                  "version": "1.0.0"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("def-123"))
                .andExpect(jsonPath("$.name").value("Rest Test Workflow"));
    }

    @Test
    void testGetWorkflowStatusEndpoint() throws Exception {
        WorkflowInstance inst = WorkflowInstance.builder()
                .instanceId("inst-999")
                .definitionId("def-123")
                .state(WorkflowState.RUNNING)
                .build();

        when(workflowEngine.getWorkflowStatus("inst-999")).thenReturn(inst);

        mockMvc.perform(get("/api/v20/workflows/instances/inst-999/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instanceId").value("inst-999"))
                .andExpect(jsonPath("$.state").value("RUNNING"));
    }

    @Test
    void testGetWorkflowStatisticsEndpoint() throws Exception {
        when(workflowEngine.getWorkflowStatistics()).thenReturn(Map.of(
                "totalDefinitions", 5L,
                "totalInstances", 10L,
                "successRate", 100.0
        ));

        mockMvc.perform(get("/api/v20/workflows/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDefinitions").value(5))
                .andExpect(jsonPath("$.successRate").value(100.0));
    }
}
