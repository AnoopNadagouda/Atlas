import React, { useState } from 'react';
import { ReasoningDashboard } from '../components/ReasoningDashboard';
import { GoalManagerView } from '../components/GoalManagerView';
import { ThoughtGraphView } from '../components/ThoughtGraphView';
import { DecisionExplorer } from '../components/DecisionExplorer';
import { ReflectionViewer } from '../components/ReflectionViewer';
import { CritiquePanel } from '../components/CritiquePanel';
import { ReasoningTimeline } from '../components/ReasoningTimeline';
import { RecoveryCenter } from '../components/RecoveryCenter';
import { ReasoningAnalyticsView } from '../components/ReasoningAnalyticsView';
import { Cpu, Target, GitGraph, Compass, Eye, ShieldCheck, Clock, RefreshCw, BarChart2 } from 'lucide-react';

export const ReasoningPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'dashboard' | 'goals' | 'graph' | 'decisions' | 'reflection' | 'critique' | 'timeline' | 'recovery' | 'analytics'>('dashboard');

  const tabs = [
    { id: 'dashboard', label: 'Dashboard', icon: Cpu },
    { id: 'goals', label: 'Goal Manager', icon: Target },
    { id: 'graph', label: 'Thought Graph', icon: GitGraph },
    { id: 'decisions', label: 'Decision Explorer', icon: Compass },
    { id: 'reflection', label: 'Self-Reflection', icon: Eye },
    { id: 'critique', label: 'Critique & Safety', icon: ShieldCheck },
    { id: 'timeline', label: 'Timeline', icon: Clock },
    { id: 'recovery', label: 'Recovery Center', icon: RefreshCw },
    { id: 'analytics', label: 'Analytics', icon: BarChart2 },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h1 style={{ fontSize: '1.875rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
            <Cpu style={{ color: '#8b5cf6' }} size={32} />
            Enterprise AI Reasoning & Autonomous Decision Engine
          </h1>
          <p style={{ color: 'var(--text-dim)', margin: '4px 0 0 0', fontSize: '0.95rem' }}>
            Phase 5.8 (v5.8.0): Multi-Step Planning, Tree/Graph of Thoughts, Self-Reflection & Autonomous Recovery
          </p>
        </div>
        <div style={{ display: 'flex', gap: '8px' }}>
          <span className="badge badge-success" style={{ padding: '6px 12px', fontSize: '0.85rem' }}>
            Microservice Port 8089 Active
          </span>
          <span className="badge badge-info" style={{ padding: '6px 12px', fontSize: '0.85rem' }}>
            API v22 Enabled
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
            <ReasoningDashboard />
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '24px' }}>
              <GoalManagerView />
              <ThoughtGraphView />
            </div>
          </>
        )}
        {activeTab === 'goals' && <GoalManagerView />}
        {activeTab === 'graph' && <ThoughtGraphView />}
        {activeTab === 'decisions' && <DecisionExplorer />}
        {activeTab === 'reflection' && <ReflectionViewer />}
        {activeTab === 'critique' && <CritiquePanel />}
        {activeTab === 'timeline' && <ReasoningTimeline />}
        {activeTab === 'recovery' && <RecoveryCenter />}
        {activeTab === 'analytics' && <ReasoningAnalyticsView />}
      </div>
    </div>
  );
};
