package com.atlas.common.utils;

public final class AtlasConstants {

    private AtlasConstants() {}

    // HTTP Header Constants
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String TRACE_ID_HEADER = "X-Trace-ID";

    // Kafka Topic Names
    public static final String TOPIC_CRAWL_URLS_SEED = "crawl.urls.seed";
    public static final String TOPIC_CRAWL_RAW_DOCUMENTS = "crawl.raw.documents";
    public static final String TOPIC_DOCUMENT_CLEANED = "document.cleaned";
    public static final String TOPIC_CRAWL_PARSED_DOCUMENTS = "crawl.parsed.documents";
    public static final String TOPIC_CRAWL_CLEANED_DOCUMENTS = "crawl.cleaned.documents";
    public static final String TOPIC_INDEX_BUILD_TASKS = "index.build.tasks";
    public static final String TOPIC_SEARCH_QUERY_EXECUTED = "search.query.executed";
    public static final String TOPIC_CRAWL_URLS_DLQ = "crawl.urls.dlq";

    // Default Configuration Thresholds
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_TIMEZONE = "UTC";
    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
