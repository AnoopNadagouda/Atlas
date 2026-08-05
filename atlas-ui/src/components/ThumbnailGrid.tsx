import React from 'react';
import { FileText, Image, Video, Music, FileSpreadsheet, Presentation } from 'lucide-react';

interface ThumbnailGridProps {
  documents: any[];
  selectedDocumentId: string | null;
  onSelectDocument: (doc: any) => void;
}

export const ThumbnailGrid: React.FC<ThumbnailGridProps> = ({
  documents,
  selectedDocumentId,
  onSelectDocument,
}) => {
  const getFormatIcon = (format: string) => {
    switch (format?.toUpperCase()) {
      case 'PDF':
        return <FileText size={28} color="#ef4444" />;
      case 'DOCX':
      case 'WORD':
        return <FileText size={28} color="#3b82f6" />;
      case 'PPTX':
      case 'POWERPOINT':
        return <Presentation size={28} color="#eab308" />;
      case 'XLSX':
      case 'CSV':
        return <FileSpreadsheet size={28} color="#22c55e" />;
      case 'PNG':
      case 'JPEG':
      case 'IMAGE':
        return <Image size={28} color="#ec4899" />;
      case 'MP4':
      case 'VIDEO':
        return <Video size={28} color="#a855f7" />;
      case 'MP3':
      case 'AUDIO':
        return <Music size={28} color="#06b6d4" />;
      default:
        return <FileText size={28} color="#9ca3af" />;
    }
  };

  return (
    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(220px, 1fr))', gap: '16px' }}>
      {documents.map((doc) => {
        const isSelected = doc.documentId === selectedDocumentId;
        return (
          <div
            key={doc.documentId}
            onClick={() => onSelectDocument(doc)}
            className="glass-panel"
            style={{
              padding: '16px',
              cursor: 'pointer',
              borderColor: isSelected ? '#6366f1' : 'var(--border-color)',
              background: isSelected ? 'rgba(99, 102, 241, 0.12)' : 'var(--bg-glass)',
              transition: 'all 0.2s ease',
            }}
          >
            <div style={{ height: '90px', background: 'rgba(0,0,0,0.25)', borderRadius: '6px', display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '12px' }}>
              {getFormatIcon(doc.fileType)}
            </div>
            <h4 style={{ fontSize: '0.9rem', fontWeight: 600, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
              {doc.title}
            </h4>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '8px', fontSize: '0.75rem', color: 'var(--text-muted)' }}>
              <span className="badge badge-info">{doc.fileType}</span>
              <span>{(doc.sizeBytes / 1024 / 1024).toFixed(1)} MB</span>
            </div>
          </div>
        );
      })}
    </div>
  );
};
