package com.bbva.packws.model;

import java.util.List;

import org.springframework.batch.core.JobExecution;

import com.everis.web.model.BaseModel;

public class SchedulerModel extends BaseModel {

	private List<JobExecution> runningJobInstances;

	public List<JobExecution> getRunningJobInstances() {
		return runningJobInstances;
	}

	public void setRunningJobInstances(List<JobExecution> runningJobInstances) {
		this.runningJobInstances = runningJobInstances;
	}
}
