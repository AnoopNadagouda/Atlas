import React from 'react';

export const WorkflowGraph: React.FC = () => {
  return (
    <div className="glass-panel p-6 rounded-2xl border border-white/20 bg-slate-950/80 text-white min-h-[250px] flex items-center justify-center">
      <div className="flex items-center space-x-6">
        <div className="p-3 bg-blue-600/30 border border-blue-500/50 rounded-xl text-xs font-semibold text-blue-200">
          🕸️ Crawl Domain
        </div>
        <div className="text-slate-500 font-bold">➔</div>
        <div className="p-3 bg-emerald-600/30 border border-emerald-500/50 rounded-xl text-xs font-semibold text-emerald-200">
          🧹 Clean HTML
        </div>
        <div className="text-slate-500 font-bold">➔</div>
        <div className="p-3 bg-amber-600/30 border border-amber-500/50 rounded-xl text-xs font-semibold text-amber-200">
          👤 Human Approval
        </div>
      </div>
    </div>
  );
};
