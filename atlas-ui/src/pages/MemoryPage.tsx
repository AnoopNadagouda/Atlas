import React, { useState } from 'react';
import { MemoryDashboard } from '../components/MemoryDashboard';
import { MemoryExplorer } from '../components/MemoryExplorer';
import { ConversationHistory } from '../components/ConversationHistory';
import { MemoryGraph } from '../components/MemoryGraph';
import { MemoryAnalyticsView } from '../components/MemoryAnalytics';
import { MemoryTimeline } from '../components/MemoryTimeline';
import { MemorySearchPanel } from '../components/MemorySearchPanel';
import { MemorySettings } from '../components/MemorySettings';
import { Brain, Search, Clock, Network, BarChart2, Settings, MessageSquare, List } from 'lucide-react';

export const MemoryPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'dashboard' | 'explorer' | 'search' | 'conversation' | 'graph' | 'timeline' | 'analytics' | 'settings'>('dashboard');

  const tabs = [
    { id: 'dashboard', label: 'Dashboard', icon: Brain },
    { id: 'explorer', label: 'Memory Explorer', icon: List },
    { id: 'search', label: 'Semantic Search', icon: Search },
    { id: 'conversation', label: 'Conversation History', icon: MessageSquare },
    { id: 'graph', label: 'Knowledge Graph', icon: Network },
    { id: 'timeline', label: 'Timeline Stream', icon: Clock },
    { id: 'analytics', label: 'Analytics', icon: BarChart2 },
    { id: 'settings', label: 'Settings', icon: Settings },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h1 style={{ fontSize: '1.875rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
            <Brain style={{ color: 'var(--accent-primary, #6366f1)' }} size={32} />
            Enterprise AI Memory & Context Platform
          </h1>
          <p style={{ color: 'var(--text-dim)', margin: '4px 0 0 0', fontSize: '0.95rem' }}>
            Phase 5.7 (v5.7.0): Long-Term Context, Dynamic Decay & Continuous Knowledge Learning
          </p>
        </div>
        <div style={{ display: 'flex', gap: '8px' }}>
          <span className="badge badge-success" style={{ padding: '6px 12px', fontSize: '0.85rem' }}>
            Microservice Port 8088 Active
          </span>
          <span className="badge badge-info" style={{ padding: '6px 12px', fontSize: '0.85rem' }}>
            API v21 Enabled
          </span>
        </div>
      </div>

      {/* Tabs */}
      <div style={{ display: 'flex', gap: '8px', borderBottom: '1px solid var(--border-color, rgba(255, 255, 255, 0.1))', paddingBottom: '12px', overflowX: 'auto' }}>
        {tabs.map((tab) => {
          const Icon = tab.icon;
          const isActive = activeTab === tab.id;
          return (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id as any)}
              className={`btn ${isActive ? 'btn-primary' : 'btn-secondary'}`}
              style={{ display: 'flex', alignItems: 'center', gap: '8px', padding: '8px 16px', borderRadius: '8px', fontSize: '0.875rem', fontWeight: 600 }}
            >
              <Icon size={16} />
              <span>{tab.label}</span>
            </button>
          );
        })}
      </div>

      {/* Tab Content */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
        {activeTab === 'dashboard' && (
          <>
            <MemoryDashboard />
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
              <MemoryExplorer />
              <ConversationHistory />
            </div>
          </>
        )}
        {activeTab === 'explorer' && <MemoryExplorer />}
        {activeTab === 'search' && <MemorySearchPanel />}
        {activeTab === 'conversation' && <ConversationHistory />}
        {activeTab === 'graph' && <MemoryGraph />}
        {activeTab === 'timeline' && <MemoryTimeline />}
        {activeTab === 'analytics' && <MemoryAnalyticsView />}
        {activeTab === 'settings' && <MemorySettings />}
      </div>
    </div>
  );
};
