# ATLAS: ENTERPRISE DISTRIBUTED AI SEARCH PLATFORM
## Phase 1 Production Release (v1.0.0) — Distributed Keyword Search Engine

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/AnoopNadagouda/Atlas)
[![Release](https://img.shields.io/badge/release-v1.0.0-blue.svg)](https://github.com/AnoopNadagouda/Atlas/releases/tag/v1.0.0)
[![Java 21](https://img.shields.io/badge/java-21-orange.svg)](https://oracle.com/java)
[![Spring Boot](https://img.shields.io/badge/spring--boot-3.2.5-green.svg)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/kafka-3.6.2-black.svg)](https://kafka.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/postgresql-16-blue.svg)](https://postgresql.org)
[![Redis](https://img.shields.io/badge/redis-7.2-red.svg)](https://redis.io)

**Atlas** is an enterprise-grade, cloud-native distributed search platform built to crawl, clean, index, rank, and search multi-modal web documents at a scale exceeding **1 Billion documents**.

Designed from scratch following Clean Architecture, Event-Driven Architecture, and Domain-Driven Design (DDD), Atlas features a custom-built Inverted Index Engine and BM25 Search Engine without relying on third-party search platforms such as Lucene, Elasticsearch, Solr, or OpenSearch.

---

## 🏛️ System Architecture

Atlas is composed of decoupled, event-driven Spring Boot microservices orchestrated through Spring Cloud API Gateway, Apache Kafka, PostgreSQL, Redis, and a glassmorphic React UI.

```mermaid
graph TD
    Client["💻 React UI / Web Clients"] -->|HTTP / REST| Gateway["🚪 Atlas API Gateway (Port 8080)"]
    
    subgraph "Microservices Cluster"
        Gateway -->|Route /api/v1/crawl| Crawler["🕸️ Crawl Worker Service (Port 8083)"]
        Gateway -->|Route /api/v1/parser| Parser["🧹 HTML Parser Service (Port 8085)"]
        Gateway -->|Route /api/v1/index| Indexer["⚙️ Index Builder Worker (Port 8084)"]
        Gateway -->|Route /api/v1/search| Search["🔍 Keyword Search Service (Port 8082)"]
    end

    subgraph "Event Backbone & Messaging"
        Crawler -->|Publish Raw HTML| K1["📨 Kafka: crawl.raw.documents"]
        K1 -->|Consume Raw HTML| Parser
        Parser -->|Publish Clean Documents| K2["📨 Kafka: document.cleaned"]
        K2 -->|Consume Clean Documents| Indexer
    end

    subgraph "Persistence & Caching Infrastructure"
        Crawler -->|State & Jobs| DB[("🐘 PostgreSQL (atlas_db)")]
        Parser -->|Parsed Docs & Links| DB
        Indexer -->|Segment Metadata| DB
        Indexer -->|Write Disk Segments| Disk[("💾 On-Disk Segment Storage (./data/index_segments)")]
        Search -->|Read Disk Segments| Disk
        Search -->|Search Query Cache| Redis[("⚡ Redis Cache")]
    end
```

---

## 🔄 End-to-End Pipeline Workflow Diagrams

### 1. Web Crawling Pipeline (`atlas-crawler-worker`)
```mermaid
sequenceDiagram
    autonumber
    User->>Crawler: POST /api/v1/crawl/jobs (Seed URL, Depth, Limits)
    Crawler->>Frontier: Enqueue Normalized Seed URL
    loop Concurrent Fetch Loop
        Frontier->>Robots: Check robots.txt & Sitemap rules
        Robots-->>Frontier: Allowed / Disallowed
        Frontier->>Fetcher: Execute HTTP Fetch (Resilient Java 21 HttpClient)
        Fetcher-->>Frontier: Raw HTML + Response Metadata
        Frontier->>Kafka: Publish Raw Document Event (crawl.raw.documents)
    end
```

### 2. HTML Parsing & Cleaning Pipeline (`atlas-parser-service`)
```mermaid
sequenceDiagram
    autonumber
    Kafka->>Consumer: Consume Raw HTML Event (crawl.raw.documents)
    Consumer->>DOMEngine: JSoup DOM Parsing & Boilerplate Node Stripping
    DOMEngine->>Normalizer: Unicode NFC, HTML Entity & Whitespace Collapse
    DOMEngine->>Deduper: Exact Hash (SHA-256) & 64-bit SimHash Near-Duplicate Check
    Deduper-->>Pipeline: Unique Document Validated
    Pipeline->>PostgreSQL: Save Parsed Document & Hyperlinks (parsed_links)
    Pipeline->>Kafka: Publish Cleaned Event (document.cleaned)
```

### 3. Custom Inverted Indexing Pipeline (`atlas-index-builder-worker`)
```mermaid
sequenceDiagram
    autonumber
    Kafka->>Consumer: Consume Cleaned Event (document.cleaned)
    Consumer->>TextAnalyzer: Tokenization -> Normalization -> Stop Words -> Porter Stemmer
    TextAnalyzer->>MemoryIndex: Populate Term-Posting Dictionary (DF, CF, Positions, FieldFlags)
    MemoryIndex->>SegmentWriter: Flush In-Memory Index to Immutable Segment (dict.json, postings.json)
    SegmentWriter->>PostgreSQL: Record Index Segment Metadata & Document Mappings
```

### 4. Query Processing & BM25 Search Pipeline (`atlas-keyword-search`)
```mermaid
sequenceDiagram
    autonumber
    User->>SearchService: GET /api/v1/search?q="cloud search" AND engine
    SearchService->>Redis: Check Query Cache Key
    alt Cache Hit
        Redis-->>SearchService: Return Cached PageResponse<SearchResultDto>
    else Cache Miss
        SearchService->>QueryParser: Parse AST (Boolean AND/OR/NOT + Quoted Phrase)
        QueryParser->>QueryNormalizer: Normalize & Stem Query Terms
        QueryNormalizer->>LookupEngine: Traversal On-Disk Index Segments
        LookupEngine->>BM25Ranker: Calculate Robertson-Spärck Jones BM25 Scores
        BM25Ranker->>SnippetGen: Extract Top Passage & Highlight Terms (<b>term</b>)
        SnippetGen->>Redis: Store Search Result in Redis Cache
    end
    SearchService-->>User: Return Ranked Search Results
```

---

## 🛠️ Technology Stack & Shared Libraries

| Layer | Technology | Purpose |
| :--- | :--- | :--- |
| **Language & Runtime** | Java 21 (LTS) | Virtual Threads, Records, Sealed Interfaces, Pattern Matching |
| **Framework** | Spring Boot 3.2.5 | Service container, Dependency Injection, Actuator Metrics |
| **Gateway & Config** | Spring Cloud Gateway & Config | Edge routing, dynamic application properties, cluster health aggregation |
| **Event Streaming** | Apache Kafka 3.6.2 | Decoupled event streams (`crawl.raw.documents`, `document.cleaned`) |
| **Relational Database**| PostgreSQL 16 (`atlas_db`) | Crawl state, document metadata, extracted link graph, segment statistics |
| **Caching Layer** | Redis 7.2 | High-speed search query caching & session management |
| **HTML Processing** | JSoup 1.17.2 | DOM parsing, HTML cleaning, metadata & hyperlink extraction |
| **UI Interface** | React + TypeScript + Vite | Modern glassmorphic search engine web application |
| **Containerization** | Docker & Docker Compose | Multi-stage production container builds and local developer stack |

---

## 📦 Microservices Topology & Port Directory

| Microservice | Port | Primary Responsibilities |
| :--- | :--- | :--- |
| `atlas-config-service` | `8888` | Centralized Spring Cloud Config Server |
| `atlas-api-gateway` | `8080` | Edge routing gateway, rate limiting, and unified cluster health checks |
| `atlas-search-gateway` | `8081` | Query router gateway and Redis cache abstraction |
| `atlas-keyword-search` | `8082` | Custom BM25 ranking search engine, phrase matcher & snippet generator |
| `atlas-crawler-worker` | `8083` | Distributed web crawler, URL frontier priority scheduler & fetcher |
| `atlas-index-builder-worker` | `8084` | Custom inverted index segment builder & disk persistence worker |
| `atlas-parser-service` | `8085` | HTML parser, boilerplate remover, SHA-256 & 64-bit SimHash deduplicator |
| `atlas-ui` | `3000` | Glassmorphic React search UI frontend |

---

## 🌐 Complete REST API Endpoint Reference

### 1. Crawl Management APIs (`/api/v1/crawl/*`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/crawl/jobs` | Submit and dispatch new crawl job |
| `GET` | `/api/v1/crawl/jobs` | List all registered crawl jobs |
| `GET` | `/api/v1/crawl/jobs/{id}` | Fetch detailed crawl job status |
| `POST` | `/api/v1/crawl/jobs/{id}/pause` | Pause an active crawl job |
| `POST` | `/api/v1/crawl/jobs/{id}/resume` | Resume a paused crawl job |
| `POST` | `/api/v1/crawl/jobs/{id}/cancel` | Cancel an active crawl job |
| `GET` | `/api/v1/crawl/jobs/{id}/statistics` | Fetch job statistics (pages/sec, queued URLs) |

### 2. Document Parser APIs (`/api/v1/parser/*`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/parser/statistics` | Real-time parser statistics (processed, duplicates, rate) |
| `GET` | `/api/v1/parser/documents/{id}` | Fetch parsed document metadata & preview |
| `GET` | `/api/v1/parser/duplicates` | Fetch paged list of detected duplicates |
| `GET` | `/api/v1/parser/failures` | Fetch paged list of parsing validation failures |

### 3. Inverted Index APIs (`/api/v1/index/*`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/index/build` | Trigger manual inverted index segment flush to disk |
| `GET` | `/api/v1/index/statistics` | Collection statistics (total docs, terms, vocab size, segments) |
| `GET` | `/api/v1/index/segments` | List all active immutable index segments |
| `GET` | `/api/v1/index/segments/{id}` | Fetch detailed index segment metadata |

### 4. BM25 Search Engine APIs (`/api/v1/search/*`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/search?q={query}&page=0&size=10` | Execute BM25 keyword search query |
| `GET` | `/api/v1/search/{query}` | Execute path-based search query |
| `POST` | `/api/v1/search/query` | Execute structured SearchRequest payload |
| `GET` | `/api/v1/search/statistics` | Search engine latency, query count & cache metrics |
| `GET` | `/api/v1/search/cache` | Redis search cache stats (hits, misses, hit ratio) |
| `DELETE` | `/api/v1/search/cache` | Clear and invalidate Redis search cache entries |

---

## ⚡ Quick Start & Deployment Guide

### Prerequisites
- **Java 21 LTS JDK**
- **Apache Maven 3.9+**
- **Docker & Docker Compose**

### 1. Build Multi-Module Project
```bash
# Clone the repository
git clone https://github.com/AnoopNadagouda/Atlas.git
cd Atlas

# Build all 14 Maven modules and run the full test suite
mvn clean test
```

### 2. Start Full Infrastructure Stack via Docker Compose
```bash
# Launch PostgreSQL, Kafka, Redis, Microservices & UI
docker-compose up -d --build

# Verify container cluster health
docker-compose ps
```

---

## 🧪 Comprehensive Testing Suite

Atlas includes a complete multi-level automated testing suite across unit, integration, and failure recovery domains:

```bash
# Execute unit tests across all 14 modules
mvn test

# Run microservice integration tests
mvn verify
```

```text
Test Suite Summary:
✓ UrlNormalizerTest & RobotsTxtParserTest
✓ HtmlParserEngineTest, SimHashDetectorTest & LanguageDetectorTest
✓ TokenizerTest, PorterStemmerTest & StopWordFilterTest
✓ InvertedIndexMemoryTest & SegmentWriterTest
✓ QueryParserTest, BM25RankerTest & SnippetGeneratorTest
✓ All 14 Maven Modules: BUILD SUCCESS
```

---

## 🔮 Future Roadmap (Phase 2+)

- [ ] **Phase 2.1 – Distributed PageRank Engine**: Iterative MapReduce / Graph-based link rank computation.
- [ ] **Phase 2.2 – Semantic Search & Vector Indexing**: HNSW vector index integration for dense embedding retrieval.
- [ ] **Phase 2.3 – Hybrid Rank Fusion**: Reciprocal Rank Fusion (RRF) combining BM25 keyword, PageRank, and Dense Semantic Vector scores.
- [ ] **Phase 2.4 – Neural RAG & AI Copilot**: Context-aware LLM answer synthesis over retrieved web passages.

---

## 📄 License & Attribution

Atlas is released under the **MIT License**. Created by [Anoop Nadagouda](https://github.com/AnoopNadagouda).
