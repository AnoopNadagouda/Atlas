package com.atlas.keywordsearch.ranking;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.domain.ranking.PageRankScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingPipeline {

    private final PageRankEngine pageRankEngine;
    private final FreshnessScorer freshnessScorer;

    private static final double WEIGHT_RRF = 0.40;
    private static final double WEIGHT_PAGERANK = 0.35;
    private static final double WEIGHT_FRESHNESS = 0.25;

    public List<SearchResultDto> applyRankingPipeline(List<SearchResultDto> initialResults) {
        if (initialResults == null || initialResults.isEmpty()) return initialResults;

        log.info("[RankingPipeline] Applying multi-signal ranking pipeline (RRF, PageRank, Freshness) across {} results",
                initialResults.size());

        for (SearchResultDto result : initialResults) {
            double rrfScore = result.getScore();

            PageRankScore pr = pageRankEngine.getScore(result.getId());
            double prScore = pr != null ? pr.getCurrentScore() : 0.15;

            double freshness = freshnessScorer.calculateFreshnessScore(Instant.now());

            double finalScore = (WEIGHT_RRF * rrfScore) + (WEIGHT_PAGERANK * prScore) + (WEIGHT_FRESHNESS * freshness);
            result.setScore(finalScore);
        }

        initialResults.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return initialResults;
    }
}
