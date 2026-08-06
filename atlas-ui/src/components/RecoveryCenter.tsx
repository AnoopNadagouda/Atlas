import React from 'react';
import { RefreshCw } from 'lucide-react';

export const RecoveryCenter: React.FC = () => {
  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <RefreshCw size={22} style={{ color: '#f59e0b' }} />
        Autonomous Failure Analyzer & Recovery Center
      </h3>

      <div style={{ padding: '16px', borderRadius: '8px', background: 'rgba(245, 158, 11, 0.05)', border: '1px solid rgba(245, 158, 11, 0.2)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <span style={{ fontWeight: 700, fontSize: '0.9rem', color: '#fbbf24' }}>Session Failure Handled: session-db-timeout</span>
          <span className="badge badge-success">Recovered</span>
        </div>
        <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', margin: '8px 0' }}>
          <strong>Root Cause:</strong> Upstream DB Connection Timeout after 5000ms.
        </p>
        <p style={{ fontSize: '0.85rem', color: '#34d399', margin: 0 }}>
          <strong>Recovery Strategy Executed:</strong> Switched to replica connection pool and resumed query execution without loss of state.
        </p>
      </div>
    </div>
  );
};
