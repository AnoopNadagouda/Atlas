import React, { useState, useEffect } from 'react';
import { Upload, Search, RefreshCw, Filter, ShieldCheck, Cpu } from 'lucide-react';
import { ThumbnailGrid } from '../components/ThumbnailGrid';
import { UniversalPreviewPanel } from '../components/UniversalPreviewPanel';
import { MetadataInspector } from '../components/MetadataInspector';
import { OcrConfidenceViewer } from '../components/OcrConfidenceViewer';
import { MediaInformationPanel } from '../components/MediaInformationPanel';

export const DocumentExplorer: React.FC = () => {
  const [documents, setDocuments] = useState<any[]>([]);
  const [selectedDoc, setSelectedDoc] = useState<any>(null);
  const [preview, setPreview] = useState<any>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [formatFilter, setFormatFilter] = useState('ALL');
  const [loading, setLoading] = useState(false);

  // Mock seed dataset for standalone UI demo / API sync
  useEffect(() => {
    const seedDocs = [
      {
        documentId: 'doc-pdf-001',
        title: 'Atlas Architecture Blueprint.pdf',
        fileType: 'PDF',
        mimeType: 'application/pdf',
        sizeBytes: 2450000,
        extractedText: 'Atlas Distributed AI Search Engine Architecture and Parallel Hybrid BM25 HNSW RRF Pipeline.',
        ocrConfidenceScore: 0.985,
        version: 'v1.0',
        metadataRegistry: {
          cameraMake: 'Canon',
          cameraModel: 'EOS R5',
          lensInfo: 'RF 50mm f/1.2L',
          gpsLatitude: '37.7749 N',
          gpsLongitude: '122.4194 W',
          gpsAltitude: 35.2,
          md5Hash: 'd41d8cd98f00b204e9800998ecf8427e',
          sha256Hash: 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855',
          dominantColors: ['#0F172A', '#6366F1', '#06B6D4'],
        },
      },
      {
        documentId: 'doc-word-001',
        title: 'Product Requirements Document.docx',
        fileType: 'DOCX',
        mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        sizeBytes: 1250000,
        extractedText: 'Product specification document for multi-modal search and OCR enrichment.',
        ocrConfidenceScore: 0.990,
        version: 'v2.1',
      },
      {
        documentId: 'doc-video-001',
        title: 'Atlas System Demo.mp4',
        fileType: 'VIDEO',
        mimeType: 'video/mp4',
        sizeBytes: 45800000,
        extractedText: 'Transcript: Distributed search coordinator fanning out queries across cluster shards.',
        ocrConfidenceScore: 0.940,
        version: 'v1.0',
        mediaAsset: {
          mediaType: 'VIDEO',
          durationSeconds: 184.5,
          codec: 'H.264 / AAC',
          bitrate: 12500000,
          width: 3840,
          height: 2160,
        },
      },
      {
        documentId: 'doc-img-001',
        title: 'System Sharding Diagram.png',
        fileType: 'PNG',
        mimeType: 'image/png',
        sizeBytes: 3200000,
        extractedText: 'Diagram OCR: Distributed Shard Coordinator, Raft Consensus, Redis Cache.',
        ocrConfidenceScore: 0.978,
        version: 'v1.0',
        metadataRegistry: {
          cameraMake: 'Canon',
          cameraModel: 'EOS R5',
          width: 3840,
          height: 2160,
          md5Hash: 'a5c9f801e01f2154c1e08928f117a22a',
          dominantColors: ['#6366F1', '#06B6D4'],
        },
      },
    ];

    setDocuments(seedDocs);
    setSelectedDoc(seedDocs[0]);
    updatePreview(seedDocs[0], '');
  }, []);

  const updatePreview = (doc: any, q: string) => {
    if (!doc) return;
    setPreview({
      documentId: doc.documentId,
      title: doc.title,
      fileType: doc.fileType,
      highlightedSnippet: `<mark>${doc.extractedText}</mark>`,
      outlineToc: [
        { title: '1. Executive Summary', pageNumber: 1 },
        { title: '2. Multi-Modal Pipeline', pageNumber: 2 },
      ],
      slidePreviews: doc.fileType === 'PPTX' ? [{ slideNumber: 1, title: 'Slide 1' }] : [],
      spreadsheetPreview: doc.fileType === 'XLSX' || doc.fileType === 'CSV' ? { sheetName: 'Sheet1', columns: ['ID', 'Metric'], rows: [[1, 'Latency']] } : {},
    });
  };

  const handleSelectDoc = (doc: any) => {
    setSelectedDoc(doc);
    updatePreview(doc, searchQuery);
  };

  const filteredDocs = documents.filter((d) => {
    const matchesFormat = formatFilter === 'ALL' || d.fileType === formatFilter;
    const matchesQuery = !searchQuery || d.title.toLowerCase().includes(searchQuery.toLowerCase()) || d.extractedText.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesFormat && matchesQuery;
  });

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Header Bar */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h2 style={{ fontSize: '1.75rem', fontWeight: 700, background: 'linear-gradient(90deg, #fff, #9ca3af)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
            Multi-Modal Document Intelligence Explorer
          </h2>
          <p style={{ color: 'var(--text-dim)', fontSize: '0.9rem', marginTop: '4px' }}>
            Enterprise document ingestion, OCR confidence viewing, EXIF/Media inspection, and universal preview.
          </p>
        </div>

        <div style={{ display: 'flex', gap: '12px' }}>
          <button className="btn btn-primary" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Upload size={16} /> Upload Document
          </button>
        </div>
      </div>

      {/* Controls & Filter Bar */}
      <div className="glass-panel" style={{ padding: '16px', display: 'flex', gap: '16px', alignItems: 'center' }}>
        <div style={{ position: 'relative', flex: 1 }}>
          <Search size={18} style={{ position: 'absolute', left: '12px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
          <input
            type="text"
            className="input"
            placeholder="Search multi-modal documents by keyword, title, or metadata..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            style={{ paddingLeft: '40px', width: '100%' }}
          />
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <Filter size={16} color="var(--text-muted)" />
          <select className="input" value={formatFilter} onChange={(e) => setFormatFilter(e.target.value)}>
            <option value="ALL">All Formats</option>
            <option value="PDF">PDF</option>
            <option value="DOCX">Word (.docx)</option>
            <option value="PNG">Images (PNG/JPEG)</option>
            <option value="VIDEO">Video (MP4/MKV)</option>
          </select>
        </div>
      </div>

      {/* Main Grid & Preview Split */}
      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '24px' }}>
        {/* Left Column: Thumbnail Grid */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <h3 style={{ fontSize: '1.1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '8px' }}>
            Indexed Documents ({filteredDocs.length})
          </h3>
          <ThumbnailGrid
            documents={filteredDocs}
            selectedDocumentId={selectedDoc?.documentId || null}
            onSelectDocument={handleSelectDoc}
          />
        </div>

        {/* Right Column: Universal Preview & Inspectors */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <UniversalPreviewPanel document={selectedDoc} preview={preview} />

          {selectedDoc?.ocrConfidenceScore && (
            <OcrConfidenceViewer
              confidenceScore={selectedDoc.ocrConfidenceScore}
              extractedText={selectedDoc.extractedText}
            />
          )}

          {selectedDoc?.metadataRegistry && (
            <MetadataInspector
              metadataRegistry={selectedDoc.metadataRegistry}
              metadata={selectedDoc.metadata}
            />
          )}

          {(selectedDoc?.mediaAsset || selectedDoc?.fileType === 'VIDEO') && (
            <MediaInformationPanel
              mediaAsset={selectedDoc.mediaAsset}
              metadataRegistry={selectedDoc.metadataRegistry}
            />
          )}
        </div>
      </div>
    </div>
  );
};
