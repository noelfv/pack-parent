package com.bbva.batch.enums;

public enum ParameterType {

    PARAM_JNDI("JNDI", String.class),
    PARAM_SELECT("SELECT", String.class),
    PARAM_FROM("FROM", String.class),
    PARAM_WHERE("WHERE", String.class),
    PARAM_GROUP("GROUP", String.class),
    PARAM_SORT("SORT", String.class),
    PARAM_PAGE_SIZE("PAGE_SIZE", Long.class),
    PARAM_FORMAT("FORMAT", String.class),
    PARAM_RESOURCE("RESOURCE", String.class),
    PARAM_FIELDS("FIELDS", String.class),
    PARAM_DELIMITER("DELIMITER", String.class),
    PARAM_QUOTE_CHARACTER("QUOTE_CHARACTER", String.class),
    PARAM_APPEND("APPEND", String.class),
    PARAM_CLASS_NAME("CLASS_NAME", String.class),
    PARAM_FORMAT_DATE("FORMAT_DATE", String.class),
    PARAM_RANGE("RANGE", String.class),
    PARAM_QUERY("QUERY", String.class),
    PARAM_WSDL("WSDL", Byte.class),
    PARAM_WSDL_OPERATION("WSDL_OPERATION", String.class),
    PARAM_RULE("RULE", Byte.class),
    PARAM_DECISOR_PARAM("DECISOR_PARAM", Byte.class);
    
    private String nameParam;
    private Class<?> dataType;

    ParameterType(String nameParam, Class<?> dataType) {
        this.nameParam = nameParam;
        this.dataType = dataType;
    }

    public String getName() {
        return this.nameParam;
    }

    public String getDataType() {
        return dataType.getSimpleName();
    }
}
