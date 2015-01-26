package com.bbva.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.factory.JobBatchFactory;
import com.bbva.batch.service.JobBatchService;
import com.everis.web.listener.WebServletContextListener;

public class ExecuteJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(ExecuteJob.class);
    private Long idJobBatch;

    @Override
    protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
        LOG.info("ExecuteJob:executeInternal: [Group:" + arg.getJobDetail().getGroup() + "][Name:" + arg.getJobDetail().getName() + "]");
        
        JobLauncher jobLauncher = WebServletContextListener.getBean("jobLauncher");
        JobBatchService jobBatchService = WebServletContextListener.getBean("jobBatchService");
        JobBatchFactory jobBatchFactory = WebServletContextListener.getBean("jobBatchFactory");
        
        JobBatch jobBatch = jobBatchService.obtener(idJobBatch, true);
        
        Job job = jobBatchFactory.createJob(jobBatch);
        JobParameters param = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();

        JobExecution execution;
        try {
            execution = jobLauncher.run(job, param);
            Long outId = execution.getId();
            LOG.error("Id Proceso: [" + outId + "]");
            LOG.error("Estado del Proceso: " + execution.getStatus());
            if (!execution.getAllFailureExceptions().isEmpty()) {
                LOG.error("Estado del Proceso: " + execution.getAllFailureExceptions());
            }
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.error("", e);
        } catch (JobRestartException e) {
            LOG.error("", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.error("", e);
        } catch (JobParametersInvalidException e) {
            LOG.error("", e);
        }
    }

    public Long getJobBatch() {
        return idJobBatch;
    }

    public void setJobBatch(Long jobBatch) {
        this.idJobBatch = jobBatch;
    }
}
