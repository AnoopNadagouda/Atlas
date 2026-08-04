import React, { useState } from 'react';
import { Layers, Plus, Play, Pause, AlertCircle } from 'lucide-react';

export const CrawlerPage: React.FC = () => {
  const [seedUrl, setSeedUrl] = useState('');
  const [jobName, setJobName] = useState('');

  const activeJobs = [
    { id: 'job-001', name: 'Atlas Documentation Crawler', seeds: ['https://docs.atlas.search'], status: 'RUNNING', pages: 1420 },
    { id: 'job-002', name: 'GitHub Code Repo Ingestion', seeds: ['https://github.com/spring-projects'], status: 'COMPLETED', pages: 8500 },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Distributed Crawl Scheduler</h2>
        <p style={{ color: 'var(--text-muted)' }}>Dispatch seed URLs to Kafka ingestion pipeline (`crawl.urls.seed`).</p>
      </div>

      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.1rem', marginBottom: '16px', display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Plus size={18} color="var(--primary)" /> Register New Crawl Target
        </h3>
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 2fr auto', gap: '12px' }}>
          <input
            type="text"
            className="input-field"
            placeholder="Job Name (e.g. Spring Docs)"
            value={jobName}
            onChange={(e) => setJobName(e.target.value)}
          />
          <input
            type="text"
            className="input-field"
            placeholder="Seed URL (https://...)"
            value={seedUrl}
            onChange={(e) => setSeedUrl(e.target.value)}
          />
          <button className="btn btn-primary">
            <Play size={16} /> Dispatch Job
          </button>
        </div>
      </div>

      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.1rem', marginBottom: '16px' }}>Active Crawl Jobs</h3>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
          {activeJobs.map((job) => (
            <div key={job.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '12px 16px', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
              <div>
                <h4 style={{ fontSize: '1rem', fontWeight: 600 }}>{job.name}</h4>
                <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{job.seeds.join(', ')}</span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
                <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>{job.pages} pages</span>
                <span className={`badge ${job.status === 'RUNNING' ? 'badge-success' : 'badge-info'}`}>{job.status}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
