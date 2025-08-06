package com.simsoft.shapes.controller;

import com.simsoft.shapes.model.Shape;
import com.simsoft.shapes.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShapeController {
    
    @Autowired
    private ShapeService shapeService;
    
    @GetMapping("/setShapes")
    public ResponseEntity<Map<String, Object>> setShapes(
            @RequestParam(defaultValue = "3") int circles,
            @RequestParam(defaultValue = "3") int rects,
            @RequestParam(defaultValue = "3") int triangles) {
        
        shapeService.createShapes(circles, rects, triangles);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Shapes updated successfully");
        response.put("circles", circles);
        response.put("squares", rects);
        response.put("triangles", triangles);
        response.put("totalShapes", circles + rects + triangles);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/shapes")
    public ResponseEntity<List<Shape>> getShapes() {
        return ResponseEntity.ok(shapeService.getAllShapes());
    }
    
    @GetMapping("/panel")
    public ResponseEntity<Object> getPanelInfo() {
        return ResponseEntity.ok(shapeService.getPanelInfo());
    }
}
