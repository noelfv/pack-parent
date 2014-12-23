package com.bbva.batch.factory;

import java.util.List;

import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.job.flow.FlowJob;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;

public interface JobBatchFactory {

    FlowJob createFlowJob(JobBatch jobBatch);
    List<FlowJob> createFlowJobs(ApplicationBatch applicationBatch);
    SimpleJob createJob(JobBatch jobBatch);
    List<SimpleJob> createJobs(ApplicationBatch applicationBatch);
}
