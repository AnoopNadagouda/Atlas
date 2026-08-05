import React from 'react';

export const MemoryTimeline: React.FC = () => {
    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl border border-slate-800 shadow-xl">
            <h3 className="text-xl font-bold mb-4 text-amber-400">⏳ Memory Lifecycle Timeline</h3>
            <div className="border-l-2 border-slate-700 pl-4 space-y-4 font-mono text-sm">
                <div>
                    <span className="text-xs text-slate-500">18:45:10</span>
                    <p className="text-slate-300">MemoryCreated: Key 'pref-theme' created (SHORT_TERM)</p>
                </div>
                <div>
                    <span className="text-xs text-slate-500">18:46:20</span>
                    <p className="text-slate-300">MemoryConsolidated: 5 short-term memories merged into LONG_TERM summary</p>
                </div>
            </div>
        </div>
    );
};
