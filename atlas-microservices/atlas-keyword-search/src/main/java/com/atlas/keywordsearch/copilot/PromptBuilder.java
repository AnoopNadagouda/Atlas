package com.atlas.keywordsearch.copilot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PromptBuilder {

    public String buildPrompt(String question, String context) {
        if (context == null || context.isBlank()) {
            return String.format("""
                    System: You are Atlas AI Search Copilot.
                    Question: %s
                    No context documents available.
                    Hallucination Guardrail: Reply exact text: "I couldn't find enough indexed information to answer confidently."
                    """, question);
        }

        return String.format("""
                System Instructions:
                You are Atlas AI Search Copilot, an enterprise search assistant.
                Answer the user's question accurately using ONLY the provided Context Documents.
                Every factual assertion must include a numbered inline citation matching the document (e.g. [1], [2]).

                Hallucination Guardrail:
                If the provided context does not contain enough information to answer confidently, reply: "I couldn't find enough indexed information to answer confidently."

                User Question:
                %s

                Context Documents:
                %s
                """, question, context);
    }
}
