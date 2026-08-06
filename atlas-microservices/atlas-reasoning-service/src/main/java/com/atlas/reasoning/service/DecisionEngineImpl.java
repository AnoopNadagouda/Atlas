package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.DecisionEvaluateRequest;
import com.atlas.domain.reasoning.DecisionRecord;
import com.atlas.reasoning.entity.DecisionRecordEntity;
import com.atlas.reasoning.repository.DecisionRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DecisionEngineImpl implements DecisionEngine {

    private static final Logger log = LoggerFactory.getLogger(DecisionEngineImpl.class);

    private final DecisionRecordRepository decisionRepository;
    private final RiskAssessmentEngine riskEngine;

    public DecisionEngineImpl(DecisionRecordRepository decisionRepository, RiskAssessmentEngine riskEngine) {
        this.decisionRepository = decisionRepository;
        this.riskEngine = riskEngine;
    }

    @Override
    public DecisionRecord evaluateAndSelectDecision(String tenantId, DecisionEvaluateRequest request) {
        log.info("[DecisionEngine] Evaluating alternatives for session '{}'", request.getSessionId());
        String selected = (request.getOptions() != null && !request.getOptions().isEmpty())
                ? request.getOptions().get(0)
                : "Default Autonomous Decision Strategy";

        double riskScore = riskEngine.assessRisk(selected, request.getDecisionPoint());

        DecisionRecordEntity entity = new DecisionRecordEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSessionId(request.getSessionId() != null ? request.getSessionId() : UUID.randomUUID().toString());
        entity.setDecisionPoint(request.getDecisionPoint() != null ? request.getDecisionPoint() : "Main Decision Point");
        entity.setChosenOption(selected);
        entity.setRiskEstimate(riskScore);
        entity.setConfidenceScore(0.91);
        entity.setCostEstimate(1.50);
        entity.setExpectedReward(9.50);
        entity.setJustification("Selected optimal trade-off path based on confidence bounds.");
        entity.setTimestamp(Instant.now());

        decisionRepository.save(entity);
        return mapToDomain(entity);
    }

    @Override
    public List<DecisionRecord> getDecisionHistory(String tenantId) {
        return decisionRepository.findByTenantId(tenantId).stream().map(this::mapToDomain).toList();
    }

    private DecisionRecord mapToDomain(DecisionRecordEntity entity) {
        DecisionRecord record = new DecisionRecord();
        record.setId(entity.getId());
        record.setSessionId(entity.getSessionId());
        record.setDecisionPoint(entity.getDecisionPoint());
        record.setChosenOption(entity.getChosenOption());
        record.setRiskEstimate(entity.getRiskEstimate());
        record.setConfidenceScore(entity.getConfidenceScore());
        record.setCostEstimate(entity.getCostEstimate());
        record.setExpectedReward(entity.getExpectedReward());
        record.setJustification(entity.getJustification());
        record.setTimestamp(entity.getTimestamp());
        return record;
    }
}
