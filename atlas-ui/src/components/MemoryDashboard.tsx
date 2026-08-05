import React, { useState, useEffect } from 'react';

export const MemoryDashboard: React.FC = () => {
    const [analytics, setAnalytics] = useState({
        totalMemories: 1420,
        activeMemories: 1100,
        consolidatedMemories: 240,
        decayedMemories: 80,
        averageImportanceScore: 0.78
    });

    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl backdrop-blur-md bg-opacity-80 border border-slate-800 shadow-2xl">
            <div className="flex justify-between items-center mb-6">
                <div>
                    <h2 className="text-2xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-emerald-400">
                        🧠 Enterprise AI Memory Dashboard
                    </h2>
                    <p className="text-slate-400 text-sm">Real-time long-term context, semantic store & memory decay monitoring</p>
                </div>
                <span className="px-3 py-1 bg-emerald-500/20 text-emerald-400 text-xs font-semibold rounded-full border border-emerald-500/30">
                    Port 8088 Active
                </span>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
                <div className="p-4 bg-slate-800/60 rounded-lg border border-slate-700">
                    <p className="text-slate-400 text-xs font-medium">Total Memories</p>
                    <p className="text-3xl font-extrabold text-white mt-1">{analytics.totalMemories.toLocaleString()}</p>
                </div>
                <div className="p-4 bg-slate-800/60 rounded-lg border border-slate-700">
                    <p className="text-slate-400 text-xs font-medium">Active Memories</p>
                    <p className="text-3xl font-extrabold text-blue-400 mt-1">{analytics.activeMemories.toLocaleString()}</p>
                </div>
                <div className="p-4 bg-slate-800/60 rounded-lg border border-slate-700">
                    <p className="text-slate-400 text-xs font-medium">Consolidated</p>
                    <p className="text-3xl font-extrabold text-emerald-400 mt-1">{analytics.consolidatedMemories.toLocaleString()}</p>
                </div>
                <div className="p-4 bg-slate-800/60 rounded-lg border border-slate-700">
                    <p className="text-slate-400 text-xs font-medium">Avg Importance</p>
                    <p className="text-3xl font-extrabold text-purple-400 mt-1">{(analytics.averageImportanceScore * 100).toFixed(0)}%</p>
                </div>
            </div>
        </div>
    );
};
