package com.everis.enums;

public enum Contenido {

    TXT("text/plain", "txt"),
    XLS("application/vnd.ms-excel", "xls"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
    PDF("application/pdf", "pdf"),
    ZIP("application/zip", "zip"),
    BIN("application/octet-stream", "bin"),
    PNG("image/png", "png"),
    JPEG("image/jpeg", "jpeg"),
    TIIF("image/tiff", "tiif"),
    SVG("application/xml", "svg");

    private String contentType;
    private String extension;

    Contenido(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String toString() {
        return contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
