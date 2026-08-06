import React from 'react';
import { Bot, Cpu, Zap, Activity, CheckCircle, Shield } from 'lucide-react';

export const AgentFleet: React.FC = () => {
  const agents = [
    { id: 'agent-orchestrator-1', name: 'Master Lead Orchestrator', role: 'CHIEF_ORCHESTRATOR', status: 'ACTIVE', tasksCompleted: 142, load: '18%', memory: '1.2 GB' },
    { id: 'agent-reasoner-4', name: 'Neural Deductive Reasoner', role: 'REASONING_ENGINE', status: 'ACTIVE', tasksCompleted: 98, load: '34%', memory: '2.4 GB' },
    { id: 'agent-crawler-8', name: 'Distributed Crawler Dispatcher', role: 'DATA_INGESTION', status: 'IDLE', tasksCompleted: 310, load: '4%', memory: '512 MB' },
    { id: 'agent-indexer-2', name: 'Vector Index Construction Agent', role: 'EMBEDDING_INDEXER', status: 'ACTIVE', tasksCompleted: 215, load: '48%', memory: '3.1 GB' },
    { id: 'agent-policy-1', name: 'Enterprise Governance Agent', role: 'SECURITY_AUDITOR', status: 'ACTIVE', tasksCompleted: 512, load: '8%', memory: '256 MB' },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px' }}>
          <div>
            <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '10px' }}>
              <Bot className="text-accent" size={24} /> AIOS Autonomous Agent Fleet
            </h3>
            <p style={{ color: 'var(--text-dim)', marginTop: '4px', marginBottom: 0, fontSize: '0.9rem' }}>
              Dynamic Multi-Agent Coordination, Capability-Based Task Allocation & Shared Memory Context
            </p>
          </div>
          <span className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.2)', color: '#60a5fa', padding: '6px 12px' }}>
            5 Registered Fleet Nodes
          </span>
        </div>

        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', fontSize: '0.9rem' }}>
            <thead>
              <tr style={{ borderBottom: '1px solid rgba(255, 255, 255, 0.1)', color: 'var(--text-dim)' }}>
                <th style={{ padding: '12px' }}>Agent Name & ID</th>
                <th style={{ padding: '12px' }}>Role</th>
                <th style={{ padding: '12px' }}>Status</th>
                <th style={{ padding: '12px' }}>Tasks Completed</th>
                <th style={{ padding: '12px' }}>CPU Load</th>
                <th style={{ padding: '12px' }}>RAM Allocation</th>
              </tr>
            </thead>
            <tbody>
              {agents.map((agent) => (
                <tr key={agent.id} style={{ borderBottom: '1px solid rgba(255, 255, 255, 0.05)' }}>
                  <td style={{ padding: '12px' }}>
                    <div style={{ fontWeight: 600 }}>{agent.name}</div>
                    <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>{agent.id}</div>
                  </td>
                  <td style={{ padding: '12px' }}>
                    <span className="badge" style={{ backgroundColor: 'rgba(168, 85, 247, 0.15)', color: '#c084fc', fontSize: '0.75rem' }}>
                      {agent.role}
                    </span>
                  </td>
                  <td style={{ padding: '12px' }}>
                    <span className="badge" style={{ backgroundColor: agent.status === 'ACTIVE' ? 'rgba(34, 197, 94, 0.15)' : 'rgba(234, 179, 8, 0.15)', color: agent.status === 'ACTIVE' ? '#4ade80' : '#fde047', fontSize: '0.75rem' }}>
                      {agent.status}
                    </span>
                  </td>
                  <td style={{ padding: '12px' }}>{agent.tasksCompleted}</td>
                  <td style={{ padding: '12px' }}>{agent.load}</td>
                  <td style={{ padding: '12px' }}>{agent.memory}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
