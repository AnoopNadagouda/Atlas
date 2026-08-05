import React from 'react';

export const MemorySettings: React.FC = () => {
    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl border border-slate-800 shadow-xl">
            <h3 className="text-xl font-bold mb-4 text-slate-300">⚙️ Memory System Settings</h3>
            <div className="space-y-4">
                <div className="flex justify-between items-center bg-slate-800/40 p-3 rounded-lg">
                    <div>
                        <p className="font-semibold text-sm">Automatic Ebbinghaus Memory Decay</p>
                        <p className="text-xs text-slate-400">Decay unused memories based on logarithmic forgetting curve</p>
                    </div>
                    <input type="checkbox" defaultChecked className="toggle" />
                </div>
                <div className="flex justify-between items-center bg-slate-800/40 p-3 rounded-lg">
                    <div>
                        <p className="font-semibold text-sm">Background Memory Consolidation</p>
                        <p className="text-xs text-slate-400">Summarize short-term memories into long-term knowledge</p>
                    </div>
                    <input type="checkbox" defaultChecked className="toggle" />
                </div>
            </div>
        </div>
    );
};
