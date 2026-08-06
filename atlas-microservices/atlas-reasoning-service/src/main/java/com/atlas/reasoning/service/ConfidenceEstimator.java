package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.ConfidenceReportResponse;

public interface ConfidenceEstimator {
    double estimateConfidence(String prompt, String reasoningTrace);
    ConfidenceReportResponse generateConfidenceReport(String tenantId, String sessionId);
}
