// React kütüphanesini ve performance hook'larını import et
import React, { memo, useMemo } from 'react';
// WebSocket context hook'unu import et
import { useWebSocket } from '../context/WebSocketContext';
// Tek şekil bileşenini import et
import Shape from './Shape';

/**
 * Canvas bileşeni - Ana çizim alanı
 * Tüm şekilleri render eder ve görüntüler
 * Performance optimizasyonu için React.memo kullanır
 */
const Canvas = memo(() => {
  // WebSocket context'inden şekiller, panel bilgileri ve başlatma durumunu al
  const { shapes, panelInfo, isInitialized, isConnected } = useWebSocket();
  
  
  const renderedShapes = useMemo(() => {
    return shapes.map(shape => (
      // Her şekil için benzersiz key ve şekil verisini geç
      <Shape key={shape.id} shape={shape} />
    ));
  }, [shapes]); // Sadece shapes değiştiğinde yeniden hesapla
  
 
  if (!isInitialized || !isConnected || !panelInfo) {
    return <div>Loading panel info...</div>;
  }
  
  /**
   * Canvas container'ının stil tanımları
   * Sunucudan gelen panel boyutlarını kullanır
   */
  const canvasStyle = {
    width: `${panelInfo.width}px`,   // Panel genişliği (1000px)
    height: `${panelInfo.height}px`, // Panel yüksekliği (800px)
    position: 'relative',            // Şekillerin absolute pozisyonlanması için
    border: '1px solid black',       // Basit sınır
    overflow: 'hidden',              // Taşan şekilleri gizle
  };
  
  return (
    <div>
      {/* Ana çizim alanı */}
      <div className="canvas" style={canvasStyle}>
        {/* Tüm şekilleri render et */}
        {renderedShapes}
      </div>
      
      {/* Panel boyut bilgisi */}
      <div>Panel: {panelInfo.width} × {panelInfo.height}px</div>
    </div>
  );
});

// Debug için bileşen adını ayarla
Canvas.displayName = 'Canvas';

// Bu bileşeni dışarıya aktar
export default Canvas;
