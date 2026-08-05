package com.atlas.keywordsearch.ltr;

import com.atlas.domain.ltr.LtrModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LtrModelRegistry {

    private final Map<String, LtrModel> models = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedModels() {
        log.info("Initializing Learning-to-Rank (LTR) Model Registry...");

        LtrModel defaultLinear = LtrModel.builder()
                .modelId("ltr-linear-v1")
                .modelName("Linear LTR Model")
                .modelType("LINEAR")
                .version("1.0.0")
                .featureWeights(Map.of(
                        "bm25_norm", 0.30,
                        "semantic_norm", 0.35,
                        "pageRank_norm", 0.15,
                        "freshness_norm", 0.10,
                        "ctr_norm", 0.05,
                        "entityMatch_norm", 0.05
                ))
                .status("ACTIVE")
                .build();

        LtrModel xgboostModel = LtrModel.builder()
                .modelId("ltr-xgboost-v1")
                .modelName("XGBoost Ranker")
                .modelType("XGBOOST")
                .version("2.1.0")
                .featureWeights(Map.of("xgboost_trees", 100.0))
                .status("READY")
                .build();

        registerModel(defaultLinear);
        registerModel(xgboostModel);
    }

    public void registerModel(LtrModel model) {
        if (model == null || model.getModelId() == null) return;
        models.put(model.getModelId(), model);
        log.info("[LtrModelRegistry] Registered LTR Model '{}' (Type: {}, Status: {})", model.getModelName(), model.getModelType(), model.getStatus());
    }

    public List<LtrModel> getAllModels() {
        return new ArrayList<>(models.values());
    }

    public LtrModel getActiveModel() {
        return models.values().stream()
                .filter(m -> "ACTIVE".equalsIgnoreCase(m.getStatus()))
                .findFirst()
                .orElse(null);
    }
}
