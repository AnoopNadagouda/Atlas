import React from 'react';

export const WorkflowDashboard: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="glass-panel p-4 rounded-xl border border-white/10 bg-slate-900/50 backdrop-blur-md">
          <p className="text-xs text-slate-400">Total Workflows</p>
          <h3 className="text-2xl font-bold text-blue-400 mt-1">12</h3>
        </div>
        <div className="glass-panel p-4 rounded-xl border border-white/10 bg-slate-900/50 backdrop-blur-md">
          <p className="text-xs text-slate-400">Active Executions</p>
          <h3 className="text-2xl font-bold text-emerald-400 mt-1">4</h3>
        </div>
        <div className="glass-panel p-4 rounded-xl border border-white/10 bg-slate-900/50 backdrop-blur-md">
          <p className="text-xs text-slate-400">Pending Approvals</p>
          <h3 className="text-2xl font-bold text-amber-400 mt-1">2</h3>
        </div>
        <div className="glass-panel p-4 rounded-xl border border-white/10 bg-slate-900/50 backdrop-blur-md">
          <p className="text-xs text-slate-400">Success Rate</p>
          <h3 className="text-2xl font-bold text-indigo-400 mt-1">99.4%</h3>
        </div>
      </div>
    </div>
  );
};
