import React from 'react';
import { DiagramViewer } from '../components/DiagramViewer';
import { Layers } from 'lucide-react';

export const ArchitecturePage: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
          <Layers className="text-accent" size={28} /> Production System Architecture Diagrams
        </h2>
        <p style={{ color: 'var(--text-dim)', marginTop: '6px', marginBottom: 0, fontSize: '0.95rem' }}>
          Detailed pipeline diagrams for microservices, search ranking, Kafka events, DAG workflows, reasoning engines, and AIOS.
        </p>
      </div>

      <DiagramViewer />
    </div>
  );
};
