import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart, History, Clock } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics' | 'history'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);
  const [selectedDate, setSelectedDate] = useState('2026-07-15');

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockHistory = {
    selectedSnapshot: 'snap-2026-07-15',
    activeTimestamp: '2026-07-15T00:00:00Z',
    versions: [
      { id: 'v-1.0', date: '2026-07-01', title: 'Atlas Search - Single Node BM25 Engine', snippet: 'Phase 1.0 Custom Inverted Index.' },
      { id: 'v-2.0', date: '2026-07-15', title: 'Atlas Search - Hybrid Search & Grounded Copilot', snippet: 'Phase 2.0 Parallel Hybrid Search.' },
      { id: 'v-3.0', date: '2026-08-01', title: 'Atlas Platform - Enterprise AI Search Engine', snippet: 'Phase 4.2 Time Travel Search.' },
    ],
    diff: {
      docId: 'doc-foundation-001',
      from: 'v-1.0',
      to: 'v-2.0',
      added: ['Parallel Hybrid Search combining BM25 and HNSW vectors', 'Grounded AI Copilot'],
      removed: ['Single Node BM25 Engine'],
      similarity: '82.0%'
    }
  };

  const mockAnalytics = {
    quality: { ndcg: 0.894, precision: 0.850, recall: 0.912, mrr: 0.925, ctr: '42.5%', zeroResultRate: '1.2%' },
    experiment: { id: 'exp-001', name: 'PageRank Heavy Ranking Experiment', trafficSplit: '25%', activeProfile: 'PAGERANK_HEAVY', status: 'RUNNING' }
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
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'GRAPH', 'CLUSTER', 'PAGERANK', 'TIME_TRAVEL'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Time Travel Platform</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Time Travel Search, Historical Indexing, Snapshot Retrieval, Document Version Diffs, AI Search Copilot, and Analytics.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Type query or search historical index snapshot..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Time Travel Search
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
            className={`btn ${searchMode === 'history' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('history')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <History size={14} /> Time Travel Search (Historical Index)
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
        </div>
      </div>

      {searchMode === 'history' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #3b82f6' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#60a5fa', fontWeight: 600 }}>
              <Clock size={20} /> Time Travel Timeline & Document Version Difference Viewer
            </div>
            <span className="badge badge-info">Active Snapshot: {mockHistory.selectedSnapshot}</span>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Historical Snapshot Timeline:</span>
            <input
              type="range"
              min="1"
              max="3"
              step="1"
              defaultValue="2"
              onChange={(e) => {
                const val = e.target.value;
                if (val === '1') setSelectedDate('2026-07-01');
                if (val === '2') setSelectedDate('2026-07-15');
                if (val === '3') setSelectedDate('2026-08-01');
              }}
              style={{ width: '100%', cursor: 'pointer' }}
            />
            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.8rem', color: 'var(--text-muted)' }}>
              <span>2026-07-01 (v-1.0)</span>
              <strong style={{ color: '#60a5fa' }}>Selected: {selectedDate}</strong>
              <span>2026-08-01 (v-3.0)</span>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Document Version Diff (v-1.0 ➔ v-2.0):</span>
            <div style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid rgba(255, 255, 255, 0.06)', borderRadius: '8px', padding: '12px', display: 'flex', flexDirection: 'column', gap: '6px', fontSize: '0.85rem' }}>
              <div>Similarity Score: <strong style={{ color: '#10b981' }}>{mockHistory.diff.similarity}</strong></div>
              <div style={{ color: '#10b981' }}>+ Added: {mockHistory.diff.added.join(', ')}</div>
              <div style={{ color: '#ef4444' }}>- Removed: {mockHistory.diff.removed.join(', ')}</div>
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
          <span>Grounded Historical Results ({mockResults.length} indexed versioned documents)</span>
          <span>Time Travel Planner Latency: 16ms (Snapshot Selection Validated)</span>
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
