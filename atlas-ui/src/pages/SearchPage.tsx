import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockAnalytics = {
    quality: { ndcg: 0.894, precision: 0.850, recall: 0.912, mrr: 0.925, ctr: '42.5%', zeroResultRate: '1.2%' },
    experiment: { id: 'exp-001', name: 'PageRank Heavy Ranking Experiment', trafficSplit: '25%', activeProfile: 'PAGERANK_HEAVY', status: 'RUNNING' }
  };

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
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'GRAPH', 'CLUSTER', 'PAGERANK', 'ANALYTICS'],
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
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Analytics Platform</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Search Analytics, Relevance Quality (NDCG@10, MRR), A/B Ranking Experiments, Autocomplete, Grounded AI Copilot, and Operations Dashboard.</p>
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
            className={`btn ${searchMode === 'analytics' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('analytics')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <LineChart size={14} /> Search Analytics & A/B Testing
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
        </div>
      </div>

      {searchMode === 'analytics' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #f59e0b' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#fbbf24', fontWeight: 600 }}>
              <LineChart size={20} /> Search Quality Analytics & Online A/B Ranking Experiment Engine
            </div>
            <span className="badge badge-info">Active Experiment: {mockAnalytics.experiment.status}</span>
          </div>

          <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap' }}>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>NDCG@10 Relevance</div>
              <strong style={{ fontSize: '1.1rem', color: '#fbbf24' }}>{mockAnalytics.quality.ndcg}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>MRR Score</div>
              <strong style={{ fontSize: '1.1rem', color: '#10b981' }}>{mockAnalytics.quality.mrr}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Click-Through Rate (CTR)</div>
              <strong style={{ fontSize: '1.1rem', color: '#60a5fa' }}>{mockAnalytics.quality.ctr}</strong>
            </div>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Zero Result Rate</div>
              <strong style={{ fontSize: '1.1rem', color: '#f472b6' }}>{mockAnalytics.quality.zeroResultRate}</strong>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Active Ranking Experiment:</span>
            <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <div>
                <strong style={{ color: '#f3f4f6' }}>{mockAnalytics.experiment.name}</strong>
                <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Profile: <strong style={{ color: '#fbbf24' }}>{mockAnalytics.experiment.activeProfile}</strong></div>
              </div>
              <span className="badge badge-info">Traffic Split: {mockAnalytics.experiment.trafficSplit}</span>
            </div>
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
          <span>Analytics Engine Latency: 18ms (Search Event & Click Tracked)</span>
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
