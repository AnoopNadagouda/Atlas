import React, { useState } from 'react';
import { Settings, Play, Calendar, CheckCircle, Clock } from 'lucide-react';

export const AutomationCenter: React.FC = () => {
  const [jobs, setJobs] = useState([
    { id: 'job-1', name: 'Nightly Vector Index Reindexing & Compression', cron: '0 0 * * *', lastRun: '6 hours ago', status: 'SUCCESS', nextRun: '18 hours' },
    { id: 'job-2', name: 'Memory Consolidation & Knowledge Graph Pruning', cron: '*/30 * * * *', lastRun: '12 mins ago', status: 'SUCCESS', nextRun: '18 mins' },
    { id: 'job-3', name: 'Multi-Tenant Security Audit & Log Scrubbing', cron: '0 */6 * * *', lastRun: '2 hours ago', status: 'SUCCESS', nextRun: '4 hours' },
  ]);

  const [jobName, setJobName] = useState('');
  const [cron, setCron] = useState('0 0 * * *');

  const handleAddJob = (e: React.FormEvent) => {
    e.preventDefault();
    if (!jobName) return;
    setJobs([...jobs, {
      id: `job-${jobs.length + 1}`,
      name: jobName,
      cron,
      lastRun: 'Never',
      status: 'SCHEDULED',
      nextRun: 'Pending cron cycle'
    }]);
    setJobName('');
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <Settings className="text-accent" size={24} /> Schedule Background Automation Job
        </h3>
        <form onSubmit={handleAddJob} style={{ display: 'grid', gridTemplateColumns: '2fr 1fr 1fr', gap: '16px', alignItems: 'end' }}>
          <div>
            <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Job Name</label>
            <input
              className="input"
              type="text"
              placeholder="e.g., Hourly Autonomous Memory Decay & Summarization"
              value={jobName}
              onChange={e => setJobName(e.target.value)}
              style={{ width: '100%' }}
            />
          </div>
          <div>
            <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'block' }}>Cron Expression</label>
            <input
              className="input"
              type="text"
              placeholder="0 * * * *"
              value={cron}
              onChange={e => setCron(e.target.value)}
              style={{ width: '100%' }}
            />
          </div>
          <button type="submit" className="btn btn-primary">
            <Calendar size={16} /> Schedule Job
          </button>
        </form>
      </div>

      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.25rem', fontWeight: 700, marginBottom: '16px' }}>Enterprise Automation Jobs</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
          {jobs.map(job => (
            <div key={job.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '16px', background: 'rgba(255, 255, 255, 0.03)', borderRadius: '8px', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
              <div>
                <div style={{ fontWeight: 600, fontSize: '1rem' }}>{job.name}</div>
                <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', marginTop: '4px' }}>
                  Cron: <code>{job.cron}</code> • Last Run: {job.lastRun} • Next: {job.nextRun}
                </div>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#4ade80', fontSize: '0.75rem' }}>
                  {job.status}
                </span>
                <button className="btn btn-secondary" style={{ padding: '6px 12px', fontSize: '0.8rem' }}>
                  <Play size={14} /> Run Now
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
