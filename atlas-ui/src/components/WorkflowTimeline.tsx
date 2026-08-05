import React from 'react';

export const WorkflowTimeline: React.FC = () => {
  const events = [
    { step: 'crawl-web', status: 'COMPLETED', duration: '240ms', time: '18:42:01' },
    { step: 'parse-clean', status: 'COMPLETED', duration: '110ms', time: '18:42:02' },
    { step: 'agent-summary', status: 'WAITING_FOR_APPROVAL', duration: 'In Progress', time: '18:42:03' }
  ];

  return (
    <div className="glass-panel p-4 rounded-xl border border-white/10 bg-slate-900/60 text-white space-y-3">
      <h3 className="text-sm font-semibold text-slate-300">Execution Timeline</h3>
      <div className="space-y-2">
        {events.map((evt, idx) => (
          <div key={idx} className="flex justify-between items-center bg-slate-800/40 p-2.5 rounded-lg text-xs">
            <span className="font-mono text-blue-300">{evt.step}</span>
            <span className={`px-2 py-0.5 rounded font-semibold ${evt.status === 'COMPLETED' ? 'bg-emerald-500/20 text-emerald-300' : 'bg-amber-500/20 text-amber-300'}`}>
              {evt.status}
            </span>
            <span className="text-slate-400">{evt.duration}</span>
            <span className="text-slate-500">{evt.time}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
