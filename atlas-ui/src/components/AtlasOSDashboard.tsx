import React from 'react';
import { Cpu, Activity, Play, CheckCircle, AlertTriangle, ShieldCheck, Zap, Server } from 'lucide-react';

interface AtlasOSDashboardProps {
  onNavigate?: (tab: string) => void;
}

export const AtlasOSDashboard: React.FC<AtlasOSDashboardProps> = ({ onNavigate }) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Top Banner */}
      <div className="glass-panel" style={{ padding: '24px', background: 'linear-gradient(135deg, rgba(37, 99, 235, 0.15), rgba(147, 51, 234, 0.15))', border: '1px solid rgba(255, 255, 255, 0.15)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
              <Cpu className="text-accent" size={28} />
              <h2 style={{ fontSize: '1.5rem', fontWeight: 700, margin: 0 }}>Atlas AI Operating System (AIOS v5.9.0)</h2>
            </div>
            <p style={{ color: 'var(--text-dim)', marginTop: '8px', marginBottom: 0, fontSize: '0.95rem' }}>
              Autonomous Enterprise Orchestration, Dynamic Multi-Agent Fleet Management & Self-Healing Core
            </p>
          </div>
          <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.2)', color: '#4ade80', padding: '8px 16px', fontSize: '0.875rem', fontWeight: 600, border: '1px solid rgba(34, 197, 94, 0.3)' }}>
            System Autonomous: ONLINE
          </span>
        </div>
      </div>

      {/* Metrics Row */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))', gap: '16px' }}>
        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-dim)', fontSize: '0.875rem' }}>
            <span>Active Missions</span>
            <Play size={18} color="#3b82f6" />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '8px' }}>14</div>
          <div style={{ fontSize: '0.75rem', color: '#4ade80', marginTop: '4px' }}>↑ 3 created in last hour</div>
        </div>

        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-dim)', fontSize: '0.875rem' }}>
            <span>Agent Fleet</span>
            <Zap size={18} color="#a855f7" />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '8px' }}>42 Active</div>
          <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>100% capacity balance</div>
        </div>

        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-dim)', fontSize: '0.875rem' }}>
            <span>Platform Health</span>
            <Activity size={18} color="#22c55e" />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '8px' }}>99.98%</div>
          <div style={{ fontSize: '0.75rem', color: '#4ade80', marginTop: '4px' }}>19 / 19 Modules Healthy</div>
        </div>

        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', color: 'var(--text-dim)', fontSize: '0.875rem' }}>
            <span>Governance & Policy</span>
            <ShieldCheck size={18} color="#eab308" />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 700, marginTop: '8px' }}>Enforced</div>
          <div style={{ fontSize: '0.75rem', color: '#4ade80', marginTop: '4px' }}>0 Policy Violations</div>
        </div>
      </div>

      {/* Quick Launch & Status Panel */}
      <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '20px' }}>
        <div className="glass-panel" style={{ padding: '20px' }}>
          <h3 style={{ fontSize: '1.1rem', fontWeight: 600, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Server size={20} color="#3b82f6" /> AIOS Microservice Mesh Overview
          </h3>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            {[
              { name: 'Atlas Orchestrator Service', port: 8090, status: 'OPERATIONAL', load: '18%' },
              { name: 'Atlas Agent Core Service', port: 8086, status: 'OPERATIONAL', load: '32%' },
              { name: 'Atlas Workflow Service', port: 8087, status: 'OPERATIONAL', load: '24%' },
              { name: 'Atlas Memory Service', port: 8088, status: 'OPERATIONAL', load: '41%' },
              { name: 'Atlas Reasoning Service', port: 8089, status: 'OPERATIONAL', load: '29%' },
            ].map((svc) => (
              <div key={svc.name} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '12px', background: 'rgba(255, 255, 255, 0.03)', borderRadius: '8px' }}>
                <div>
                  <div style={{ fontWeight: 600 }}>{svc.name}</div>
                  <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>Port :{svc.port} • REST / gRPC / Kafka</div>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
                  <span style={{ fontSize: '0.85rem', color: 'var(--text-dim)' }}>Load: {svc.load}</span>
                  <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#4ade80', fontSize: '0.75rem', padding: '4px 8px' }}>
                    {svc.status}
                  </span>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <h3 style={{ fontSize: '1.1rem', fontWeight: 600, margin: 0 }}>AIOS Quick Actions</h3>
          <button className="btn btn-primary" style={{ width: '100%' }} onClick={() => onNavigate?.('mission-control')}>
            <Play size={16} /> Formulate Enterprise Mission
          </button>
          <button className="btn btn-secondary" style={{ width: '100%' }} onClick={() => onNavigate?.('agent-fleet')}>
            <Zap size={16} /> Inspect Agent Fleet
          </button>
          <button className="btn btn-secondary" style={{ width: '100%' }} onClick={() => onNavigate?.('platform-health')}>
            <Activity size={16} /> View 19-Module Health
          </button>
          <button className="btn btn-secondary" style={{ width: '100%' }} onClick={() => onNavigate?.('automation-center')}>
            <CheckCircle size={16} /> Automation Jobs
          </button>
        </div>
      </div>
    </div>
  );
};
