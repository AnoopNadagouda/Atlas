import React from 'react';
import { BookOpen, Sparkles, Calendar, User, ArrowRight } from 'lucide-react';

export const BlogPage: React.FC = () => {
  const posts = [
    {
      title: 'Announcing Atlas v6.0.0: Open-Source AI Operating System & Distributed Search Engine',
      date: 'August 6, 2026',
      author: 'Atlas Core Engineering Team',
      snippet: 'Today we are thrilled to release Atlas v6.0.0 to the open-source community! Atlas combines custom inverted index BM25 search with 10-mode reasoning, multi-agent fleet dispatching, and autonomous AIOS orchestration.',
      badge: 'Release v6.0.0'
    },
    {
      title: 'Building a Custom Inverted Index Engine from Scratch in Java 21',
      date: 'July 28, 2026',
      author: 'Atlas Search Team',
      snippet: 'Why we bypassed Lucene and Elasticsearch to build an in-memory & on-disk inverted index engine supporting BM25 scoring and Varbyte posting list compression.',
      badge: 'Deep Dive'
    },
    {
      title: 'Autonomous Multi-Agent Collaboration & Ebbinghaus Memory Decay',
      date: 'July 15, 2026',
      author: 'AI & Reasoning Team',
      snippet: 'How Atlas manages multi-agent fleet coordination with Ebbinghaus memory decay curves and Tree-of-Thoughts deductive reasoning.',
      badge: 'AI & Reasoning'
    },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
          <Sparkles className="text-accent" size={28} /> Atlas Engineering Blog & Release Notes
        </h2>
        <p style={{ color: 'var(--text-dim)', marginTop: '6px', marginBottom: 0, fontSize: '0.95rem' }}>
          Official technical articles, architecture deep dives, and release announcements.
        </p>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
        {posts.map((post, idx) => (
          <div key={idx} className="glass-panel" style={{ padding: '24px', background: 'rgba(15, 23, 42, 0.85)', border: '1px solid rgba(255, 255, 255, 0.1)' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '8px' }}>
              <span className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.15)', color: '#60a5fa', fontSize: '0.75rem' }}>
                {post.badge}
              </span>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-dim)', display: 'flex', alignItems: 'center', gap: '12px' }}>
                <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}><Calendar size={14} /> {post.date}</span>
                <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}><User size={14} /> {post.author}</span>
              </div>
            </div>

            <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: '8px 0', color: '#fff' }}>{post.title}</h3>
            <p style={{ color: '#cbd5e1', fontSize: '0.95rem', lineHeight: 1.6, margin: '8px 0 16px' }}>{post.snippet}</p>

            <button className="btn btn-secondary" style={{ padding: '6px 14px', fontSize: '0.85rem' }}>
              Read Full Post <ArrowRight size={14} style={{ marginLeft: '4px' }} />
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};
