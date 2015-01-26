package com.bbva.batch.monitoring;

import org.springframework.batch.core.JobExecution;

public interface BatchMonitoringNotifier {

    void notify(JobExecution jobExecution);
}
