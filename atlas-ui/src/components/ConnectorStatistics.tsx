import React from 'react';
import { BarChart3, ArrowUpRight, Zap, RefreshCw } from 'lucide-react';

interface ConnectorStatisticsProps {
  totalSyncedDocs: number;
  activeConnectorsCount: number;
  avgLatencyMs: number;
}

export const ConnectorStatistics: React.FC<ConnectorStatisticsProps> = ({
  totalSyncedDocs,
  activeConnectorsCount,
  avgLatencyMs,
}) => {
  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        <BarChart3 size={18} color="#06b6d4" /> Sync Throughput & Performance Metrics
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '16px' }}>
        <div style={{ padding: '16px', background: 'rgba(99,102,241,0.1)', borderRadius: '8px', border: '1px solid rgba(99,102,241,0.2)' }}>
          <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)', textTransform: 'uppercase' }}>Active Connectors</span>
          <h4 style={{ fontSize: '1.5rem', fontWeight: 700, color: '#6366f1', marginTop: '4px' }}>{activeConnectorsCount}</h4>
        </div>

        <div style={{ padding: '16px', background: 'rgba(34,197,94,0.1)', borderRadius: '8px', border: '1px solid rgba(34,197,94,0.2)' }}>
          <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)', textTransform: 'uppercase' }}>Docs Synced</span>
          <h4 style={{ fontSize: '1.5rem', fontWeight: 700, color: '#22c55e', marginTop: '4px' }}>{totalSyncedDocs.toLocaleString()}</h4>
        </div>

        <div style={{ padding: '16px', background: 'rgba(6,182,212,0.1)', borderRadius: '8px', border: '1px solid rgba(6,182,212,0.2)' }}>
          <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)', textTransform: 'uppercase' }}>Avg Latency</span>
          <h4 style={{ fontSize: '1.5rem', fontWeight: 700, color: '#06b6d4', marginTop: '4px' }}>{avgLatencyMs} ms</h4>
        </div>
      </div>
    </div>
  );
};
