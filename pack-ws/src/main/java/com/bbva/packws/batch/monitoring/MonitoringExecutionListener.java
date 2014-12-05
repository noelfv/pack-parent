package com.bbva.packws.batch.monitoring;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;

public class MonitoringExecutionListener {

	private BatchMonitoringNotifier monitoringNotifier;

	@AfterJob
	public void executeAfterJob(JobExecution jobExecution) {
		monitoringNotifier.notify(jobExecution);
	}

	public void setMonitoringNotifier(BatchMonitoringNotifier monitoringNotifier) {
		this.monitoringNotifier = monitoringNotifier;
	}
}