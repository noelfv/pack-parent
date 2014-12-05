package com.bbva.packws.batch.monitoring;

import org.springframework.batch.core.JobExecution;

import com.bbva.packws.service.NotificacionService;

public interface BatchMonitoringNotifier {
	
	void setNotificacionService(NotificacionService notificacionService);
	void notify(JobExecution jobExecution);
}
