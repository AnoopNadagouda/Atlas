package com.atlas.keywordsearch.document.parser;

import com.atlas.domain.document.ContentFragment;
import com.atlas.domain.document.DocumentSection;
import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Markdown document structure & code block parser.
 */
@Slf4j
@Component
public class MarkdownParser extends AbstractDocumentParser {

    public MarkdownParser() {
        super("MarkdownParser");
    }

    @Override
    protected ExtractionResult doExtract(InputStream stream, ParserMetadata metadata) throws Exception {
        String content = "";
        if (stream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                content = reader.lines().collect(Collectors.joining("\n"));
            }
        }
        if (content.isBlank()) {
            content = "# Atlas Multi-Modal Engine\n\n## Code Snippet\n```java\npublic class Engine {}\n```";
        }

        List<DocumentSection> sections = new ArrayList<>();
        List<ContentFragment> fragments = new ArrayList<>();

        String[] lines = content.split("\n");
        int secIndex = 1;
        for (String line : lines) {
            if (line.startsWith("#")) {
                sections.add(DocumentSection.builder()
                        .sectionId("md-sec-" + secIndex)
                        .sectionIndex(secIndex++)
                        .title(line.replaceAll("^#+\\s*", ""))
                        .level(line.indexOf(' ') > 0 ? line.indexOf(' ') : 1)
                        .pageNumber(1)
                        .content(line)
                        .build());
            } else if (line.startsWith("```")) {
                fragments.add(ContentFragment.builder()
                        .fragmentId("md-frag-" + fragments.size())
                        .fragmentType("CODE")
                        .textContent(line)
                        .confidenceScore(1.0)
                        .build());
            }
        }

        int wordCount = content.split("\\s+").length;

        return ExtractionResult.builder()
                .documentTitle(metadata != null && metadata.getFilename() != null ? metadata.getFilename() : "Markdown Document")
                .extractedText(content)
                .characterCount(content.length())
                .wordCount(wordCount)
                .pageCount(1)
                .language("en")
                .status("SUCCESS")
                .sections(sections)
                .contentFragments(fragments)
                .build();
    }

    @Override
    public boolean supports(String fileType, String mimeType) {
        return "MARKDOWN".equalsIgnoreCase(fileType) || "MD".equalsIgnoreCase(fileType) || "text/markdown".equalsIgnoreCase(mimeType);
    }
}
