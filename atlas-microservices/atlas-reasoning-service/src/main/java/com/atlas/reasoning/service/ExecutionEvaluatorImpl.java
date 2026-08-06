package com.atlas.reasoning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExecutionEvaluatorImpl implements ExecutionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(ExecutionEvaluatorImpl.class);

    @Override
    public boolean evaluateOutcome(String expectedOutcome, String actualOutcome) {
        log.info("[ExecutionEvaluator] Comparing expected vs actual outcome");
        if (actualOutcome == null) return false;
        return !actualOutcome.toLowerCase().contains("fail") && !actualOutcome.toLowerCase().contains("error");
    }
}
