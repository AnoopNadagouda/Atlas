import React from 'react';
import { FileText, Eye, Layers, Table, Grid } from 'lucide-react';

interface UniversalPreviewPanelProps {
  document: any;
  preview: any;
}

export const UniversalPreviewPanel: React.FC<UniversalPreviewPanelProps> = ({ document, preview }) => {
  if (!document) {
    return (
      <div className="glass-panel" style={{ padding: '32px', textAlign: 'center', color: 'var(--text-muted)' }}>
        Select a document from the explorer to launch universal preview.
      </div>
    );
  }

  const format = (document.fileType || 'PDF').toUpperCase();

  return (
    <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <FileText size={22} color="#6366f1" />
          <div>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 600 }}>{document.title}</h3>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Format: {format} | ID: {document.documentId}</span>
          </div>
        </div>
        <span className="badge badge-info">{document.version || 'v1.0'}</span>
      </div>

      {/* Snippet Highlight View */}
      {preview?.highlightedSnippet && (
        <div style={{ background: 'rgba(0,0,0,0.3)', padding: '16px', borderRadius: '8px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ fontSize: '0.8rem', fontWeight: 600, color: 'var(--text-dim)', marginBottom: '6px', display: 'flex', alignItems: 'center', gap: '6px' }}>
            <Eye size={14} /> Highlighted Content Snippet
          </div>
          <p
            style={{ fontSize: '0.9rem', lineHeight: '1.5' }}
            dangerouslySetInnerHTML={{ __html: preview.highlightedSnippet }}
          />
        </div>
      )}

      {/* Page Preview / Outline TOC */}
      {preview?.outlineToc && preview.outlineToc.length > 0 && (
        <div>
          <h4 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '10px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Layers size={16} color="#06b6d4" /> Document Outline & TOC
          </h4>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
            {preview.outlineToc.map((sec: any, idx: number) => (
              <div key={idx} style={{ padding: '8px 12px', background: 'rgba(255,255,255,0.03)', borderRadius: '6px', fontSize: '0.85rem', display: 'flex', justifyContent: 'space-between' }}>
                <span>{sec.title}</span>
                <span style={{ color: 'var(--text-dim)' }}>Page {sec.pageNumber || 1}</span>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Slide Preview for Presentations */}
      {(format === 'PPTX' || format === 'POWERPOINT') && preview?.slidePreviews && (
        <div>
          <h4 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '10px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Grid size={16} color="#eab308" /> Presentation Slides
          </h4>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '12px' }}>
            {preview.slidePreviews.map((slide: any, idx: number) => (
              <div key={idx} style={{ padding: '12px', background: 'rgba(255,255,255,0.04)', border: '1px solid var(--border-color)', borderRadius: '8px' }}>
                <span style={{ fontSize: '0.75rem', color: '#eab308', fontWeight: 600 }}>Slide {slide.slideNumber}</span>
                <p style={{ fontSize: '0.85rem', fontWeight: 500, marginTop: '4px' }}>{slide.title}</p>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Spreadsheet Grid Preview */}
      {(format === 'XLSX' || format === 'CSV') && preview?.spreadsheetPreview?.rows && (
        <div>
          <h4 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '10px', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Table size={16} color="#22c55e" /> Spreadsheet Preview ({preview.spreadsheetPreview.sheetName || 'Sheet1'})
          </h4>
          <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
              <thead>
                <tr style={{ background: 'rgba(255,255,255,0.05)', textAlign: 'left' }}>
                  {preview.spreadsheetPreview.columns?.map((col: string, idx: number) => (
                    <th key={idx} style={{ padding: '8px 12px', borderBottom: '1px solid var(--border-color)' }}>{col}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {preview.spreadsheetPreview.rows.map((row: any[], rIdx: number) => (
                  <tr key={rIdx} style={{ borderBottom: '1px solid rgba(255,255,255,0.03)' }}>
                    {row.map((cell: any, cIdx: number) => (
                      <td key={cIdx} style={{ padding: '8px 12px' }}>{String(cell)}</td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};
