package com.bbva.batch.domain;

import java.util.List;

public class JobBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private String cronExpression;
    private String type;
    private ApplicationBatch application;
    private List<StepBatch> steps;

    public JobBatch() {
        super();
    }

    public JobBatch(String name, String cronExpression, ApplicationBatch application) {
        super();
        this.name = name;
        this.cronExpression = cronExpression;
        this.application = application;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getCountSteps() {
        int size = 0;
        /* if(this.steps != null) {
            size = this.steps.size();
        } */
        
        return size;
    }
}
