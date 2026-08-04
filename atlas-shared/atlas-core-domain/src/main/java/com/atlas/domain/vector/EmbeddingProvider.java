package com.atlas.domain.vector;

import java.util.List;

public interface EmbeddingProvider {
    float[] embedText(String text);
    List<float[]> embedBatch(List<String> texts);
    String getProviderName();
    int getDimension();
}
