package com.bbva.batch.enums;

public enum ItemReaderType {
    
    READER_TEXT_DELIMIT("READER_TEXT_DELIMIT"),
    READER_TEXT_POSITION("READER_TEXT_POSITION"),
    READER_XML("READER_XML"),
    READER_TABLE("READER_TABLE");
    
    private String name;
    
    ItemReaderType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
