package com.atlas.domain.copilot;

import java.util.function.Consumer;

public interface LlmProvider {

    String getProviderName();

    String generateAnswer(String prompt);

    void streamAnswer(String prompt, Consumer<String> tokenConsumer);

    boolean isHealthy();
}
