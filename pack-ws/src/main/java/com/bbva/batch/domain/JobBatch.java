package com.bbva.batch.domain;

import java.util.List;

public class JobBatch extends EntityBatch {

	private static final long serialVersionUID = 1L;
    private String name;
    private String cronExpression;
    private List<StepStoreProcedureBatch> steps;

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

	public List<StepStoreProcedureBatch> getSteps() {
        return steps;
    }

    public void setSteps(List<StepStoreProcedureBatch> steps) {
        this.steps = steps;
    }
}
