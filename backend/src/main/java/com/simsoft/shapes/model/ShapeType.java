package com.simsoft.shapes.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ShapeType {
    CIRCLE("circle"),
    SQUARE("square"),
    TRIANGLE("triangle");
    
    private final String value;
    
    ShapeType(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
}
