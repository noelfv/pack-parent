package com.bbva.batch.enums;

public enum ItemReaderType {

    READER_TEXT_DELIMIT("READER_TEXT_DELIMIT"),
    READER_TEXT_POSITION("READER_TEXT_POSITION"),
    READER_XML("READER_XML"),
    READER_TABLE("READER_TABLE");

    private String nameReader;

    ItemReaderType(String nameReader) {
        this.nameReader = nameReader;
    }

    public String getName() {
        return this.nameReader;
    }
}
