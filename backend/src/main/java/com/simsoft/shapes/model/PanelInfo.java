package com.simsoft.shapes.model;

/**
 * Panel boyut bilgilerini tutan sınıf
 * İstemciye gönderilecek çizim alanı boyutlarını içerir
 */
public class PanelInfo {
    private double width;  // Panel genişliği (piksel)
    private double height; // Panel yüksekliği (piksel)
    
    /**
     * Varsayılan constructor (JSON deserialization için gerekli)
     */
    public PanelInfo() {}
    
    /**
     * Panel boyutları ile constructor
     * 
     * @param width Panel genişliği (piksel)
     * @param height Panel yüksekliği (piksel)
     */
    public PanelInfo(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    // Getter ve Setter metodları (JSON serialization için gerekli)
    
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}
