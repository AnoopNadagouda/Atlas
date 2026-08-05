import React, { useState } from 'react';

export const MemorySearchPanel: React.FC = () => {
    const [query, setQuery] = useState('');

    return (
        <div className="p-6 bg-slate-900 text-white rounded-xl border border-slate-800 shadow-xl">
            <h3 className="text-xl font-bold mb-4 text-blue-400">🔎 Semantic Memory Search</h3>
            <div className="flex gap-2 mb-4">
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Search memories by natural language concept or query..."
                    className="flex-1 bg-slate-800 border border-slate-700 rounded-lg px-4 py-2 text-white placeholder-slate-500 focus:outline-none focus:border-blue-500"
                />
                <button className="bg-blue-600 hover:bg-blue-500 text-white px-6 py-2 rounded-lg font-semibold transition-all">
                    Search
                </button>
            </div>
        </div>
    );
};
