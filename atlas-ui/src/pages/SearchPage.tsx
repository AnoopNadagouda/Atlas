import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid'>('hybrid');

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 2.2 Parallel Hybrid Search Engine with Reciprocal Rank Fusion (RRF). Executes BM25 keyword search and 384-dim HNSW vector search simultaneously.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID'],
    },
    {
      id: 'doc-bm25-002',
      title: 'Custom Inverted Index & Robertson-Spärck Jones BM25 Ranker',
      url: 'https://atlas.search/docs/bm25-spec',
      snippet: 'Term-posting dictionary holding document frequencies, collection frequencies, token positions, and field flags (TITLE, BODY, HEADING).',
      bm25Score: 0.941,
      semanticScore: 0.720,
      rrfScore: 0.0315,
      sources: ['KEYWORD', 'HYBRID'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio Playground</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Keyword (BM25), Semantic Vector, and Parallel Hybrid Search with Reciprocal Rank Fusion (RRF).</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Search across web, documentation, code repositories, or vector embeddings..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary">
            <Zap size={18} /> Search
          </button>
        </div>

        <div style={{ display: 'flex', gap: '12px', alignItems: 'center', flexWrap: 'wrap' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Retrieval Mode:</span>
          <button
            className={`btn ${searchMode === 'keyword' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('keyword')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Layers size={14} /> Keyword (BM25)
          </button>
          <button
            className={`btn ${searchMode === 'semantic' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('semantic')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Brain size={14} /> Semantic (Vector)
          </button>
          <button
            className={`btn ${searchMode === 'hybrid' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('hybrid')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Sparkles size={14} /> Hybrid (RRF + Vector)
          </button>
        </div>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Showing {mockResults.length} indexed contract results ({searchMode.toUpperCase()} Mode)</span>
          <span>Parallel Latency: 14ms (Virtual Threads: True)</span>
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

            <div style={{ display: 'flex', gap: '16px', fontSize: '0.75rem', color: 'var(--text-dim)', paddingTop: '8px', borderTop: '1px solid rgba(255, 255, 255, 0.05)' }}>
              <span>BM25 Score: <strong>{result.bm25Score}</strong></span>
              <span>Semantic Score: <strong>{result.semanticScore}</strong></span>
              <span>RRF Fusion Score: <strong>{result.rrfScore}</strong></span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
