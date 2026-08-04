# ATLAS: ENTERPRISE DISTRIBUTED AI SEARCH PLATFORM
## Phase 1.3: HTML Parser & Document Cleaning Pipeline

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
    ├── atlas-keyword-search/             # BM25 Inverted Index Engine & Swagger (Port 8082)
    ├── atlas-crawler-worker/             # Phase 1.2 Distributed Web Crawler Worker (Port 8083)
    ├── atlas-parser-service/             # Phase 1.3 HTML Parser & Document Cleaner (Port 8085)
    ├── atlas-index-builder-worker/       # Index Segment File Builder & Swagger (Port 8084)
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
