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
import { DocsPage } from './pages/DocsPage';
import { ApiExplorerPage } from './pages/ApiExplorerPage';
import { ArchitecturePage } from './pages/ArchitecturePage';
import { ScreenshotsPage } from './pages/ScreenshotsPage';
import { BlogPage } from './pages/BlogPage';
import { CommandPalette } from './components/CommandPalette';

const App: React.FC = () => {
  const [cmdOpen, setCmdOpen] = React.useState(false);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      <CommandPalette isOpen={cmdOpen} onClose={() => setCmdOpen(false)} />
      <div style={{ display: 'flex', flex: 1 }}>
        <Sidebar />
        <main style={{ flex: 1, padding: '32px', maxWidth: '1400px' }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/aios" element={<AIOSPage />} />
            <Route path="/docs" element={<DocsPage />} />
            <Route path="/api-explorer" element={<ApiExplorerPage />} />
            <Route path="/architecture" element={<ArchitecturePage />} />
            <Route path="/screenshots" element={<ScreenshotsPage />} />
            <Route path="/blog" element={<BlogPage />} />
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
