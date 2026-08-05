import React from 'react';
import { Camera, MapPin, Hash, Palette, Info } from 'lucide-react';

interface MetadataInspectorProps {
  metadataRegistry: any;
  metadata: any;
}

export const MetadataInspector: React.FC<MetadataInspectorProps> = ({ metadataRegistry, metadata }) => {
  const reg = metadataRegistry || {};
  const meta = metadata || {};

  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px', borderBottom: '1px solid var(--border-color)', paddingBottom: '8px' }}>
        <Info size={18} color="#06b6d4" /> Metadata Inspector (EXIF / IPTC / XMP)
      </h3>

      {/* EXIF / Camera Properties */}
      {reg.cameraMake && (
        <div>
          <h4 style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Camera size={14} /> Camera & EXIF Info
          </h4>
          <div style={{ fontSize: '0.85rem', display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '6px' }}>
            <div>Make: <strong>{reg.cameraMake}</strong></div>
            <div>Model: <strong>{reg.cameraModel}</strong></div>
            <div>Lens: <strong>{reg.lensInfo}</strong></div>
            <div>Resolution: <strong>{reg.width} x {reg.height}</strong></div>
          </div>
        </div>
      )}

      {/* GPS Coordinates */}
      {reg.gpsLatitude && (
        <div>
          <h4 style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <MapPin size={14} color="#ef4444" /> GPS Geo-Location
          </h4>
          <div style={{ fontSize: '0.85rem' }}>
            Location: <strong>{reg.gpsLatitude}, {reg.gpsLongitude}</strong> ({reg.gpsAltitude}m elev.)
          </div>
        </div>
      )}

      {/* File Hashes */}
      {reg.md5Hash && (
        <div>
          <h4 style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Hash size={14} color="#a855f7" /> Checksums & Hashes
          </h4>
          <div style={{ fontSize: '0.75rem', fontFamily: 'monospace', wordBreak: 'break-all', display: 'flex', flexDirection: 'column', gap: '4px' }}>
            <div>MD5: {reg.md5Hash}</div>
            <div>SHA256: {reg.sha256Hash}</div>
          </div>
        </div>
      )}

      {/* Dominant Colors */}
      {reg.dominantColors && reg.dominantColors.length > 0 && (
        <div>
          <h4 style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Palette size={14} color="#ec4899" /> Dominant Colors
          </h4>
          <div style={{ display: 'flex', gap: '8px' }}>
            {reg.dominantColors.map((hex: string, idx: number) => (
              <div key={idx} style={{ display: 'flex', alignItems: 'center', gap: '4px', fontSize: '0.75rem' }}>
                <span style={{ width: '14px', height: '14px', borderRadius: '50%', background: hex, display: 'inline-block', border: '1px solid #fff' }} />
                <span>{hex}</span>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};
