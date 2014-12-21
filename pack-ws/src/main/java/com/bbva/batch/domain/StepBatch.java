package com.bbva.batch.domain;

import java.util.List;

public abstract class StepBatch extends EntityBatch {

	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private List<ParameterBatch> parameters;

	public String getName() {
		return name;
	}

	public void setNameStep(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ParameterBatch> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterBatch> parameters) {
		this.parameters = parameters;
	}

}
