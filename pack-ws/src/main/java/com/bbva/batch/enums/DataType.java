package com.bbva.batch.enums;

public enum DataType {
    
    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    DATE("Date"),
    DOUBLE("Double"),
    BIGDECIMAL("BigDecimal");
    
    private String nameType;

    DataType(String nameType) {
        this.nameType = nameType;
    }

    public String getName() {
        return nameType;
    }
}
