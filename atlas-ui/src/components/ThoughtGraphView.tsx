import React from 'react';
import { GitGraph } from 'lucide-react';

export const ThoughtGraphView: React.FC = () => {
  const nodes = [
    { id: 'node-1', label: 'THOUGHT_ROOT', text: 'Analyze slow SQL queries on tenant db', score: 0.96 },
    { id: 'node-2', label: 'ALTERNATIVE_A', text: 'Add B-Tree Index on tenant_id column', score: 0.94 },
    { id: 'node-3', label: 'ALTERNATIVE_B', text: 'Partition table by creation timestamp', score: 0.88 },
    { id: 'node-4', label: 'CRITIQUE_EVAL', text: 'B-Tree Index provides immediate 90% latency drop with low overhead', score: 0.95 },
  ];

  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <GitGraph size={22} style={{ color: '#8b5cf6' }} />
        Thought Graph Explorer (Tree & Graph of Thoughts)
      </h3>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '2px dashed #8b5cf6', paddingLeft: '20px' }}>
        {nodes.map((node) => (
          <div key={node.id} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)', position: 'relative' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <span className="badge badge-info" style={{ background: 'rgba(139, 92, 246, 0.2)', color: '#a78bfa' }}>{node.label}</span>
              <span style={{ fontSize: '0.8rem', color: '#10b981', fontWeight: 700 }}>Confidence: {node.score}</span>
            </div>
            <p style={{ margin: '8px 0 0 0', fontSize: '0.9rem', color: 'var(--text-main)' }}>{node.text}</p>
          </div>
        ))}
      </div>
    </div>
  );
};
