import React from 'react';
import { Cpu, Target, GitBranch, Compass, RefreshCw } from 'lucide-react';

export const ReasoningDashboard: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '16px' }}>
        <div className="card" style={{ padding: '20px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
            <span style={{ color: 'var(--text-dim)', fontSize: '0.875rem', fontWeight: 600 }}>Active Reasoning Sessions</span>
            <Cpu style={{ color: '#6366f1' }} size={20} />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 800 }}>128</div>
          <div style={{ fontSize: '0.75rem', color: '#10b981', marginTop: '4px' }}>+18% from last hour</div>
        </div>

        <div className="card" style={{ padding: '20px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
            <span style={{ color: 'var(--text-dim)', fontSize: '0.875rem', fontWeight: 600 }}>Autonomous Goals</span>
            <Target style={{ color: '#ec4899' }} size={20} />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 800 }}>42 Active</div>
          <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>96% Success Rate</div>
        </div>

        <div className="card" style={{ padding: '20px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
            <span style={{ color: 'var(--text-dim)', fontSize: '0.875rem', fontWeight: 600 }}>Avg Confidence Score</span>
            <Compass style={{ color: '#10b981' }} size={20} />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 800 }}>0.93</div>
          <div style={{ fontSize: '0.75rem', color: '#10b981', marginTop: '4px' }}>Optimal Threshold ({'>'}0.85)</div>
        </div>

        <div className="card" style={{ padding: '20px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
            <span style={{ color: 'var(--text-dim)', fontSize: '0.875rem', fontWeight: 600 }}>Autonomous Recoveries</span>
            <RefreshCw style={{ color: '#f59e0b' }} size={20} />
          </div>
          <div style={{ fontSize: '1.75rem', fontWeight: 800 }}>18 Recovered</div>
          <div style={{ fontSize: '0.75rem', color: '#10b981', marginTop: '4px' }}>Zero Human Interventions</div>
        </div>
      </div>

      {/* Reasoning Modes Summary */}
      <div className="card" style={{ padding: '24px', borderRadius: '12px', background: 'var(--bg-secondary, #1e293b)' }}>
        <h3 style={{ fontSize: '1.1rem', fontWeight: 700, margin: '0 0 16px 0', display: 'flex', alignItems: 'center', gap: '8px' }}>
          <GitBranch size={20} style={{ color: '#8b5cf6' }} />
          Supported Reasoning Modes (Phase 5.8)
        </h3>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '12px' }}>
          {[
            'CHAIN_OF_THOUGHT', 'TREE_OF_THOUGHTS', 'GRAPH_OF_THOUGHTS', 'REFLECTION', 'REACT',
            'PLAN_AND_EXECUTE', 'GOAL_ORIENTED', 'CONSTRAINT_BASED', 'MULTI_STEP_DECISION', 'ADAPTIVE_REPLANNING'
          ].map((mode) => (
            <div key={mode} style={{ padding: '12px', borderRadius: '8px', background: 'rgba(255,255,255,0.03)', border: '1px solid rgba(255,255,255,0.08)' }}>
              <div style={{ fontSize: '0.8rem', fontWeight: 700, color: '#8b5cf6' }}>{mode}</div>
              <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '4px' }}>Active Engine</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
