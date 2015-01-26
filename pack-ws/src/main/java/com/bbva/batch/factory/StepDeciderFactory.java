package com.bbva.batch.factory;

import org.springframework.batch.core.job.flow.JobExecutionDecider;

import com.bbva.batch.domain.StepBatch;

public interface StepDeciderFactory {

    JobExecutionDecider create(StepBatch stepBatch) throws Exception;
}
