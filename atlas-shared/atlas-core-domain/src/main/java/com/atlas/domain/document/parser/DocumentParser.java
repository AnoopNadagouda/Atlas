package com.atlas.domain.document.parser;

import java.io.InputStream;

/**
 * Universal interface for document parsers.
 */
public interface DocumentParser {

    /**
     * Extracts text, sections, fragments, and metadata from the document stream.
     *
     * @param stream   Input file byte stream
     * @param metadata Technical metadata about the file
     * @return ExtractionResult containing parsed document elements
     */
    ExtractionResult extract(InputStream stream, ParserMetadata metadata);

    /**
     * Checks health and availability of the parser engine.
     *
     * @return ParserHealth status
     */
    ParserHealth checkHealth();

    /**
     * Retrieves statistics on parser operations and performance.
     *
     * @return ParserStatistics metrics
     */
    ParserStatistics getStatistics();

    /**
     * Determines whether this parser supports the specified file or MIME type.
     *
     * @param fileType Upper-case document type string (e.g. PDF, DOCX)
     * @param mimeType Standard MIME type string (e.g. application/pdf)
     * @return true if supported, false otherwise
     */
    boolean supports(String fileType, String mimeType);
}
