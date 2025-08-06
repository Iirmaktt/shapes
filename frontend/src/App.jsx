import React from 'react';
import { WebSocketProvider } from './context/WebSocketContext';
import Header from './components/Header';
import Controls from './components/Controls';
import Canvas from './components/Canvas';

function App() {
  return (
    <WebSocketProvider>
      <div className="app">
        <Header />
        <Controls />
        <main className="main-content">
          <Canvas />
        </main>
      </div>
    </WebSocketProvider>
  );
}

export default App;
