# Changelog

All notable changes to the Atlas Search Engine project will be documented in this file.

## [v5.5.2] - 2026-08-05
### Phase 5.5 Milestone 2: Tool SDK & Execution Framework
- Extensible, type-safe Agent Tool SDK with schema validation and micrometer observability metrics.
- Automatic Spring Bean tool discovery and thread-safe registry (`ToolRegistry`).
- Sandboxed virtual-thread tool execution engine (`ToolExecutor`) with permission checks and timeout management.
- Production built-in tools (`BM25SearchTool`, `VectorSearchTool`, `WebCrawlTool`, `DocumentParseTool`).
- Resilient non-blocking WebClient integration with retry and fallback capabilities.
- Complete Tool SDK REST API (`/api/v1/tools`, `/api/v18/tools`) with OpenAPI Swagger annotations.

## [v5.5.1] - 2026-08-05
### Phase 5.5 Milestone 1: Agent Core Framework
- Core state machine (`AgentState`), execution parameters (`AgentExecutionConfig`), context container (`AgentContext`), and result records (`AgentResult`).
- New application microservice `atlas-agent-service` bootstrapped on Port 8086.

## [v5.0.0] - 2026-08-05
### Production Release & Final Verification
- Production release of Atlas Distributed AI Search Platform.
- One-command startup via `docker compose up`.
- CI/CD workflow automation with GitHub Actions.
- Performance benchmark suite for latency, QPS, indexing throughput, and memory profiling.

## [v4.3.0] - 2026-08-05
### GitHub Code Search & Source Intelligence
- Added AST symbol parsers for Java, Python, JavaScript, and TypeScript.
- Added repository indexing, symbol registry, and grounded AI Code Copilot.

## [v4.2.0] - 2026-08-05
### Time Travel Search
- Added versioned document stores, index snapshots, and version difference engine.

## [v4.1.0] - 2026-08-05
### Search Analytics & Experiments
- Added search event logging, relevance quality evaluation (NDCG@10, MRR), and online A/B ranking experiments.

## [v4.0.0] - 2026-08-05
### Enterprise Production Foundation
- Added Spring Security JWT, Redis Rate Limiting, Micrometer Metrics, OpenTelemetry Tracing, and Audit Logs.
