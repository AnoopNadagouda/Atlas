package com.atlas.keywordsearch.engine;

import com.atlas.keywordsearch.config.SearchProperties;
import com.atlas.keywordsearch.query.ParsedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BM25Ranker {

    private final SearchProperties properties;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoredDocument {
        private String docId;
        private double bm25Score;
        private Set<String> matchedTerms;
        private Set<String> matchedFields;
        private Map<String, Double> termContributions;
    }

    public List<ScoredDocument> rank(ParsedQuery query, SegmentLookupEngine.LookupResult lookupResult) {
        Map<String, ScoredDocument> candidateDocs = new HashMap<>();

        int totalDocs = Math.max(1, lookupResult.getTotalCollectionDocs());
        double avgDl = Math.max(1.0, lookupResult.getAverageDocLength());
        double k1 = properties.getK1();
        double b = properties.getB();

        Map<String, Map<String, SegmentLookupEngine.PostingEntry>> termDocPostings = lookupResult.getTermDocPostings();
        Map<String, Integer> termDFs = lookupResult.getTermDocFrequencies();
        Map<String, Integer> docLengths = lookupResult.getDocLengths();

        for (String term : query.getNormalizedTerms()) {
            if (!termDocPostings.containsKey(term)) continue;

            int df = termDFs.getOrDefault(term, 0);
            double idf = Math.log((totalDocs - df + 0.5) / (df + 0.5) + 1.0);

            Map<String, SegmentLookupEngine.PostingEntry> postings = termDocPostings.get(term);
            for (Map.Entry<String, SegmentLookupEngine.PostingEntry> entry : postings.entrySet()) {
                String docId = entry.getKey();
                SegmentLookupEngine.PostingEntry posting = entry.getValue();

                int tf = posting.getTermFrequency();
                int docLen = docLengths.getOrDefault(docId, 100);

                // Field Boost calculation
                double fieldBoost = properties.getBodyBoost();
                if (posting.getFieldFlags() != null) {
                    if (posting.getFieldFlags().contains("TITLE")) fieldBoost = Math.max(fieldBoost, properties.getTitleBoost());
                    if (posting.getFieldFlags().contains("HEADING")) fieldBoost = Math.max(fieldBoost, properties.getHeadingBoost());
                }

                double tfWeight = (tf * (k1 + 1.0)) / (tf + k1 * (1.0 - b + b * (docLen / avgDl)));
                double termScore = idf * tfWeight * fieldBoost;

                ScoredDocument doc = candidateDocs.computeIfAbsent(docId, id -> ScoredDocument.builder()
                        .docId(id)
                        .bm25Score(0.0)
                        .matchedTerms(new HashSet<>())
                        .matchedFields(new HashSet<>())
                        .termContributions(new HashMap<>())
                        .build());

                doc.setBm25Score(doc.getBm25Score() + termScore);
                doc.getMatchedTerms().add(term);
                if (posting.getFieldFlags() != null) {
                    doc.getMatchedFields().addAll(posting.getFieldFlags());
                }
                doc.getTermContributions().put(term, termScore);
            }
        }

        // Apply MUST_NOT filtering
        for (String notTerm : query.getMustNotTerms()) {
            if (termDocPostings.containsKey(notTerm)) {
                Set<String> notDocIds = termDocPostings.get(notTerm).keySet();
                for (String notId : notDocIds) {
                    candidateDocs.remove(notId);
                }
            }
        }

        List<ScoredDocument> sortedList = new ArrayList<>(candidateDocs.values());
        sortedList.sort((d1, d2) -> Double.compare(d2.getBm25Score(), d1.getBm25Score()));

        return sortedList;
    }
}
