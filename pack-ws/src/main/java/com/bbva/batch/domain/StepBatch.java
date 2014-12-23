package com.bbva.batch.domain;

import java.util.List;

public class StepBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private String reader;
    private String writer;
    private List<ParameterBatch> parameters;

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

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public List<ParameterBatch> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterBatch> parameters) {
        this.parameters = parameters;
    }

}
