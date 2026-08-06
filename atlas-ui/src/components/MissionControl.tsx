import React, { useState } from 'react';
import { Play, Pause, RotateCcw, XCircle, Plus, Clock } from 'lucide-react';


export const MissionControl: React.FC = () => {
  const [missions, setMissions] = useState([
    { id: 'msn-101', title: 'Global Enterprise Market Analysis & Indexing', status: 'IN_PROGRESS', priority: 5, progress: 68, createdAt: '10 mins ago' },
    { id: 'msn-102', title: 'Autonomous Knowledge Graph Reasoning Synthesis', status: 'PAUSED', priority: 4, progress: 42, createdAt: '25 mins ago' },
    { id: 'msn-103', title: 'Cross-Node Multi-Tenant Security & Audit Verification', status: 'COMPLETED', priority: 3, progress: 100, createdAt: '1 hour ago' },
  ]);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState(3);

  const handleCreateMission = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title) return;
    const newMission = {
      id: `msn-${Math.floor(100 + Math.random() * 900)}`,
      title,
      status: 'CREATED',
      priority,
      progress: 0,
      createdAt: 'Just now',
    };
    setMissions([newMission, ...missions]);
    setTitle('');
    setDescription('');
  };

  const updateStatus = (id: string, newStatus: string) => {
    setMissions(missions.map(m => m.id === id ? { ...m, status: newStatus, progress: newStatus === 'COMPLETED' ? 100 : m.progress } : m));
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <Plus className="text-accent" size={24} /> Formulate Autonomous Mission
        </h3>
        <form onSubmit={handleCreateMission} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '16px' }}>
            <div>
              <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Mission Title</label>
              <input
                className="input"
                type="text"
                placeholder="e.g., Enterprise Financial Risk Modeling & Neural Search Sync"
                value={title}
                onChange={e => setTitle(e.target.value)}
                style={{ width: '100%' }}
              />
            </div>
            <div>
              <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Priority Level (1-10)</label>
              <input
                className="input"
                type="number"
                min="1"
                max="10"
                value={priority}
                onChange={e => setPriority(parseInt(e.target.value) || 1)}
                style={{ width: '100%' }}
              />
            </div>
          </div>
          <div>
            <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Objective Description & Constraints</label>
            <textarea
              className="input"
              rows={3}
              placeholder="Describe objective goals, required sub-agents, memory retention policies..."
              value={description}
              onChange={e => setDescription(e.target.value)}
              style={{ width: '100%', resize: 'vertical' }}
            />
          </div>
          <button type="submit" className="btn btn-primary" style={{ alignSelf: 'flex-start' }}>
            <Play size={18} /> Deploy Mission to AIOS
          </button>
        </form>
      </div>

      {/* Mission List */}
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px' }}>Active Enterprise Missions</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          {missions.map(mission => (
            <div key={mission.id} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <span style={{ fontWeight: 700, fontSize: '1.05rem' }}>{mission.title}</span>
                    <span className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.2)', color: '#60a5fa', fontSize: '0.75rem' }}>ID: {mission.id}</span>
                    <span className="badge" style={{ backgroundColor: 'rgba(168, 85, 247, 0.2)', color: '#c084fc', fontSize: '0.75rem' }}>Priority: {mission.priority}</span>
                  </div>
                  <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '4px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <Clock size={14} /> Created {mission.createdAt}
                  </div>
                </div>

                <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                  {mission.status === 'CREATED' && (
                    <button className="btn btn-primary" style={{ padding: '6px 12px', fontSize: '0.85rem' }} onClick={() => updateStatus(mission.id, 'IN_PROGRESS')}>
                      <Play size={14} /> Execute
                    </button>
                  )}
                  {mission.status === 'IN_PROGRESS' && (
                    <button className="btn btn-secondary" style={{ padding: '6px 12px', fontSize: '0.85rem' }} onClick={() => updateStatus(mission.id, 'PAUSED')}>
                      <Pause size={14} /> Pause
                    </button>
                  )}
                  {mission.status === 'PAUSED' && (
                    <button className="btn btn-primary" style={{ padding: '6px 12px', fontSize: '0.85rem' }} onClick={() => updateStatus(mission.id, 'IN_PROGRESS')}>
                      <RotateCcw size={14} /> Resume
                    </button>
                  )}
                  {mission.status !== 'COMPLETED' && (
                    <button className="btn btn-secondary" style={{ padding: '6px 12px', fontSize: '0.85rem', color: '#f87171' }} onClick={() => updateStatus(mission.id, 'CANCELLED')}>
                      <XCircle size={14} /> Cancel
                    </button>
                  )}
                </div>
              </div>

              {/* Progress bar */}
              <div style={{ marginTop: '16px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.8rem', marginBottom: '6px' }}>
                  <span>Execution Progress</span>
                  <span>{mission.progress}%</span>
                </div>
                <div style={{ height: '8px', background: 'rgba(255, 255, 255, 0.1)', borderRadius: '4px', overflow: 'hidden' }}>
                  <div style={{ width: `${mission.progress}%`, height: '100%', background: mission.progress === 100 ? '#22c55e' : 'linear-gradient(90deg, #3b82f6, #a855f7)', transition: 'width 0.3s' }} />
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
