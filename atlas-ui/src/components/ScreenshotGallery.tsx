import React, { useState } from 'react';
import { Layout, Monitor, Sparkles, CheckCircle2 } from 'lucide-react';

export const ScreenshotGallery: React.FC = () => {
  const [activeTab, setActiveTab] = useState('search-dashboard');

  const screens: Record<string, { title: string; subtitle: string; features: string[]; mockData: string }> = {
    'search-dashboard': {
      title: '1. Search Studio & Query Engine Dashboard',
      subtitle: 'BM25 Keyword Search + Neural Embedding Hybrid Ranking & Faceted Filtering',
      features: ['Instant BM25 Scoring', 'Reciprocal Rank Fusion (RRF)', 'Facet Aggregations', 'Highlighting Snippets'],
      mockData: `[Query: "distributed inverted index"] => Returned 14,820 matches in 12ms
Result 1: "Atlas Custom Inverted Index Engine Architecture" (BM25: 4.82, RRF: 0.98)
Result 2: "Multithreaded Segment Compaction & Varbyte Compression" (BM25: 4.51, RRF: 0.92)`
    },
    'mission-control': {
      title: '2. Mission Control Center',
      subtitle: 'Autonomous Enterprise Mission Formulation, Agent Assignment & Lifecycle Control',
      features: ['Goal Decomposition', 'Priority Scheduling', 'Pause/Resume/Cancel', 'Checkpoint Recovery'],
      mockData: `[Mission MSN-101: "Global Enterprise Financial Risk Indexing"]
Status: IN_PROGRESS (Progress: 68%) | Priority: 8 | Agents: 5 Assigned
Step 1: Parse SEC Filings [COMPLETED]
Step 2: Consolidate Memory Context [IN_PROGRESS]`
    },
    'workflow-designer': {
      title: '3. Visual Workflow Designer & DAG Engine',
      subtitle: 'Declarative Node Execution State Machine & Cron Workflow Scheduler',
      features: ['Topological Sort', 'Parallel Worker Execution', 'Node Retry Policies', 'Execution Timelines'],
      mockData: `DAG Topology: Node_A (Fetch HTML) -> Node_B (Clean Text) -> Node_C (Index Segment)
Worker Node Status: Node_A [SUCCESS], Node_B [SUCCESS], Node_C [RUNNING]`
    },
    'memory-graph': {
      title: '4. AI Memory Graph & Concept Explorer',
      subtitle: 'Ebbinghaus Forgetting Curve Memory Decay & Vector Context Consolidation',
      features: ['Episodic Memory Store', 'Ebbinghaus Decay Score', 'Vector Cosine Distance', 'Knowledge Graph Nodes'],
      mockData: `Memory Item MEM-892: "Tenant Security Policies for AWS S3 Sync"
Decay Score: 0.94 (Active) | Consolidation: LTM | Vector Dim: 1536`
    },
    'reasoning-graph': {
      title: '5. Multi-Mode Reasoning & Thought Graph',
      subtitle: '10 Reasoning Modes (Tree of Thoughts, ReAct, Re-Planning) & Critic Evaluator',
      features: ['Tree of Thoughts (ToT)', 'Critic Evaluation Score', 'Hallucination Detector', 'Self-Reflection Log'],
      mockData: `Reasoning Session RSN-402 (Mode: TREE_OF_THOUGHTS)
Path 1: Confidence 0.88 [ACCEPTED]
Path 2: Confidence 0.42 [REJECTED by Critic Engine]`
    },
    'analytics-dashboard': {
      title: '6. Enterprise Telemetry & Analytics Dashboard',
      subtitle: 'Micrometer, OpenTelemetry & Prometheus Performance Monitoring',
      features: ['Query QPS Rate', 'Service Latency P99', 'Active Agent Count', 'Resource Balancer Load'],
      mockData: `Total Search Queries: 1.48M/day | Average P99 Latency: 14ms | Platform Uptime: 99.98%`
    },
    'plugin-marketplace': {
      title: '7. Plugin Marketplace & Extension SDK',
      subtitle: 'Isolated Java ClassLoader Sandbox & Tool Capability Registration',
      features: ['Plugin Security Sandbox', 'Capability Verification', 'Dynamic Hot-Loading', 'SDK Hooks'],
      mockData: `Installed Plugins: 12 Active
- S3 Document Importer v1.4 [ACTIVE]
- PostgreSQL Vector Connector v2.1 [ACTIVE]`
    },
    'connector-dashboard': {
      title: '8. Connector Hub & Sync Dashboard',
      subtitle: 'Enterprise SaaS & Database Connectors with Incremental Delta Sync',
      features: ['Delta Change Tracking', 'Schema Mapping', 'Encrypted Credentials', 'Sync Scheduling'],
      mockData: `Connector: Jira Enterprise Cloud (ID: conn-jira-01)
Status: SYNCING (Delta: +1,420 tickets updated in last 15 mins)`
    },
    'knowledge-graph': {
      title: '9. Neural Knowledge Graph Explorer',
      subtitle: 'Automated Entity Resolution, Relationship Extraction & Graph Queries',
      features: ['Entity Resolution', 'Edge Weight Scoring', 'Graph Traversal API', 'Multi-Tenant Partition'],
      mockData: `Graph Node: "Atlas Microservices Mesh" --[orchestrates]--> Node: "Agent Core Service"
Edge Weight: 0.96 | Metadata: { port: 8086, protocol: "gRPC/REST" }`
    },
    'code-search': {
      title: '10. Code & Syntax Search Studio',
      subtitle: 'Abstract Syntax Tree (AST) Indexing & Code Symbol Vector Search',
      features: ['AST Parsing', 'Symbol Reference Graph', 'Language Tokenizer', 'Exact Diff Match'],
      mockData: `Symbol Query: "AtlasOrchestrator" => Found 18 references across 6 files
File: AtlasOrchestratorImpl.java (Line 67: executeMission)`
    },
    'time-travel-search': {
      title: '11. Time Travel & Historical Version Search',
      subtitle: 'Point-in-Time Index Snapshot Queries & Document Version Diffing',
      features: ['Point-in-Time Snapshots', 'Version Diff Viewer', 'Segment Time Travel', 'Audit Replay'],
      mockData: `Snapshot PIT-2026-08-01 12:00:00 UTC
Query: "security policy" => Returned version v1.2 (diff: +4 lines modified)`
    },
    'multi-tenant-dashboard': {
      title: '12. Multi-Tenant Governance & Isolation Hub',
      subtitle: 'Tenant Quotas, Custom Data Schemas & Compliance Security Audit Logs',
      features: ['Tenant Quota Enforcement', 'API Key Scopes', 'Security Audit Trail', 'Schema Partitioning'],
      mockData: `Active Tenant: "Acme Corporation" (tenant-acme)
Quota Usage: 45,000 / 100,000 requests/day | API Key Scopes: READ, WRITE, EXECUTE`
    },
    'aios-dashboard': {
      title: '13. Atlas AIOS Master Dashboard',
      subtitle: 'Central AI Operating System Control Hub across all 19 Atlas Modules',
      features: ['Cluster Overview', 'Platform Health Matrix', 'Resource Allocator', 'Policy Engine'],
      mockData: `Atlas AIOS v6.0.0 Status: ONLINE
Active Missions: 14 | Fleet Agents: 42 | Microservices: 19 / 19 Healthy`
    },
  };

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '10px' }}>
            <Monitor className="text-accent" size={24} /> Dashboard Screenshot Showcase (13 Dashboards)
          </h3>
          <p style={{ color: 'var(--text-dim)', marginTop: '4px', marginBottom: 0, fontSize: '0.9rem' }}>
            Interactive showcase demonstrating the full commercial UI suite of Atlas v6.0.0.
          </p>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: '20px' }}>
        {/* Sidebar */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '6px', maxHeight: '520px', overflowY: 'auto' }}>
          {Object.keys(screens).map(key => {
            const isSelected = activeTab === key;
            return (
              <button
                key={key}
                onClick={() => setActiveTab(key)}
                className={`btn ${isSelected ? 'btn-primary' : 'btn-secondary'}`}
                style={{ justifyContent: 'flex-start', textAlign: 'left', padding: '10px 12px', fontSize: '0.85rem' }}
              >
                {screens[key].title}
              </button>
            );
          })}
        </div>

        {/* Display Card */}
        <div className="glass-panel" style={{ padding: '24px', background: 'rgba(15, 23, 42, 0.95)', border: '1px solid rgba(255, 255, 255, 0.12)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '12px' }}>
            <div>
              <h4 style={{ fontSize: '1.15rem', fontWeight: 700, margin: '0 0 4px', color: '#60a5fa' }}>
                {screens[activeTab].title}
              </h4>
              <p style={{ fontSize: '0.875rem', color: 'var(--text-dim)', margin: 0 }}>
                {screens[activeTab].subtitle}
              </p>
            </div>
            <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#4ade80', fontSize: '0.75rem' }}>
              SCREENSHOT MOCKUP
            </span>
          </div>

          {/* Features */}
          <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap', margin: '16px 0' }}>
            {screens[activeTab].features.map((f, i) => (
              <span key={i} className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.12)', color: '#93c5fd', fontSize: '0.75rem', border: '1px solid rgba(59, 130, 246, 0.2)' }}>
                <CheckCircle2 size={12} style={{ marginRight: '4px' }} /> {f}
              </span>
            ))}
          </div>

          {/* Mock Console */}
          <pre style={{ background: 'rgba(0, 0, 0, 0.6)', padding: '20px', borderRadius: '8px', border: '1px solid rgba(255, 255, 255, 0.1)', color: '#38bdf8', fontFamily: 'monospace', fontSize: '0.85rem', lineHeight: 1.6, overflowX: 'auto' }}>
            {screens[activeTab].mockData}
          </pre>
        </div>
      </div>
    </div>
  );
};
