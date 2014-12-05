package com.bbva.packws.service;

import org.springframework.batch.core.JobExecution;

public interface NotificacionService {

	boolean notificarPorCorreo(JobExecution jobExecution);
}
