import React, { useState, useEffect } from 'react';
import { Network, Plus, Play, Pause, RefreshCw, Shield, Layers } from 'lucide-react';
import { ConnectorHealthPanel } from '../components/ConnectorHealthPanel';
import { ConnectorStatistics } from '../components/ConnectorStatistics';
import { SyncHistory } from '../components/SyncHistory';
import { ConnectorWizard } from '../components/ConnectorWizard';
import { ConnectorSettings } from '../components/ConnectorSettings';

export const ConnectorDashboard: React.FC = () => {
  const [connectors, setConnectors] = useState<any[]>([]);
  const [syncJobs, setSyncJobs] = useState<any[]>([]);
  const [showWizard, setShowWizard] = useState(false);
  const [activeTab, setActiveTab] = useState<'dashboard' | 'history' | 'settings'>('dashboard');

  useEffect(() => {
    // Seed connectors list
    const seed = [
      { connectorId: 'github', name: 'GitHub Enterprise', status: 'UP', latencyMs: 12, category: 'CODE_REPOSITORY' },
      { connectorId: 'gitlab', name: 'GitLab Enterprise', status: 'UP', latencyMs: 15, category: 'CODE_REPOSITORY' },
      { connectorId: 'confluence', name: 'Atlassian Confluence', status: 'UP', latencyMs: 18, category: 'WIKI_DOCUMENTATION' },
      { connectorId: 'jira', name: 'Atlassian Jira', status: 'UP', latencyMs: 14, category: 'WIKI_DOCUMENTATION' },
      { connectorId: 'notion', name: 'Notion Workspace', status: 'UP', latencyMs: 22, category: 'WIKI_DOCUMENTATION' },
      { connectorId: 'gdrive', name: 'Google Drive', status: 'UP', latencyMs: 16, category: 'CLOUD_STORAGE' },
      { connectorId: 'slack', name: 'Slack Enterprise Grid', status: 'UP', latencyMs: 10, category: 'CHAT_COLLABORATION' },
      { connectorId: 'aws_s3', name: 'Amazon S3', status: 'UP', latencyMs: 8, category: 'CLOUD_STORAGE' },
    ];

    const seedJobs = [
      { jobId: 'job-gh-01', connectorId: 'github', syncMode: 'FULL', status: 'COMPLETED', docsProcessed: 1420 },
      { jobId: 'job-conf-02', connectorId: 'confluence', syncMode: 'INCREMENTAL', status: 'COMPLETED', docsProcessed: 310 },
      { jobId: 'job-jira-03', connectorId: 'jira', syncMode: 'INCREMENTAL', status: 'COMPLETED', docsProcessed: 540 },
    ];

    setConnectors(seed);
    setSyncJobs(seedJobs);
  }, []);

  const handleAddConnector = (newConfig: any) => {
    setConnectors([...connectors, { ...newConfig, status: 'UP', latencyMs: 15 }]);
    setShowWizard(false);
  };

  const handleTriggerSync = (connectorId: string) => {
    const newJob = {
      jobId: 'job-' + Math.random().toString(36).substring(2, 7),
      connectorId,
      syncMode: 'MANUAL_FULL',
      status: 'COMPLETED',
      docsProcessed: Math.floor(Math.random() * 500) + 50,
    };
    setSyncJobs([newJob, ...syncJobs]);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Top Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h2 style={{ fontSize: '1.75rem', fontWeight: 700, background: 'linear-gradient(90deg, #fff, #9ca3af)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
            Enterprise Federation & Connectors Hub
          </h2>
          <p style={{ color: 'var(--text-dim)', fontSize: '0.9rem', marginTop: '4px' }}>
            Unified connector framework integrating 16 enterprise knowledge sources with ACL filtering.
          </p>
        </div>

        <div style={{ display: 'flex', gap: '12px' }}>
          <button onClick={() => setShowWizard(true)} className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Plus size={16} /> Add Connector
          </button>
        </div>
      </div>

      {/* Tabs */}
      <div style={{ display: 'flex', gap: '12px', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px' }}>
        <button className={`btn ${activeTab === 'dashboard' ? 'btn-primary' : 'btn-secondary'}`} onClick={() => setActiveTab('dashboard')}>
          Dashboard
        </button>
        <button className={`btn ${activeTab === 'history' ? 'btn-primary' : 'btn-secondary'}`} onClick={() => setActiveTab('history')}>
          Sync History
        </button>
        <button className={`btn ${activeTab === 'settings' ? 'btn-primary' : 'btn-secondary'}`} onClick={() => setActiveTab('settings')}>
          Security & Settings
        </button>
      </div>

      {showWizard ? (
        <ConnectorWizard onAddConnector={handleAddConnector} onCancel={() => setShowWizard(false)} />
      ) : (
        <>
          {activeTab === 'dashboard' && (
            <>
              <ConnectorStatistics totalSyncedDocs={2270} activeConnectorsCount={connectors.length} avgLatencyMs={14.5} />
              <ConnectorHealthPanel connectors={connectors} />

              {/* Connectors Table */}
              <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
                <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
                  <Network size={18} color="#6366f1" /> Active Connector Adapters
                </h3>

                <div style={{ overflowX: 'auto' }}>
                  <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
                    <thead>
                      <tr style={{ background: 'rgba(255,255,255,0.05)', textAlign: 'left' }}>
                        <th style={{ padding: '10px 12px' }}>Connector ID</th>
                        <th style={{ padding: '10px 12px' }}>Name</th>
                        <th style={{ padding: '10px 12px' }}>Category</th>
                        <th style={{ padding: '10px 12px' }}>Health</th>
                        <th style={{ padding: '10px 12px' }}>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {connectors.map((c) => (
                        <tr key={c.connectorId} style={{ borderBottom: '1px solid rgba(255,255,255,0.03)' }}>
                          <td style={{ padding: '10px 12px', fontFamily: 'monospace' }}>{c.connectorId}</td>
                          <td style={{ padding: '10px 12px', fontWeight: 600 }}>{c.name}</td>
                          <td style={{ padding: '10px 12px' }}>
                            <span className="badge badge-info">{c.category || 'ENTERPRISE'}</span>
                          </td>
                          <td style={{ padding: '10px 12px' }}>
                            <span className="badge badge-success">UP ({c.latencyMs || 15} ms)</span>
                          </td>
                          <td style={{ padding: '10px 12px' }}>
                            <button onClick={() => handleTriggerSync(c.connectorId)} className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '0.75rem', display: 'flex', alignItems: 'center', gap: '4px' }}>
                              <RefreshCw size={12} /> Sync Now
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </>
          )}

          {activeTab === 'history' && <SyncHistory jobs={syncJobs} />}
          {activeTab === 'settings' && <ConnectorSettings />}
        </>
      )}
    </div>
  );
};
