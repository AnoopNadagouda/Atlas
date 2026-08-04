package com.atlas.parserservice.pipeline;

import com.atlas.common.dto.ExtractedLinkDto;
import com.atlas.common.utils.HashUtils;
import com.atlas.common.utils.RegexUtils;
import com.atlas.kafka.events.CleanedDocumentEvent;
import com.atlas.kafka.events.RawDocumentEvent;
import com.atlas.parserservice.config.ParserProperties;
import com.atlas.parserservice.entity.ParseFailureEntity;
import com.atlas.parserservice.entity.ParsedDocumentEntity;
import com.atlas.parserservice.entity.ParsedLinkEntity;
import com.atlas.parserservice.parser.HtmlParserEngine;
import com.atlas.parserservice.parser.LanguageDetector;
import com.atlas.parserservice.parser.SimHashDetector;
import com.atlas.parserservice.repository.ParseFailureRepository;
import com.atlas.parserservice.repository.ParsedDocumentRepository;
import com.atlas.parserservice.repository.ParsedLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentCleanerPipeline {

    private final ParserProperties properties;
    private final HtmlParserEngine parserEngine;
    private final SimHashDetector simHashDetector;
    private final LanguageDetector languageDetector;
    private final ParsedDocumentRepository documentRepository;
    private final ParsedLinkRepository linkRepository;
    private final ParseFailureRepository failureRepository;
    private final CleanedDocumentProducer documentProducer;

    @Transactional
    public void processRawDocument(RawDocumentEvent rawEvent) {
        if (rawEvent == null || rawEvent.getRawHtml() == null) {
            recordFailure(rawEvent != null ? rawEvent.getDocumentId() : "unknown", 
                    rawEvent != null ? rawEvent.getUrl() : "unknown", 
                    "INVALID_INPUT", "Raw HTML document is null");
            return;
        }

        log.info("Processing raw HTML document ID '{}' for URL: {}", rawEvent.getDocumentId(), rawEvent.getNormalizedUrl());

        try {
            // 1. Parse HTML & Remove Boilerplate
            HtmlParserEngine.ParsedHtmlData parsedData = parserEngine.parse(rawEvent.getRawHtml(), rawEvent.getUrl());

            // 2. Validate clean content length
            if (parsedData.getCleanText().length() < properties.getMinContentLength()) {
                log.warn("Document ID '{}' rejected: Clean content length ({}) is below minimum threshold ({})",
                        rawEvent.getDocumentId(), parsedData.getCleanText().length(), properties.getMinContentLength());
                recordFailure(rawEvent.getDocumentId(), rawEvent.getUrl(), "CONTENT_TOO_SHORT", "Clean text length below minimum threshold");
                return;
            }

            // 3. Compute Hashes & Fingerprints
            String contentHash = HashUtils.sha256(parsedData.getCleanText());
            long simhash = simHashDetector.calculateSimHash(parsedData.getCleanText());

            // 4. Perform Duplicate Detection
            boolean isDuplicate = false;
            String duplicateType = "NONE";
            String duplicateOfDocId = null;

            // 4a. Exact Content Hash Check
            Optional<ParsedDocumentEntity> exactMatch = documentRepository.findByContentHash(contentHash);
            if (exactMatch.isPresent()) {
                isDuplicate = true;
                duplicateType = "EXACT";
                duplicateOfDocId = exactMatch.get().getId();
                log.info("Exact duplicate detected for docId '{}' matching original docId '{}'", rawEvent.getDocumentId(), duplicateOfDocId);
            }

            // 4b. Canonical URL Check
            if (!isDuplicate && !parsedData.getCanonicalUrl().isEmpty() && !parsedData.getCanonicalUrl().equalsIgnoreCase(rawEvent.getNormalizedUrl())) {
                Optional<ParsedDocumentEntity> canonicalMatch = documentRepository.findByCanonicalUrl(parsedData.getCanonicalUrl());
                if (canonicalMatch.isPresent()) {
                    isDuplicate = true;
                    duplicateType = "CANONICAL";
                    duplicateOfDocId = canonicalMatch.get().getId();
                    log.info("Canonical duplicate detected for docId '{}' target canonical docId '{}'", rawEvent.getDocumentId(), duplicateOfDocId);
                }
            }

            // 4c. SimHash Near-Duplicate Check
            if (!isDuplicate && simhash != 0L) {
                List<ParsedDocumentEntity> candidates = documentRepository.findAllNonDuplicates();
                for (ParsedDocumentEntity candidate : candidates) {
                    if (simHashDetector.isNearDuplicate(simhash, candidate.getSimhash(), properties.getSimhashHammingThreshold())) {
                        isDuplicate = true;
                        duplicateType = "SIMHASH";
                        duplicateOfDocId = candidate.getId();
                        log.info("SimHash near-duplicate detected for docId '{}' matching candidate docId '{}'", rawEvent.getDocumentId(), duplicateOfDocId);
                        break;
                    }
                }
            }

            // 5. Language Detection
            LanguageDetector.DetectionResult langResult = languageDetector.detectLanguage(parsedData.getCleanText(), parsedData.getHtmlLang());

            // 6. Domain Extraction
            String domain = RegexUtils.extractDomain(rawEvent.getNormalizedUrl());

            // 7. Save Entity to Database
            ParsedDocumentEntity entity = ParsedDocumentEntity.builder()
                    .id(rawEvent.getDocumentId())
                    .jobId(rawEvent.getJobId())
                    .url(rawEvent.getUrl())
                    .normalizedUrl(rawEvent.getNormalizedUrl())
                    .canonicalUrl(parsedData.getCanonicalUrl())
                    .domain(domain)
                    .title(parsedData.getTitle())
                    .description(parsedData.getDescription())
                    .cleanText(parsedData.getCleanText())
                    .language(langResult.getLanguage())
                    .languageConfidence(langResult.getConfidence())
                    .isDuplicate(isDuplicate)
                    .duplicateType(duplicateType)
                    .duplicateOfDocId(duplicateOfDocId)
                    .simhash(simhash)
                    .contentHash(contentHash)
                    .contentLength(parsedData.getCleanText().length())
                    .processedAt(Instant.now())
                    .build();

            documentRepository.save(entity);

            // 8. Save Extracted Links for future PageRank
            if (parsedData.getLinks() != null && !parsedData.getLinks().isEmpty()) {
                for (ExtractedLinkDto link : parsedData.getLinks()) {
                    ParsedLinkEntity linkEntity = ParsedLinkEntity.builder()
                            .docId(rawEvent.getDocumentId())
                            .sourceUrl(link.getSourceUrl())
                            .targetUrl(link.getTargetUrl())
                            .anchorText(link.getAnchorText())
                            .isExternal(link.isExternal())
                            .createdAt(Instant.now())
                            .build();
                    linkRepository.save(linkEntity);
                }
            }

            // 9. Publish CleanedDocumentEvent to Kafka
            List<CleanedDocumentEvent.ExtractedLinkItem> eventLinks = parsedData.getLinks() != null ?
                    parsedData.getLinks().stream().map(l -> CleanedDocumentEvent.ExtractedLinkItem.builder()
                            .sourceUrl(l.getSourceUrl())
                            .targetUrl(l.getTargetUrl())
                            .anchorText(l.getAnchorText())
                            .isExternal(l.isExternal())
                            .build()).toList() : List.of();

            CleanedDocumentEvent cleanedEvent = CleanedDocumentEvent.builder()
                    .documentId(entity.getId())
                    .jobId(entity.getJobId())
                    .url(entity.getUrl())
                    .normalizedUrl(entity.getNormalizedUrl())
                    .canonicalUrl(entity.getCanonicalUrl())
                    .domain(entity.getDomain())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .cleanText(entity.getCleanText())
                    .headings(parsedData.getHeadings())
                    .extractedLinks(eventLinks)
                    .language(entity.getLanguage())
                    .languageConfidence(entity.getLanguageConfidence())
                    .isDuplicate(entity.isDuplicate())
                    .duplicateType(entity.getDuplicateType())
                    .duplicateOfDocId(entity.getDuplicateOfDocId())
                    .simhash(entity.getSimhash())
                    .contentHash(entity.getContentHash())
                    .contentLength(entity.getContentLength())
                    .timestamp(Instant.now())
                    .build();

            documentProducer.publishCleanedDocument(cleanedEvent);
            log.info("Successfully processed and published document ID '{}'", rawEvent.getDocumentId());

        } catch (Exception e) {
            log.error("Failed to process raw HTML document ID '{}': {}", rawEvent.getDocumentId(), e.getMessage(), e);
            recordFailure(rawEvent.getDocumentId(), rawEvent.getUrl(), "PARSE_EXCEPTION", e.getMessage());
        }
    }

    private void recordFailure(String docId, String url, String reason, String errorMessage) {
        ParseFailureEntity failure = ParseFailureEntity.builder()
                .docId(docId)
                .url(url)
                .reason(reason)
                .errorMessage(errorMessage)
                .failedAt(Instant.now())
                .build();
        failureRepository.save(failure);
    }
}
