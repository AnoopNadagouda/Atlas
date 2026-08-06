import React, { useState } from 'react';
import { ShieldCheck } from 'lucide-react';


export const PolicyManager: React.FC = () => {
  const [policies, setPolicies] = useState([
    { id: 'pol-1', name: 'Multi-Tenant Data Isolation Guardrail', category: 'SECURITY', status: 'ENFORCED', target: 'ALL_MICROSERVICES' },
    { id: 'pol-2', name: 'Autonomous Recovery Max Retries Policy', category: 'RECOVERY', status: 'ENFORCED', target: 'ORCHESTRATOR' },
    { id: 'pol-3', name: 'Agent Reasoning Deductive Depth Cap (Max 10)', category: 'GOVERNANCE', status: 'ENFORCED', target: 'REASONING_SERVICE' },
    { id: 'pol-4', name: 'Episodic Memory Vector Retention Window', category: 'STORAGE', status: 'ENFORCED', target: 'MEMORY_SERVICE' },
  ]);

  const togglePolicy = (id: string) => {
    setPolicies(policies.map(p => p.id === id ? { ...p, status: p.status === 'ENFORCED' ? 'DISABLED' : 'ENFORCED' } : p));
  };

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '10px' }}>
            <ShieldCheck className="text-accent" size={24} /> AIOS Governance & Policy Engine
          </h3>
          <p style={{ color: 'var(--text-dim)', marginTop: '4px', marginBottom: 0, fontSize: '0.9rem' }}>
            Declarative Policy Rules, Real-Time Compliance Audit & Tenant Security Boundaries
          </p>
        </div>
        <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.2)', color: '#4ade80', padding: '8px 16px' }}>
          Policy Engine ACTIVE
        </span>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
        {policies.map(p => (
          <div key={p.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
            <div>
              <div style={{ fontWeight: 600, fontSize: '1rem' }}>{p.name}</div>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '4px' }}>
                Category: <code>{p.category}</code> • Target: <code>{p.target}</code>
              </div>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <span className="badge" style={{ backgroundColor: p.status === 'ENFORCED' ? 'rgba(34, 197, 94, 0.15)' : 'rgba(239, 68, 68, 0.15)', color: p.status === 'ENFORCED' ? '#4ade80' : '#f87171', fontSize: '0.75rem' }}>
                {p.status}
              </span>
              <button className="btn btn-secondary" style={{ padding: '6px 12px', fontSize: '0.8rem' }} onClick={() => togglePolicy(p.id)}>
                Toggle Rule
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
