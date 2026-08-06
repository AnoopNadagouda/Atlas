import React, { useState } from 'react';
import { Target, Plus } from 'lucide-react';

export const GoalManagerView: React.FC = () => {
  const [goals] = useState([
    { id: 'goal-01', title: 'Automate Database Query Optimization', priority: 1, status: 'ACTIVE', progress: 85, created: '10 mins ago' },
    { id: 'goal-02', title: 'Self-Heal Spring Boot Timeout Errors', priority: 2, status: 'COMPLETED', progress: 100, created: '1 hour ago' },
    { id: 'goal-03', title: 'Synthesize Multi-Tenant Security Policy', priority: 1, status: 'ACTIVE', progress: 60, created: '2 hours ago' },
  ]);

  return (
    <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Target size={22} style={{ color: '#ec4899' }} />
          Autonomous Goal Manager
        </h3>
        <button className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '6px', padding: '8px 16px', borderRadius: '8px', fontSize: '0.85rem' }}>
          <Plus size={16} /> Formulate New Goal
        </button>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
        {goals.map((g) => (
          <div key={g.id} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <div>
                <span style={{ fontSize: '0.95rem', fontWeight: 700 }}>{g.title}</span>
                <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '4px', display: 'flex', gap: '16px' }}>
                  <span>ID: {g.id}</span>
                  <span>Priority: P{g.priority}</span>
                  <span>Created: {g.created}</span>
                </div>
              </div>
              <span className={`badge ${g.status === 'COMPLETED' ? 'badge-success' : 'badge-info'}`}>
                {g.status}
              </span>
            </div>
            <div style={{ marginTop: '12px', background: 'rgba(255,255,255,0.1)', height: '6px', borderRadius: '3px', overflow: 'hidden' }}>
              <div style={{ width: `${g.progress}%`, background: g.status === 'COMPLETED' ? '#10b981' : '#ec4899', height: '100%' }} />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
