package com.atlas.domain.memory;

/**
 * Result wrapper for memory query matches with similarity and importance scores.
 */
public class MemorySearchResult {
    private Memory memory;
    private double relevanceScore;
    private double vectorSimilarity;

    public MemorySearchResult() {}

    public MemorySearchResult(Memory memory, double relevanceScore, double vectorSimilarity) {
        this.memory = memory;
        this.relevanceScore = relevanceScore;
        this.vectorSimilarity = vectorSimilarity;
    }

    public Memory getMemory() { return memory; }
    public void setMemory(Memory memory) { this.memory = memory; }

    public double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(double relevanceScore) { this.relevanceScore = relevanceScore; }

    public double getVectorSimilarity() { return vectorSimilarity; }
    public void setVectorSimilarity(double vectorSimilarity) { this.vectorSimilarity = vectorSimilarity; }
}
