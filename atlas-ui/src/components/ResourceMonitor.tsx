import React from 'react';
import { Zap } from 'lucide-react';


export const ResourceMonitor: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <Zap className="text-accent" size={24} /> Autonomous Resource Balancer & Compute Allocator
        </h3>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '16px' }}>
          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>CPU Pool Allocated</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 700, marginTop: '4px' }}>68 / 192 Cores</div>
            <div style={{ height: '6px', background: 'rgba(255, 255, 255, 0.1)', borderRadius: '3px', marginTop: '12px', overflow: 'hidden' }}>
              <div style={{ width: '35%', height: '100%', background: '#3b82f6' }} />
            </div>
          </div>

          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Memory Pool (RAM)</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 700, marginTop: '4px' }}>136 / 384 GB</div>
            <div style={{ height: '6px', background: 'rgba(255, 255, 255, 0.1)', borderRadius: '3px', marginTop: '12px', overflow: 'hidden' }}>
              <div style={{ width: '35%', height: '100%', background: '#a855f7' }} />
            </div>
          </div>

          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Reasoning GPU VRAM</div>
            <div style={{ fontSize: '1.5rem', fontWeight: 700, marginTop: '4px' }}>24 / 96 GB</div>
            <div style={{ height: '6px', background: 'rgba(255, 255, 255, 0.1)', borderRadius: '3px', marginTop: '12px', overflow: 'hidden' }}>
              <div style={{ width: '25%', height: '100%', background: '#22c55e' }} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
