package com.bbva.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExecuteJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(ExecuteJob.class);
    private Long jobBatch;
    
    @Override
    protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
        LOG.info("ExecuteJob:executeInternal");
    }

    public Long getJobBatch() {
        return jobBatch;
    }

    public void setJobBatch(Long jobBatch) {
        this.jobBatch = jobBatch;
    }
}
