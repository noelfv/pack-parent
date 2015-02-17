package com.bbva.batch.factory;

import java.util.List;

import org.springframework.batch.core.Job;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;

public interface JobBatchFactory {

    String SIMPLE = "SIMPLE";
    String FLOW = "FLOW";
    
    Job createJob(JobBatch jobBatch);
    List<Job> createJobs(ApplicationBatch applicationBatch);
}
