import React from 'react';

export const WorkflowTemplates: React.FC = () => {
  const templates = [
    { title: 'Full Web Search Indexing Pipeline', category: 'SEARCH', steps: 5 },
    { title: 'Multi-Modal PDF OCR & Vectorization', category: 'PARSER', steps: 4 },
    { title: 'AI Code Copilot Repository Audit', category: 'AGENT', steps: 6 }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
      {templates.map((tmpl, idx) => (
        <div key={idx} className="glass-panel p-5 rounded-2xl border border-white/10 bg-slate-900/60 hover:border-blue-500/50 transition">
          <span className="text-[10px] uppercase font-bold tracking-wider px-2 py-0.5 bg-blue-500/20 text-blue-300 rounded">
            {tmpl.category}
          </span>
          <h3 className="font-bold text-sm text-white mt-2">{tmpl.title}</h3>
          <p className="text-xs text-slate-400 mt-1">{tmpl.steps} Steps DAG Template</p>
          <button className="mt-4 w-full py-1.5 bg-blue-600/80 hover:bg-blue-500 rounded-lg text-xs font-semibold text-white">
            Use Template
          </button>
        </div>
      ))}
    </div>
  );
};
