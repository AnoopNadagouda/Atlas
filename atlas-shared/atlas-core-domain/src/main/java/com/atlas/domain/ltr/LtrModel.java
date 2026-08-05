package com.atlas.domain.ltr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LtrModel {
    private String modelId;
    private String modelName;
    private String modelType; // LINEAR, XGBOOST, LAMBDAMART, ONNX
    private String version;
    private Map<String, Double> featureWeights;
    private String status;
}
