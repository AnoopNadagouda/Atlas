package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.DecisionEvaluateRequest;
import com.atlas.domain.reasoning.DecisionRecord;
import java.util.List;

public interface DecisionEngine {
    DecisionRecord evaluateAndSelectDecision(String tenantId, DecisionEvaluateRequest request);
    List<DecisionRecord> getDecisionHistory(String tenantId);
}
