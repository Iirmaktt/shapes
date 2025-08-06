// React kütüphanesini ve useState hook'unu import et
import React, { useState } from 'react';
// WebSocket context hook'unu import et
import { useWebSocket } from '../context/WebSocketContext';

/**
 * Kontrol bileşeni
 * Kullanıcının şekil sayılarını ayarlamasına izin verir
 */
const Controls = () => {
  // WebSocket context'inden gerekli fonksiyonları ve verileri al
  const { updateShapes, stats, isInitialized } = useWebSocket();
  
  // Yerel state - kullanıcının girdiği şekil sayıları
  const [circles, setCircles] = useState(3);     // Daire sayısı
  const [squares, setSquares] = useState(3);     // Kare sayısı  
  const [triangles, setTriangles] = useState(3); // Üçgen sayısı
  const [isUpdating, setIsUpdating] = useState(false); // API çağrısı devam ediyor mu?
  
  /**
   * Şekilleri güncelleme fonksiyonu
   * REST API çağrısı yapar ve yeni şekil sayılarını gönderir
   */
  const handleUpdateShapes = async () => {
    setIsUpdating(true); // Güncelleme başladı
    try {
      // WebSocket context'indeki updateShapes fonksiyonunu çağır
      await updateShapes(circles, squares, triangles);
    } catch (error) {
      // Hata durumunda konsola yazdır
      console.error('Failed to update shapes:', error);
    } finally {
      setIsUpdating(false); // Güncelleme bitti (başarılı ya da başarısız)
    }
  };
  
  // Eğer panel bilgileri henüz gelmemişse yükleme mesajı göster
  if (!isInitialized) {
    return <div>Loading controls...</div>;
  }
  
  return (
    <div>
      {/* Daire sayısı kontrolü */}
      <label>
        Circles: 
        <input 
          type="number" 
          min="0" 
          max="50" 
          value={circles} 
          onChange={(e) => setCircles(parseInt(e.target.value) || 0)} 
        />
      </label>
      
      {/* Kare sayısı kontrolü */}
      <label>
        Squares: 
        <input 
          type="number" 
          min="0" 
          max="50" 
          value={squares} 
          onChange={(e) => setSquares(parseInt(e.target.value) || 0)} 
        />
      </label>
      
      {/* Üçgen sayısı kontrolü */}
      <label>
        Triangles: 
        <input 
          type="number" 
          min="0" 
          max="50" 
          value={triangles} 
          onChange={(e) => setTriangles(parseInt(e.target.value) || 0)} 
        />
      </label>
      
      {/* Güncelleme butonu - API çağrısı sırasında devre dışı */}
      <button onClick={handleUpdateShapes} disabled={isUpdating}>
        {isUpdating ? 'Updating...' : 'Update Shapes'}
      </button>
      
      {/* İstatistik bilgileri */}
      <div>
        Total: {stats.total} | Moving: {stats.moving} | Static: {stats.total - stats.moving}
      </div>
    </div>
  );
};

// Bu bileşeni dışarıya aktar
export default Controls;
