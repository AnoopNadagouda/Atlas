import React, { useState } from 'react';

export const WorkflowDesigner: React.FC = () => {
  const [workflowName, setWorkflowName] = useState('New Enterprise AI Workflow');
  const [yamlContent, setYamlContent] = useState(`name: Enterprise Search & Indexing Workflow
version: 1.0.0
steps:
  - stepId: crawl-web
    name: Crawl Domain
    type: CONNECTOR_EXECUTION
  - stepId: parse-clean
    name: Parse HTML Content
    type: SEQUENTIAL
    dependsOn: ["crawl-web"]
  - stepId: agent-summary
    name: Generate Summary Copilot
    type: AGENT_EXECUTION
    agentId: agent-001
    dependsOn: ["parse-clean"]
  - stepId: human-approval
    name: Approve Executive Summary
    type: HUMAN_APPROVAL
    approvalRole: WORKFLOW_ADMIN
    dependsOn: ["agent-summary"]
`);

  return (
    <div className="glass-panel p-6 rounded-2xl border border-white/20 backdrop-blur-xl bg-slate-900/60 text-white space-y-4">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-bold bg-gradient-to-r from-blue-400 to-indigo-300 bg-clip-text text-transparent">
          ⚡ Atlas Workflow Designer
        </h2>
        <button className="px-4 py-2 bg-blue-600 hover:bg-blue-500 rounded-lg text-sm font-semibold transition">
          Validate & Deploy DAG
        </button>
      </div>

      <div>
        <label className="text-xs font-semibold text-slate-400">Workflow Name</label>
        <input 
          type="text" 
          value={workflowName}
          onChange={(e) => setWorkflowName(e.target.value)}
          className="w-full mt-1 bg-slate-800/80 border border-slate-700 rounded-lg px-3 py-2 text-sm focus:outline-none focus:border-blue-500"
        />
      </div>

      <div>
        <label className="text-xs font-semibold text-slate-400">DAG Topology Definition (YAML / JSON)</label>
        <textarea
          rows={12}
          value={yamlContent}
          onChange={(e) => setYamlContent(e.target.value)}
          className="w-full mt-1 font-mono text-xs bg-slate-950/90 border border-slate-800 rounded-lg p-3 text-emerald-400 focus:outline-none"
        />
      </div>
    </div>
  );
};
