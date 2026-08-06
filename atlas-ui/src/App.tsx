import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Navbar } from './components/Navbar';
import { Sidebar } from './components/Sidebar';
import { Home } from './pages/Home';
import { SearchPage } from './pages/SearchPage';
import { DocumentExplorer } from './pages/DocumentExplorer';
import { ConnectorDashboard } from './pages/ConnectorDashboard';
import { FederatedSearchExplorer } from './pages/FederatedSearchExplorer';
import { CrawlerPage } from './pages/CrawlerPage';
import { StatusPage } from './pages/StatusPage';
import { MemoryPage } from './pages/MemoryPage';
import { ReasoningPage } from './pages/ReasoningPage';
import { AIOSPage } from './pages/AIOSPage';

const App: React.FC = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      <div style={{ display: 'flex', flex: 1 }}>
        <Sidebar />
        <main style={{ flex: 1, padding: '32px', maxWidth: '1400px' }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/aios" element={<AIOSPage />} />
            <Route path="/search" element={<SearchPage />} />
            <Route path="/federated-search" element={<FederatedSearchExplorer />} />
            <Route path="/connectors" element={<ConnectorDashboard />} />
            <Route path="/documents" element={<DocumentExplorer />} />
            <Route path="/crawler" element={<CrawlerPage />} />
            <Route path="/status" element={<StatusPage />} />
            <Route path="/memory" element={<MemoryPage />} />
            <Route path="/reasoning" element={<ReasoningPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
};


export default App;
