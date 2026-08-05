import React, { useState } from 'react';
import { Settings, Shield, RefreshCw, Key, Lock } from 'lucide-react';

export const ConnectorSettings: React.FC = () => {
  const [encryptionStatus, setEncryptionStatus] = useState('AES-256 GCM Active');
  const [rotationInterval, setRotationInterval] = useState('30 Days');

  return (
    <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px' }}>
      <h3 style={{ fontSize: '1.1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px', borderBottom: '1px solid var(--border-color)', paddingBottom: '10px' }}>
        <Settings size={20} color="#06b6d4" /> Global Connector & Security Settings
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
        <div style={{ padding: '16px', background: 'rgba(255,255,255,0.03)', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
          <h4 style={{ fontSize: '0.9rem', fontWeight: 600, marginBottom: '8px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Lock size={16} color="#6366f1" /> Secret Encryption Engine
          </h4>
          <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '12px' }}>
            All connector credentials, OAuth tokens, and API keys are encrypted at rest using AES-256 GCM.
          </p>
          <div className="badge badge-success">{encryptionStatus}</div>
        </div>

        <div style={{ padding: '16px', background: 'rgba(255,255,255,0.03)', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
          <h4 style={{ fontSize: '0.9rem', fontWeight: 600, marginBottom: '8px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <RefreshCw size={16} color="#22c55e" /> Automated Secret Rotation
          </h4>
          <p style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '12px' }}>
            Configured rotation schedule for OAuth refresh tokens and PAT keys.
          </p>
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem' }}>Interval:</span>
            <select className="input" value={rotationInterval} onChange={(e) => setRotationInterval(e.target.value)}>
              <option value="7 Days">7 Days</option>
              <option value="30 Days">30 Days</option>
              <option value="90 Days">90 Days</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  );
};
