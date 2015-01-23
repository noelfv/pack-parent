package com.bbva.batch.enums;

public enum ItemWriterType {

    WRITER_TEXT_DELIMIT("WRITER_TEXT_DELIMIT"),
    WRITER_TEXT_POSITION("WRITER_TEXT_POSITION"),
    WRITER_XML("WRITER_XML"),
    WRITER_TABLE("WRITER_TABLE"),
    WRITER_QUERY("WRITER_QUERY");

    private String nameWriter;

    ItemWriterType(String nameWriter) {
        this.nameWriter = nameWriter;
    }

    public String getName() {
        return this.nameWriter;
    }
}
