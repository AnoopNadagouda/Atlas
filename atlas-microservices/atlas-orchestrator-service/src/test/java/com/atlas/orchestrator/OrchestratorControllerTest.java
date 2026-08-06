package com.atlas.orchestrator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrchestratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPlatformHealth() throws Exception {
        mockMvc.perform(get("/api/v23/orchestrator/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.overallStatus").value("UP"))
            .andExpect(jsonPath("$.microserviceStatuses.['atlas-orchestrator-service']").value("UP"));
    }

    @Test
    public void testGetPlatformStatistics() throws Exception {
        mockMvc.perform(get("/api/v23/orchestrator/statistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalMissions").exists())
            .andExpect(jsonPath("$.overallHealthScore").value(99.98));
    }

    @Test
    public void testCreateMissionApi() throws Exception {
        String json = """
            {
              "title": "Autonomous Data Pipeline",
              "description": "Execute enterprise crawling and index refresh",
              "priority": 1
            }
            """;

        mockMvc.perform(post("/api/v23/orchestrator/missions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Tenant-ID", "tenant-test-1")
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("Autonomous Data Pipeline"))
            .andExpect(jsonPath("$.status").value("CREATED"));
    }
}
