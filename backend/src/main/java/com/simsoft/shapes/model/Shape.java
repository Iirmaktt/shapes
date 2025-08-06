package com.simsoft.shapes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Shape {
    private String id;
    private ShapeType type;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double size;
    private String color;
    private boolean isMoving;
    
    public Shape() {}
    
    public Shape(String id, ShapeType type, double x, double y, double size, String color) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.isMoving = false;
        
        // Initialize random velocity
        double speed = Math.random() * 2 + 1; // Speed between 1-3
        double angle = Math.random() * 2 * Math.PI;
        this.velocityX = Math.cos(angle) * speed;
        this.velocityY = Math.sin(angle) * speed;
    }
    
    // Getters and setters
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
    
    @JsonProperty("isMoving")
    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { this.isMoving = moving; }
    
    public void move(double panelWidth, double panelHeight) {
        if (!isMoving) return;
        
        // Update position
        x += velocityX;
        y += velocityY;
        
        // Boundary collision detection and reflection
        if (x <= 0 || x >= panelWidth - size) {
            velocityX = -velocityX;
            x = Math.max(0, Math.min(x, panelWidth - size));
        }
        
        if (y <= 0 || y >= panelHeight - size) {
            velocityY = -velocityY;
            y = Math.max(0, Math.min(y, panelHeight - size));
        }
    }
}
