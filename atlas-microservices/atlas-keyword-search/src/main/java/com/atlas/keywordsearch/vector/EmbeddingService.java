package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.EmbeddingProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingProvider embeddingProvider;

    public float[] generateEmbedding(String text) {
        log.info("Generating embedding for text via provider: {}", embeddingProvider.getProviderName());
        return embeddingProvider.embedText(text);
    }

    public List<float[]> generateBatchEmbeddings(List<String> texts) {
        log.info("Generating batch embeddings for {} items via provider: {}", texts.size(), embeddingProvider.getProviderName());
        return embeddingProvider.embedBatch(texts);
    }
}
