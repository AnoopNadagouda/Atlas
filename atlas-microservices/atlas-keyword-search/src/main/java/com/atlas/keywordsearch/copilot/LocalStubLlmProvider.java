package com.atlas.keywordsearch.copilot;

import com.atlas.domain.copilot.LlmProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component("localStubLlmProvider")
public class LocalStubLlmProvider implements LlmProvider {

    @Override
    public String getProviderName() {
        return "Local-RAG-Engine (MiniLM / Llama-3-Stub)";
    }

    @Override
    public String generateAnswer(String prompt) {
        log.info("Generating RAG grounded answer locally for prompt length: {}", prompt.length());
        if (!prompt.contains("Context Documents:")) {
            return "I couldn't find enough indexed information to answer confidently.";
        }

        return "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF).";
    }

    @Override
    public void streamAnswer(String prompt, Consumer<String> tokenConsumer) {
        log.info("Streaming RAG grounded answer locally for prompt length: {}", prompt.length());
        String fullAnswer = generateAnswer(prompt);
        String[] words = fullAnswer.split(" ");

        for (String word : words) {
            tokenConsumer.accept(word + " ");
            try {
                Thread.sleep(30); // Simulate streaming token generation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
