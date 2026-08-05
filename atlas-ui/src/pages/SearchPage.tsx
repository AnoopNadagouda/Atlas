import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart, History, Clock, Code, FileCode, Users, Key, Package, Download } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics' | 'history' | 'code' | 'tenants' | 'plugins'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);
  const [selectedTenant, setSelectedTenant] = useState('default-tenant');

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockPlugins = [
    { id: 'plugin-markdown-parser', name: 'Markdown Document Parser', version: 'v1.2.0', category: 'PARSER', status: 'ENABLED', permissions: 'FILESYSTEM_READ, PARSER_REGISTER' },
    { id: 'plugin-ddg-search', name: 'DuckDuckGo External Web Search', version: 'v2.0.1', category: 'SEARCH_PROVIDER', status: 'ENABLED', permissions: 'NETWORK_CONNECT, SEARCH_FEDERATION' }
  ];

  const mockMarketplace = [
    { id: 'plugin-gemini-llm', name: 'Google Gemini 1.5 Pro Provider', version: 'v3.0.0', publisher: 'Google AI Team', downloads: '14.2k', rating: '4.9 ⭐' },
    { id: 'plugin-pdf-parser', name: 'PDF & OCR Document Parser', version: 'v2.1.4', publisher: 'Atlas Community', downloads: '8.9k', rating: '4.8 ⭐' }
  ];

  const mockTenants = [
    { id: 'default-tenant', name: 'Default Organization', domain: 'atlas.search', status: 'ACTIVE', docs: '150,000 / 1,000,000', storage: '1.28 GB / 100 GB', keys: 2 },
    { id: 'tenant-acme', name: 'Acme Corporation', domain: 'acme.org', status: 'ACTIVE', docs: '45,000 / 500,000', storage: '0.45 GB / 50 GB', keys: 1 }
  ];

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise Multi-Tenant AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 5.2 Plugin SDK, Extension Framework & Marketplace Foundation for modular platform extension.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      pageRankScore: 0.384,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'PLUGIN_EXTENDED', 'MARKETPLACE'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Plugin Marketplace Platform</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Plugin SDK, Extension Framework, Marketplace Discovery, Multi-Tenant Isolation, Code Search, and Operations Dashboard.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Search plugin-extended index..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Search
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
            className={`btn ${searchMode === 'plugins' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('plugins')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Package size={14} /> Plugin SDK & Marketplace
          </button>
          <button
            className={`btn ${searchMode === 'tenants' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('tenants')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Users size={14} /> Multi-Tenant SaaS
          </button>
          <button
            className={`btn ${searchMode === 'code' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('code')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Code size={14} /> GitHub Code Search
          </button>
        </div>
      </div>

      {searchMode === 'plugins' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px', borderLeft: '4px solid #10b981' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#10b981', fontWeight: 600 }}>
              <Package size={20} /> Plugin SDK, Extension Framework & Marketplace Catalog
            </div>
            <span className="badge badge-info">Installed Plugins: {mockPlugins.length}</span>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Installed Extension Plugins:</span>
            {mockPlugins.map((plugin) => (
              <div key={plugin.id} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong style={{ color: '#60a5fa' }}>{plugin.name}</strong> <span style={{ color: 'var(--text-muted)', fontSize: '0.75rem' }}>({plugin.version})</span>
                  <div style={{ fontSize: '0.8rem', color: '#10b981' }}>Permissions: {plugin.permissions}</div>
                </div>
                <span className="badge badge-info">{plugin.status}</span>
              </div>
            ))}
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Plugin Marketplace Recommendations:</span>
            {mockMarketplace.map((item) => (
              <div key={item.id} style={{ background: 'rgba(255, 255, 255, 0.02)', border: '1px solid rgba(255, 255, 255, 0.06)', borderRadius: '8px', padding: '12px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong style={{ color: '#818cf8' }}>{item.name}</strong> <span style={{ color: 'var(--text-muted)', fontSize: '0.75rem' }}>by {item.publisher}</span>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Downloads: {item.downloads} | Rating: {item.rating}</div>
                </div>
                <button className="btn btn-primary" style={{ padding: '4px 12px', fontSize: '0.75rem' }}>
                  <Download size={12} /> Install Plugin
                </button>
              </div>
            ))}
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Grounded with Extension Plugins)
            </div>
          </div>
          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Plugin-Extended Search Results ({mockResults.length} indexed documents)</span>
          <span>Tenant Context: {selectedTenant}</span>
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
