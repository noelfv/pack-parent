package com.bbva.batch.domain;

import java.util.List;

public class StepBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Long order;
    private String reader;
    private String writer;
    private String nextStep;
    private JobBatch job;
    private List<ParameterBatch> parameters;

    public StepBatch() {
        super();
    }

    public StepBatch(String name, Long order, String reader, String writer, JobBatch job) {
        super();
        this.name = name;
        this.order = order;
        this.reader = reader;
        this.writer = writer;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public JobBatch getJob() {
        return job;
    }

    public void setJob(JobBatch job) {
        this.job = job;
    }

    public List<ParameterBatch> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterBatch> parameters) {
        this.parameters = parameters;
    }

}
