import React, { useState } from 'react';
import { Layers, Server, Activity, ArrowRight } from 'lucide-react';

export const DiagramViewer: React.FC = () => {
  const [selectedDiagram, setSelectedDiagram] = useState('overall');

  const diagrams: Record<string, { title: string; desc: string; content: string }> = {
    overall: {
      title: '1. Overall System Architecture',
      desc: 'High-level decoupling across API Gateway, microservices cluster, Kafka event bus, PostgreSQL, Redis, and React UI.',
      content: `[React UI / AIOS Shell] --> (API Gateway:8080)
(API Gateway:8080) --> [Orchestrator Service:8090]
(API Gateway:8080) --> [Agent Core Service:8086]
(API Gateway:8080) --> [Workflow Service:8087]
(API Gateway:8080) --> [Memory Service:8088]
(API Gateway:8080) --> [Reasoning Service:8089]
(API Gateway:8080) --> [Keyword Search:8082]
(API Gateway:8080) --> [Crawler Worker:8083]
(API Gateway:8080) --> [Parser Service:8085]
(API Gateway:8080) --> [Index Builder Worker:8084]`
    },
    microservices: {
      title: '2. Microservice Communication',
      desc: 'Synchronous REST/gRPC routing and asynchronous Kafka event streaming between modules.',
      content: `Orchestrator (8090) => Agent Service (8086): REST Task Dispatch
Agent Service (8086) => Reasoning Service (8089): Multi-Step Deductive Plan
Reasoning Service (8089) => Workflow Service (8087): Trigger DAG Execution
Workflow Service (8087) => Memory Service (8088): Persist Episodic Context
Memory Service (8088) => Keyword Search (8082): Hybrid Retrieval Sync`
    },
    kafka: {
      title: '3. Kafka Event Flow',
      desc: 'Event backbone topics handling crawl documents, cleaned items, memory decay, reasoning logs, and orchestrator lifecycle.',
      content: `Topic: crawl.raw.documents => Producer: Crawler Worker => Consumer: HTML Parser
Topic: document.cleaned => Producer: HTML Parser => Consumer: Index Builder
Topic: agent.events.lifecycle => Producer: Agent Core => Consumer: Memory & Analytics
Topic: reasoning.events.lifecycle => Producer: Reasoning Service => Consumer: Orchestrator
Topic: orchestrator.events.lifecycle => Producer: Orchestrator => Consumer: All Services`
    },
    search: {
      title: '4. Search Pipeline',
      desc: 'Query tokenizer -> Vocabulary dictionary lookup -> Inverted posting list fetch -> BM25 scoring -> Hybrid vector re-ranking.',
      content: `[User Query] -> Tokenizer & Normalizer -> Dictionary Binary Search -> Posting List Intersection -> BM25 Score Calc -> Rescorer -> [Ranked Search Results]`
    },
    crawler: {
      title: '5. Crawler Pipeline',
      desc: 'Seed URLs -> Frontier Queue -> Robots.txt Parser -> Domain Politeness Limiter -> Multithreaded Ingestion -> Raw HTML Publish.',
      content: `[Seed URLs] -> Priority Frontier -> Robots.txt Compliance -> Rate Limiter -> HTTP Fetcher -> Duplicate Filter -> Kafka: crawl.raw.documents`
    },
    agent: {
      title: '6. Agent Core Pipeline',
      desc: 'Goal Reception -> Capability Matching -> Tool Registry Resolution -> Autonomous Execution Loop -> Result Verification.',
      content: `[User Goal] -> Goal Decomposer -> Tool Selector -> Execution Sandbox -> Action Loop -> Critic Evaluation -> Final Output`
    },
    workflow: {
      title: '7. Workflow DAG State Machine',
      desc: 'DAG Definition -> Topology Topological Sort -> Node Dependency Resolution -> Parallel Async Execution -> Status Checkpointing.',
      content: `[DAG Definition] -> Dependency Parser -> Execution Plan -> Worker Node 1..N -> State Checkpointer -> Final Aggregation`
    },
    memory: {
      title: '8. Memory Architecture',
      desc: 'Short-Term Buffer -> Ebbinghaus Decay Manager -> Semantic/Episodic Consolidation -> Vector Embedding Store -> Knowledge Graph.',
      content: `[Input Context] -> Short-Term Memory -> Decay Evaluator -> Vector Embedding -> Long-Term Vector Store -> Knowledge Graph Builder`
    },
    reasoning: {
      title: '9. Multi-Mode Reasoning Architecture',
      desc: '10 Reasoning Modes (Tree of Thoughts, ReAct, Re-Planning) -> Critic Evaluator -> Self-Reflection -> Recovery Planner.',
      content: `[Reasoning Task] -> Mode Selector (CoT / ToT / ReAct) -> Thought Generator -> Critic Evaluator -> Reflection Engine -> Decision Trace`
    },
    aios: {
      title: '10. AIOS Orchestrator Engine',
      desc: 'Mission Control -> Objective Planner -> Global Scheduler -> Policy Engine -> Resource Balancer -> Self-Healing Recovery.',
      content: `[Enterprise Mission] -> Policy Engine Validation -> Objective Decomposition -> Global Scheduler -> Multi-Agent Fleet -> Recovery Manager`
    },
    plugin: {
      title: '11. Plugin SDK Architecture',
      desc: 'Isolated ClassLoader sandbox, lifecycle hooks (initialize, execute, shutdown), capability security verification.',
      content: `[Plugin SDK Jar] -> Isolated Sandbox -> Security Policy Check -> Tool Registration -> Runtime Execution Engine`
    },
    connector: {
      title: '12. Connector SDK Architecture',
      desc: 'Enterprise source interfaces (Database, Cloud Storage, SaaS APIs), incremental delta sync, schema mapper.',
      content: `[Data Source (S3/SQL/Jira)] -> Connector SDK -> Delta Change Detector -> Document Transformer -> Indexing Pipeline`
    },
    deployment: {
      title: '13. Production Deployment Architecture',
      desc: 'Edge NGINX Proxy / API Gateway -> Microservices Container Mesh -> Postgres Primary/Replica -> Redis & Kafka Clusters.',
      content: `[Client] -> NGINX / Gateway (8080) -> Docker/K8s Services Mesh (8081-8090) -> Datastores (Postgres:5432, Redis:6379, Kafka:9092)`
    },
    k8s: {
      title: '14. Kubernetes Cluster Architecture',
      desc: 'K8s Ingress Controller -> Service Pods with Horizontal Pod Autoscalers (HPA) -> StatefulSets for Kafka & Postgres.',
      content: `[Ingress Controller] -> Service Deployments (Gateway, Orchestrator, Microservices) -> HPA Autoscaler -> StatefulSets (Postgres, Kafka)`
    },
    lifecycle: {
      title: '15. Request Lifecycle',
      desc: 'Request Auth & Rate Limiting -> Route Resolution -> Service Handler -> Event Notification -> Telemetry Metrics Logging.',
      content: `[REST Request] -> API Gateway Auth -> Service Route -> Business Execution -> Audit Logger -> Prometheus Metrics -> Response`
    },
    ranking: {
      title: '16. Hybrid Ranking Pipeline',
      desc: 'Custom BM25 Inverted Index Score + Vector Embedding Cosine Similarity + Reciprocal Rank Fusion (RRF) -> Final Result List.',
      content: `[Query] -> BM25 Keyword Search + Vector Embedding Search -> Reciprocal Rank Fusion (RRF) -> Freshness & Quality Boost -> Final Ranked List`
    },
  };

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '10px' }}>
            <Layers className="text-accent" size={24} /> Production Architecture Diagrams (16 Pipelines)
          </h3>
          <p style={{ color: 'var(--text-dim)', marginTop: '4px', marginBottom: 0, fontSize: '0.9rem' }}>
            Select a system pipeline below to inspect its detailed architecture flow.
          </p>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: '20px' }}>
        {/* Sidebar Selector */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '6px', maxHeight: '520px', overflowY: 'auto' }}>
          {Object.keys(diagrams).map((key) => {
            const isSelected = selectedDiagram === key;
            return (
              <button
                key={key}
                onClick={() => setSelectedDiagram(key)}
                className={`btn ${isSelected ? 'btn-primary' : 'btn-secondary'}`}
                style={{ justifyContent: 'flex-start', textAlign: 'left', padding: '10px 12px', fontSize: '0.85rem' }}
              >
                {diagrams[key].title}
              </button>
            );
          })}
        </div>

        {/* Viewer Box */}
        <div className="glass-panel" style={{ padding: '24px', background: 'rgba(15, 23, 42, 0.9)', border: '1px solid rgba(255, 255, 255, 0.12)' }}>
          <h4 style={{ fontSize: '1.15rem', fontWeight: 700, margin: '0 0 8px', color: '#60a5fa' }}>
            {diagrams[selectedDiagram].title}
          </h4>
          <p style={{ color: 'var(--text-dim)', fontSize: '0.9rem', marginBottom: '20px' }}>
            {diagrams[selectedDiagram].desc}
          </p>
          <pre style={{ background: 'rgba(0, 0, 0, 0.5)', padding: '20px', borderRadius: '8px', border: '1px solid rgba(255, 255, 255, 0.1)', color: '#4ade80', fontFamily: 'monospace', fontSize: '0.9rem', lineHeight: 1.6, overflowX: 'auto' }}>
            {diagrams[selectedDiagram].content}
          </pre>
        </div>
      </div>
    </div>
  );
};
