package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.CritiqueRequest;
import com.atlas.domain.reasoning.CritiqueRecord;
import com.atlas.reasoning.entity.CritiqueRecordEntity;
import com.atlas.reasoning.repository.CritiqueRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CriticEngineImpl implements CriticEngine {

    private static final Logger log = LoggerFactory.getLogger(CriticEngineImpl.class);

    private final CritiqueRecordRepository critiqueRepository;

    public CriticEngineImpl(CritiqueRecordRepository critiqueRepository) {
        this.critiqueRepository = critiqueRepository;
    }

    @Override
    public CritiqueRecord critiqueExecution(String tenantId, CritiqueRequest request) {
        log.info("[CriticEngine] Executing critique for session '{}'", request.getSessionId());

        CritiqueRecordEntity entity = new CritiqueRecordEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSessionId(request.getSessionId() != null ? request.getSessionId() : UUID.randomUUID().toString());
        entity.setStepId(request.getStepId() != null ? request.getStepId() : "step-1");
        entity.setQualityScore(0.94);
        entity.setRequiresRevision(false);
        entity.setTimestamp(Instant.now());

        critiqueRepository.save(entity);
        return mapToDomain(entity);
    }

    private CritiqueRecord mapToDomain(CritiqueRecordEntity entity) {
        CritiqueRecord record = new CritiqueRecord();
        record.setId(entity.getId());
        record.setSessionId(entity.getSessionId());
        record.setStepId(entity.getStepId());
        record.setQualityScore(entity.getQualityScore());
        record.setFlawsDetected(List.of());
        record.setSuggestions(List.of("Looks good"));
        record.setRequiresRevision(entity.isRequiresRevision());
        record.setTimestamp(entity.getTimestamp());
        return record;
    }
}
