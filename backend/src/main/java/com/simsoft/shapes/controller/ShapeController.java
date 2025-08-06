package com.simsoft.shapes.controller;

import com.simsoft.shapes.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API kontrolcüsü
 * Şekil sayılarını ayarlamak için HTTP endpoint'leri sağlar
 */
@RestController // Bu sınıfın REST kontrolcüsü olduğunu belirtir
@RequestMapping("/api") // Tüm endpoint'ler /api ile başlar
public class ShapeController {
    
    // ShapeService'i otomatik olarak enjekte et (Dependency Injection)
    @Autowired
    private ShapeService shapeService;
    
    /**
     * Şekil sayılarını ayarlayan GET endpoint'i
     * URL: /api/setShapes?circles=4&rects=5&triangles=3
     * 
     * @param circles Daire sayısı (varsayılan: 3)
     * @param rects Kare sayısı (varsayılan: 3) 
     * @param triangles Üçgen sayısı (varsayılan: 3)
     * @return JSON yanıtı ile başarı mesajı ve güncel sayılar
     */
    @GetMapping("/setShapes") // GET isteğini yakalar
    public ResponseEntity<Map<String, Object>> setShapes(
            @RequestParam(defaultValue = "3") int circles,    // Query parametresi: circles
            @RequestParam(defaultValue = "3") int rects,      // Query parametresi: rects
            @RequestParam(defaultValue = "3") int triangles) { // Query parametresi: triangles
        
        // ShapeService aracılığıyla yeni şekilleri oluştur
        shapeService.createShapes(circles, rects, triangles);
        
        // İstemciye gönderilecek yanıt haritası oluştur
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Shapes updated successfully");
        response.put("circles", circles);
        response.put("squares", rects);
        response.put("triangles", triangles);
        response.put("totalShapes", circles + rects + triangles);
        
        // HTTP 200 OK ile JSON yanıtı döndür
        return ResponseEntity.ok(response);
    }
}
