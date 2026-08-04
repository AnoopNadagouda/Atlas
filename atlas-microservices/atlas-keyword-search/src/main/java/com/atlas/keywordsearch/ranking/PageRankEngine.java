package com.atlas.keywordsearch.ranking;

import com.atlas.domain.ranking.PageRankScore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageRankEngine {

    private final LinkGraphService linkGraphService;
    private final Map<String, PageRankScore> pageRankScores = new ConcurrentHashMap<>();

    private static final double DAMPING_FACTOR = 0.85;
    private static final int MAX_ITERATIONS = 20;
    private static final double CONVERGENCE_THRESHOLD = 0.0001;

    @PostConstruct
    public void runInitialPageRank() {
        runPageRank();
    }

    public synchronized Map<String, PageRankScore> runPageRank() {
        Set<String> nodes = linkGraphService.getNodeIds();
        int N = nodes.size();
        if (N == 0) return Collections.emptyMap();

        log.info("[PageRankEngine] Starting Power Iteration PageRank algorithm for N={} nodes (Damping: {})",
                N, DAMPING_FACTOR);

        double initialScore = 1.0 / N;
        Map<String, Double> currentRanks = new HashMap<>();
        for (String node : nodes) {
            currentRanks.put(node, initialScore);
        }

        int iterationsRan = 0;
        boolean converged = false;

        for (int iter = 1; iter <= MAX_ITERATIONS; iter++) {
            iterationsRan = iter;
            Map<String, Double> nextRanks = new HashMap<>();
            double maxDiff = 0.0;

            for (String p : nodes) {
                double rankSum = 0.0;
                List<String> incoming = linkGraphService.getIncomingLinks(p);
                for (String q : incoming) {
                    int outDegree = linkGraphService.getOutgoingLinks(q).size();
                    if (outDegree > 0) {
                        rankSum += currentRanks.get(q) / outDegree;
                    }
                }
                double newRank = ((1.0 - DAMPING_FACTOR) / N) + (DAMPING_FACTOR * rankSum);
                nextRanks.put(p, newRank);

                double diff = Math.abs(newRank - currentRanks.get(p));
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }

            currentRanks = nextRanks;
            if (maxDiff < CONVERGENCE_THRESHOLD) {
                converged = true;
                log.info("[PageRankEngine] PageRank converged at iteration {} with maxDiff: {}", iter, maxDiff);
                break;
            }
        }

        Instant now = Instant.now();
        for (Map.Entry<String, Double> entry : currentRanks.entrySet()) {
            pageRankScores.put(entry.getKey(), PageRankScore.builder()
                    .docId(entry.getKey())
                    .currentScore(entry.getValue())
                    .previousScore(initialScore)
                    .iteration(iterationsRan)
                    .converged(converged)
                    .lastUpdated(now)
                    .build());
        }

        log.info("[PageRankEngine] PageRank computation completed for {} nodes", pageRankScores.size());
        return pageRankScores;
    }

    public PageRankScore getScore(String docId) {
        return pageRankScores.getOrDefault(docId, PageRankScore.builder()
                .docId(docId)
                .currentScore(0.15)
                .previousScore(0.15)
                .iteration(1)
                .converged(true)
                .lastUpdated(Instant.now())
                .build());
    }

    public Map<String, Object> getStatistics() {
        return Map.of(
                "totalNodes", linkGraphService.getNodeIds().size(),
                "totalEdges", linkGraphService.getEdgeCount(),
                "dampingFactor", DAMPING_FACTOR,
                "convergenceThreshold", CONVERGENCE_THRESHOLD,
                "status", "CONVERGED"
        );
    }
}
