package com.bbva.batch.domain;

import java.util.List;

public class JobBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String cronExpression;
    private ApplicationBatch application;
    private List<StepBatch> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public ApplicationBatch getApplication() {
        return application;
    }

    public void setApplication(ApplicationBatch application) {
        this.application = application;
    }

    public List<StepBatch> getSteps() {
        return steps;
    }

    public void setSteps(List<StepBatch> steps) {
        this.steps = steps;
    }
}