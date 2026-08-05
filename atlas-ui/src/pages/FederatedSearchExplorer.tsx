import React, { useState } from 'react';
import { Search, Globe, ShieldCheck, Lock, ExternalLink, Clock, Layers } from 'lucide-react';

export const FederatedSearchExplorer: React.FC = () => {
  const [query, setQuery] = useState('Atlas search architecture');
  const [results, setResults] = useState<any[]>([]);
  const [perSourceLatency, setPerSourceLatency] = useState<any>({});
  const [executionTime, setExecutionTime] = useState<number | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    setTimeout(() => {
      const mockResults = [
        {
          resultId: 'internal-1',
          title: 'Atlas Hybrid BM25 & HNSW Vector Engine Architecture',
          snippet: 'Internal index result for parallel hybrid BM25 and vector search execution.',
          documentUrl: '/documents/doc-pdf-001',
          score: 0.98,
          sourceName: 'Atlas Internal Index',
          connectorId: 'internal_index',
          repository: 'atlas-keyword-search',
          permissionStatus: 'PUBLIC',
          aclStatus: 'VERIFIED',
          lastSync: 'Just now',
          owner: 'Atlas System',
        },
        {
          resultId: 'gh-1',
          title: '[GitHub] Issue #101: Optimize Parallel Vector Shard Query Fan-Out',
          snippet: 'GitHub code & issue documentation for parallel HNSW vector shards in Atlas search coordinator.',
          documentUrl: 'https://github.com/AnoopNadagouda/Atlas/issues/101',
          score: 0.96,
          sourceName: 'GitHub Enterprise',
          connectorId: 'github',
          repository: 'AnoopNadagouda/Atlas',
          permissionStatus: 'ALLOWED',
          aclStatus: 'VERIFIED',
          lastSync: '2 mins ago',
          owner: 'Anoop Nadagouda',
        },
        {
          resultId: 'conf-1',
          title: '[Confluence] Atlas Enterprise Architecture Blueprint & Runbook',
          snippet: 'Confluence Wiki Page detailing distributed cluster topology, Kafka topics, and DR failover runbook.',
          documentUrl: 'https://confluence.company.com/display/ENG/Atlas+Runbook',
          score: 0.95,
          sourceName: 'Atlassian Confluence',
          connectorId: 'confluence',
          repository: 'ENG Space',
          permissionStatus: 'ALLOWED',
          aclStatus: 'VERIFIED',
          lastSync: '10 mins ago',
          owner: 'Infra Team',
        },
        {
          resultId: 'slack-1',
          title: '[Slack] #proj-atlas: Discussion on 1B doc index partitioning',
          snippet: 'Slack conversation regarding sub-10ms BM25 scoring and disk segment partitioning strategy.',
          documentUrl: 'https://company.slack.com/archives/C12345/p1722800000',
          score: 0.92,
          sourceName: 'Slack Enterprise Grid',
          connectorId: 'slack',
          repository: '#proj-atlas',
          permissionStatus: 'ALLOWED',
          aclStatus: 'VERIFIED',
          lastSync: '15 mins ago',
          owner: 'Lead Search Engineer',
        },
      ];

      setResults(mockResults);
      setPerSourceLatency({
        internal_index: 8,
        github: 22,
        confluence: 31,
        slack: 18,
      });
      setExecutionTime(38);
      setLoading(false);
    }, 300);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Header */}
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700, background: 'linear-gradient(90deg, #fff, #9ca3af)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
          Unified Federated Search Explorer
        </h2>
        <p style={{ color: 'var(--text-dim)', fontSize: '0.9rem', marginTop: '4px' }}>
          Execute cross-system federated search across internal indexes and enterprise connectors with instant ACL verification.
        </p>
      </div>

      {/* Search Input Box */}
      <form onSubmit={handleSearch} className="glass-panel" style={{ padding: '20px', display: 'flex', gap: '16px', alignItems: 'center' }}>
        <div style={{ position: 'relative', flex: 1 }}>
          <Search size={20} style={{ position: 'absolute', left: '14px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
          <input
            type="text"
            className="input"
            placeholder="Search across GitHub, Confluence, Slack, Google Drive, Jira, and internal indexes..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            style={{ paddingLeft: '44px', width: '100%', fontSize: '1rem' }}
          />
        </div>
        <button type="submit" className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '8px', padding: '12px 24px' }}>
          <Globe size={18} /> Federated Search
        </button>
      </form>

      {/* Per-Source Latency & Status Bar */}
      {executionTime !== null && (
        <div className="glass-panel" style={{ padding: '16px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '16px', fontSize: '0.85rem' }}>
            <span style={{ color: 'var(--text-muted)', display: 'flex', alignItems: 'center', gap: '6px' }}>
              <Clock size={14} color="#6366f1" /> Total Latency: <strong>{executionTime} ms</strong>
            </span>
            <span className="badge badge-success">Partial Failure Tolerant</span>
          </div>

          <div style={{ display: 'flex', gap: '12px', fontSize: '0.75rem' }}>
            {Object.entries(perSourceLatency).map(([source, lat]) => (
              <span key={source} className="badge badge-info">
                {source}: {String(lat)} ms
              </span>
            ))}
          </div>
        </div>
      )}

      {/* Federated Search Results List */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        {results.map((res) => (
          <div key={res.resultId} className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <a href={res.documentUrl} target="_blank" rel="noopener noreferrer" style={{ fontSize: '1.1rem', fontWeight: 600, color: '#6366f1', textDecoration: 'none', display: 'flex', alignItems: 'center', gap: '6px' }}>
                  {res.title} <ExternalLink size={14} />
                </a>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginTop: '2px', display: 'block' }}>
                  {res.documentUrl}
                </span>
              </div>

              <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                <span className="badge badge-info">{res.sourceName}</span>
                <span className="badge badge-success" style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
                  <ShieldCheck size={12} /> {res.aclStatus}
                </span>
              </div>
            </div>

            <p style={{ fontSize: '0.9rem', color: 'var(--text-muted)', lineHeight: '1.5' }}>
              {res.snippet}
            </p>

            <div style={{ display: 'flex', gap: '16px', fontSize: '0.75rem', color: 'var(--text-dim)', borderTop: '1px solid rgba(255,255,255,0.03)', paddingTop: '8px' }}>
              <span>Repository: <strong>{res.repository}</strong></span>
              <span>Owner: <strong>{res.owner}</strong></span>
              <span>Last Synced: <strong>{res.lastSync}</strong></span>
              <span>RRF Score: <strong>{res.score}</strong></span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
