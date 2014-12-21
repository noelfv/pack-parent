package com.bbva.packws.batch.monitoring.impl;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;

import com.bbva.packws.batch.monitoring.BatchMonitoringNotifier;
import com.bbva.packws.service.NotificacionService;

public class BatchMonitoringNotifierImpl implements BatchMonitoringNotifier {

	private static final Logger LOG = Logger.getLogger(BatchMonitoringNotifierImpl.class);
	
	private NotificacionService notificacionService;
	
	@Override
	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	@Override
	public void notify(JobExecution jobExecution) {
		LOG.error("BatchMonitoringNotifierImpl: " + jobExecution.getExitStatus().getExitCode());
        if(!jobExecution.getExitStatus().getExitCode().equalsIgnoreCase("COMPLETED")) {
            notificacionService.notificarPorCorreo(jobExecution);
        }
	}

}
