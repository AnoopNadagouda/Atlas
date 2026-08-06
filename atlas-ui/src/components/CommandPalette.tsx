import React, { useState, useEffect } from 'react';
import { Search, BookOpen, Code, Cpu, Server, X } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

export const CommandPalette: React.FC<{ isOpen: boolean; onClose: () => void }> = ({ isOpen, onClose }) => {
  const [query, setQuery] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if ((e.metaKey || e.ctrlKey) && e.key === 'k') {
        e.preventDefault();
        isOpen ? onClose() : null;
      }
      if (e.key === 'Escape' && isOpen) {
        onClose();
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [isOpen, onClose]);

  if (!isOpen) return null;

  const items = [
    { title: 'Overview & Landing', category: 'Navigation', route: '/', icon: Server },
    { title: 'Atlas AIOS Shell', category: 'Platform', route: '/aios', icon: Cpu },
    { title: 'Interactive Documentation', category: 'Docs', route: '/docs', icon: BookOpen },
    { title: 'API Explorer & Sandbox', category: 'APIs', route: '/api-explorer', icon: Code },
    { title: 'System Architecture Diagrams (16 Pipelines)', category: 'Architecture', route: '/architecture', icon: Server },
    { title: 'Dashboard Screenshot Showcase (13 Dashboards)', category: 'UI', route: '/screenshots', icon: BookOpen },
    { title: 'Open-Source Release Notes & Blog', category: 'Community', route: '/blog', icon: BookOpen },
    { title: 'Keyword & Neural Search Studio', category: 'Studio', route: '/search', icon: Search },
    { title: 'Federated Search Explorer', category: 'Studio', route: '/federated-search', icon: Search },
    { title: 'Enterprise Connector Hub', category: 'Connectors', route: '/connectors', icon: Server },
  ];

  const filtered = items.filter(i => i.title.toLowerCase().includes(query.toLowerCase()) || i.category.toLowerCase().includes(query.toLowerCase()));

  const handleSelect = (route: string) => {
    navigate(route);
    onClose();
  };

  return (
    <div style={{ position: 'fixed', inset: 0, zIndex: 9999, backgroundColor: 'rgba(0, 0, 0, 0.75)', backdropFilter: 'blur(8px)', display: 'flex', alignItems: 'flex-start', justifyContent: 'center', paddingTop: '10vh' }}>
      <div className="glass-panel" style={{ width: '90%', maxWidth: '640px', padding: '0', overflow: 'hidden', border: '1px solid rgba(255, 255, 255, 0.15)', boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.5)' }}>
        <div style={{ display: 'flex', alignItems: 'center', padding: '16px 20px', borderBottom: '1px solid rgba(255, 255, 255, 0.1)' }}>
          <Search size={20} color="var(--text-dim)" style={{ marginRight: '12px' }} />
          <input
            type="text"
            placeholder="Type a command or search docs, APIs, architecture... (Esc to close)"
            value={query}
            onChange={e => setQuery(e.target.value)}
            autoFocus
            style={{ width: '100%', background: 'transparent', border: 'none', outline: 'none', color: '#fff', fontSize: '1rem' }}
          />
          <button onClick={onClose} style={{ background: 'transparent', border: 'none', color: 'var(--text-dim)', cursor: 'pointer' }}>
            <X size={18} />
          </button>
        </div>

        <div style={{ maxHeight: '360px', overflowY: 'auto', padding: '8px' }}>
          {filtered.length === 0 ? (
            <div style={{ padding: '24px', textAlign: 'center', color: 'var(--text-dim)', fontSize: '0.9rem' }}>
              No matching docs or tools found.
            </div>
          ) : (
            filtered.map((item, idx) => {
              const Icon = item.icon;
              return (
                <div
                  key={idx}
                  onClick={() => handleSelect(item.route)}
                  style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '12px 16px', borderRadius: '8px', cursor: 'pointer', transition: 'background 0.2s', marginBottom: '4px' }}
                  className="command-item"
                  onMouseEnter={e => (e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.08)')}
                  onMouseLeave={e => (e.currentTarget.style.backgroundColor = 'transparent')}
                >
                  <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <Icon size={18} color="#3b82f6" />
                    <span style={{ fontWeight: 500, fontSize: '0.95rem' }}>{item.title}</span>
                  </div>
                  <span className="badge" style={{ backgroundColor: 'rgba(59, 130, 246, 0.15)', color: '#60a5fa', fontSize: '0.75rem' }}>
                    {item.category}
                  </span>
                </div>
              );
            })
          )}
        </div>
      </div>
    </div>
  );
};
