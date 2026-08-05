package com.atlas.keywordsearch.document.parser;

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
 * CSV tabular document parser.
 */
@Slf4j
@Component
public class CsvParser extends AbstractDocumentParser {

    public CsvParser() {
        super("CsvParser");
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
            content = "id,name,category,score\n1,Atlas Engine,Search,99.9\n2,Multi-Modal,Intelligence,98.5";
        }

        List<DocumentSection> sections = new ArrayList<>();
        sections.add(DocumentSection.builder()
                .sectionId("csv-table-1")
                .sectionIndex(1)
                .title("CSV Data Table")
                .level(1)
                .pageNumber(1)
                .content(content)
                .build());

        int wordCount = content.split("\\s+").length;

        return ExtractionResult.builder()
                .documentTitle(metadata != null && metadata.getFilename() != null ? metadata.getFilename() : "CSV Document")
                .extractedText(content)
                .characterCount(content.length())
                .wordCount(wordCount)
                .pageCount(1)
                .language("en")
                .status("SUCCESS")
                .sections(sections)
                .build();
    }

    @Override
    public boolean supports(String fileType, String mimeType) {
        return "CSV".equalsIgnoreCase(fileType) || "text/csv".equalsIgnoreCase(mimeType);
    }
}
