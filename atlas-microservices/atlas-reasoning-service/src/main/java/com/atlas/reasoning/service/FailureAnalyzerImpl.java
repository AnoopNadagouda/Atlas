package com.atlas.reasoning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FailureAnalyzerImpl implements FailureAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(FailureAnalyzerImpl.class);

    @Override
    public String analyzeFailure(String errorLog, String sessionId) {
        log.info("[FailureAnalyzer] Analyzing failure root cause for session '{}'", sessionId);
        if (errorLog != null && errorLog.toLowerCase().contains("timeout")) {
            return "ROOT_CAUSE: Upstream dependency timeout. Recommended action: Increase timeout threshold & retry with exponential backoff.";
        }
        return "ROOT_CAUSE: Transient execution anomaly detected. Recommended action: Execute fallback alternative strategy.";
    }
}
