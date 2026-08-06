package com.atlas.orchestrator;

import com.atlas.common.dto.orchestrator.MissionCreateRequest;
import com.atlas.domain.orchestrator.Mission;
import com.atlas.orchestrator.engine.AtlasOrchestrator;
import com.atlas.orchestrator.service.MissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AtlasOrchestratorTest {

    @Autowired
    private MissionManager missionManager;

    @Autowired
    private AtlasOrchestrator atlasOrchestrator;

    @Test
    public void testMissionExecutionLifecycle() {
        MissionCreateRequest request = new MissionCreateRequest();
        request.setTitle("Enterprise Search & Multi-Agent Synthesis Mission");
        request.setDescription("Orchestrate multi-agent crawling, memory consolidation, reasoning graph evaluation, and index updates.");
        request.setPriority(1);

        Mission mission = missionManager.createMission("test-tenant", request);
        assertNotNull(mission.getId());
        assertEquals("CREATED", mission.getStatus());

        var execution = atlasOrchestrator.executeMission(mission.getId());
        assertNotNull(execution);
        assertEquals("COMPLETED", execution.getStatus());
        assertTrue(execution.getExecutionTimeMs() >= 0);

        Mission updatedMission = missionManager.getMissions("test-tenant").stream()
            .filter(m -> m.getId().equals(mission.getId()))
            .findFirst().orElse(null);
        assertNotNull(updatedMission);
        assertEquals("COMPLETED", updatedMission.getStatus());
    }
}
