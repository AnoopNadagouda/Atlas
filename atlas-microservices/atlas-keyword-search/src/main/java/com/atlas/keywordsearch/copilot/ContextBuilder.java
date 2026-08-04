package com.atlas.keywordsearch.copilot;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasCopilotProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContextBuilder {

    private final AtlasCopilotProperties copilotProperties;

    public String buildContext(List<SearchResultDto> results) {
        if (results == null || results.isEmpty()) {
            return "";
        }

        StringBuilder contextBuilder = new StringBuilder();
        Set<String> seenDocIds = new HashSet<>();
        int approxCharBudget = copilotProperties.getMaxContextTokens() * 4; // Approx 4 chars per token
        int currentLength = 0;
        int citationIndex = 1;

        for (SearchResultDto doc : results) {
            if (doc.getId() == null || seenDocIds.contains(doc.getId())) {
                continue;
            }
            seenDocIds.add(doc.getId());

            String snippet = doc.getSnippet() != null ? doc.getSnippet().trim() : "";
            String entry = String.format("[%d] Document ID: %s | Title: %s | URL: %s\nSnippet: %s\n\n",
                    citationIndex++,
                    doc.getId(),
                    doc.getTitle() != null ? doc.getTitle() : "Untitled",
                    doc.getUrl() != null ? doc.getUrl() : "",
                    snippet);

            if (currentLength + entry.length() > approxCharBudget) {
                log.info("Reached maximum context token budget of {} tokens ({}/{} chars)",
                        copilotProperties.getMaxContextTokens(), currentLength, approxCharBudget);
                break;
            }

            contextBuilder.append(entry);
            currentLength += entry.length();
        }

        return contextBuilder.toString().trim();
    }
}
