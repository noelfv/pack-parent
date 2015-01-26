package com.bbva.batch.util;

import java.io.Serializable;

public class DeciderParam implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String query;
    private String value;

    public DeciderParam() {
        super();
    }

    public DeciderParam(String name, String query) {
        super();
        this.name = name;
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
