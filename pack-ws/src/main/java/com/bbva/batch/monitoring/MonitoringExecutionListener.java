package com.bbva.batch.monitoring;

import javax.annotation.Resource;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("monitoringExecutionListener")
public class MonitoringExecutionListener implements JobExecutionListener {

    @Resource(name = "batchMonitoringNotifier")
    private BatchMonitoringNotifier monitoringNotifier;

    public void setMonitoringNotifier(BatchMonitoringNotifier monitoringNotifier) {
        this.monitoringNotifier = monitoringNotifier;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        monitoringNotifier.notify(jobExecution);
    }
}