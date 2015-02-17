package com.bbva.batch.domain;

import java.util.Date;

public class ParameterBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private Long order;
    private String typeValue;
    private String stringVal;
    private Long longVal;
    private Double doubleVal;
    private Date dateVal;
    private byte[] byteVal;
    private StepBatch step;

    public ParameterBatch(){
        super();
    }
    
    public ParameterBatch(String name, String type, Long order,
                          String typeValue, String stringVal) {
        super();
        this.name = name;
        this.type = type;
        this.order = order;
        this.typeValue = typeValue;
        this.stringVal = stringVal;
    }

    public ParameterBatch(String name, String type, Long order,
                          String typeValue, Long longVal) {
        super();
        this.name = name;
        this.type = type;
        this.order = order;
        this.typeValue = typeValue;
        this.longVal = longVal;
    }

    public ParameterBatch(String name, String type, Long order,
                          String typeValue, Double doubleVal) {
        super();
        this.name = name;
        this.type = type;
        this.order = order;
        this.typeValue = typeValue;
        this.doubleVal = doubleVal;
    }

    public ParameterBatch(String name, String type, Long order,
                          String typeValue, Date dateVal) {
        super();
        this.name = name;
        this.type = type;
        this.order = order;
        this.typeValue = typeValue;
        this.dateVal = dateVal;
    }

    public ParameterBatch(String name, String type, Long order,
            String typeValue, byte[] byteVal) {
        super();
        this.name = name;
        this.type = type;
        this.order = order;
        this.typeValue = typeValue;
        this.byteVal = byteVal;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    public byte[] getByteVal() {
        return byteVal;
    }

    public void setByteVal(byte[] byteVal) {
        this.byteVal = byteVal;
    }

    public StepBatch getStep() {
        return step;
    }

    public void setStep(StepBatch step) {
        this.step = step;
    }

}
