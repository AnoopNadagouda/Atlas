# ATLAS: ENTERPRISE DISTRIBUTED AI SEARCH PLATFORM
## Phase 1.5: Query Processing & BM25 Search Engine

**Atlas** is an enterprise-grade, cloud-native distributed AI search platform built to crawl, index, rank, and semantically search multi-modal documents at a scale exceeding **1 Billion documents**.

---

### System Architecture & Module Topology

```text
atlas-search/
├── pom.xml                               # Root Maven parent POM (Java 21, Spring Boot 3.2.5)
├── mvnw & mvnw.cmd                       # Maven Wrapper for reproducible builds
├── docker-compose.yml                    # Multi-container orchestration stack
├── .github/workflows/ci.yml              # GitHub Actions CI/CD Pipeline
├── README.md                             # Comprehensive Engineering Documentation
│
├── atlas-shared/                         # Shared Foundation Libraries
│   ├── atlas-core-domain/                # Base domain models, value objects, exceptions
│   ├── atlas-common-dto/                 # Standardized ApiResponse<T>, DTOs & PageResponse
│   ├── atlas-common-utils/               # Centralized String, JSON, Hash, Retry, File, Regex utils
│   ├── atlas-kafka-events/               # Shared Kafka event payloads (CrawlSeed, RawDoc, CleanDoc)
│   ├── atlas-logging/                    # MDC Correlation ID (X-Correlation-ID) tracing & version controller
│   └── atlas-test-support/               # BaseUnitTest, BaseIntegrationTest & Testcontainers setup
│
├── atlas-infrastructure/                 # Core Infrastructure Services
│   ├── atlas-config-service/             # Spring Cloud Config Server (Port 8888)
│   └── atlas-api-gateway/                # Spring Cloud API Gateway with Cluster Health (Port 8080)
│
└── atlas-microservices/                  # Application Microservices
    ├── atlas-search-gateway/             # Query Router, Swagger & Redis Cache (Port 8081)
    ├── atlas-keyword-search/             # Phase 1.5 Query Processing & BM25 Search Engine (Port 8082)
    ├── atlas-crawler-worker/             # Phase 1.2 Distributed Web Crawler Worker (Port 8083)
    ├── atlas-parser-service/             # Phase 1.3 HTML Parser & Document Cleaner (Port 8085)
    ├── atlas-index-builder-worker/       # Phase 1.4 Custom Inverted Index Engine (Port 8084)
    └── atlas-ui/                         # Glassmorphic React TypeScript UI (Port 3000)
```

---

### Phase 1.2 Distributed Web Crawler Components

1. **Crawl Scheduler (`CrawlSchedulerService`)**:
   - Manages crawl job creation, execution state (`RUNNING`, `PAUSED`, `CANCELLED`, `COMPLETED`), and lifecycle control APIs.
   - Configurable limits: `maxDepth`, `maxPages`, `maxConcurrency`, `timeoutSeconds`, `userAgent`.
   - Persists crawl progress and URL status in PostgreSQL (`crawl_jobs` and `crawl_urls`).

2. **URL Frontier (`UrlFrontier` & `UrlNormalizer`)**:
   - Concurrent priority-queue URL scheduler enforcing URL deduplication (`jobId:normalizedUrl`).
   - Normalization rules: Strips fragments (`#...`), standardizes HTTP/HTTPS ports (80/443), strips trailing slashes, resolves relative URLs.

3. **robots.txt & sitemap.xml Support (`RobotsManager` & `SitemapParser`)**:
   - Domain-level `robots.txt` parser evaluating `User-Agent`, `Allow`, `Disallow`, `Crawl-delay`, and `Sitemap` rules.
   - Automatic `/sitemap.xml` discovery and `<loc>` seed URL extraction.

4. **Resilient HTTP Client (`ResilientFetcher`)**:
   - Production Java 21 `HttpClient` supporting connection pooling, GZIP decompression, redirect handling, and exponential backoff retry policies.

5. **Kafka Raw HTML Pipeline (`RawDocumentProducer`)**:
   - Publishes raw HTML pages to Kafka topic `crawl.raw.documents` containing: `documentId`, `jobId`, `url`, `normalizedUrl`, `depth`, `httpStatus`, `contentType`, `responseHeaders`, `rawHtml`, and `fetchTimestamp`.

---

### Crawl Management REST APIs

| HTTP Method | Endpoint Path | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/crawl/jobs` | Submit and dispatch new crawl job |
| `GET` | `/api/v1/crawl/jobs` | List all registered crawl jobs |
| `GET` | `/api/v1/crawl/jobs/{id}` | Fetch detailed crawl job status |
| `POST` | `/api/v1/crawl/jobs/{id}/pause` | Pause an active crawl job |
| `POST` | `/api/v1/crawl/jobs/{id}/resume` | Resume a paused crawl job |
| `POST` | `/api/v1/crawl/jobs/{id}/cancel` | Cancel an active crawl job |
| `GET` | `/api/v1/crawl/jobs/{id}/statistics` | Fetch job statistics (pages/sec, queued URLs) |
| `GET` | `/api/v1/crawl/jobs/{id}/urls` | Fetch paged list of crawled URLs |

---

### Phase 1.3 HTML Parser & Document Cleaner Components

1. **HTML Parsing & Boilerplate Removal (`HtmlParserEngine`)**:
   - JSoup-powered HTML DOM extraction removing non-content nodes (`<script>`, `<style>`, `<nav>`, `<footer>`, `<aside>`, `.ad`, `.banner`, `.cookie`).
   - Extracts document metadata: `title`, `description`, `keywords`, `canonicalUrl`, `headings` (H1-H6), and `cleanText`.

2. **Text Normalization**:
   - Unicode NFC normalization, HTML entity decoding, control character removal, and whitespace collapse.

3. **Multi-Strategy Duplicate Detection (`DocumentCleanerPipeline`)**:
   - **Exact Content Hash**: SHA-256 hash comparison against stored document hashes.
   - **Canonical URL Matching**: Identifies alternate URLs pointing to identical canonical sources.
   - **Near-Duplicate Detection (SimHash)**: Calculates 64-bit SimHash fingerprints and checks Hamming distance ($\le 3$ threshold).

4. **Language Detection & Link Extraction (`LanguageDetector` & `ParsedLinkEntity`)**:
   - Heuristic language detection returning primary language ISO code and confidence score.
   - Extracts all valid `<a href="...">` hyperlinks (internal/external) into `parsed_links` database table for future PageRank calculations.

5. **Kafka Publishing (`CleanedDocumentProducer`)**:
   - Consumes from `crawl.raw.documents` and publishes cleaned structured events to `document.cleaned` and `crawl.cleaned.documents`.

---

### Document Parser REST APIs

| HTTP Method | Endpoint Path | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/parser/statistics` | Real-time parser statistics (processed, duplicates, duplicate rate) |
| `GET` | `/api/v1/parser/documents/{id}` | Fetch parsed document metadata & preview |
| `GET` | `/api/v1/parser/duplicates` | Fetch paged list of detected duplicates |
| `GET` | `/api/v1/parser/failures` | Fetch paged list of parsing validation failures |
| `GET` | `/api/v1/parser/health` | Service health status |

---

### Phase 1.4 Custom Inverted Index Engine Components

1. **Text Analysis Engine (`TextAnalyzer`)**:
   - **`Tokenizer`**: Splits text into tokens preserving hyphens (`cloud-native`) and numbers.
   - **`NormalizerEngine`**: Lowercase & Unicode NFC normalization.
   - **`StopWordFilter`**: Language-aware stop word filter (removes common English stop words `the`, `is`, `and`, etc.).
   - **`PorterStemmer`**: Extensible Porter Stemmer algorithm reducing word variations to base stems.

2. **Posting List Structure (`PostingList` & `Posting`)**:
   - Maintains term-to-posting dictionary:
     - Document Frequency ($df$): Number of unique documents containing the term.
     - Collection Frequency ($cf$): Total occurrences across all documents.
     - Postings Entry: `docId`, Term Frequency ($tf$), exact token `positions` array, and `fieldFlags` (`TITLE`, `BODY`, `HEADING`).

3. **Segmented Index Persistence (`SegmentWriter`)**:
   - Accumulates in-memoryPosting dictionary batches and serializes immutable segments to disk (`./data/index_segments/{segment_id}/`).
   - On-Disk Storage Format:
     - `dict.json`: Vocabulary terms with document frequencies ($df$) and collection frequencies ($cf$).
     - `postings.json`: Full posting lists with document frequencies, positions, and field bitmasks.
     - `segment_meta.json`: Segment ID, document count, vocabulary size, total term count, createdAt.

4. **Index Coordinator & Metadata (`IndexCoordinatorService`)**:
   - Consumes clean documents from Kafka topic `document.cleaned`.
   - Coordinates multi-field indexing (`TITLE`, `BODY`, `HEADING`), batch segment flushes, and persists segment metadata to PostgreSQL.

---

### Inverted Index REST APIs

| HTTP Method | Endpoint Path | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/index/build` | Trigger manual segment flush to disk |
| `GET` | `/api/v1/index/statistics` | Collection statistics (total docs, terms, vocab size, segments) |
| `GET` | `/api/v1/index/segments` | List all active immutable index segments |
| `GET` | `/api/v1/index/segments/{id}` | Fetch detailed index segment metadata |
| `GET` | `/api/v1/index/status` | Inverted index engine service status |

---

### Phase 1.5 Query Processing & BM25 Search Engine Components

1. **Query Processing & Parser (`QueryParser` & `QueryNormalizer`)**:
   - Parses single-word, multi-word, quoted phrases (`"distributed search"`), and Boolean operators (`AND`, `OR`, `NOT`).
   - Normalizes terms matching indexing normalization (Unicode NFC, lowercasing, stop words, Porter stemming).

2. **Index Lookup Engine (`SegmentLookupEngine`)**:
   - Traverses active on-disk index segments (`./data/index_segments/`).
   - Fetches posting lists for query terms, computes document frequencies ($df$) and term frequencies ($tf$).
   - Evaluates boolean constraints (`AND`, `OR`, `NOT`) and positional phrase constraints.

3. **BM25 Ranking Engine (`BM25Ranker`)**:
   - Robertson-Spärck Jones BM25 formula implemented from scratch:
     $$\text{IDF}(t) = \ln \left( \frac{N - n(t) + 0.5}{n(t) + 0.5} + 1 \right)$$
     $$\text{Score}(D, t) = \text{IDF}(t) \cdot \frac{f(t, D) \cdot (k_1 + 1)}{f(t, D) + k_1 \cdot \left(1 - b + b \cdot \frac{|D|}{\text{avgdl}}\right)} \cdot \text{fieldBoost}$$
   - Externalized parameters ($k_1 = 1.2$, $b = 0.75$, `titleBoost = 2.0`, `headingBoost = 1.5`, `bodyBoost = 1.0`).

4. **Snippet Generator & Redis Cache (`SnippetGenerator` & `SearchCacheService`)**:
   - Extracts relevant passage with highest term density and highlights terms (`<b>term</b>`).
   - Redis query caching with configurable TTL (`atlas.search.cacheTtlSeconds`) and cache statistics tracking.

---

### Keyword Search REST APIs

| HTTP Method | Endpoint Path | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/search?q={query}&page=0&size=10` | Execute BM25 keyword search query |
| `GET` | `/api/v1/search/{query}` | Execute path-based search query |
| `POST` | `/api/v1/search/query` | Execute structured SearchRequest payload |
| `GET` | `/api/v1/search/statistics` | Search engine latency, query count & cache metrics |
| `GET` | `/api/v1/search/cache` | Redis search cache stats (hits, misses, hit ratio) |
| `DELETE` | `/api/v1/search/cache` | Clear and invalidate Redis search cache entries |

---

### Shared Library Capabilities

#### 1. Centralized Utilities (`atlas-common-utils`)
- **`StringUtils`**: Null-safe operations, slug generation (`toSlug`), string truncation.
- **`JsonUtils`**: Thread-safe `ObjectMapper` with Java 8 time module registered.
- **`HashUtils`**: SHA-256 digesting and 64-bit SimHash near-duplicate fingerprinting.
- **`RetryUtils`**: Functional backoff execution supplier.
- **`AtlasConstants`**: Unified header names (`X-Correlation-ID`), Kafka topic constants, ISO date formats.

#### 2. Distributed Tracing & Logging (`atlas-logging`)
- Every HTTP request entering **API Gateway** receives a unique `X-Correlation-ID`.
- Propagates seamlessly via MDC context through:
  $$\text{Client} \xrightarrow{X-\text{Correlation-ID}} \text{API Gateway} \xrightarrow{\text{REST}} \text{Microservices} \xrightarrow{\text{Headers}} \text{Kafka Records} \xrightarrow{\text{Logs}} \text{Background Workers}$$

#### 3. Standardized Testing Infrastructure (`atlas-test-support`)
- **`BaseUnitTest`**: Pure unit testing base extending Mockito.
- **`BaseIntegrationTest`**: `@SpringBootTest` base class with test property overrides.
- **`AtlasContainers`**: Pre-configured Testcontainers for PostgreSQL, Redis, and Apache Kafka.
- **`TestDataFactory`**: Centralized test DTO generation.

---

### Key Operational Endpoints

#### 1. Cluster Health Aggregator Endpoint
```bash
GET http://localhost:8080/cluster/health
```
Returns unified health state across microservices and datastores (PostgreSQL, Redis, Kafka).

#### 2. Service Version & Build Metadata Endpoint
```bash
GET http://localhost:8080/version
GET http://localhost:8081/version
```
Returns:
```json
{
  "success": true,
  "data": {
    "serviceName": "atlas-search-gateway",
    "version": "1.0.0-SNAPSHOT",
    "buildTime": "2026-08-04T11:55:00.000Z",
    "gitCommit": "git-7a3b19f",
    "branch": "main",
    "javaVersion": "21.0.11",
    "springBootVersion": "3.2.5"
  }
}
```

#### 3. OpenAPI / Swagger Documentation UI
Available on all backend REST microservices:
- **Search Gateway**: `http://localhost:8081/swagger-ui.html`
- **Keyword Search**: `http://localhost:8082/swagger-ui.html`
- **Crawler Worker**: `http://localhost:8083/swagger-ui.html`
- **Index Builder**: `http://localhost:8084/swagger-ui.html`

---

### Local Development & Docker Commands

#### 1. Build and Run Tests using Maven Wrapper
```bash
./mvnw clean test
```

#### 2. Launch Entire Distributed System via Docker Compose
```bash
docker compose up --build -d
```

#### 3. Verify Container Health Metrics
```bash
docker compose ps
```

---

### CI/CD Pipeline (`.github/workflows/ci.yml`)
- Triggers automatically on push/pull_request to `main`/`master`.
- Validates JDK 21 build, executes unit and integration tests, caches Maven dependencies, and verifies Docker image builds.
