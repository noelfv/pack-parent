package com.bbva.packws.batch.monitoring.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Component;

import com.bbva.batch.monitoring.BatchMonitoringNotifier;
import com.bbva.packws.service.NotificacionService;

@Component("batchMonitoringNotifier")
public class BatchMonitoringNotifierImpl implements BatchMonitoringNotifier {

    private static final Logger LOG = Logger.getLogger(BatchMonitoringNotifierImpl.class);

    @Resource(name = "notificacionService")
    private NotificacionService notificacionService;

    @Override
    public void notify(JobExecution jobExecution) {
        LOG.error("BatchMonitoringNotifierImpl: " + jobExecution.getExitStatus().getExitCode());
        if (!jobExecution.getExitStatus().getExitCode().equalsIgnoreCase("COMPLETED")) {
            notificacionService.notificarPorCorreo(jobExecution);
        }
    }

}
