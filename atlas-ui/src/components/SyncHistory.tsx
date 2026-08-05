import React from 'react';
import { History, CheckCircle2, XCircle, Clock } from 'lucide-react';

interface SyncHistoryProps {
  jobs: any[];
}

export const SyncHistory: React.FC<SyncHistoryProps> = ({ jobs }) => {
  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        <History size={18} color="#a855f7" /> Connector Sync Job Execution Logs
      </h3>

      <div style={{ overflowX: 'auto' }}>
        <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
          <thead>
            <tr style={{ background: 'rgba(255,255,255,0.05)', textAlign: 'left' }}>
              <th style={{ padding: '10px 12px' }}>Job ID</th>
              <th style={{ padding: '10px 12px' }}>Connector</th>
              <th style={{ padding: '10px 12px' }}>Mode</th>
              <th style={{ padding: '10px 12px' }}>Status</th>
              <th style={{ padding: '10px 12px' }}>Docs Synced</th>
            </tr>
          </thead>
          <tbody>
            {jobs.map((job) => (
              <tr key={job.jobId} style={{ borderBottom: '1px solid rgba(255,255,255,0.03)' }}>
                <td style={{ padding: '10px 12px', fontFamily: 'monospace' }}>{job.jobId}</td>
                <td style={{ padding: '10px 12px', fontWeight: 600 }}>{job.connectorId}</td>
                <td style={{ padding: '10px 12px' }}>
                  <span className="badge badge-info">{job.syncMode || 'FULL'}</span>
                </td>
                <td style={{ padding: '10px 12px' }}>
                  <span className={`badge ${job.status === 'COMPLETED' ? 'badge-success' : 'badge-danger'}`}>
                    {job.status}
                  </span>
                </td>
                <td style={{ padding: '10px 12px' }}>{job.docsProcessed || 1}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
