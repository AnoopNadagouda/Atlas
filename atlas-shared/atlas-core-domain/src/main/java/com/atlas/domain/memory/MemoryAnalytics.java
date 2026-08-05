package com.atlas.domain.memory;

import java.util.HashMap;
import java.util.Map;

/**
 * Domain model holding aggregated memory system metrics.
 */
public class MemoryAnalytics {
    private long totalMemories;
    private long activeMemories;
    private long consolidatedMemories;
    private long decayedMemories;
    private double averageImportanceScore;
    private Map<String, Long> countByType;

    public MemoryAnalytics() {
        this.countByType = new HashMap<>();
    }

    public long getTotalMemories() { return totalMemories; }
    public void setTotalMemories(long totalMemories) { this.totalMemories = totalMemories; }

    public long getActiveMemories() { return activeMemories; }
    public void setActiveMemories(long activeMemories) { this.activeMemories = activeMemories; }

    public long getConsolidatedMemories() { return consolidatedMemories; }
    public void setConsolidatedMemories(long consolidatedMemories) { this.consolidatedMemories = consolidatedMemories; }

    public long getDecayedMemories() { return decayedMemories; }
    public void setDecayedMemories(long decayedMemories) { this.decayedMemories = decayedMemories; }

    public double getAverageImportanceScore() { return averageImportanceScore; }
    public void setAverageImportanceScore(double averageImportanceScore) { this.averageImportanceScore = averageImportanceScore; }

    public Map<String, Long> getCountByType() { return countByType; }
    public void setCountByType(Map<String, Long> countByType) { this.countByType = countByType; }
}
