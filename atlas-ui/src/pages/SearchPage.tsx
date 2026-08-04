import React, { useState } from 'react';
import { Search, Sparkles, ExternalLink, Zap, Brain, Layers, Bot, Copy, Square, Network, Cpu, Database, Server, Activity, BarChart3 } from 'lucide-react';

export const SearchPage: React.FC = () => {
  const [query, setQuery] = useState('');
  const [searchMode, setSearchMode] = useState<'keyword' | 'semantic' | 'hybrid' | 'copilot' | 'graph' | 'cluster' | 'ranking'>('copilot');
  const [isStreaming, setIsStreaming] = useState(false);

  const mockCopilotAnswer = "Based on the retrieved indexed documents [1], Atlas executes parallel hybrid search combining BM25 term frequencies and 384-dimensional HNSW ANN vector similarity scores via Reciprocal Rank Fusion (RRF) [2]. Connected entities include Spring Boot, Apache Kafka, and PostgreSQL [Graph-Fact]. Distributed search coordinator fanned out query across 2 active cluster shards.";

  const mockRanking = {
    docId: 'doc-foundation-001',
    weights: { rrf: '40%', pageRank: '35%', freshness: '25%' },
    signals: { bm25: 0.892, semantic: 0.945, rrf: 0.0328, pageRank: 0.384, freshness: 0.95 },
    finalScore: 0.478
  };

  const mockCluster = {
    clusterName: 'atlas-search-cluster',
    status: 'GREEN',
    nodeId: 'search-node-1',
    shards: [
      { id: 'shard-0', docs: '500,000', size: '128 MB', status: 'ACTIVE', primary: true },
      { id: 'shard-1', docs: '500,000', size: '128 MB', status: 'ACTIVE', primary: true },
    ]
  };

  const mockEntityCard = {
    name: 'Atlas Search Engine',
    type: 'PRODUCT',
    category: 'Distributed Search Platform',
    confidence: '99%',
    connectedTechnologies: [
      { name: 'Spring Boot', relation: 'USES', type: 'FRAMEWORK' },
      { name: 'Apache Kafka', relation: 'USES', type: 'EVENT_STREAMING' },
      { name: 'PostgreSQL', relation: 'USES', type: 'DATABASE' },
      { name: 'Redis', relation: 'USES', type: 'CACHE' },
    ]
  };

  const mockResults = [
    {
      id: 'doc-foundation-001',
      title: 'Atlas Platform - Enterprise AI Search Engine',
      url: 'https://atlas.search/docs/hybrid-rrf',
      snippet: 'Phase 2.2 Parallel Hybrid Search Engine with Reciprocal Rank Fusion (RRF). Executes BM25 keyword search and 384-dim HNSW vector search simultaneously.',
      bm25Score: 0.892,
      semanticScore: 0.945,
      rrfScore: 0.0328,
      pageRankScore: 0.384,
      sources: ['KEYWORD', 'SEMANTIC', 'HYBRID', 'GRAPH', 'CLUSTER', 'PAGERANK'],
    },
    {
      id: 'doc-bm25-002',
      title: 'Custom Inverted Index & Robertson-Spärck Jones BM25 Ranker',
      url: 'https://atlas.search/docs/bm25-spec',
      snippet: 'Term-posting dictionary holding document frequencies, collection frequencies, token positions, and field flags (TITLE, BODY, HEADING).',
      bm25Score: 0.941,
      semanticScore: 0.720,
      rrfScore: 0.0315,
      pageRankScore: 0.210,
      sources: ['KEYWORD', 'HYBRID', 'PAGERANK'],
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Search Studio & Ranking Pipeline</h2>
        <p style={{ color: 'var(--text-muted)' }}>Test Keyword (BM25), Semantic Vector, Parallel Hybrid RRF, PageRank, Freshness Decay, Knowledge Graph, and Distributed Cluster Sharding.</p>
      </div>

      <div className="glass-panel" style={{ padding: '20px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ display: 'flex', gap: '12px' }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <input
              type="text"
              className="input-field"
              placeholder="Ask AI Search Copilot, inspect PageRank, or search distributed cluster..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              style={{ paddingLeft: '44px' }}
            />
            <Search size={20} style={{ position: 'absolute', left: '14px', top: '12px', color: 'var(--text-dim)' }} />
          </div>
          <button className="btn btn-primary" onClick={() => setIsStreaming(true)}>
            <Zap size={18} /> Search & Ask Copilot
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
            className={`btn ${searchMode === 'ranking' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('ranking')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <BarChart3 size={14} /> Ranking Inspector
          </button>
          <button
            className={`btn ${searchMode === 'cluster' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('cluster')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Server size={14} /> Cluster Dashboard
          </button>
          <button
            className={`btn ${searchMode === 'graph' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('graph')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Network size={14} /> Knowledge Graph
          </button>
          <button
            className={`btn ${searchMode === 'hybrid' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('hybrid')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Sparkles size={14} /> Hybrid (RRF + Vector)
          </button>
          <button
            className={`btn ${searchMode === 'semantic' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('semantic')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Brain size={14} /> Semantic (Vector)
          </button>
          <button
            className={`btn ${searchMode === 'keyword' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setSearchMode('keyword')}
            style={{ padding: '6px 14px', fontSize: '0.8rem' }}
          >
            <Layers size={14} /> Keyword (BM25)
          </button>
        </div>
      </div>

      {searchMode === 'ranking' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #ec4899' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#f472b6', fontWeight: 600 }}>
              <BarChart3 size={20} /> Multi-Signal Ranking Pipeline & PageRank Inspector
            </div>
            <span className="badge badge-info">Final Composition Score: {mockRanking.finalScore}</span>
          </div>

          <div style={{ display: 'flex', gap: '24px', flexWrap: 'wrap' }}>
            <div>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Target Document ID</span>
              <div style={{ fontWeight: 600, fontSize: '1rem', color: '#f3f4f6' }}>{mockRanking.docId}</div>
            </div>
            <div>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Signal Weight Mix</span>
              <div style={{ fontWeight: 600, fontSize: '1rem', color: '#f472b6' }}>RRF {mockRanking.weights.rrf} | PageRank {mockRanking.weights.pageRank} | Freshness {mockRanking.weights.freshness}</div>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Ranking Signal Breakdown:</span>
            <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap' }}>
              <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>BM25 Score</div>
                <strong style={{ fontSize: '1.1rem', color: '#818cf8' }}>{mockRanking.signals.bm25}</strong>
              </div>
              <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Semantic Vector</div>
                <strong style={{ fontSize: '1.1rem', color: '#818cf8' }}>{mockRanking.signals.semantic}</strong>
              </div>
              <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Distributed PageRank</div>
                <strong style={{ fontSize: '1.1rem', color: '#ec4899' }}>{mockRanking.signals.pageRank}</strong>
              </div>
              <div style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px', flex: 1 }}>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Freshness Decay</div>
                <strong style={{ fontSize: '1.1rem', color: '#10b981' }}>{mockRanking.signals.freshness}</strong>
              </div>
            </div>
          </div>
        </div>
      )}

      {searchMode === 'cluster' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #3b82f6' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#60a5fa', fontWeight: 600 }}>
              <Server size={20} /> Distributed Search Cluster Manager & Shard Routing
            </div>
            <span className="badge badge-info">Cluster Status: {mockCluster.status}</span>
          </div>

          <div style={{ display: 'flex', gap: '24px', flexWrap: 'wrap' }}>
            <div>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Cluster Name</span>
              <div style={{ fontWeight: 600, fontSize: '1rem', color: '#f3f4f6' }}>{mockCluster.clusterName}</div>
            </div>
            <div>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Active Coordinator Node</span>
              <div style={{ fontWeight: 600, fontSize: '1rem', color: '#60a5fa' }}>{mockCluster.nodeId}</div>
            </div>
            <div>
              <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Sharding Strategy</span>
              <div style={{ fontWeight: 600, fontSize: '1rem', color: '#f3f4f6' }}>HashShardingStrategy (MurmurHash-3)</div>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Shard Distribution & Routing Table:</span>
            <div style={{ display: 'flex', gap: '12px', flexWrap: 'wrap' }}>
              {mockCluster.shards.map((s) => (
                <div key={s.id} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '8px', padding: '12px 16px', display: 'flex', flexDirection: 'column', gap: '4px', flex: 1, minWidth: '200px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <strong style={{ color: '#818cf8' }}>{s.id}</strong>
                    <span className="badge badge-info">{s.status}</span>
                  </div>
                  <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Docs: {s.docs} | Size: {s.size}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {(searchMode === 'copilot' || searchMode === 'graph') && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #10b981' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#10b981', fontWeight: 600 }}>
              <Network size={20} /> Knowledge Graph Entity Card & Traversal
            </div>
            <span className="badge badge-info">Canonical Confidence: {mockEntityCard.confidence}</span>
          </div>

          <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap', alignItems: 'center' }}>
            <div>
              <h3 style={{ fontSize: '1.2rem', fontWeight: 700, color: '#f3f4f6' }}>{mockEntityCard.name}</h3>
              <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>{mockEntityCard.category} ({mockEntityCard.type})</p>
            </div>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>Connected Knowledge Graph Nodes (1-Hop Traversal):</span>
            <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
              {mockEntityCard.connectedTechnologies.map((tech) => (
                <div key={tech.name} style={{ background: 'rgba(255, 255, 255, 0.04)', border: '1px solid rgba(255, 255, 255, 0.08)', borderRadius: '6px', padding: '8px 12px', display: 'flex', alignItems: 'center', gap: '8px', fontSize: '0.85rem' }}>
                  <Cpu size={14} style={{ color: '#818cf8' }} />
                  <span><strong>{tech.name}</strong> ({tech.relation})</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {searchMode === 'copilot' && (
        <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px', borderLeft: '4px solid #6366f1' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#818cf8', fontWeight: 600 }}>
              <Bot size={20} /> AI Search Copilot Answer (Grounded with Graph Facts)
            </div>
            <div style={{ display: 'flex', gap: '8px' }}>
              <button className="btn btn-secondary" style={{ padding: '4px 10px', fontSize: '0.75rem' }} onClick={() => navigator.clipboard.writeText(mockCopilotAnswer)}>
                <Copy size={12} /> Copy Answer
              </button>
              {isStreaming && (
                <button className="btn btn-danger" style={{ padding: '4px 10px', fontSize: '0.75rem' }} onClick={() => setIsStreaming(false)}>
                  <Square size={12} /> Stop Generation
                </button>
              )}
            </div>
          </div>

          <p style={{ fontSize: '1rem', lineHeight: '1.6', color: '#f3f4f6' }}>{mockCopilotAnswer}</p>

          <div style={{ display: 'flex', gap: '8px', flexWrap: 'wrap', alignItems: 'center', fontSize: '0.8rem', color: 'var(--text-muted)' }}>
            <span>Sources Cited:</span>
            <span className="badge badge-info">[1] doc-foundation-001</span>
            <span className="badge badge-info">[2] doc-bm25-002</span>
            <span className="badge badge-info">[Graph-Fact] Spring Boot, Kafka, Postgres</span>
          </div>
        </div>
      )}

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', display: 'flex', justifyContent: 'space-between' }}>
          <span>Grounded Retrieval Results ({mockResults.length} indexed documents)</span>
          <span>Ranking Pipeline Latency: 21ms (Signals: BM25, Vector, RRF, PageRank, Freshness)</span>
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

            <div style={{ display: 'flex', gap: '16px', fontSize: '0.75rem', color: 'var(--text-dim)', paddingTop: '8px', borderTop: '1px solid rgba(255, 255, 255, 0.05)' }}>
              <span>BM25 Score: <strong>{result.bm25Score}</strong></span>
              <span>Semantic Score: <strong>{result.semanticScore}</strong></span>
              <span>PageRank Score: <strong>{result.pageRankScore}</strong></span>
              <span>RRF Fusion Score: <strong>{result.rrfScore}</strong></span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
