import React from 'react';
import { WebSocketProvider } from './context/WebSocketContext';
import Header from './components/Header';
import Controls from './components/Controls';
import Canvas from './components/Canvas';

function App() {
  return (
    <WebSocketProvider>
      <div>
        <Header />
        <Controls />
        <Canvas />
      </div>
    </WebSocketProvider>
  );
}

export default App;
