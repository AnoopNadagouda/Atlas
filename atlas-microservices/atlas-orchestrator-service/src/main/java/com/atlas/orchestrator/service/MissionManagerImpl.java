package com.atlas.orchestrator.service;

import com.atlas.common.dto.orchestrator.MissionCreateRequest;
import com.atlas.domain.orchestrator.Mission;
import com.atlas.orchestrator.entity.MissionEntity;
import com.atlas.orchestrator.repository.ExecutionTimelineRepository;
import com.atlas.orchestrator.repository.MissionExecutionRepository;
import com.atlas.orchestrator.repository.MissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MissionManagerImpl implements MissionManager {

    private static final Logger log = LoggerFactory.getLogger(MissionManagerImpl.class);

    private final MissionRepository missionRepository;
    private final ExecutionTimelineRepository timelineRepository;
    private final MissionExecutionRepository executionRepository;

    public MissionManagerImpl(MissionRepository missionRepository,
                              ExecutionTimelineRepository timelineRepository,
                              MissionExecutionRepository executionRepository) {
        this.missionRepository = missionRepository;
        this.timelineRepository = timelineRepository;
        this.executionRepository = executionRepository;
    }

    @Override
    public Mission createMission(String tenantId, MissionCreateRequest request) {
        log.info("[MissionManager] Formulating enterprise mission for tenant '{}': {}", tenantId, request.getTitle());

        MissionEntity entity = new MissionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTenantId(tenantId);
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setStatus("CREATED");
        entity.setPriority(request.getPriority() > 0 ? request.getPriority() : 1);
        entity.setProgressPercentage(0.0);
        entity.setCreatedAt(Instant.now());

        missionRepository.save(entity);
        recordTimelineEvent(entity.getId(), "MISSION_CREATED", "Mission formulated with priority " + entity.getPriority());
        return mapToDomain(entity);
    }

    @Override
    public List<Mission> getMissions(String tenantId) {
        return missionRepository.findByTenantId(tenantId).stream().map(this::mapToDomain).toList();
    }

    @Override
    public Mission getMissionById(String missionId) {
        return missionRepository.findById(missionId)
                .map(this::mapToDomain)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found: " + missionId));
    }

    @Override
    public Mission updateMissionStatus(String missionId, String status) {
        MissionEntity entity = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission not found: " + missionId));
        entity.setStatus(status);
        if ("COMPLETED".equalsIgnoreCase(status)) {
            entity.setProgressPercentage(100.0);
            entity.setCompletedAt(Instant.now());
        }
        missionRepository.save(entity);
        recordTimelineEvent(missionId, "STATUS_UPDATED", "Status updated to " + status);
        return mapToDomain(entity);
    }

    @Override
    public List<com.atlas.domain.orchestrator.ExecutionTimeline> getMissionTimeline(String missionId) {
        return timelineRepository.findByMissionIdOrderByTimestampAsc(missionId).stream().map(entity -> {
            com.atlas.domain.orchestrator.ExecutionTimeline t = new com.atlas.domain.orchestrator.ExecutionTimeline();
            t.setEventId(entity.getEventId());
            t.setMissionId(entity.getMissionId());
            t.setEventType(entity.getEventType());
            t.setEventDetails(entity.getEventDetails());
            t.setTimestamp(entity.getTimestamp());
            return t;
        }).toList();
    }

    @Override
    public java.util.Map<String, Object> getMissionAnalytics(String missionId) {
        Mission mission = getMissionById(missionId);
        long timelineEvents = timelineRepository.findByMissionIdOrderByTimestampAsc(missionId).size();
        var executions = executionRepository.findByMissionId(missionId);

        java.util.Map<String, Object> analytics = new java.util.HashMap<>();
        analytics.put("missionId", missionId);
        analytics.put("title", mission.getTitle());
        analytics.put("status", mission.getStatus());
        analytics.put("progressPercentage", mission.getProgressPercentage());
        analytics.put("priority", mission.getPriority());
        analytics.put("totalEvents", timelineEvents);
        analytics.put("totalExecutions", executions.size());
        analytics.put("tenantId", mission.getTenantId());
        analytics.put("createdAt", mission.getCreatedAt());
        analytics.put("completedAt", mission.getCompletedAt());
        return analytics;
    }

    private void recordTimelineEvent(String missionId, String type, String details) {
        try {
            com.atlas.orchestrator.entity.ExecutionTimelineEntity timeline = new com.atlas.orchestrator.entity.ExecutionTimelineEntity();
            timeline.setEventId(UUID.randomUUID().toString());
            timeline.setMissionId(missionId);
            timeline.setEventType(type);
            timeline.setEventDetails(details);
            timeline.setTimestamp(Instant.now());
            timelineRepository.save(timeline);
        } catch (Exception e) {
            log.warn("[MissionManager] Could not record timeline event: {}", e.getMessage());
        }
    }

    private Mission mapToDomain(MissionEntity entity) {
        Mission mission = new Mission();
        mission.setId(entity.getId());
        mission.setTenantId(entity.getTenantId());
        mission.setTitle(entity.getTitle());
        mission.setDescription(entity.getDescription());
        mission.setStatus(entity.getStatus());
        mission.setPriority(entity.getPriority());
        mission.setProgressPercentage(entity.getProgressPercentage());
        mission.setCreatedAt(entity.getCreatedAt());
        mission.setCompletedAt(entity.getCompletedAt());
        return mission;
    }
}

