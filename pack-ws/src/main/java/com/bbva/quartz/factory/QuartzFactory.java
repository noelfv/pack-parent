package com.bbva.quartz.factory;

import com.bbva.batch.domain.JobBatch;

public interface QuartzFactory {

    void createJob(JobBatch jobBatch);

}