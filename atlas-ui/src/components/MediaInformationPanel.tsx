import React from 'react';
import { Film, Music, Cpu, Clock, Layers } from 'lucide-react';

interface MediaInformationPanelProps {
  mediaAsset?: any;
  metadataRegistry?: any;
}

export const MediaInformationPanel: React.FC<MediaInformationPanelProps> = ({ mediaAsset, metadataRegistry }) => {
  const asset = mediaAsset || {};
  const reg = metadataRegistry || {};

  const isVideo = asset.mediaType === 'VIDEO' || reg.codec?.includes('H.264');

  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '12px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        {isVideo ? <Film size={18} color="#a855f7" /> : <Music size={18} color="#06b6d4" />}
        Audio & Video Technical Specifications
      </h3>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', fontSize: '0.85rem' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Clock size={14} color="#eab308" /> Duration: <strong>{asset.durationSeconds || reg.durationSeconds || 184.5} sec</strong>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Cpu size={14} color="#6366f1" /> Codec: <strong>{asset.codec || reg.codec || 'H.264 / AAC'}</strong>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Layers size={14} color="#22c55e" /> Resolution: <strong>{asset.width || reg.width || 3840} x {asset.height || reg.height || 2160}</strong>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Film size={14} color="#ec4899" /> Bitrate: <strong>{((asset.bitrate || reg.bitrate || 12500000) / 1000000).toFixed(1)} Mbps</strong>
        </div>
      </div>
    </div>
  );
};
