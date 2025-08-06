package com.simsoft.shapes.service;

import com.simsoft.shapes.model.PanelInfo;
import com.simsoft.shapes.model.Shape;
import com.simsoft.shapes.model.ShapeData;
import com.simsoft.shapes.model.ShapeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ShapeService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    private final PanelInfo panelInfo = new PanelInfo(1000, 800);
    private final Map<String, Shape> shapes = new ConcurrentHashMap<>();
    private final String[] colors = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"};
    private final Random random = new Random();
    
    // Initialize with default shapes
    public ShapeService() {
        initializeDefaultShapes();
    }
    
    private void initializeDefaultShapes() {
        createShapes(3, 3, 3);
    }
    
    public void createShapes(int circles, int squares, int triangles) {
        shapes.clear();
        
        // Create circles
        for (int i = 0; i < circles; i++) {
            Shape circle = createRandomShape("circle_" + i, ShapeType.CIRCLE);
            shapes.put(circle.getId(), circle);
        }
        
        // Create squares
        for (int i = 0; i < squares; i++) {
            Shape square = createRandomShape("square_" + i, ShapeType.SQUARE);
            shapes.put(square.getId(), square);
        }
        
        // Create triangles
        for (int i = 0; i < triangles; i++) {
            Shape triangle = createRandomShape("triangle_" + i, ShapeType.TRIANGLE);
            shapes.put(triangle.getId(), triangle);
        }
        
        // Start movement for 25% of shapes
        setRandomMovingShapes();
    }
    
    private Shape createRandomShape(String id, ShapeType type) {
        double size = 20 + random.nextDouble() * 30; // Size between 20-50
        double x = random.nextDouble() * (panelInfo.getWidth() - size);
        double y = random.nextDouble() * (panelInfo.getHeight() - size);
        String color = colors[random.nextInt(colors.length)];
        
        return new Shape(id, type, x, y, size, color);
    }
    
    private void setRandomMovingShapes() {
        List<Shape> allShapes = new ArrayList<>(shapes.values());
        
        // Stop all shapes
        allShapes.forEach(shape -> shape.setMoving(false));
        
        // Make 25% of shapes move (minimum 1)
        int movingCount = Math.max(1, allShapes.size() / 4);
        Collections.shuffle(allShapes);
        
        for (int i = 0; i < movingCount && i < allShapes.size(); i++) {
            allShapes.get(i).setMoving(true);
        }
    }
    
    @Scheduled(fixedRate = 30) // 30ms = ~33 FPS
    public void updateAndBroadcastShapes() {
        if (shapes.isEmpty()) return;
        
        // Update moving shapes
        shapes.values().forEach(shape -> {
            if (shape.isMoving()) {
                shape.move(panelInfo.getWidth(), panelInfo.getHeight());
            }
        });
        
        // Broadcast all shapes to clients
        ShapeData shapeData = new ShapeData(new ArrayList<>(shapes.values()));
        messagingTemplate.convertAndSend("/topic/shapes", shapeData);
    }
    
    @Scheduled(fixedRate = 3000) // Change moving shapes every 3 seconds
    public void changeMovingShapes() {
        if (!shapes.isEmpty()) {
            setRandomMovingShapes();
        }
    }
    
    public void sendPanelInfo() {
        Map<String, Object> panelMessage = new HashMap<>();
        panelMessage.put("messageType", "panel");
        panelMessage.put("width", panelInfo.getWidth());
        panelMessage.put("height", panelInfo.getHeight());
        
        messagingTemplate.convertAndSend("/topic/shapes", panelMessage);
    }
    
    public List<Shape> getAllShapes() {
        return new ArrayList<>(shapes.values());
    }
    
    public PanelInfo getPanelInfo() {
        return panelInfo;
    }
}
