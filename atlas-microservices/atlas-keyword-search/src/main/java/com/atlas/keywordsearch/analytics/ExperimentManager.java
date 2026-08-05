package com.atlas.keywordsearch.analytics;

import com.atlas.domain.analytics.RankingExperiment;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ExperimentManager {

    private final Map<String, RankingExperiment> experiments = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedExperiments() {
        log.info("Initializing Ranking Experiment Manager...");
        startExperiment("exp-001", "PageRank Heavy Ranking Experiment", 25, "PAGERANK_HEAVY");
    }

    public synchronized RankingExperiment startExperiment(String id, String name, int trafficSplit, String profile) {
        RankingExperiment exp = RankingExperiment.builder()
                .experimentId(id)
                .name(name)
                .trafficSplitPercent(trafficSplit)
                .activeProfile(profile)
                .status("RUNNING")
                .createdAt(Instant.now())
                .build();
        experiments.put(id, exp);
        log.info("[ExperimentManager] Started A/B Ranking Experiment '{}' (Profile: {}, Split: {}%)", id, profile, trafficSplit);
        return exp;
    }

    public synchronized void stopExperiment(String id) {
        RankingExperiment exp = experiments.get(id);
        if (exp != null) {
            exp.setStatus("STOPPED");
            log.info("[ExperimentManager] Stopped A/B Ranking Experiment '{}'", id);
        }
    }

    public List<RankingExperiment> getActiveExperiments() {
        return new ArrayList<>(experiments.values());
    }
}
