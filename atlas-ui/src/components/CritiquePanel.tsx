import React from 'react';
import { ShieldCheck, CheckCircle2 } from 'lucide-react';

export const CritiquePanel: React.FC = () => {
  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <ShieldCheck size={22} style={{ color: '#06b6d4' }} />
        Critic Engine & Hallucination Detector
      </h3>

      <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)', border: '1px solid rgba(255,255,255,0.08)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
          <span style={{ fontWeight: 700, fontSize: '0.9rem' }}>Execution Evaluation: Session #882</span>
          <span className="badge badge-success">Quality Score: 0.94</span>
        </div>
        <div style={{ display: 'flex', gap: '16px', fontSize: '0.85rem' }}>
          <span style={{ color: '#10b981', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <CheckCircle2 size={16} /> Zero Hallucinations Detected
          </span>
          <span style={{ color: '#10b981', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <CheckCircle2 size={16} /> Schema Constrained
          </span>
        </div>
      </div>
    </div>
  );
};
