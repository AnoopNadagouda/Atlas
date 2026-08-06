import React from 'react';
import { ScreenshotGallery } from '../components/ScreenshotGallery';
import { Monitor } from 'lucide-react';

export const ScreenshotsPage: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
          <Monitor className="text-accent" size={28} /> Dashboard Screenshots Showcase
        </h2>
        <p style={{ color: 'var(--text-dim)', marginTop: '6px', marginBottom: 0, fontSize: '0.95rem' }}>
          Interactive UI preview for all 13 enterprise dashboards built into Atlas v6.0.0.
        </p>
      </div>

      <ScreenshotGallery />
    </div>
  );
};
