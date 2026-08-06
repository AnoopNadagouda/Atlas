import React from 'react';
import { Clock } from 'lucide-react';

export const ReasoningTimeline: React.FC = () => {
  const steps = [
    { time: '11:35:01', label: 'Goal Formulated', desc: 'Formulated goal "Automate Database Query Optimization"' },
    { time: '11:35:02', label: 'Task Decomposed', desc: 'Decomposed goal into 4 sub-task execution nodes' },
    { time: '11:35:03', label: 'Alternatives Generated', desc: 'Evaluated 3 candidate decision paths' },
    { time: '11:35:04', label: 'Decision Executed', desc: 'Selected B-Tree Indexing strategy (Confidence: 0.94)' },
    { time: '11:35:05', label: 'Self-Reflection', desc: 'Extracted performance optimization lesson' },
  ];

  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Clock size={22} style={{ color: '#f59e0b' }} />
        Reasoning Session Execution Timeline
      </h3>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '12px', borderLeft: '2px solid rgba(255,255,255,0.1)', paddingLeft: '16px' }}>
        {steps.map((s, i) => (
          <div key={i} style={{ position: 'relative' }}>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)', fontWeight: 600 }}>{s.time}</span>
            <div style={{ fontSize: '0.9rem', fontWeight: 700, marginTop: '2px' }}>{s.label}</div>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)' }}>{s.desc}</div>
          </div>
        ))}
      </div>
    </div>
  );
};
