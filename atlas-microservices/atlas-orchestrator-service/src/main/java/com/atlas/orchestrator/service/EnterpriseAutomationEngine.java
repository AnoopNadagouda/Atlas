package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.AutomationJob;
import java.util.List;

public interface EnterpriseAutomationEngine {
    AutomationJob scheduleJob(String tenantId, String jobName, String cronExpression);
    List<AutomationJob> getJobs(String tenantId);
}
