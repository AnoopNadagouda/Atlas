import React from 'react';
import { Activity } from 'lucide-react';


export const PlatformHealth: React.FC = () => {
  const modules = [
    { name: 'atlas-orchestrator-service', type: 'Microservice', port: 8090, status: 'UP', latency: '4ms' },
    { name: 'atlas-agent-service', type: 'Microservice', port: 8086, status: 'UP', latency: '8ms' },
    { name: 'atlas-workflow-service', type: 'Microservice', port: 8087, status: 'UP', latency: '6ms' },
    { name: 'atlas-memory-service', type: 'Microservice', port: 8088, status: 'UP', latency: '12ms' },
    { name: 'atlas-reasoning-service', type: 'Microservice', port: 8089, status: 'UP', latency: '15ms' },
    { name: 'atlas-search-gateway', type: 'Microservice', port: 8081, status: 'UP', latency: '3ms' },
    { name: 'atlas-keyword-search', type: 'Microservice', port: 8082, status: 'UP', latency: '7ms' },
    { name: 'atlas-crawler-worker', type: 'Microservice', port: 8083, status: 'UP', latency: '10ms' },
    { name: 'atlas-parser-service', type: 'Microservice', port: 8085, status: 'UP', latency: '9ms' },
    { name: 'atlas-index-builder-worker', type: 'Microservice', port: 8084, status: 'UP', latency: '11ms' },
    { name: 'atlas-api-gateway', type: 'Gateway', port: 8080, status: 'UP', latency: '2ms' },
    { name: 'atlas-config-service', type: 'Config Server', port: 8888, status: 'UP', latency: '3ms' },
    { name: 'PostgreSQL Database', type: 'Infrastructure', port: 5432, status: 'UP', latency: '1ms' },
    { name: 'Redis Cache Cluster', type: 'Infrastructure', port: 6379, status: 'UP', latency: '0.5ms' },
    { name: 'Apache Kafka Event Bus', type: 'Infrastructure', port: 9092, status: 'UP', latency: '2ms' },
  ];

  return (
    <div className="glass-panel" style={{ padding: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, margin: 0, display: 'flex', alignItems: 'center', gap: '10px' }}>
            <Activity className="text-accent" size={24} /> Platform Health Matrix (19 Modules)
          </h3>
          <p style={{ color: 'var(--text-dim)', marginTop: '4px', marginBottom: 0, fontSize: '0.9rem' }}>
            Aggregated Health Status, Response Latency & Actuator Health Indicators
          </p>
        </div>
        <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.2)', color: '#4ade80', padding: '8px 16px', fontSize: '0.85rem' }}>
          100% HEALTHY
        </span>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '16px' }}>
        {modules.map((m) => (
          <div key={m.name} style={{ padding: '16px', borderRadius: '8px', background: 'rgba(255, 255, 255, 0.03)', border: '1px solid rgba(255, 255, 255, 0.08)' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <div style={{ fontWeight: 600, fontSize: '0.95rem' }}>{m.name}</div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-dim)', marginTop: '2px' }}>
                  {m.type} • Port :{m.port}
                </div>
              </div>
              <span className="badge" style={{ backgroundColor: 'rgba(34, 197, 94, 0.15)', color: '#4ade80', fontSize: '0.75rem' }}>
                {m.status}
              </span>
            </div>
            <div style={{ marginTop: '12px', fontSize: '0.8rem', color: 'var(--text-dim)', display: 'flex', justifyContent: 'space-between' }}>
              <span>Actuator Ping:</span>
              <span style={{ color: '#60a5fa' }}>{m.latency}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
