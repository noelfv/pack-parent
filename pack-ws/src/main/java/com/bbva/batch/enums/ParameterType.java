package com.bbva.batch.enums;

public enum ParameterType {

    PARAM_JNDI("JNDI", String.class),
    PARAM_JOB("JOB", Long.class),
    PARAM_SELECT("SELECT", String.class),
    PARAM_FROM("FROM", String.class),
    PARAM_WHERE("WHERE", String.class),
    PARAM_GROUP("GROUP", String.class),
    PARAM_SORT("SORT", String.class),
    PARAM_PAGE_SIZE("PAGE_SIZE", Long.class),
    
    PARAM_FORMAT("FORMAT", String.class),
    PARAM_RESOURCE("RESOURCE", String.class);
    
    private String name;
    private Class<?> dataType;
    
    ParameterType(String name, Class<?> dataType) {
        this.name = name;
        this.dataType = dataType;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDataType() {
        return dataType.getName();
    }
}
