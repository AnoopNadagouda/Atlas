import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart, History, Clock, Code, FileCode, Users, Key } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics' | 'history' | 'code' | 'tenants'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);
  const [selectedTenant, setSelectedTenant] = useState('default-tenant');

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockTenants = [
    { id: 'default-tenant', name: 'Default Organization', domain: 'atlas.search', status: 'ACTIVE', docs: '150,000 / 1,000,000', storage: '1.28 GB / 100 GB', keys: 2 },
    { id: 'tenant-acme', name: 'Acme Corporation', domain: 'acme.org', status: 'ACTIVE', docs: '45,000 / 500,000', storage: '0.45 GB / 50 GB', keys: 1 }
  ];

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise Multi-Tenant AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 5.1 Enterprise Multi-Tenant Platform with Tenant Storage Isolation, Quota Enforcement, and API Key Rotation.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      pageRankScore: 0.384,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'TENANT_ISOLATED', 'ENTERPRISE_SAAS'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight 700 }}>Search Studio & Multi-Tenant SaaS Platform</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Multi-Tenant Storage Isolation, Quota Management, Tenant API Keys, Code Search, Time Travel Search, and Operations Dashboard.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Search tenant-isolated index..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Tenant Search
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
            className={`btn ${searchMode === 'tenants' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('tenants')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Users size={14} /> Multi-Tenant SaaS Manager
          </button>
          <button
            className={`btn ${searchMode === 'code' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('code')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Code size={14} /> GitHub Code Search
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

      {searchMode === 'tenants' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #818cf8' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Users size={20} /> Enterprise Multi-Tenant SaaS Manager & API Key Registry
            </div>
            <span className="badge badge-info">Active Context: {selectedTenant}</span>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Registered Organizations & Quotas:</span>
            {mockTenants.map((tenant) => (
              <div key={tenant.id} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong style={{ color: '#60a5fa' }}>{tenant.name}</strong> <span style={{ color: 'var(--text-muted)', fontSize: '0.75rem' }}>({tenant.domain})</span>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Docs: {tenant.docs} | Storage: {tenant.storage}</div>
                </div>
                <div style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
                  <span className="badge badge-info">{tenant.keys} API Keys</span>
                  <button
                    className={`btn ${selectedTenant === tenant.id ? 'btn-primary' : 'btn-secondary'}`}
                    onClick={() => setSelectedTenant(tenant.id)}
                    style={{ padding: '4px 10px', fontSize: '0.75rem' }}
                  >
                    Switch Context
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Tenant Context: {selectedTenant})
            </div>
          </div>
          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Tenant Isolated Search Results ({mockResults.length} indexed documents)</span>
          <span>Tenant Context: {selectedTenant} (Storage Isolated at ./data/{selectedTenant}/)</span>
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
