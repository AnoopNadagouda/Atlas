package com.atlas.reasoning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlternativeGeneratorImpl implements AlternativeGenerator {

    private static final Logger log = LoggerFactory.getLogger(AlternativeGeneratorImpl.class);

    @Override
    public List<String> generateAlternatives(String goalId, String currentContext) {
        log.info("[AlternativeGenerator] Generating alternative decision paths for goal '{}'", goalId);
        return List.of(
            "Option A (Direct Execution): Fast path with minimal validation overhead",
            "Option B (Multi-Step Tree Search): High-confidence path with self-correction steps",
            "Option C (Consensus Ensemble): Aggregated decision path combining memory and web search"
        );
    }
}
