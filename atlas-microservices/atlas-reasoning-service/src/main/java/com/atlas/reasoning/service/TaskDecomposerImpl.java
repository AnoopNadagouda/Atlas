package com.atlas.reasoning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDecomposerImpl implements TaskDecomposer {

    private static final Logger log = LoggerFactory.getLogger(TaskDecomposerImpl.class);

    @Override
    public List<String> decomposeGoal(String goalDescription, String reasoningMode) {
        log.info("[TaskDecomposer] Decomposing goal '{}' under mode '{}'", goalDescription, reasoningMode);
        return List.of(
            "Step 1: Analyze context and input parameters for " + goalDescription,
            "Step 2: Generate candidate strategy alternatives",
            "Step 3: Evaluate risk, cost, and confidence bounds",
            "Step 4: Execute autonomous decision and validate outcome"
        );
    }
}
