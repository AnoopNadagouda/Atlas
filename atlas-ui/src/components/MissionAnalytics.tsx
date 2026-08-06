import React from 'react';
import { BarChart2 } from 'lucide-react';


export const MissionAnalytics: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <BarChart2 className="text-accent" size={24} /> Mission Execution & AIOS Telemetry Analytics
        </h3>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))', gap: '16px' }}>
          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Total Missions Executed</div>
            <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '4px' }}>1,482</div>
            <div style={{ fontSize: '0.75rem', color: '#4ade80', marginTop: '4px' }}>+12.4% vs last week</div>
          </div>

          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Success Rate</div>
            <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '4px' }}>99.64%</div>
            <div style={{ fontSize: '0.75rem', color: '#4ade80', marginTop: '4px' }}>Automatic recovery active</div>
          </div>

          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Mean Mission Latency</div>
            <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '4px' }}>342 ms</div>
            <div style={{ fontSize: '0.75rem', color: '#60a5fa', marginTop: '4px' }}>Optimal distribution</div>
          </div>

          <div className="glass-panel" style={{ padding: '20px' }}>
            <div style={{ color: 'var(--text-dim)', fontSize: '0.85rem' }}>Cross-Agent Messages</div>
            <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '4px' }}>48.5K / day</div>
            <div style={{ fontSize: '0.75rem', color: '#c084fc', marginTop: '4px' }}>Kafka event bus</div>
          </div>
        </div>
      </div>
    </div>
  );
};
