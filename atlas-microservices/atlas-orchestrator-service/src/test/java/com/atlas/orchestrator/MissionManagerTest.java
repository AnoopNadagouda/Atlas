package com.atlas.orchestrator;

import com.atlas.common.dto.orchestrator.MissionCreateRequest;
import com.atlas.domain.orchestrator.Mission;
import com.atlas.orchestrator.service.MissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MissionManagerTest {

    @Autowired
    private MissionManager missionManager;

    @Test
    public void testCreateGetUpdateMission() {
        MissionCreateRequest req = new MissionCreateRequest();
        req.setTitle("Autonomous System Benchmark Mission");
        req.setDescription("Benchmark indexing and reasoning latency.");
        req.setPriority(8);

        Mission mission = missionManager.createMission("tenant-benchmark", req);
        assertNotNull(mission.getId());
        assertEquals("tenant-benchmark", mission.getTenantId());
        assertEquals("CREATED", mission.getStatus());
        assertEquals(8, mission.getPriority());

        Mission fetched = missionManager.getMissionById(mission.getId());
        assertNotNull(fetched);
        assertEquals(mission.getTitle(), fetched.getTitle());

        Mission updated = missionManager.updateMissionStatus(mission.getId(), "COMPLETED");
        assertEquals("COMPLETED", updated.getStatus());
        assertEquals(100.0, updated.getProgressPercentage());
    }

    @Test
    public void testMissionTimelineAndAnalytics() {
        MissionCreateRequest req = new MissionCreateRequest();
        req.setTitle("Timeline & Analytics Mission");
        req.setDescription("Track timeline events and analytics metrics.");
        req.setPriority(3);

        Mission mission = missionManager.createMission("tenant-analytics", req);
        var timeline = missionManager.getMissionTimeline(mission.getId());
        assertNotNull(timeline);
        assertFalse(timeline.isEmpty());

        var analytics = missionManager.getMissionAnalytics(mission.getId());
        assertNotNull(analytics);
        assertEquals(mission.getId(), analytics.get("missionId"));
    }
}
