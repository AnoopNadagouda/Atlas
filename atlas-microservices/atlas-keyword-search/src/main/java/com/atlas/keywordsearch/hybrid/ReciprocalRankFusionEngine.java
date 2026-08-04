package com.atlas.keywordsearch.hybrid;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasHybridProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReciprocalRankFusionEngine {

    private final AtlasHybridProperties hybridProperties;

    public List<SearchResultDto> fuse(List<SearchResultDto> bm25Results, List<SearchResultDto> semanticResults) {
        int k = hybridProperties.getRrfK();
        Map<String, SearchResultDto> mergedMap = new LinkedHashMap<>();

        // Map BM25 Ranks
        if (bm25Results != null) {
            for (int rank = 0; rank < bm25Results.size(); rank++) {
                SearchResultDto doc = bm25Results.get(rank);
                int oneBasedRank = rank + 1;

                double rrfContribution = 1.0 / (k + oneBasedRank);

                SearchResultDto merged = mergedMap.computeIfAbsent(doc.getId(), id -> SearchResultDto.builder()
                        .id(doc.getId())
                        .url(doc.getUrl())
                        .title(doc.getTitle())
                        .snippet(doc.getSnippet())
                        .domain(doc.getDomain())
                        .matchedTerms(new HashSet<>())
                        .matchedFields(new HashSet<>())
                        .retrievalSources(new HashSet<>())
                        .termContributions(new HashMap<>())
                        .metadata(doc.getMetadata() != null ? doc.getMetadata() : new HashMap<>())
                        .build());

                merged.setBm25Score(doc.getBm25Score());
                merged.setKeywordRank(oneBasedRank);
                merged.setRrfScore(merged.getRrfScore() + rrfContribution);
                merged.getRetrievalSources().add("KEYWORD");
                if (doc.getMatchedTerms() != null) merged.getMatchedTerms().addAll(doc.getMatchedTerms());
                if (doc.getMatchedFields() != null) merged.getMatchedFields().addAll(doc.getMatchedFields());
            }
        }

        // Map Semantic Ranks
        if (semanticResults != null) {
            for (int rank = 0; rank < semanticResults.size(); rank++) {
                SearchResultDto doc = semanticResults.get(rank);
                int oneBasedRank = rank + 1;

                double rrfContribution = 1.0 / (k + oneBasedRank);

                SearchResultDto merged = mergedMap.computeIfAbsent(doc.getId(), id -> SearchResultDto.builder()
                        .id(doc.getId())
                        .url(doc.getUrl())
                        .title(doc.getTitle())
                        .snippet(doc.getSnippet())
                        .domain(doc.getDomain())
                        .matchedTerms(new HashSet<>())
                        .matchedFields(new HashSet<>())
                        .retrievalSources(new HashSet<>())
                        .termContributions(new HashMap<>())
                        .metadata(doc.getMetadata() != null ? doc.getMetadata() : new HashMap<>())
                        .build());

                merged.setVectorScore(doc.getVectorScore());
                merged.setSemanticRank(oneBasedRank);
                merged.setRrfScore(merged.getRrfScore() + rrfContribution);
                merged.getRetrievalSources().add("SEMANTIC");
                if (doc.getMatchedTerms() != null) merged.getMatchedTerms().addAll(doc.getMatchedTerms());
                if (merged.getSnippet() == null || merged.getSnippet().isBlank()) {
                    merged.setSnippet(doc.getSnippet());
                }
            }
        }

        // Sort by RRF score descending
        List<SearchResultDto> fusedList = new ArrayList<>(mergedMap.values());
        fusedList.sort((d1, d2) -> Double.compare(d2.getRrfScore(), d1.getRrfScore()));

        // Assign final rank & composite score
        for (int i = 0; i < fusedList.size(); i++) {
            SearchResultDto item = fusedList.get(i);
            item.setFinalRank(i + 1);
            item.setScore(item.getRrfScore());
            if (item.getRetrievalSources().contains("KEYWORD") && item.getRetrievalSources().contains("SEMANTIC")) {
                item.getRetrievalSources().add("HYBRID");
            }
        }

        log.info("Reciprocal Rank Fusion completed for {} merged documents (k={})", fusedList.size(), k);
        return fusedList;
    }
}
