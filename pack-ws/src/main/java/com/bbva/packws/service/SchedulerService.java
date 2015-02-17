package com.bbva.packws.service;

import org.quartz.SchedulerException;

import com.bbva.batch.domain.JobBatch;


public interface SchedulerService {

    void deleteAll();
    void delete(JobBatch jobBatch) throws SchedulerException;
    void rescheduler(JobBatch jobBatch) throws SchedulerException;
}
