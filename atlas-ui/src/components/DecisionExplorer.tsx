import React from 'react';
import { Compass, Check } from 'lucide-react';

export const DecisionExplorer: React.FC = () => {
  const decisions = [
    {
      id: 'dec-101',
      point: 'Select Microservice Retry Strategy',
      chosen: 'Exponential Backoff with Jitter (Max 5 Retries)',
      justification: 'Minimizes thundering herd risk while ensuring 99.9% eventual resolution.',
      riskScore: 0.08,
      confidence: 0.94,
    },
    {
      id: 'dec-102',
      point: 'Vector Store Index Scaling Method',
      chosen: 'HNSW Tiered Indexing with In-Memory Caching',
      justification: 'Balances search recall precision with low search query latency.',
      riskScore: 0.12,
      confidence: 0.91,
    }
  ];

  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Compass size={22} style={{ color: '#3b82f6' }} />
        Autonomous Decision Explorer
      </h3>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        {decisions.map((d) => (
          <div key={d.id} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)', border: '1px solid rgba(255,255,255,0.08)' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <span style={{ fontWeight: 700, fontSize: '0.95rem' }}>{d.point}</span>
              <span style={{ fontSize: '0.8rem', color: '#3b82f6', fontWeight: 700 }}>Confidence: {d.confidence}</span>
            </div>
            <div style={{ marginTop: '8px', padding: '10px', borderRadius: '6px', background: 'rgba(16, 185, 129, 0.1)', color: '#34d399', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
              <Check size={16} /> Selected Option: {d.chosen}
            </div>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', margin: '8px 0 0 0' }}>
              <strong>Justification:</strong> {d.justification}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};
