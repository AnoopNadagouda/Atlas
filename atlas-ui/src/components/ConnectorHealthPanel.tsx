import React from 'react';
import { ShieldCheck, Activity, AlertTriangle, CheckCircle2, Clock } from 'lucide-react';

interface ConnectorHealthPanelProps {
  connectors: any[];
}

export const ConnectorHealthPanel: React.FC<ConnectorHealthPanelProps> = ({ connectors }) => {
  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Activity size={18} color="#22c55e" /> Enterprise Connector Health Status
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '12px' }}>
        {connectors.map((c) => {
          const isUp = c.status !== 'DOWN' && c.status !== 'FAILED';
          return (
            <div
              key={c.connectorId}
              style={{
                padding: '12px',
                background: 'rgba(255,255,255,0.03)',
                border: '1px solid var(--border-color)',
                borderRadius: '8px',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
              }}
            >
              <div>
                <span style={{ fontSize: '0.85rem', fontWeight: 600 }}>{c.name}</span>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)', display: 'flex', alignItems: 'center', gap: '4px', marginTop: '2px' }}>
                  <Clock size={12} /> {c.latencyMs || 15} ms
                </div>
              </div>
              <div>
                {isUp ? (
                  <CheckCircle2 size={18} color="#22c55e" />
                ) : (
                  <AlertTriangle size={18} color="#ef4444" />
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
