package com.bbva.batch.enums;

public enum DataType {
    
    STRING("String"),
    LONG("LONG"),
    DATE("Date"),
    DOUBLE("Double");
    
    private String name;

    DataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
