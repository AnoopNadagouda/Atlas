package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.EmbeddingProvider;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NoOpEmbeddingProvider implements EmbeddingProvider {

    @Override
    public float[] embedText(String text) {
        return new float[384]; // Standard 384-dim placeholder embedding
    }

    @Override
    public List<float[]> embedBatch(List<String> texts) {
        if (texts == null) return Collections.emptyList();
        return texts.stream().map(this::embedText).toList();
    }

    @Override
    public String getProviderName() {
        return "NoOpEmbeddingProvider-v1.0";
    }

    @Override
    public int getDimension() {
        return 384;
    }
}
