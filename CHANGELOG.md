# Changelog

All notable changes to the Atlas Search Engine project will be documented in this file.

## [v5.8.0] - 2026-08-06
### Phase 5.8: Enterprise AI Reasoning, Self-Reflection & Autonomous Decision Engine
- Created new microservice `atlas-reasoning-service` running on Port `8089`.
- Production Reasoning Engine supporting 10 Modes: `CHAIN_OF_THOUGHT`, `TREE_OF_THOUGHTS`, `GRAPH_OF_THOUGHTS`, `REFLECTION`, `REACT`, `PLAN_AND_EXECUTE`, `GOAL_ORIENTED`, `CONSTRAINT_BASED`, `MULTI_STEP_DECISION`, `ADAPTIVE_REPLANNING`.
- Autonomous Goal Manager (`GoalManager`), Multi-Step Task Decomposer (`TaskDecomposer`), and Candidate Alternative Generator (`AlternativeGenerator`).
- Decision Engine with Risk Assessment & Cost-Reward Tradeoff Evaluator (`DecisionEngine`, `RiskAssessmentEngine`, `ConfidenceEstimator`).
- Execution Evaluator & Critic Engine with Hallucination Detection (`CriticEngine`, `ExecutionEvaluator`).
- Post-Task Self-Reflection Engine (`SelfReflectionEngine`).
- Failure Cause Analyzer (`FailureAnalyzer`) & Autonomous Recovery Planner (`RecoveryPlanner`).
- Thought Graph Builder (`ThoughtGraphBuilder`) & Reasoning Trace Store (`ReasoningTraceStore`).
- REST APIs (`/api/v22/reasoning`) with Swagger OpenAPI annotations.
- Kafka Event Publisher for reasoning lifecycle events (`reasoning.events.lifecycle`).
- Glassmorphic React UI components (`ReasoningDashboard`, `GoalManagerView`, `ThoughtGraphView`, `DecisionExplorer`, `ReflectionViewer`, `CritiquePanel`, `ReasoningTimeline`, `RecoveryCenter`, `ReasoningAnalyticsView`).

## [v5.7.0] - 2026-08-05
### Phase 5.7: Enterprise AI Memory, Long-Term Context & Knowledge Learning Platform
- Created new microservice `atlas-memory-service` running on Port `8088`.
- Production multi-type Memory Engine supporting Short-Term, Long-Term, Semantic, Episodic, Procedural, Conversation, Workflow, Tool Execution, Search, and Knowledge Memory.
- Ebbinghaus Forgetting Curve Memory Decay Manager (`MemoryDecayManager`).
- Automatic Memory Consolidation Service (`MemoryConsolidationService`).
- Knowledge Graph Memory Relationship Builder (`MemoryGraphBuilder`).
- Vector Embedding Service (`MemoryEmbeddingService`) & Importance Scorer (`MemoryImportanceScorer`).
- REST APIs (`/api/v21/memory`) with Swagger OpenAPI annotations.
- Kafka Event Publisher for memory lifecycle events (`memory.events.lifecycle`).
- Glassmorphic React UI components (`MemoryDashboard`, `MemoryExplorer`, `ConversationHistory`, `MemoryGraph`, `MemoryAnalytics`, `MemoryTimeline`, `MemorySearchPanel`, `MemorySettings`).
### Phase 5.6: Enterprise AI Workflow Automation Platform
- Created new microservice `atlas-workflow-service` running on Port `8087`.
- Production DAG Workflow Engine supporting Sequential, Parallel, Conditional branches, Looping, Retries, Timeouts, Pause/Resume, Cancel/Restart, Checkpoint Recovery, and Rollback.
- Human Approval step gate manager (`HumanApprovalManager`).
- REST APIs (`/api/v20/workflows`) with Swagger OpenAPI annotations.
- Kafka Event Publisher for workflow lifecycle events (`workflow.events.execution`).
- Glassmorphic React UI components (`WorkflowDesigner`, `WorkflowDashboard`, `WorkflowTimeline`, `WorkflowExecutionView`, `WorkflowGraph`, `ApprovalCenter`, `WorkflowTemplates`, `WorkflowAnalytics`).
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
