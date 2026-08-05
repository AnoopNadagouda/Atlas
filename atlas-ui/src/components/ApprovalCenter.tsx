import React from 'react';

export const ApprovalCenter: React.FC = () => {
  return (
    <div className="glass-panel p-6 rounded-2xl border border-white/20 bg-slate-900/60 text-white space-y-4">
      <h2 className="text-lg font-bold text-amber-400">✋ Human Approval Center</h2>
      <div className="bg-slate-800/50 p-4 rounded-xl border border-amber-500/30 flex justify-between items-center">
        <div>
          <h4 className="font-semibold text-sm">Approve Executive Summary Copilot Run</h4>
          <p className="text-xs text-slate-400">Workflow: Search & Indexing Pipeline | Step: human-approval</p>
        </div>
        <div className="flex space-x-2">
          <button className="px-4 py-2 bg-emerald-600 hover:bg-emerald-500 rounded-lg text-xs font-semibold">Approve</button>
          <button className="px-4 py-2 bg-rose-600 hover:bg-rose-500 rounded-lg text-xs font-semibold">Reject</button>
        </div>
      </div>
    </div>
  );
};
