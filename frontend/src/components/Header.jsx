// React kütüphanesini import et
import React from 'react';
// WebSocket context hook'unu import et
import { useWebSocket } from '../context/WebSocketContext';

/**
 * Başlık bileşeni
 * Uygulama başlığını ve WebSocket bağlantı durumunu gösterir
 */
const Header = () => {
  // WebSocket context'inden bağlantı durumu ve yeniden bağlanma fonksiyonunu al
  const { isConnected, reconnect } = useWebSocket();
  
  return (
    <div>
      {/* Ana başlık */}
      <h1>Shape Movement</h1>
      
      {/* Bağlantı durumu bölümü */}
      <div>
        {/* Bağlantı durumunu göster - bağlıysa "Connected", değilse "Disconnected" */}
        Status: {isConnected ? 'Connected' : 'Disconnected'}
        
        {/* Eğer bağlantı yoksa yeniden bağlanma butonu göster */}
        {!isConnected && (
          <button onClick={reconnect}>Reconnect</button>
        )}
      </div>
    </div>
  );
};

// Bu bileşeni dışarıya aktar
export default Header;
