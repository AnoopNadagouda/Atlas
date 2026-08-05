import React from 'react';
import { Scan, CheckCircle2, Globe } from 'lucide-react';

interface OcrConfidenceViewerProps {
  confidenceScore: number;
  language?: string;
  extractedText?: string;
}

export const OcrConfidenceViewer: React.FC<OcrConfidenceViewerProps> = ({
  confidenceScore,
  language = 'en',
  extractedText = '',
}) => {
  const percent = Math.round((confidenceScore || 0.95) * 100);

  return (
    <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '12px' }}>
      <h3 style={{ fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
        <Scan size={18} color="#22c55e" /> OCR Confidence & Language Detection
      </h3>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <CheckCircle2 size={16} color="#22c55e" />
          <span style={{ fontSize: '0.9rem', fontWeight: 600 }}>OCR Score: {percent}%</span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px', fontSize: '0.8rem', color: 'var(--text-dim)' }}>
          <Globe size={14} /> Language: <strong style={{ textTransform: 'uppercase' }}>{language}</strong>
        </div>
      </div>

      {/* Progress Bar */}
      <div style={{ width: '100%', height: '8px', background: 'rgba(255,255,255,0.1)', borderRadius: '4px', overflow: 'hidden' }}>
        <div style={{ width: `${percent}%`, height: '100%', background: 'linear-gradient(90deg, #22c55e, #06b6d4)', borderRadius: '4px' }} />
      </div>

      {extractedText && (
        <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)', background: 'rgba(0,0,0,0.2)', padding: '10px', borderRadius: '6px', maxHeight: '100px', overflowY: 'auto' }}>
          <strong>Normalized OCR Text:</strong>
          <p style={{ marginTop: '4px' }}>{extractedText}</p>
        </div>
      )}
    </div>
  );
};
