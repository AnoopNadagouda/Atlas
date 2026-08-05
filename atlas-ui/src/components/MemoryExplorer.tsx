import React, { useState } from 'react';

export const MemoryExplorer: React.FC = () => {
    const [memories] = useState([
        { id: 'mem-1', key: 'pref-theme', type: 'SHORT_TERM', importance: 0.8, content: 'User prefers dark glassmorphic layout theme' },
        { id: 'mem-2', key: 'user-query-pattern', type: 'SEMANTIC', importance: 0.9, content: 'User searches frequently for Java 21 microservices architecture' },
        { id: 'mem-3', key: 'workflow-summary-001', type: 'LONG_TERM', importance: 0.95, content: 'Executed automated index rebuild workflow successfully' }
    ]);

    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl border border-slate-800 shadow-xl">
            <h3 className="text-xl font-bold mb-4 text-blue-400">🔍 Memory Explorer</h3>
            <div className="space-y-3">
                {memories.map(m => (
                    <div key={m.id} className="p-4 bg-slate-800/80 rounded-lg border border-slate-700 flex justify-between items-center">
                        <div>
                            <span className="px-2 py-0.5 text-xs rounded bg-blue-500/20 text-blue-300 font-mono mr-2">{m.type}</span>
                            <span className="font-semibold text-slate-200">{m.key}</span>
                            <p className="text-slate-400 text-sm mt-1">{m.content}</p>
                        </div>
                        <span className="text-xs font-mono text-purple-400">Importance: {(m.importance * 100).toFixed(0)}%</span>
                    </div>
                ))}
            </div>
        </div>
    );
};
