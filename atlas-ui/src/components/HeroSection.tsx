import React from 'react';
import { Cpu, Play, BookOpen, Search, Shield, Zap, Sparkles, Terminal } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export const HeroSection: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div style={{ position: 'relative', overflow: 'hidden', padding: '60px 24px 40px', borderRadius: '16px', background: 'linear-gradient(135deg, rgba(15, 23, 42, 0.9), rgba(30, 27, 75, 0.8), rgba(15, 23, 42, 0.95))', border: '1px solid rgba(255, 255, 255, 0.12)', boxShadow: '0 20px 40px rgba(0,0,0,0.5)' }}>
      {/* Background glow effects */}
      <div style={{ position: 'absolute', top: '-100px', left: '20%', width: '300px', height: '300px', background: 'radial-gradient(circle, rgba(59, 130, 246, 0.25) 0%, transparent 70%)', pointerEvents: 'none', filter: 'blur(50px)' }} />
      <div style={{ position: 'absolute', bottom: '-100px', right: '20%', width: '350px', height: '350px', background: 'radial-gradient(circle, rgba(168, 85, 247, 0.25) 0%, transparent 70%)', pointerEvents: 'none', filter: 'blur(60px)' }} />

      <div style={{ position: 'relative', zIndex: 2, display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center', maxWidth: '900px', margin: '0 auto' }}>
        <div style={{ display: 'inline-flex', alignItems: 'center', gap: '8px', padding: '6px 16px', borderRadius: '20px', background: 'rgba(59, 130, 246, 0.12)', border: '1px solid rgba(59, 130, 246, 0.3)', marginBottom: '24px' }}>
          <Sparkles size={16} color="#60a5fa" />
          <span style={{ fontSize: '0.85rem', fontWeight: 600, color: '#60a5fa' }}>Atlas v6.0.0 Open-Source Release</span>
        </div>

        <h1 style={{ fontSize: '3rem', fontWeight: 800, letterSpacing: '-0.03em', lineHeight: 1.15, margin: '0 0 20px', background: 'linear-gradient(180deg, #FFFFFF 0%, #94A3B8 100%)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
          Enterprise Distributed AI Search Platform & Autonomous AIOS
        </h1>

        <p style={{ fontSize: '1.15rem', color: '#94a3b8', lineHeight: 1.6, marginBottom: '32px', maxWidth: '750px' }}>
          Atlas is an open-source, event-driven distributed search engine and Autonomous AI Operating System built to crawl, index, rank, reason, and execute complex enterprise missions at scale.
        </p>

        <div style={{ display: 'flex', gap: '16px', flexWrap: 'wrap', justifyContent: 'center', marginBottom: '48px' }}>
          <button className="btn btn-primary" style={{ padding: '12px 28px', fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '10px' }} onClick={() => navigate('/aios')}>
            <Cpu size={20} /> Launch AIOS Shell
          </button>
          <button className="btn btn-secondary" style={{ padding: '12px 28px', fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '10px' }} onClick={() => navigate('/docs')}>
            <BookOpen size={20} /> Documentation & Guides
          </button>
          <button className="btn btn-secondary" style={{ padding: '12px 28px', fontSize: '1rem', fontWeight: 600, display: 'flex', alignItems: 'center', gap: '10px' }} onClick={() => navigate('/api-explorer')}>
            <Terminal size={20} /> Interactive API Explorer
          </button>
        </div>

        {/* Feature Highlights Row */}
        <div style={{ width: '100%', display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '16px', textAlign: 'left' }}>
          {[
            { icon: Search, title: 'BM25 Keyword Search', desc: 'Custom inverted index segments with 1B+ scale.' },
            { icon: Cpu, title: 'Multi-Agent Fleet', desc: 'Autonomous capability-based agent dispatcher.' },
            { icon: Zap, title: '10-Mode Reasoning', desc: 'Tree-of-Thoughts & ReAct decision engine.' },
            { icon: Shield, title: 'Multi-Tenant Security', desc: 'Strict data isolation & governance audit logs.' },
          ].map((f, i) => {
            const Icon = f.icon;
            return (
              <div key={i} className="glass-panel" style={{ padding: '20px', background: 'rgba(255, 255, 255, 0.02)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
                <Icon size={22} color="#3b82f6" style={{ marginBottom: '10px' }} />
                <h4 style={{ fontSize: '1rem', fontWeight: 600, margin: '0 0 6px' }}>{f.title}</h4>
                <p style={{ fontSize: '0.825rem', color: 'var(--text-dim)', margin: 0 }}>{f.desc}</p>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};
