import React from 'react';
import { Clock, CheckCircle2, Zap, Database, Brain } from 'lucide-react';


export const MissionTimeline: React.FC = () => {
  const events = [
    { id: 'evt-1', time: '14:48:02', title: 'Mission Formulated & Validated', details: 'Validated policy compliance with zero security violations.', icon: CheckCircle2, color: '#22c55e' },
    { id: 'evt-2', time: '14:48:05', title: 'Task Dispatched to Agent Service (8086)', details: 'Assigned Master Lead Orchestrator to mission context.', icon: Zap, color: '#3b82f6' },
    { id: 'evt-3', time: '14:48:12', title: 'Reasoning Engine Triggered (8089)', details: 'Constructed deductive proof tree with depth 4.', icon: Brain, color: '#a855f7' },
    { id: 'evt-4', time: '14:48:25', title: 'Memory Consolidation (8088)', details: 'Persisted 12 episodic concepts to long-term vector store.', icon: Database, color: '#eab308' },
  ];

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Clock className="text-accent" size={24} /> Autonomous Mission Execution Timeline
      </h3>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px', position: 'relative' }}>
        {events.map((evt, idx) => {
          const Icon = evt.icon;
          return (
            <div key={evt.id} style={{ display: 'flex', gap: '16px', alignItems: 'flex-start' }}>
              <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <div style={{ width: '36px', height: '36px', borderRadius: '50%', backgroundColor: 'rgba(255, 255, 255, 0.05)', display: 'flex', alignItems: 'center', justifyContent: 'center', border: `1px solid ${evt.color}` }}>
                  <Icon size={18} color={evt.color} />
                </div>
                {idx < events.length - 1 && (
                  <div style={{ width: '2px', height: '30px', background: 'rgba(255, 255, 255, 0.1)', margin: '4px 0' }} />
                )}
              </div>

              <div style={{ flex: 1, padding: '12px 16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontWeight: 600, fontSize: '0.95rem' }}>{evt.title}</span>
                  <span style={{ fontSize: '0.75rem', color: 'var(--text-dim)' }}>{evt.time}</span>
                </div>
                <p style={{ color: 'var(--text-dim)', fontSize: '0.85rem', margin: '4px 0 0' }}>{evt.details}</p>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
