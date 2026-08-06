import React from 'react';
import { Server, Cpu, HardDrive, Network, Layers } from 'lucide-react';

export const ClusterOverview: React.FC = () => {
  const nodes = [
    { id: 'node-us-east-1a', name: 'Atlas AIOS Cluster Alpha', cpu: '24 / 64 Cores (37.5%)', ram: '48 GB / 128 GB (37.5%)', role: 'PRIMARY_MASTER', ip: '10.0.1.12' },
    { id: 'node-us-east-1b', name: 'Atlas AIOS Cluster Beta', cpu: '16 / 64 Cores (25.0%)', ram: '32 GB / 128 GB (25.0%)', role: 'WORKER_NODE', ip: '10.0.1.13' },
    { id: 'node-us-east-1c', name: 'Atlas AIOS Cluster Gamma', cpu: '28 / 64 Cores (43.7%)', ram: '56 GB / 128 GB (43.7%)', role: 'WORKER_NODE', ip: '10.0.1.14' },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <Server className="text-accent" size={24} /> Distributed Multi-Node Cluster Topology
        </h3>
        <p style={{ color: 'var(--text-dim)', marginBottom: '20px', fontSize: '0.9rem' }}>
          Autonomous Node Auto-Scaling, Global State Synchronization & Failover Coordinator
        </p>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '16px' }}>
          {nodes.map(node => (
            <div key={node.id} style={{ padding: '20px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                <span style={{ fontWeight: 700, fontSize: '1.05rem' }}>{node.name}</span>
                <span className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.2)', color: '#60a5fa', fontSize: '0.75rem' }}>
                  {node.role}
                </span>
              </div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-dim)', display: 'flex', flexDirection: 'column', gap: '6px' }}>
                <div>IP Address: <code style={{ color: '#fff' }}>{node.ip}</code></div>
                <div>CPU Utilization: <span style={{ color: '#4ade80' }}>{node.cpu}</span></div>
                <div>RAM Utilization: <span style={{ color: '#4ade80' }}>{node.ram}</span></div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
