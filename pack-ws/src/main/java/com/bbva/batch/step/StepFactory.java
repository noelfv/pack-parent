package com.bbva.batch.step;

import java.util.List;

import org.springframework.batch.core.Step;

import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.domain.StepBatch;

public interface StepFactory {

    Step createStep(StepBatch stepBatch);
    List<Step> createSteps(JobBatch jobBatch);
}
