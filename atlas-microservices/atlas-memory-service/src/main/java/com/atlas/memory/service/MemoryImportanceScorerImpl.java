package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryType;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class MemoryImportanceScorerImpl implements MemoryImportanceScorer {

    @Override
    public double calculateScore(Memory memory) {
        if (memory == null) return 0.0;

        double baseWeight = switch (memory.getType()) {
            case KNOWLEDGE, PROCEDURAL -> 0.9;
            case SEMANTIC, LONG_TERM -> 0.8;
            case EPISODIC, WORKFLOW -> 0.7;
            case TOOL_EXECUTION, CONVERSATION -> 0.5;
            case SEARCH, SHORT_TERM -> 0.3;
        };

        double recencyBoost = 1.0;
        if (memory.getLastAccessedAt() != null) {
            long hoursAgo = Duration.between(memory.getLastAccessedAt(), Instant.now()).toHours();
            recencyBoost = Math.exp(-0.01 * hoursAgo);
        }

        double frequencyBoost = Math.min(2.0, 1.0 + Math.log1p(memory.getAccessCount()));

        double finalScore = memory.getImportanceScore() * baseWeight * recencyBoost * frequencyBoost;
        return Math.max(0.0, Math.min(1.0, finalScore));
    }
}
