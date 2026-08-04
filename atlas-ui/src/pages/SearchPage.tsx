import React, { useState } from 'react';
import { Search, Sparkles, SlidersHorizontal, ExternalLink, Zap } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'hybrid'>('keyword');

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Infrastructure Foundation Ready',
      url: 'https://atlas.search/docs/foundation',
      snippet: 'Phase 1.1 Infrastructure & Project Foundation is active. All Spring Boot microservices, Kafka topics, PostgreSQL schema mappings, and Redis query caches are initialized.',
      score: 0.99,
      domain: 'atlas.search',
    },
    {
      id: 'doc-bm25-002',
      title: 'BM25 Custom Inverted Index Segment Architecture',
      url: 'https://atlas.search/docs/bm25-spec',
      snippet: 'Memory mapped segment file structures (.doc, .pos, .dict) supporting fast term dictionary lookups and BM25 term frequency calculations.',
      score: 0.88,
      domain: 'atlas.search',
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio Playground</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test keyword BM25 retrieval, query expansion, and pipeline health.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Search across web, documentation, code repositories, or PDFs..."
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

        <div style={{ display: 'flex', gap: '16px', alignItems: 'center' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Retrieval Mode:</span>
          <button
            className={`btn ${searchMode === 'keyword' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('keyword')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            BM25 Keyword
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
          <span>Showing {mockResults.length} indexed contract results</span>
          <span>Latency: 12ms (Cached: false)</span>
        </div>

        {mockResults.map((result) => (
          <div key={result.id} className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <a href={result.url} target="_blank" rel="noreferrer" style={{ color: '#818cf8', fontWeight: 600, fontSize: '1.1rem', textDecoration: 'none', display: 'flex', alignItems: 'center', gap: '6px' }}>
                {result.title} <ExternalLink size={14} />
              </a>
              <span className="badge badge-info">Score: {result.score}</span>
            </div>
            <div style={{ fontSize: '0.8rem', color: 'var(--accent-cyan)' }}>{result.url}</div>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>{result.snippet}</p>
          </div>
        ))}
      </div>
    </div>
  );
};
