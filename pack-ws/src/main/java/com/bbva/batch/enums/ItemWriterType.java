package com.bbva.batch.enums;

public enum ItemWriterType {

    WRITER_TEXT_DELIMIT("WRITER_TEXT_DELIMIT"),
    WRITER_TEXT_POSITION("WRITER_TEXT_POSITION"),
    WRITER_XML("WRITER_XML"),
    WRITER_TABLE("WRITER_TABLE");
    
    private String name;
    
    ItemWriterType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
