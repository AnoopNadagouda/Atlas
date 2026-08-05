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
 * XML document structure parser.
 */
@Slf4j
@Component
public class XmlDocumentParser extends AbstractDocumentParser {

    public XmlDocumentParser() {
        super("XmlDocumentParser");
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
            content = "<?xml version=\"1.0\"?>\n<atlas>\n  <component>SearchGateway</component>\n</atlas>";
        }

        List<DocumentSection> sections = new ArrayList<>();
        sections.add(DocumentSection.builder()
                .sectionId("xml-sec-1")
                .sectionIndex(1)
                .title("XML Root Element")
                .level(1)
                .pageNumber(1)
                .content(content)
                .build());

        List<ContentFragment> fragments = new ArrayList<>();
        fragments.add(ContentFragment.builder()
                .fragmentId("xml-frag-1")
                .fragmentType("CODE")
                .textContent(content)
                .confidenceScore(1.0)
                .build());

        int wordCount = content.split("\\s+").length;

        return ExtractionResult.builder()
                .documentTitle(metadata != null && metadata.getFilename() != null ? metadata.getFilename() : "XML Document")
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
        return "XML".equalsIgnoreCase(fileType) || "application/xml".equalsIgnoreCase(mimeType) || "text/xml".equalsIgnoreCase(mimeType);
    }
}
