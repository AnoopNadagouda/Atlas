import React from 'react';
import { BarChart2 } from 'lucide-react';

export const ReasoningAnalyticsView: React.FC = () => {
  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <BarChart2 size={22} style={{ color: '#6366f1' }} />
        Reasoning Platform Performance Analytics
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '16px' }}>
        <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)' }}>
          <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>Completed Sessions</div>
          <div style={{ fontSize: '1.5rem', fontWeight: 800, color: '#10b981' }}>120</div>
        </div>
        <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)' }}>
          <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>Failed Sessions</div>
          <div style={{ fontSize: '1.5rem', fontWeight: 800, color: '#ef4444' }}>8</div>
        </div>
        <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)' }}>
          <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>Avg Correctness</div>
          <div style={{ fontSize: '1.5rem', fontWeight: 800, color: '#6366f1' }}>95.2%</div>
        </div>
        <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)' }}>
          <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>Hallucinations Caught</div>
          <div style={{ fontSize: '1.5rem', fontWeight: 800, color: '#06b6d4' }}>2</div>
        </div>
      </div>
    </div>
  );
};
