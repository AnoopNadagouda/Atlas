import React from 'react';
import { Activity, Server, Database, Cpu, CheckCircle2 } from 'lucide-react';

export const StatusPage: React.FC = () => {
  const services = [
    { name: 'atlas-config-service', port: 8888, status: 'UP', type: 'Spring Cloud Config' },
    { name: 'atlas-api-gateway', port: 8080, status: 'UP', type: 'Spring Cloud Gateway' },
    { name: 'atlas-search-gateway', port: 8081, status: 'UP', type: 'Search Router & Cache' },
    { name: 'atlas-keyword-search', port: 8082, status: 'UP', type: 'BM25 Inverted Index' },
    { name: 'atlas-crawler-worker', port: 8083, status: 'UP', type: 'Distributed Crawler' },
    { name: 'atlas-index-builder-worker', port: 8084, status: 'UP', type: 'Segment File Writer' },
  ];

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div>
        <h2 style={{ fontSize: '1.75rem', fontWeight: 700 }}>Microservices Cluster Health</h2>
        <p style={{ color: 'var(--text-muted)' }}>Spring Boot Actuator health aggregator status.</p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '16px' }}>
        {services.map((svc) => (
          <div key={svc.name} className="glass-panel" style={{ padding: '20px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
              <h3 style={{ fontSize: '1.1rem', fontWeight: 600 }}>{svc.name}</h3>
              <div style={{ fontSize: '0.8rem', color: 'var(--text-muted)', marginTop: '2px' }}>{svc.type}</div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-dim)', marginTop: '4px' }}>Port: {svc.port}</div>
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '6px' }}>
              <span className="badge badge-success" style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
                <CheckCircle2 size={12} /> {svc.status}
              </span>
              <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Actuator: /actuator/health</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
