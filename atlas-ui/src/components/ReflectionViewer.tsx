import React from 'react';
import { Eye, Lightbulb } from 'lucide-react';

export const ReflectionViewer: React.FC = () => {
  const reflections = [
    {
      id: 'ref-01',
      session: 'session-kafka-opt',
      expected: 'Zero message loss during broker failover',
      actual: '100% messages persisted with 12ms latency',
      correctness: 0.96,
      efficiency: 0.94,
      lessons: 'Pre-allocating topic partitions prevents partition rebalancing lag during peak load.'
    }
  ];

  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Eye size={22} style={{ color: '#10b981' }} />
        Post-Task Self-Reflection Viewer
      </h3>

      {reflections.map((r) => (
        <div key={r.id} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '12px' }}>
            <span style={{ fontWeight: 700, fontSize: '0.9rem' }}>Session: {r.session}</span>
            <div style={{ display: 'flex', gap: '12px', fontSize: '0.8rem' }}>
              <span style={{ color: '#10b981' }}>Correctness: {r.correctness}</span>
              <span style={{ color: '#6366f1' }}>Efficiency: {r.efficiency}</span>
            </div>
          </div>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px', fontSize: '0.85rem' }}>
            <div style={{ padding: '8px', borderRadius: '6px', background: 'rgba(255,255,255,0.02)' }}>
              <strong>Expected:</strong> {r.expected}
            </div>
            <div style={{ padding: '8px', borderRadius: '6px', background: 'rgba(255,255,255,0.02)' }}>
              <strong>Actual:</strong> {r.actual}
            </div>
          </div>
          <div style={{ marginTop: '12px', padding: '10px', borderRadius: '6px', background: 'rgba(245, 158, 11, 0.1)', color: '#fbbf24', fontSize: '0.85rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Lightbulb size={16} /> <strong>Lesson Learned:</strong> {r.lessons}
          </div>
        </div>
      ))}
    </div>
  );
};
