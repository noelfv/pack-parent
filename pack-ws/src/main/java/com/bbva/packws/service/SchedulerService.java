package com.bbva.packws.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public interface SchedulerService {

	boolean reschedulerTriggerGenerarArchivo(String time);
	boolean reschedulerTriggerDepurarArchivo(String time);
	void executeJob(Long outId) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, NoSuchJobException;
}
