import React, { useState } from 'react';
import { BookOpen, Server, Cpu, Shield, Zap, Terminal, CheckCircle2, Play } from 'lucide-react';
import { CodeBlock } from '../components/CodeBlock';

export const DocsPage: React.FC = () => {
  const [section, setSection] = useState('getting-started');

  const sections: Record<string, { title: string; content: React.ReactNode }> = {
    'getting-started': {
      title: 'Getting Started with Atlas v6.0.0',
      content: (
        <div>
          <p style={{ fontSize: '1rem', lineHeight: 1.6, color: '#cbd5e1' }}>
            Atlas is an open-source, enterprise-grade distributed search engine and Autonomous AI Operating System (AIOS).
          </p>
          <h4 style={{ color: '#60a5fa', marginTop: '24px' }}>Prerequisites</h4>
          <ul style={{ color: 'var(--text-dim)', lineHeight: 1.8 }}>
            <li>Java 21 OpenJDK / Eclipse Temurin</li>
            <li>Apache Maven 3.9+</li>
            <li>Docker & Docker Compose (v2.20+)</li>
            <li>Node.js 18+ and npm for React UI</li>
          </ul>

          <h4 style={{ color: '#60a5fa', marginTop: '24px' }}>Quick Start in 3 Commands</h4>
          <CodeBlock code={`# 1. Clone the open-source repository
git clone https://github.com/AnoopNadagouda/Atlas.git
cd Atlas

# 2. Build Java microservices reactor
mvn clean package -DskipTests

# 3. Launch full stack via Docker Compose
docker-compose up -d`} language="bash" filename="Terminal" />
        </div>
      )
    },
    'installation': {
      title: 'Installation & Deployment Guide',
      content: (
        <div>
          <p style={{ fontSize: '1rem', lineHeight: 1.6, color: '#cbd5e1' }}>
            Atlas can be deployed locally via Maven/Docker, or to Kubernetes cluster using Helm charts.
          </p>
          <h4 style={{ color: '#60a5fa', marginTop: '24px' }}>Kubernetes Helm Deployment</h4>
          <CodeBlock code={`# Install Atlas Helm Chart on Kubernetes Cluster
helm repo add atlas https://charts.atlas-search.io
helm install atlas-release atlas/atlas --namespace atlas-system --create-namespace`} language="bash" filename="Helm Terminal" />
        </div>
      )
    },
    'microservices': {
      title: 'Microservices Architecture',
      content: (
        <div>
          <p style={{ fontSize: '1rem', lineHeight: 1.6, color: '#cbd5e1' }}>
            Atlas consists of 12 decoupled Spring Boot services orchestrated via Kafka and Spring Cloud API Gateway:
          </p>
          <ul style={{ color: 'var(--text-dim)', lineHeight: 1.8 }}>
            <li><strong>Port 8080</strong>: API Gateway (`atlas-api-gateway`)</li>
            <li><strong>Port 8081</strong>: Search Gateway (`atlas-search-gateway`)</li>
            <li><strong>Port 8082</strong>: Keyword Search (`atlas-keyword-search`)</li>
            <li><strong>Port 8083</strong>: Crawl Worker (`atlas-crawler-worker`)</li>
            <li><strong>Port 8084</strong>: Index Builder Worker (`atlas-index-builder-worker`)</li>
            <li><strong>Port 8085</strong>: HTML Parser Service (`atlas-parser-service`)</li>
            <li><strong>Port 8086</strong>: Agent Core Service (`atlas-agent-service`)</li>
            <li><strong>Port 8087</strong>: Workflow Automation (`atlas-workflow-service`)</li>
            <li><strong>Port 8088</strong>: AI Memory Service (`atlas-memory-service`)</li>
            <li><strong>Port 8089</strong>: Reasoning Engine (`atlas-reasoning-service`)</li>
            <li><strong>Port 8090</strong>: AIOS Orchestrator Service (`atlas-orchestrator-service`)</li>
          </ul>
        </div>
      )
    },
    'aios': {
      title: 'Atlas AI Operating System (AIOS)',
      content: (
        <div>
          <p style={{ fontSize: '1rem', lineHeight: 1.6, color: '#cbd5e1' }}>
            The AIOS Orchestrator (Port 8090) coordinates missions, policy enforcement, multi-agent teams, and self-healing automatic recovery.
          </p>
          <CodeBlock code={`// Example Java Client Mission Formulation
MissionCreateRequest request = new MissionCreateRequest();
request.setTitle("Enterprise Knowledge Ingestion");
request.setPriority(5);

Mission mission = orchestratorClient.createMission("tenant-acme", request);
orchestratorClient.executeMission(mission.getId());`} language="java" filename="AIOSClient.java" />
        </div>
      )
    },
    'benchmarks': {
      title: 'Performance Benchmarks & Scaling',
      content: (
        <div>
          <p style={{ fontSize: '1rem', lineHeight: 1.6, color: '#cbd5e1' }}>
            Extensive benchmark results over 1 Billion synthetic and web documents:
          </p>
          <ul style={{ color: 'var(--text-dim)', lineHeight: 1.8 }}>
            <li><strong>BM25 Keyword Search Latency</strong>: P50 = 4ms, P99 = 14ms</li>
            <li><strong>Vector Hybrid Rescoring Latency</strong>: P99 = 28ms</li>
            <li><strong>Mission Dispatch Overhead</strong>: &lt; 15ms</li>
            <li><strong>Kafka Throughput</strong>: 120,000 events/sec per partition</li>
          </ul>
        </div>
      )
    },
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
      <div className="glass-panel" style={{ padding: '24px' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 800, margin: 0, display: 'flex', alignItems: 'center', gap: '12px' }}>
          <BookOpen className="text-accent" size={28} /> Atlas v6.0.0 Documentation Hub
        </h2>
        <p style={{ color: 'var(--text-dim)', marginTop: '6px', marginBottom: 0, fontSize: '0.95rem' }}>
          Comprehensive Open-Source Developer Guides, Architecture Manuals & Deployment Docs
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: '20px' }}>
        {/* Navigation Sidebar */}
        <div className="glass-panel" style={{ padding: '16px', display: 'flex', flexDirection: 'column', gap: '6px' }}>
          {Object.keys(sections).map(key => (
            <button
              key={key}
              onClick={() => setSection(key)}
              className={`btn ${section === key ? 'btn-primary' : 'btn-secondary'}`}
              style={{ justifyContent: 'flex-start', textAlign: 'left', padding: '10px 14px', fontSize: '0.875rem' }}
            >
              {sections[key].title}
            </button>
          ))}
        </div>

        {/* Main Content Pane */}
        <div className="glass-panel" style={{ padding: '32px', background: 'rgba(15, 23, 42, 0.9)', border: '1px solid rgba(255, 255, 255, 0.12)' }}>
          <h3 style={{ fontSize: '1.35rem', fontWeight: 700, margin: '0 0 16px', color: '#60a5fa' }}>
            {sections[section].title}
          </h3>
          {sections[section].content}
        </div>
      </div>
    </div>
  );
};
