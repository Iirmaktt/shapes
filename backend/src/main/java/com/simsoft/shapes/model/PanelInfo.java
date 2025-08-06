package com.simsoft.shapes.model;

public class PanelInfo {
    private double width;
    private double height;
    
    public PanelInfo() {}
    
    public PanelInfo(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}
