package com.bbva.packws.batch.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bbva.packws.service.SchedulerService;
import com.everis.web.listener.WebServletContextListener;

public class GenerarArchivoJob extends QuartzJobBean {

	private static final Logger LOG = Logger.getLogger(GenerarArchivoJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
		SchedulerService schedulerService = (SchedulerService) WebServletContextListener.getBean("schedulerService");
		Long id = 0L;
		try {
			schedulerService.executeJob(id);
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
		LOG.info("Proceso [" + id + "] Terminado");
	}
}
