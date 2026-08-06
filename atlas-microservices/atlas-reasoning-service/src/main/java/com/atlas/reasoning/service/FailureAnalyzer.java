package com.atlas.reasoning.service;

public interface FailureAnalyzer {
    String analyzeFailure(String errorLog, String sessionId);
}
