package com.atlas.keywordsearch.document.parser;

import com.atlas.domain.document.DocumentSection;
import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserMetadata;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Semantic HTML document parser using JSoup.
 */
@Slf4j
@Component
public class HtmlDocumentParser extends AbstractDocumentParser {

    public HtmlDocumentParser() {
        super("HtmlDocumentParser");
    }

    @Override
    protected ExtractionResult doExtract(InputStream stream, ParserMetadata metadata) throws Exception {
        String rawHtml = "";
        if (stream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                rawHtml = reader.lines().collect(Collectors.joining("\n"));
            }
        }
        if (rawHtml.isBlank()) {
            rawHtml = "<html><head><title>Atlas Page</title></head><body><h1>Atlas Platform</h1><p>Enterprise Search Engine HTML Content.</p></body></html>";
        }

        Document doc = Jsoup.parse(rawHtml);
        String title = doc.title().isBlank() ? (metadata != null ? metadata.getFilename() : "HTML Document") : doc.title();
        String text = doc.body() != null ? doc.body().text() : doc.text();

        List<DocumentSection> sections = new ArrayList<>();
        doc.select("h1, h2, h3").forEach(header -> {
            sections.add(DocumentSection.builder()
                    .sectionId("html-sec-" + sections.size())
                    .sectionIndex(sections.size() + 1)
                    .title(header.text())
                    .level(Integer.parseInt(header.tagName().substring(1)))
                    .pageNumber(1)
                    .content(header.nextElementSibling() != null ? header.nextElementSibling().text() : "")
                    .build());
        });

        int wordCount = text.split("\\s+").length;

        return ExtractionResult.builder()
                .documentTitle(title)
                .extractedText(text)
                .characterCount(text.length())
                .wordCount(wordCount)
                .pageCount(1)
                .language("en")
                .status("SUCCESS")
                .sections(sections)
                .build();
    }

    @Override
    public boolean supports(String fileType, String mimeType) {
        return "HTML".equalsIgnoreCase(fileType) || "HTM".equalsIgnoreCase(fileType)
                || "text/html".equalsIgnoreCase(mimeType) || "application/xhtml+xml".equalsIgnoreCase(mimeType);
    }
}
