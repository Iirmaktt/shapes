// React kütüphanesini ve gerekli hook'ları import et
import React, { memo, useRef, useEffect } from 'react';

/**
 * Tek şekil bileşeni
 * Her bir şekli (daire, kare, üçgen) render eder
 * Level 2 performance optimizasyonları içerir
 */
const Shape = memo(({ shape }) => {
  // DOM elementine direkt erişim için ref (performans optimizasyonu)
  const shapeRef = useRef(null);
  
  /**
   * Pozisyon değişikliklerini dinleyen effect
   * React'ın reconciliation sürecini bypass ederek direkt DOM manipülasyonu yapar
   * Bu, yüksek FPS için kritik performans optimizasyonudur
   */
  useEffect(() => {
    const element = shapeRef.current;
    if (element) {
      // CSS transform kullanarak pozisyon güncelle (GPU hızlandırmalı)
      element.style.transform = `translate(${shape.x}px, ${shape.y}px)`; // x ve y geldiğinde değiştirmeyi çalışıyorux
    }
  }, [shape.x, shape.y]); // Sadece x, y değiştiğinde çalıştır
  
  // Level 2 Performance Açıklamaları:
  // - React.memo: Şekil özellikleri değişmediği sürece yeniden render engelini
  // - Direct DOM manipulation: React'ın virtual DOM'unu bypass eder
  // - useRef: DOM elementine direkt erişim sağlar
  
  /**
   * Şekil tipine göre CSS stilini döndürür
   * Daire, kare, üçgen için farklı stil hesaplamaları yapar
   */
  const getShapeStyle = () => {
    // Tüm şekiller için ortak temel stil
    const baseStyle = {
      width: `${shape.size}px`,
      height: `${shape.size}px`,
      backgroundColor: shape.color,
      position: 'absolute',
      // Performans optimizasyonları:
      transition: 'none',              // CSS geçişlerini devre dışı bırak (daha yumuşak animasyon)
      willChange: shape.isMoving ? 'transform' : 'auto', // GPU katmanına taşı (sadece hareket edenler için)
    };
    
    // Üçgen için özel stil (CSS border trick kullanarak)
    if (shape.type === 'triangle') {
      return {
        ...baseStyle,
        width: 0,                      // Genişliği sıfırla
        height: 0,                     // Yüksekliği sıfırla
        backgroundColor: 'transparent', // Arka planı şeffaf yap
        // CSS border'ları kullanarak üçgen oluştur
        borderLeft: `${shape.size / 2}px solid transparent`,
        borderRight: `${shape.size / 2}px solid transparent`,
        borderBottom: `${shape.size}px solid ${shape.color}`,
      };
    }
    
    // Daire için border-radius ekle
    if (shape.type === 'circle') {
      baseStyle.borderRadius = '50%';
    }
    
    // Kare için temel stil yeterli
    return baseStyle;
  };
  
  return (
    <div
      ref={shapeRef}                    // DOM referansını bağla
      style={getShapeStyle()}           // Hesaplanan stili uygula
      data-moving={shape.isMoving}      // Debug için hareket durumu
    />
  );
}, (prevProps, nextProps) => {
  /**
   * Level 2 Custom comparison function
   * React.memo için özel karşılaştırma fonksiyonu
   * Sadece görsel özellikler değiştiğinde yeniden render eder
   */
  const prev = prevProps.shape;
  const next = nextProps.shape;
  
  // Eğer hiçbir kritik özellik değişmemişse yeniden render etme
  return (
    prev.x === next.x &&                // X pozisyonu aynı mı?
    prev.y === next.y &&                // Y pozisyonu aynı mı?
    prev.isMoving === next.isMoving &&  // Hareket durumu aynı mı?
    prev.color === next.color &&        // Renk aynı mı?
    prev.size === next.size             // Boyut aynı mı?
  );
});

// Debug için bileşen adını ayarla
Shape.displayName = 'Shape';

// Bu bileşeni dışarıya aktar
export default Shape;
