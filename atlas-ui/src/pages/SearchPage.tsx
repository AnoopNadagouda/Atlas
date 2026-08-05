import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3, HelpCircle, CheckCircle2, ShieldCheck, Lock, LineChart, History, Clock, Code, FileCode, Users, Key, Package, Download, FileText, Image, Video, FileSpreadsheet } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking' | 'query' | 'ops' | 'analytics' | 'history' | 'code' | 'tenants' | 'plugins' | 'multimodal'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);
  const [selectedTenant, setSelectedTenant] = useState('default-tenant');

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockMultiModalDocs = [
    { id: 'doc-pdf-001', title: 'Atlas Architecture Blueprint.pdf', type: 'PDF', size: '2.45 MB', ocrConfidence: '98.5%', sections: 3, preview: 'Atlas Distributed AI Search Engine Architecture and Parallel Hybrid BM25 HNSW RRF Pipeline.' },
    { id: 'doc-video-001', title: 'Atlas System Demo.mp4', type: 'VIDEO', size: '45.8 MB', ocrConfidence: '94.0%', duration: '03:04', preview: 'Transcript: Distributed search coordinator fanning out queries across cluster shards.' }
  ];

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise Multi-Modal AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 5.3 Multi-Modal Search & Document Intelligence with PDF, Office, Video, OCR, and Universal Document Previews.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      pageRankScore: 0.384,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'MULTI_MODAL', 'OCR_EXTRACTED'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Multi-Modal Document Intelligence</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Multi-Modal Ingestion, PDF/Office Parsing, OCR Extraction, Video/EXIF Metadata, Plugin SDK, Multi-Tenant SaaS, and Code Search.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Search multi-modal documents, PDFs, transcripts, video metadata..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Multi-Modal Search
          </button>
        </div>

        <div style={{ display: 'flex', gap: '12px', alignItems: 'center', flexWrap: 'wrap' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Retrieval Mode:</span>
          <button
            className={`btn ${searchMode === 'copilot' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('copilot')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Bot size={14} /> AI Search Copilot (RAG)
          </button>
          <button
            className={`btn ${searchMode === 'multimodal' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('multimodal')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <FileText size={14} /> Multi-Modal Document Explorer
          </button>
          <button
            className={`btn ${searchMode === 'plugins' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('plugins')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Package size={14} /> Plugin Marketplace
          </button>
          <button
            className={`btn ${searchMode === 'tenants' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('tenants')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Users size={14} /> Multi-Tenant SaaS
          </button>
        </div>
      </div>

      {searchMode === 'multimodal' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px', borderLeft: '4px solid #38bdf8' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#38bdf8', fontWeight: 600 }}>
              <FileText size={20} /> Multi-Modal Document Intelligence Explorer & OCR Pipeline
            </div>
            <span className="badge badge-info">Indexed Content Types: PDF, DOCX, PPTX, XLSX, MP4, JPEG</span>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Universal Documents & Previews:</span>
            {mockMultiModalDocs.map((doc) => (
              <div key={doc.id} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '16px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                    {doc.type === 'PDF' ? <FileText size={18} style={{ color: '#ef4444' }} /> : <Video size={18} style={{ color: '#818cf8' }} />}
                    <strong style={{ color: '#60a5fa' }}>{doc.title}</strong>
                  </div>
                  <div style={{ display: 'flex', gap: '6px' }}>
                    <span className="badge badge-info">Format: {doc.type}</span>
                    <span className="badge badge-info">OCR Confidence: {doc.ocrConfidence}</span>
                  </div>
                </div>
                <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>{doc.preview}</p>
                <div style={{ fontSize: '0.75rem', color: '#38bdf8' }}>File Size: {doc.size} {doc.duration ? `| Duration: ${doc.duration}` : ''}</div>
              </div>
            ))}
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Grounded with Multi-Modal Documents & OCR)
            </div>
          </div>
          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Multi-Modal Search Results ({mockResults.length} indexed documents)</span>
          <span>Tenant Context: {selectedTenant}</span>
        </div>

        {mockResults.map((result) => (
          <div key={result.id} className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <a href={result.url} target="_blank" rel="noreferrer" style={{ color: '#818cf8', fontWeight: 600, fontSize: '1.1rem', textDecoration: 'none', display: 'flex', alignItems: 'center', gap: '6px' }}>
                {result.title} <ExternalLink size={14} />
              </a>
              <div style={{ display: 'flex', gap: '6px' }}>
                {result.sources.map((src) => (
                  <span key={src} className="badge badge-info">{src}</span>
                ))}
              </div>
            </div>
            <div style={{ fontSize: '0.8rem', color: 'var(--accent-cyan)' }}>{result.url}</div>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>{result.snippet}</p>
          </div>
        ))}
      </div>
    </div>
  );
};
