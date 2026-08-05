import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockOps = {
    health: { status: 'UP', postgresql: 'UP', kafka: 'UP', redis: 'UP' },
    metrics: { jvmMem: '248 MB', p99Latency: '18.4 ms', hitRatio: '97.8%', remainingQuota: '998 / 1000' },
    auditLogs: [
      { id: 'aud-001', user: 'admin-user', role: 'ADMIN', action: 'CLUSTER_FAILOVER_PROMOTE', time: '10:14:02', status: 'SUCCESS' },
      { id: 'aud-002', user: 'operator-user', role: 'OPERATOR', action: 'INDEX_SEGMENT_MERGE_START', time: '10:12:15', status: 'SUCCESS' }
    ]
  };

  const mockQueryAnalysis = {
    rawQuery: 'atls search engine kafka',
    normalizedQuery: 'atls search engine kafka',
    correctedQuery: 'atlas search engine kafka',
    rewrittenQuery: 'atlas search engine apache kafka',
    intent: 'ENTITY_LOOKUP',
    confidence: '98.1%',
    synonyms: ['apache kafka']
  };

  const mockRanking = {
    docId: 'doc-foundation-001',
    weights: { rrf: '40%', pageRank: '35%', freshness: '25%' },
    signals: { bm25: 0.892, semantic: 0.945, rrf: 0.0328, pageRank: 0.384, freshness: 0.95 },
    finalScore: 0.478
  };

  const mockCluster = {
    clusterName: 'atlas-search-cluster',
    status: 'GREEN',
    nodeId: 'search-node-1',
    shards: [
      { id: 'shard-0', docs: '500,000', size: '128 MB', status: 'ACTIVE', primary: true },
      { id: 'shard-1', docs: '500,000', size: '128 MB', status: 'ACTIVE', primary: true },
    ]
  };

  const mockEntityCard = {
    name: 'Atlas Search Engine',
    type: 'PRODUCT',
    category: 'Distributed Search Platform',
    confidence: '99%',
    connectedTechnologies: [
      { name: 'Spring Boot', relation: 'USES', type: 'FRAMEWORK' },
      { name: 'Apache Kafka', relation: 'USES', type: 'EVENT_STREAMING' },
      { name: 'PostgreSQL', relation: 'USES', type: 'DATABASE' },
      { name: 'Redis', relation: 'USES', type: 'CACHE' },
    ]
  };

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 2.2 Parallel Hybrid Search Engine with Reciprocal Rank Fusion (RRF). Executes BM25 keyword search and 384-dim HNSW vector search simultaneously.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      pageRankScore: 0.384,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'GRAPH', 'CLUSTER', 'PAGERANK', 'ENTERPRISE'],
    },
    {
      id: 'doc-bm25-002',
      title: 'Custom Inverted Index & Robertson-Spärck Jones BM25 Ranker',
      url: 'https://atlas.search/docs/bm25-spec',
      snippet: 'Term-posting dictionary holding document frequencies, collection frequencies, token positions, and field flags (TITLE, BODY, HEADING).',
      bm25Score: 0.941,
      semanticScore: 0.720,
      rrfScore: 0.0315,
      pageRankScore: 0.210,
      sources: ['KEYWORD', 'HYBRID', 'PAGERANK'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Enterprise Operations Foundation</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Query Intelligence, Autocomplete, Grounded AI Copilot, Micrometer Observability, JWT Security, and Enterprise Operations Dashboard.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Type 'atls', ask AI Search Copilot, or search distributed cluster..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Search & Ask Copilot
          </button>
        </div>

        {query.length > 0 && (
          <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '6px', padding: '10px 14px', display: 'flex', alignItems: 'center', gap: '8px', fontSize: '0.85rem' }}>
            <HelpCircle size={16} style={{ color: '#818cf8' }} />
            <span>Did you mean: <strong style={{ color: '#60a5fa', cursor: 'pointer' }} onClick={() => setQuery(mockQueryAnalysis.correctedQuery)}>{mockQueryAnalysis.correctedQuery}</strong>? (Spell Check Confidence: {mockQueryAnalysis.confidence})</span>
          </div>
        )}

        <div style={{ display: 'flex', gap: '12px', alignItems: 'center', flexWrap: 'wrap' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Retrieval Mode:</span>
          <button
            className={`btn ${searchMode === 'copilot' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('copilot')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Bot size={14} /> AI Search Copilot (RAG)
          </button>
          <button
            className={`btn ${searchMode === 'ops' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('ops')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <ShieldCheck size={14} /> Operations & Security
          </button>
          <button
            className={`btn ${searchMode === 'query' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('query')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Brain size={14} /> Query Intelligence
          </button>
          <button
            className={`btn ${searchMode === 'ranking' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('ranking')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <BarChart3 size={14} /> Ranking Inspector
          </button>
          <button
            className={`btn ${searchMode === 'cluster' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('cluster')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Server size={14} /> Cluster Dashboard
          </button>
        </div>
      </div>

      {searchMode === 'ops' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #10b981' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#10b981', fontWeight: 600 }}>
              <ShieldCheck size={20} /> Enterprise Operations, Security & Audit Log Viewer
            </div>
            <span className="badge badge-info">System Liveness: {mockOps.health.status}</span>
          </div>

          <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap' }}>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>JVM Memory</div>
              <strong style={{ fontSize: '1.1rem', color: '#60a5fa' }}>{mockOps.metrics.jvmMem}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>P99 Search Latency</div>
              <strong style={{ fontSize: '1.1rem', color: '#10b981' }}>{mockOps.metrics.p99Latency}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Redis Hit Ratio</div>
              <strong style={{ fontSize: '1.1rem', color: '#c084fc' }}>{mockOps.metrics.hitRatio}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Rate Limit Token Quota</div>
              <strong style={{ fontSize: '1.1rem', color: '#f472b6' }}>{mockOps.metrics.remainingQuota}</strong>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Enterprise Security Audit Trail:</span>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
              {mockOps.auditLogs.map((log) => (
                <div key={log.id} style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid rgba(255, 255, 255, 0.06)', borderRadius: '6px', padding: '8px 12px', display: 'flex', justifyContent: 'space-between', fontSize: '0.8rem' }}>
                  <span><strong style={{ color: '#818cf8' }}>[{log.time}]</strong> User: <strong>{log.user}</strong> ({log.role})</span>
                  <span>Action: <strong style={{ color: '#10b981' }}>{log.action}</strong></span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {searchMode === 'query' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #a855f7' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#c084fc', fontWeight: 600 }}>
              <Brain size={20} /> Query Intelligence Pipeline & Intent Inspector
            </div>
            <span className="badge badge-info">Intent: {mockQueryAnalysis.intent}</span>
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Grounded with Graph Facts)
            </div>
          </div>
          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Grounded Retrieval Results ({mockResults.length} indexed documents)</span>
          <span>Enterprise Gateway Latency: 18ms (JWT Auth & Rate Limit Validated)</span>
        </div>

        {mockResults.map((result) => (
          <div key={result.id} className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <a href={result.url} target="_blank" rel="noreferrer" style={{ color: '#818cf8', fontWeight: 600, fontSize: '1.1rem', textDecoration: 'none', display: 'flex', alignItems: 'center', gap: '6px' }}>
                {result.title} <ExternalLink size={14} />
              </a>
              <div style={{ display: 'flex', gap: '6px' }}>
                {result.sources.map((src) => (
                  <span key={src} className="badge badge-info">{src}</span>
                ))}
              </div>
            </div>
            <div style={{ fontSize: '0.8rem', color: 'var(--accent-cyan)' }}>{result.url}</div>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>{result.snippet}</p>
          </div>
        ))}
      </div>
    </div>
  );
};
