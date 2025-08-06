package com.simsoft.shapes.model;

import java.util.List;

public class ShapeData {
    private List<Shape> shapes;
    private String messageType = "shapes";
    
    public ShapeData() {}
    
    public ShapeData(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
    public List<Shape> getShapes() { return shapes; }
    public void setShapes(List<Shape> shapes) { this.shapes = shapes; }
    
    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
}
