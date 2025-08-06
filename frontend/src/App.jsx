// React kütüphanesini import et
import React from 'react';
// WebSocket bağlantısı için Context Provider'ı import et
import { WebSocketProvider } from './context/WebSocketContext';
// Ana bileşenleri import et
import Header from './components/Header';
import Controls from './components/Controls';
import Canvas from './components/Canvas';

/**
 * Ana uygulama bileşeni
 * Tüm diğer bileşenleri WebSocket context'i içinde sarmallar
 */
function App() {
  return (
    // WebSocket bağlantısını tüm alt bileşenlerle paylaş
    <WebSocketProvider>
      <div>
        {/* Başlık ve bağlantı durumu */}
        <Header />
        {/* Şekil sayılarını kontrol etme arayüzü */}
        <Controls />
        {/* Ana çizim alanı - şekillerin gösterildiği yer */}
        <Canvas />
      </div>
    </WebSocketProvider>
  );
}

// Bu bileşeni dışarıya aktar
export default App;
