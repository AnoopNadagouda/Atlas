package com.atlas.workflow.engine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowSchedulerImpl implements WorkflowScheduler {

    private final Map<String, String> scheduledCronMap = new ConcurrentHashMap<>();

    @Override
    public void scheduleWorkflow(String definitionId, String cronExpression) {
        log.info("[WorkflowScheduler] Scheduled workflow definition '{}' with cron: '{}'", definitionId, cronExpression);
        if (cronExpression != null && !cronExpression.isBlank()) {
            scheduledCronMap.put(definitionId, cronExpression);
        }
    }

    @Override
    public void unscheduleWorkflow(String definitionId) {
        log.info("[WorkflowScheduler] Unscheduled workflow definition '{}'", definitionId);
        scheduledCronMap.remove(definitionId);
    }

    @Scheduled(fixedRate = 60000)
    public void pollScheduledJobs() {
        if (!scheduledCronMap.isEmpty()) {
            log.info("[WorkflowScheduler] Polling {} active scheduled workflow definitions", scheduledCronMap.size());
        }
    }
}
