import React, { useState } from 'react';
import { Play, Code, CheckCircle, Copy } from 'lucide-react';
import { CodeBlock } from './CodeBlock';

export const InteractiveApiRunner: React.FC = () => {
  const [selectedEndpoint, setSelectedEndpoint] = useState('create-mission');
  const [response, setResponse] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const endpoints: Record<string, { method: string; path: string; desc: string; defaultBody: string; mockResponse: string }> = {
    'create-mission': {
      method: 'POST',
      path: '/api/v23/orchestrator/missions',
      desc: 'Formulate a new autonomous mission in Atlas AIOS Orchestrator',
      defaultBody: JSON.stringify({ title: 'Enterprise Financial Risk Analysis', description: 'Parse SEC filings and execute vector search.', priority: 5 }, null, 2),
      mockResponse: JSON.stringify({ id: 'msn-891', tenantId: 'default-tenant', title: 'Enterprise Financial Risk Analysis', status: 'CREATED', priority: 5, progressPercentage: 0.0, createdAt: new Date().toISOString() }, null, 2)
    },
    'execute-mission': {
      method: 'POST',
      path: '/api/v23/orchestrator/missions/msn-891/execute',
      desc: 'Trigger autonomous execution of mission across microservices mesh',
      defaultBody: '{}',
      mockResponse: JSON.stringify({ id: 'exec-402', missionId: 'msn-891', tenantId: 'default-tenant', status: 'COMPLETED', executionTimeMs: 342.5, startTime: new Date().toISOString() }, null, 2)
    },
    'search-query': {
      method: 'POST',
      path: '/api/v1/search/query',
      desc: 'Execute BM25 keyword + Neural embedding hybrid search',
      defaultBody: JSON.stringify({ query: 'distributed inverted index', limit: 10, tenantId: 'default-tenant' }, null, 2),
      mockResponse: JSON.stringify({ query: 'distributed inverted index', totalHits: 14820, executionTimeMs: 12.4, results: [{ id: 'doc-1', score: 4.82, title: 'Inverted Index Architecture' }] }, null, 2)
    },
    'reasoning-execute': {
      method: 'POST',
      path: '/api/v22/reasoning/sessions',
      desc: 'Execute multi-step reasoning session with Tree of Thoughts',
      defaultBody: JSON.stringify({ mode: 'TREE_OF_THOUGHTS', taskDescription: 'Assess security isolation risks.' }, null, 2),
      mockResponse: JSON.stringify({ sessionId: 'rsn-301', mode: 'TREE_OF_THOUGHTS', status: 'SUCCESS', confidenceScore: 0.92 }, null, 2)
    },
  };

  const current = endpoints[selectedEndpoint];

  const handleRun = () => {
    setLoading(true);
    setTimeout(() => {
      setResponse(current.mockResponse);
      setLoading(false);
    }, 400);
  };

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Code className="text-accent" size={24} /> Interactive API Sandbox & Code Generator
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: '20px' }}>
        {/* Endpoint Selector */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
          {Object.keys(endpoints).map(key => (
            <button
              key={key}
              onClick={() => { setSelectedEndpoint(key); setResponse(null); }}
              className={`btn ${selectedEndpoint === key ? 'btn-primary' : 'btn-secondary'}`}
              style={{ justifyContent: 'flex-start', textAlign: 'left', padding: '10px 12px', fontSize: '0.85rem' }}
            >
              <span className="badge" style={{ backgroundColor: endpoints[key].method === 'POST' ? 'rgba(59, 130, 246, 0.2)' : 'rgba(34, 197, 94, 0.2)', color: endpoints[key].method === 'POST' ? '#60a5fa' : '#4ade80', fontSize: '0.7rem', padding: '2px 6px' }}>
                {endpoints[key].method}
              </span>
              <span style={{ marginLeft: '6px' }}>{key}</span>
            </button>
          ))}
        </div>

        {/* Sandbox Content */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
            <div style={{ fontWeight: 600, fontSize: '1rem', color: '#60a5fa' }}>{current.method} {current.path}</div>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', margin: '4px 0 0' }}>{current.desc}</p>
          </div>

          <div>
            <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Request Body (JSON)</label>
            <textarea
              className="input"
              rows={4}
              value={current.defaultBody}
              readOnly
              style={{ width: '100%', fontFamily: 'monospace', fontSize: '0.85rem' }}
            />
          </div>

          <button className="btn btn-primary" onClick={handleRun} disabled={loading} style={{ alignSelf: 'flex-start' }}>
            <Play size={16} /> {loading ? 'Executing API Request...' : 'Send API Request'}
          </button>

          {response && (
            <div>
              <label style={{ fontSize: '0.85rem', color: '#4ade80', marginBottom: '6px', display: 'block' }}>Response 200 OK</label>
              <CodeBlock code={response} language="json" filename="Response Payload" />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
