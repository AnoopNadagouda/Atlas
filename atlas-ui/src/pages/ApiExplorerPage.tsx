import React from 'react';
import { InteractiveApiRunner } from '../components/InteractiveApiRunner';
import { Code, Terminal } from 'lucide-react';

export const ApiExplorerPage: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
          <Terminal className="text-accent" size={28} /> Interactive API Explorer & OpenAPI Sandbox
        </h2>
        <p style={{ color: 'var(--text-dim)', marginTop: '6px', marginBottom: 0, fontSize: '0.95rem' }}>
          Test REST APIs live across Search, Agent Core, Workflow, Memory, Reasoning, and AIOS Orchestrator.
        </p>
      </div>

      <InteractiveApiRunner />
    </div>
  );
};
