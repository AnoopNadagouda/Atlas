import React from 'react';

export const MemoryAnalyticsView: React.FC = () => {
    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl border border-slate-800 shadow-xl">
            <h3 className="text-xl font-bold mb-4 text-cyan-400">📊 Memory Platform Analytics</h3>
            <div className="grid grid-cols-2 gap-4">
                <div className="p-4 bg-slate-800/60 rounded-lg">
                    <p className="text-slate-400 text-xs">Ebbinghaus Retention Rate</p>
                    <p className="text-2xl font-bold text-emerald-400 mt-1">94.2%</p>
                </div>
                <div className="p-4 bg-slate-800/60 rounded-lg">
                    <p className="text-slate-400 text-xs">Consolidation Ratio</p>
                    <p className="text-2xl font-bold text-blue-400 mt-1">18.5%</p>
                </div>
            </div>
        </div>
    );
};
