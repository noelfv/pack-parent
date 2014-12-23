package com.bbva.batch.factory.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.factory.JobBatchFactory;
import com.bbva.batch.factory.StepFactory;

@Component("jobBatchFactory")
public class JobBatchFactoryImpl implements JobBatchFactory {

    private static final Logger LOG = Logger.getLogger(JobBatchFactoryImpl.class);
    
    @Resource(name = "jobRepository")
    private JobRepository jobRepository;
    
    @Resource(name = "stepFactory")
    private StepFactory stepFactory;
    
    @Resource(name = "monitoringExecutionListener")
    private JobExecutionListener monitoringExecutionListener;
    
    @Override
    public SimpleJob createJob(JobBatch jobBatch) {
        SimpleJob job = new SimpleJob(jobBatch.getName());
        
        try {
            job.setJobRepository(jobRepository);
            job.registerJobExecutionListener(monitoringExecutionListener);
            job.setSteps(stepFactory.createSteps(jobBatch));
        } catch(Exception e) {
            LOG.error("Error al crear el trabajo: [" + jobBatch.getName() + "]", e);
            job = null;
        }
        
        return job;
    }

    @Override
    public List<SimpleJob> createJobs(ApplicationBatch applicationBatch) {
        List<SimpleJob> jobs = new ArrayList<SimpleJob>();
        SimpleJob job = null;
        
        for(JobBatch j : applicationBatch.getJobs()) {
            job = createJob(j);
            if(job != null) {
                jobs.add(job);
            }
        }
        
        return jobs;
    }

    @Override
    public FlowJob createFlowJob(JobBatch jobBatch) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FlowJob> createFlowJobs(ApplicationBatch applicationBatch) {
        // TODO Auto-generated method stub
        return null;
    }

}
