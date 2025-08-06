package com.simsoft.shapes.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Şekil tiplerini tanımlayan enum
 * JSON serialization için string değerleri içerir
 */
public enum ShapeType {
    CIRCLE("circle"),      // Daire şekli - JSON'da "circle" olarak temsil edilir
    SQUARE("square"),      // Kare şekli - JSON'da "square" olarak temsil edilir
    TRIANGLE("triangle");  // Üçgen şekli - JSON'da "triangle" olarak temsil edilir
    
    private final String value; // JSON'da kullanılacak string değer
    
    /**
     * Enum constructor
     * Her enum değeri için string karşılığını ayarlar
     * 
     * @param value JSON serialization'da kullanılacak string
     */
    ShapeType(String value) {
        this.value = value;
    }
    
    /**
     * JSON serialization için değer döndürür
     * Jackson kütüphanesi bu metodu otomatik kullanır
     * 
     * @return JSON'da kullanılacak string değer
     */
    @JsonValue
    public String getValue() {
        return value;
    }
}
