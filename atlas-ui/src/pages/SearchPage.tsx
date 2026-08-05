import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart, History, Clock, Code, FileCode } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics' | 'history' | 'code'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);
  const [selectedDate, setSelectedDate] = useState('2026-07-15');

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockCodeSearch = {
    repositories: [
      { name: 'AnoopNadagouda/Atlas', url: 'https://github.com/AnoopNadagouda/Atlas', files: 150, symbols: 450, langs: 'Java (75%), TypeScript (20%)' }
    ],
    symbols: [
      { name: 'HybridSearchService', type: 'CLASS', lang: 'JAVA', file: 'com/atlas/keywordsearch/hybrid/HybridSearchService.java', signature: 'public class HybridSearchService' },
      { name: 'executeSearch', type: 'METHOD', lang: 'JAVA', file: 'com/atlas/keywordsearch/hybrid/HybridSearchService.java', signature: 'public SearchResult executeSearch(SearchRequest req)' },
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
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'GRAPH', 'CLUSTER', 'PAGERANK', 'CODE_SEARCH'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Source Intelligence Platform</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test GitHub Code Search, AST Symbol Extraction, Repository Indexing, AI Code Copilot, Time Travel Search, and Operations Dashboard.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Type class or method symbol ('HybridSearchService', 'executeSearch')..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Code Search & Explain
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
            className={`btn ${searchMode === 'code' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('code')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Code size={14} /> GitHub Code Search (AST & Symbols)
          </button>
          <button
            className={`btn ${searchMode === 'history' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('history')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <History size={14} /> Time Travel Search
          </button>
          <button
            className={`btn ${searchMode === 'analytics' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('analytics')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <LineChart size={14} /> Search Analytics
          </button>
        </div>
      </div>

      {searchMode === 'code' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #10b981' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#10b981', fontWeight: 600 }}>
              <Code size={20} /> GitHub Code Search & AST Symbol Registry
            </div>
            <span className="badge badge-info">Repositories: {mockCodeSearch.repositories.length}</span>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Indexed Repositories:</span>
            {mockCodeSearch.repositories.map((repo) => (
              <div key={repo.name} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong style={{ color: '#60a5fa' }}>{repo.name}</strong>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{repo.langs}</div>
                </div>
                <span className="badge badge-info">{repo.symbols} Symbols ({repo.files} Files)</span>
              </div>
            ))}
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>AST Symbol Search Results:</span>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
              {mockCodeSearch.symbols.map((sym) => (
                <div key={sym.name} style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid rgba(255, 255, 255, 0.06)', borderRadius: '6px', padding: '10px 14px', display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem' }}>
                  <div>
                    <strong style={{ color: '#818cf8' }}>{sym.name}</strong> <span style={{ color: 'var(--text-muted)', fontSize: '0.75rem' }}>({sym.type})</span>
                    <div style={{ fontSize: '0.8rem', color: '#10b981' }}>{sym.signature}</div>
                    <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>File: {sym.file}</div>
                  </div>
                  <span className="badge badge-info">{sym.lang}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Grounded with Graph & AST Facts)
            </div>
          </div>
          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Grounded Source Results ({mockResults.length} indexed documents & symbols)</span>
          <span>Code Search Latency: 12ms (AST Match Validated)</span>
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
