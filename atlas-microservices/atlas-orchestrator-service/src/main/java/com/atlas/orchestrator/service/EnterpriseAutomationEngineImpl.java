package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.AutomationJob;
import com.atlas.orchestrator.entity.AutomationJobEntity;
import com.atlas.orchestrator.repository.AutomationJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class EnterpriseAutomationEngineImpl implements EnterpriseAutomationEngine {

    private static final Logger log = LoggerFactory.getLogger(EnterpriseAutomationEngineImpl.class);

    private final AutomationJobRepository repository;

    public EnterpriseAutomationEngineImpl(AutomationJobRepository repository) {
        this.repository = repository;
    }

    @Override
    public AutomationJob scheduleJob(String tenantId, String jobName, String cronExpression) {
        log.info("[EnterpriseAutomationEngine] Scheduling enterprise background automation job '{}' ({}) for tenant '{}'", jobName, cronExpression, tenantId);
        AutomationJobEntity entity = new AutomationJobEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTenantId(tenantId);
        entity.setJobName(jobName);
        entity.setCronExpression(cronExpression);
        entity.setStatus("ACTIVE");
        entity.setLastRunAt(Instant.now());
        entity.setNextRunAt(Instant.now().plusSeconds(3600));

        repository.save(entity);

        AutomationJob job = new AutomationJob();
        job.setId(entity.getId());
        job.setTenantId(entity.getTenantId());
        job.setJobName(entity.getJobName());
        job.setCronExpression(entity.getCronExpression());
        job.setStatus(entity.getStatus());
        job.setLastRunAt(entity.getLastRunAt());
        job.setNextRunAt(entity.getNextRunAt());
        return job;
    }

    @Override
    public List<AutomationJob> getJobs(String tenantId) {
        return repository.findByTenantId(tenantId).stream().map(e -> {
            AutomationJob j = new AutomationJob();
            j.setId(e.getId());
            j.setTenantId(e.getTenantId());
            j.setJobName(e.getJobName());
            j.setCronExpression(e.getCronExpression());
            j.setStatus(e.getStatus());
            j.setLastRunAt(e.getLastRunAt());
            j.setNextRunAt(e.getNextRunAt());
            return j;
        }).toList();
    }
}
