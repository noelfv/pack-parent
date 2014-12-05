package com.bbva.packws.batch.job;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.bbva.packws.service.SchedulerService;
import com.everis.web.listener.WebServletContextListener;

public class GenerarArchivoThread extends Thread {

	private static final Logger LOG = Logger.getLogger(GenerarArchivoThread.class);
	
	@Override
	public void run() {
		SchedulerService schedulerService = (SchedulerService) WebServletContextListener.getBean("schedulerService");
		try {
			schedulerService.executeJob();
		} catch (JobExecutionAlreadyRunningException e) {
			LOG.error("JobExecutionAlreadyRunningException", e);
		} catch (JobRestartException e) {
			LOG.error("JobRestartException", e);
		} catch (JobInstanceAlreadyCompleteException e) {
			LOG.error("JobInstanceAlreadyCompleteException", e);
		} catch (JobParametersInvalidException e) {
			LOG.error("JobParametersInvalidException", e);
		} catch (NoSuchJobException e) {
			LOG.error("NoSuchJobException", e);
		}
	}

}
