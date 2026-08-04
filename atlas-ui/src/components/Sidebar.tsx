import React from 'react';
import { NavLink } from 'react-router-dom';
import { Home, Search, Bot, Activity, Settings, Layers } from 'lucide-react';

export const Sidebar: React.FC = () => {
  const links = [
    { to: '/', label: 'Overview', icon: Home },
    { to: '/search', label: 'Search Studio', icon: Search },
    { to: '/crawler', label: 'Crawl Scheduler', icon: Layers },
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
