import React, { useState } from 'react';
import { AtlasOSDashboard } from '../components/AtlasOSDashboard';
import { MissionControl } from '../components/MissionControl';
import { AgentFleet } from '../components/AgentFleet';
import { MissionTimeline } from '../components/MissionTimeline';
import { AutomationCenter } from '../components/AutomationCenter';
import { PlatformHealth } from '../components/PlatformHealth';
import { ClusterOverview } from '../components/ClusterOverview';
import { ResourceMonitor } from '../components/ResourceMonitor';
import { PolicyManager } from '../components/PolicyManager';
import { MissionAnalytics } from '../components/MissionAnalytics';
import { Cpu, Play, Bot, Clock, Settings, Activity, Server, Zap, ShieldCheck, BarChart2 } from 'lucide-react';

export const AIOSPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState('dashboard');

  const tabs = [
    { id: 'dashboard', label: 'AIOS Dashboard', icon: Cpu },
    { id: 'mission-control', label: 'Mission Control', icon: Play },
    { id: 'agent-fleet', label: 'Agent Fleet', icon: Bot },
    { id: 'timeline', label: 'Timeline', icon: Clock },
    { id: 'automation-center', label: 'Automation Center', icon: Settings },
    { id: 'health', label: 'Platform Health', icon: Activity },
    { id: 'cluster', label: 'Cluster Overview', icon: Server },
    { id: 'resources', label: 'Resource Monitor', icon: Zap },
    { id: 'policy', label: 'Policy Manager', icon: ShieldCheck },
    { id: 'analytics', label: 'Analytics', icon: BarChart2 },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Navigation Sub-Header */}
      <div className="glass-panel" style={{ padding: '12px 16px', display: 'flex', gap: '8px', overflowX: 'auto', flexWrap: 'nowrap' }}>
        {tabs.map((t) => {
          const Icon = t.icon;
          const isActive = activeTab === t.id;
          return (
            <button
              key={t.id}
              onClick={() => setActiveTab(t.id)}
              className={`btn ${isActive ? 'btn-primary' : 'btn-secondary'}`}
              style={{ padding: '8px 14px', fontSize: '0.85rem', whiteSpace: 'nowrap' }}
            >
              <Icon size={16} />
              <span>{t.label}</span>
            </button>
          );
        })}
      </div>

      {/* Main Tab Content */}
      <div>
        {activeTab === 'dashboard' && <AtlasOSDashboard onNavigate={(tab) => setActiveTab(tab)} />}
        {activeTab === 'mission-control' && <MissionControl />}
        {activeTab === 'agent-fleet' && <AgentFleet />}
        {activeTab === 'timeline' && <MissionTimeline />}
        {activeTab === 'automation-center' && <AutomationCenter />}
        {activeTab === 'health' && <PlatformHealth />}
        {activeTab === 'cluster' && <ClusterOverview />}
        {activeTab === 'resources' && <ResourceMonitor />}
        {activeTab === 'policy' && <PolicyManager />}
        {activeTab === 'analytics' && <MissionAnalytics />}
      </div>
    </div>
  );
};
