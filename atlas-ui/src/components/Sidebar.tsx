import React from 'react';
import { NavLink } from 'react-router-dom';
import { Home, Search, Activity, Layers, FileText, Network, Globe, Brain, Cpu, BookOpen, Terminal, Monitor, Sparkles } from 'lucide-react';

export const Sidebar: React.FC = () => {
  const links = [
    { to: '/', label: 'Overview', icon: Home },
    { to: '/aios', label: 'Atlas AIOS Shell', icon: Cpu },
    { to: '/docs', label: 'Documentation Hub', icon: BookOpen },
    { to: '/api-explorer', label: 'API Explorer', icon: Terminal },
    { to: '/architecture', label: 'Architecture Diagrams', icon: Layers },
    { to: '/screenshots', label: 'Dashboard Gallery', icon: Monitor },
    { to: '/blog', label: 'Engineering Blog', icon: Sparkles },
    { to: '/search', label: 'Search Studio', icon: Search },
    { to: '/federated-search', label: 'Federated Search', icon: Globe },
    { to: '/connectors', label: 'Connector Hub', icon: Network },
    { to: '/documents', label: 'Document Explorer', icon: FileText },
    { to: '/crawler', label: 'Crawl Scheduler', icon: Layers },
    { to: '/memory', label: 'AI Memory Platform', icon: Brain },
    { to: '/reasoning', label: 'AI Reasoning Engine', icon: Cpu },
    { to: '/status', label: 'Cluster Status', icon: Activity },
  ];



  return (
    <aside className="glass-panel" style={{ width: '240px', borderRadius: 0, borderTop: 'none', borderBottom: 'none', borderLeft: 'none', padding: '24px 16px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
      <div style={{ fontSize: '0.75rem', fontWeight: 600, color: 'var(--text-dim)', textTransform: 'uppercase', letterSpacing: '0.05em', padding: '0 12px 12px' }}>
        Navigation Shell
      </div>
      {links.map((link) => {
        const Icon = link.icon;
        return (
          <NavLink
            key={link.to}
            to={link.to}
            className={({ isActive }) =>
              `btn ${isActive ? 'btn-primary' : 'btn-secondary'}`
            }
            style={{ justifyContent: 'flex-start', width: '100%', textDecoration: 'none' }}
          >
            <Icon size={18} />
            <span>{link.label}</span>
          </NavLink>
        );
      })}
    </aside>
  );
};
