package com.bbva.packws.batch.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class AbortarTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(AbortarTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOG.error("Abortando el proceso");
        chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);
        stepContribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
