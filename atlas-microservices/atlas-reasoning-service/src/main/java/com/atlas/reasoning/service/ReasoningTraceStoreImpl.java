package com.atlas.reasoning.service;

import com.atlas.domain.reasoning.ReasoningTrace;
import com.atlas.reasoning.entity.ReasoningTraceEntity;
import com.atlas.reasoning.repository.ReasoningTraceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ReasoningTraceStoreImpl implements ReasoningTraceStore {

    private static final Logger log = LoggerFactory.getLogger(ReasoningTraceStoreImpl.class);

    private final ReasoningTraceRepository traceRepository;

    public ReasoningTraceStoreImpl(ReasoningTraceRepository traceRepository) {
        this.traceRepository = traceRepository;
    }

    @Override
    public ReasoningTrace saveTrace(ReasoningTrace trace) {
        log.info("[ReasoningTraceStore] Saving reasoning trace for session '{}'", trace.getSessionId());
        ReasoningTraceEntity entity = traceRepository.findBySessionId(trace.getSessionId())
                .orElseGet(() -> {
                    ReasoningTraceEntity newEntity = new ReasoningTraceEntity();
                    newEntity.setId(UUID.randomUUID().toString());
                    newEntity.setSessionId(trace.getSessionId());
                    return newEntity;
                });

        entity.setTenantId("default-tenant");
        entity.setOverallConfidence(0.92);
        entity.setCreatedAt(Instant.now());

        traceRepository.save(entity);
        return trace;
    }

    @Override
    public ReasoningTrace getTraceBySessionId(String sessionId) {
        return traceRepository.findBySessionId(sessionId).map(entity -> {
            ReasoningTrace trace = new ReasoningTrace();
            trace.setId(entity.getId());
            trace.setSessionId(entity.getSessionId());
            trace.setGoalDescription("Autonomous Reasoning Strategy");
            trace.setCreatedAt(entity.getCreatedAt());
            return trace;
        }).orElseGet(() -> {
            ReasoningTrace trace = new ReasoningTrace();
            trace.setId(UUID.randomUUID().toString());
            trace.setSessionId(sessionId);
            trace.setGoalDescription("Autonomous Reasoning Strategy");
            trace.setCreatedAt(Instant.now());
            return trace;
        });
    }
}
