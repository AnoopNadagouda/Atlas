import React, { useState } from 'react';
import { PlusCircle, Shield, Key, Link as LinkIcon, Check } from 'lucide-react';

interface ConnectorWizardProps {
  onAddConnector: (config: any) => void;
  onCancel: () => void;
}

export const ConnectorWizard: React.FC<ConnectorWizardProps> = ({ onAddConnector, onCancel }) => {
  const [name, setName] = useState('');
  const [connectorId, setConnectorId] = useState('github');
  const [endpointUrl, setEndpointUrl] = useState('');
  const [authType, setAuthType] = useState('BEARER_TOKEN');
  const [authSecret, setAuthSecret] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onAddConnector({
      connectorId,
      name: name || connectorId.toUpperCase() + ' Adapter',
      endpointUrl: endpointUrl || 'https://api.' + connectorId + '.com',
      authType,
      authSecret,
      cronSchedule: '0 0/15 * * * ?',
    });
  };

  return (
    <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <h3 style={{ fontSize: '1.1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        <PlusCircle size={20} color="#6366f1" /> Register New Enterprise Connector
      </h3>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div>
          <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '4px', display: 'block' }}>Connector Adapter</label>
          <select className="input" value={connectorId} onChange={(e) => setConnectorId(e.target.value)} style={{ width: '100%' }}>
            <option value="github">GitHub Enterprise</option>
            <option value="gitlab">GitLab Enterprise</option>
            <option value="confluence">Atlassian Confluence</option>
            <option value="jira">Atlassian Jira</option>
            <option value="notion">Notion Workspace</option>
            <option value="gdrive">Google Drive</option>
            <option value="onedrive">Microsoft OneDrive</option>
            <option value="sharepoint">Microsoft SharePoint</option>
            <option value="dropbox">Dropbox Business</option>
            <option value="slack">Slack Enterprise Grid</option>
            <option value="teams">Microsoft Teams</option>
            <option value="local_fs">Local File System</option>
            <option value="aws_s3">Amazon S3</option>
            <option value="azure_blob">Azure Blob Storage</option>
            <option value="generic_rest">Generic REST API</option>
            <option value="rss_atom">RSS / Atom Feeds</option>
          </select>
        </div>

        <div>
          <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '4px', display: 'block' }}>Instance Name</label>
          <input
            type="text"
            className="input"
            placeholder="e.g. Corporate GitHub Monorepo"
            value={name}
            onChange={(e) => setName(e.target.value)}
            style={{ width: '100%' }}
          />
        </div>

        <div>
          <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '4px', display: 'block' }}>Endpoint URL</label>
          <input
            type="text"
            className="input"
            placeholder="https://api.github.com"
            value={endpointUrl}
            onChange={(e) => setEndpointUrl(e.target.value)}
            style={{ width: '100%' }}
          />
        </div>

        <div>
          <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '4px', display: 'block' }}>Authentication Type</label>
          <select className="input" value={authType} onChange={(e) => setAuthType(e.target.value)} style={{ width: '100%' }}>
            <option value="OAUTH2">OAuth 2.0</option>
            <option value="BEARER_TOKEN">Bearer Token</option>
            <option value="API_KEY">API Key</option>
            <option value="BASIC">Basic Authentication</option>
            <option value="PAT">Personal Access Token (PAT)</option>
          </select>
        </div>

        <div>
          <label style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '4px', display: 'block' }}>Secret / Token (AES-256 Encrypted)</label>
          <input
            type="password"
            className="input"
            placeholder="ghp_xxxxxxxxxxxxxxxxxxxx"
            value={authSecret}
            onChange={(e) => setAuthSecret(e.target.value)}
            style={{ width: '100%' }}
          />
        </div>

        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px', marginTop: '12px' }}>
          <button type="button" onClick={onCancel} className="btn btn-secondary">
            Cancel
          </button>
          <button type="submit" className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Check size={16} /> Register Connector
          </button>
        </div>
      </form>
    </div>
  );
};
