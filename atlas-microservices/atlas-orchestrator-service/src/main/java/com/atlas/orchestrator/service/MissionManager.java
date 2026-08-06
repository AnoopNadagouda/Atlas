package com.atlas.orchestrator.service;

import com.atlas.common.dto.orchestrator.MissionCreateRequest;
import com.atlas.domain.orchestrator.ExecutionTimeline;
import com.atlas.domain.orchestrator.Mission;
import java.util.List;
import java.util.Map;

public interface MissionManager {
    Mission createMission(String tenantId, MissionCreateRequest request);
    List<Mission> getMissions(String tenantId);
    Mission getMissionById(String missionId);
    Mission updateMissionStatus(String missionId, String status);
    List<ExecutionTimeline> getMissionTimeline(String missionId);
    Map<String, Object> getMissionAnalytics(String missionId);
}

