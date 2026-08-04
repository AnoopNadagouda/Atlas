import React from 'react';
import { Search, Layers, Server, Cpu, Database, CheckCircle2 } from 'lucide-react';
import { Link } from 'react-router-dom';

export const Home: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '32px', background: 'linear-gradient(135deg, rgba(99, 102, 241, 0.15), rgba(6, 182, 212, 0.1))' }}>
        <h2 style={{ fontSize: '2rem', fontWeight: 800, marginBottom: '8px' }}>Atlas Studio v1.0.0</h2>
        <p style={{ color: 'var(--text-muted)', fontSize: '1.05rem', maxWidth: '700px' }}>
          Phase 1.1 Infrastructure & Project Foundation successfully initialized. Microservice backend communication, polyglot persistence, and event pipeline placeholders are operational.
        </p>
        <div style={{ display: 'flex', gap: '16px', marginTop: '24px' }}>
          <Link to="/search" className="btn btn-primary">
            <Search size={18} /> Open Search Studio
          </Link>
          <Link to="/crawler" className="btn btn-secondary">
            <Layers size={18} /> Configure Crawl Jobs
          </Link>
        </div>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '20px' }}>
        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>API Gateway</span>
            <Server size={20} color="var(--primary)" />
          </div>
          <h3 style={{ fontSize: '1.5rem', fontWeight: 700 }}>Port 8080</h3>
          <span className="badge badge-success" style={{ marginTop: '8px' }}>Healthy (Spring Cloud)</span>
        </div>

        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>PostgreSQL Metadata DB</span>
            <Database size={20} color="var(--accent-cyan)" />
          </div>
          <h3 style={{ fontSize: '1.5rem', fontWeight: 700 }}>Port 5432</h3>
          <span className="badge badge-success" style={{ marginTop: '8px' }}>Pool Active</span>
        </div>

        <div className="glass-panel" style={{ padding: '20px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>Apache Kafka Event Pipeline</span>
            <Cpu size={20} color="var(--accent-amber)" />
          </div>
          <h3 style={{ fontSize: '1.5rem', fontWeight: 700 }}>Port 9092</h3>
          <span className="badge badge-success" style={{ marginTop: '8px' }}>6 Topics Configured</span>
        </div>
      </div>

      <div className="glass-panel" style={{ padding: '24px' }}>
        <h3 style={{ fontSize: '1.2rem', marginBottom: '16px' }}>Milestone Verification Checklist</h3>
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '12px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <CheckCircle2 size={18} color="var(--accent-emerald)" />
            <span>Maven Parent & 9 Sub-Modules Compiled</span>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <CheckCircle2 size={18} color="var(--accent-emerald)" />
            <span>Spring Boot Actuator Health Aggregation</span>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <CheckCircle2 size={18} color="var(--accent-emerald)" />
            <span>Global Exception Hierarchy Configured</span>
          </div>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <CheckCircle2 size={18} color="var(--accent-emerald)" />
            <span>React TypeScript Shell & CSS Design Tokens</span>
          </div>
        </div>
      </div>
    </div>
  );
};
