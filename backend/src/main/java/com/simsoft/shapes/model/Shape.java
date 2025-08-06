package com.simsoft.shapes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Şekil modeli
 * Her bir şeklin özelliklerini ve hareketini yöneten sınıf
 */
public class Shape {
    private String id;          // Şeklin benzersiz kimliği
    private ShapeType type;     // Şekil tipi (daire, kare, üçgen)
    private double x;           // X koordinatı (yatay konum)
    private double y;           // Y koordinatı (dikey konum)
    private double velocityX;   // X ekseni hızı (piksel/frame)
    private double velocityY;   // Y ekseni hızı (piksel/frame)
    private double size;        // Şeklin boyutu (piksel)
    private String color;       // Şeklin rengi (HEX format)
    private boolean isMoving;   // Şeklin hareket edip etmediği
    
    /**
     * Varsayılan constructor (JSON deserializasyon için gerekli)
     */
    public Shape() {}
    
    /**
     * Şekil oluşturucu constructor
     * 
     * @param id Benzersiz kimlik
     * @param type Şekil tipi
     * @param x Başlangıç X pozisyonu
     * @param y Başlangıç Y pozisyonu
     * @param size Şekil boyutu
     * @param color Şekil rengi
     */
    public Shape(String id, ShapeType type, double x, double y, double size, String color) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.isMoving = false; // Başlangıçta durgun
        
        // Rastgele hareket vektörü oluştur
        double speed = Math.random() * 2 + 1; // 1-3 arası hız
        double angle = Math.random() * 2 * Math.PI; // 0-360 derece açı
        this.velocityX = Math.cos(angle) * speed; // Yatay hız bileşeni
        this.velocityY = Math.sin(angle) * speed; // Dikey hız bileşeni
    }
    
    // Getter ve Setter metodları (JSON serialization için gerekli)
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public ShapeType getType() { return type; }
    public void setType(ShapeType type) { this.type = type; }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    public double getVelocityX() { return velocityX; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    
    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    
    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    /**
     * JSON serialization için özel isim kullan
     * JavaScript'te camelCase uyumluluğu için
     */
    @JsonProperty("isMoving")
    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { this.isMoving = moving; }
    
    /**
     * Şekli hareket ettiren ana metod
     * Her frame'de çağrılır ve fizik simülasyonu yapar
     * 
     * @param panelWidth Panel genişliği (sınır kontrolü için)
     * @param panelHeight Panel yüksekliği (sınır kontrolü için)
     */
    public void move(double panelWidth, double panelHeight) {
        if (!isMoving) return; // Eğer hareket etmiyorsa hiçbir şey yapma
        
        // Pozisyonu hız vektörüne göre güncelle
        x += velocityX; // Yeni X koordinatı
        y += velocityY; // Yeni Y koordinatı
        
        // Sınır çarpışma kontrolü ve yansıma (sol/sağ duvarlar)
        if (x <= 0 || x >= panelWidth - size) {
            velocityX = -velocityX; // Yatay hızı tersine çevir (yansıma)
            // Şekli sınırlar içinde tut
            x = Math.max(0, Math.min(x, panelWidth - size));
        }
        
        // Sınır çarpışma kontrolü ve yansıma (üst/alt duvarlar)
        if (y <= 0 || y >= panelHeight - size) {
            velocityY = -velocityY; // Dikey hızı tersine çevir (yansıma)
            // Şekli sınırlar içinde tut
            y = Math.max(0, Math.min(y, panelHeight - size));
        }
    }
}
