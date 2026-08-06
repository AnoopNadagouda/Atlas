package com.atlas.reasoning.service;

import com.atlas.domain.reasoning.ReasoningTrace;

public interface ReasoningTraceStore {
    ReasoningTrace saveTrace(ReasoningTrace trace);
    ReasoningTrace getTraceBySessionId(String sessionId);
}
