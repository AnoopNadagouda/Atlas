package com.atlas.orchestrator.engine;

import com.atlas.domain.orchestrator.Mission;
import com.atlas.domain.orchestrator.MissionExecution;

public interface AtlasOrchestrator {
    MissionExecution executeMission(String missionId);
    Mission pauseMission(String missionId);
    Mission resumeMission(String missionId);
    Mission cancelMission(String missionId);
}
