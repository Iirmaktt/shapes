import React from 'react';
import { useWebSocket } from '../context/WebSocketContext';

const Header = () => {
  const { isConnected, reconnect } = useWebSocket();
  
  return (
    <div className="header">
      <h1>Real-time Shape Movement Visualization</h1>
      <div className="connection-status">
        <div className={`status-indicator ${isConnected ? 'connected' : ''}`}></div>
        <span>
          {isConnected ? 'Connected to WebSocket' : 'Disconnected'}
        </span>
        {!isConnected && (
          <button 
            onClick={reconnect}
            style={{
              marginLeft: '1rem',
              padding: '0.25rem 0.5rem',
              fontSize: '0.8rem',
              backgroundColor: 'rgba(255,255,255,0.2)',
              color: 'white',
              border: '1px solid rgba(255,255,255,0.3)',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Reconnect
          </button>
        )}
      </div>
    </div>
  );
};

export default Header;
