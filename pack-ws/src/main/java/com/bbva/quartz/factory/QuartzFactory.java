package com.bbva.quartz.factory;

import java.util.List;

import com.bbva.batch.domain.JobBatch;

public interface QuartzFactory {

    void createJob(JobBatch jobBatch);
    void createJobs(List<JobBatch> jobsBatch);

}