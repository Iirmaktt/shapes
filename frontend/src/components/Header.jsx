import React from 'react';
import { useWebSocket } from '../context/WebSocketContext';

const Header = () => {
  const { isConnected, reconnect } = useWebSocket();
  
  return (
    <div>
      <h1>Shape Movement</h1>
      <div>
        Status: {isConnected ? 'Connected' : 'Disconnected'}
        {!isConnected && (
          <button onClick={reconnect}>Reconnect</button>
        )}
      </div>
    </div>
  );
};

export default Header;
