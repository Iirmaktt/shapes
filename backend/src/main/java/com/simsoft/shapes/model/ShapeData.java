package com.simsoft.shapes.model;

import java.util.List;

/**
 * WebSocket ile gönderilen şekil verisi için wrapper sınıfı
 * Tüm şekilleri ve mesaj tipini içerir
 */
public class ShapeData {
    private List<Shape> shapes;      // Gönderilecek şekiller listesi
    private String messageType = "shapes"; // Mesaj tipi (istemci tarafında ayırt etmek için)
    
    /**
     * Varsayılan constructor (JSON deserialization için gerekli)
     */
    public ShapeData() {}
    
    /**
     * Şekil listesi ile constructor
     * 
     * @param shapes Gönderilecek şekiller listesi
     */
    public ShapeData(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
    // Getter ve Setter metodları (JSON serialization için gerekli)
    
    public List<Shape> getShapes() { return shapes; }
    public void setShapes(List<Shape> shapes) { this.shapes = shapes; }
    
    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
}
