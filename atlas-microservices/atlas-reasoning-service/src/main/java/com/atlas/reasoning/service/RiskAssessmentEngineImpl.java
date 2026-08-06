package com.atlas.reasoning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentEngineImpl implements RiskAssessmentEngine {

    private static final Logger log = LoggerFactory.getLogger(RiskAssessmentEngineImpl.class);

    @Override
    public double assessRisk(String decisionContent, String context) {
        log.info("[RiskAssessmentEngine] Assessing risk for decision: {}", decisionContent);
        if (decisionContent != null && decisionContent.toLowerCase().contains("fast")) {
            return 0.25;
        }
        return 0.10;
    }
}
