package com.bbva.batch.factory;

import java.util.List;

import org.springframework.batch.core.job.flow.FlowJob;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;

public interface JobBatchFactory {

    FlowJob createJob(JobBatch jobBatch);
    List<FlowJob> createJobs(ApplicationBatch applicationBatch);
}
