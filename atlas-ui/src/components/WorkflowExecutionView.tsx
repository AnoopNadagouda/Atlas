import React from 'react';

export const WorkflowExecutionView: React.FC = () => {
  return (
    <div className="glass-panel p-6 rounded-2xl border border-white/20 bg-slate-900/60 text-white space-y-4">
      <div className="flex justify-between items-center">
        <div>
          <span className="text-xs text-slate-400 font-mono">INSTANCE ID: inst-8849-2026</span>
          <h2 className="text-lg font-bold">Enterprise Search & Indexing Workflow</h2>
        </div>
        <div className="flex space-x-2">
          <button className="px-3 py-1.5 bg-amber-600/80 hover:bg-amber-500 rounded-lg text-xs font-semibold">Pause</button>
          <button className="px-3 py-1.5 bg-rose-600/80 hover:bg-rose-500 rounded-lg text-xs font-semibold">Cancel</button>
        </div>
      </div>
    </div>
  );
};
