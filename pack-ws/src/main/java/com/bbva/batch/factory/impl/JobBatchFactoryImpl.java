package com.bbva.batch.factory.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.job.flow.support.StateTransition;
import org.springframework.batch.core.job.flow.support.state.DecisionState;
import org.springframework.batch.core.job.flow.support.state.EndState;
import org.springframework.batch.core.job.flow.support.state.StepState;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.factory.JobBatchFactory;
import com.bbva.batch.factory.StepDeciderFactory;
import com.bbva.batch.factory.StepFactory;
import com.bbva.batch.util.DeciderBatch;

import flexjson.JSONDeserializer;

@Component("jobBatchFactory")
public class JobBatchFactoryImpl implements JobBatchFactory {

    private static final Logger LOG = Logger.getLogger(JobBatchFactoryImpl.class);

    @Resource(name = "jobRepository")
    private JobRepository jobRepository;

    @Resource(name = "stepDeciderFactory")
    private StepDeciderFactory stepDeciderFactory;
    
    @Resource(name = "stepFactory")
    private StepFactory stepFactory;

    @Resource(name = "monitoringExecutionListener")
    private JobExecutionListener monitoringExecutionListener;

    @SuppressWarnings("unchecked")
    private FlowJob doCreateFlowJob(JobBatch jobBatch) {
        FlowJob flowJob = new FlowJob(jobBatch.getName());
        SimpleFlow simpleFlow = new SimpleFlow(jobBatch.getName() + "Flow");
        List<StateTransition> transitions = new ArrayList<StateTransition>();
        StepBatch stepBatch;
        Step step;
        JobExecutionDecider decider;
        JSONDeserializer<DeciderBatch> deserializerDecider = new JSONDeserializer<DeciderBatch>();
        List<DeciderBatch> deciderNext; 
        StateTransition stateTransition = null;
        int i = 0;
        
        try {           
            if(jobBatch.getSteps() != null) {
                for(i = 0; i < jobBatch.getSteps().size(); i++) {
                    stepBatch = jobBatch.getSteps().get(i);
                    if(stepBatch == null) {
                        continue;
                    }
                    
                    if(stepBatch.getNextStep() == null) {
                        throw new NullPointerException("No esta asignado el siguiente paso en el flujo. [Paso: " + stepBatch.getName() + "]");
                    }
                    
                    if(ItemWriterType.WRITER_DECISOR.getName().equalsIgnoreCase(stepBatch.getWriter())) {
                        decider = stepDeciderFactory.create(stepBatch);
                        deciderNext = (List<DeciderBatch>) deserializerDecider.deserialize(stepBatch.getNextStep());
                        for(DeciderBatch deciderBatch : deciderNext) {
                            stateTransition = StateTransition.createStateTransition(new DecisionState(decider, stepBatch.getName()), deciderBatch.getStatus(), deciderBatch.getNextStep());
                            transitions.add(stateTransition);
                        }
                    } else {
                        step = stepFactory.createStep(stepBatch);   
                        stateTransition = StateTransition.createStateTransition(new StepState(step), stepBatch.getNextStep());
                        transitions.add(stateTransition);
                    }
                }
                
                transitions.add(StateTransition.createEndStateTransition(new EndState(FlowExecutionStatus.COMPLETED, "end")));
                transitions.add(StateTransition.createEndStateTransition(new EndState(FlowExecutionStatus.FAILED, "endFail")));
            }
            
            simpleFlow.setStateTransitions(transitions);
            simpleFlow.afterPropertiesSet();
            
            flowJob.setFlow(simpleFlow);
            flowJob.setJobRepository(jobRepository);
            flowJob.afterPropertiesSet();  
        } catch (Exception e) {
            LOG.error("Error al crear el flujo de trabajo: [" + jobBatch.getName() + "]", e);
            flowJob = null;
        }

        return flowJob;
    }

    
    private SimpleJob doCreateJob(JobBatch jobBatch) {
        SimpleJob job = new SimpleJob(jobBatch.getName());

        try {
            job.setJobRepository(jobRepository);
            job.registerJobExecutionListener(monitoringExecutionListener);
            job.setSteps(stepFactory.createSteps(jobBatch));
        } catch (Exception e) {
            LOG.error("Error al crear el trabajo: [" + jobBatch.getName() + "]", e);
            job = null;
        }

        return job;
    }
    
    @Override
    public Job createJob(JobBatch jobBatch) {
        Job job;
        
        if(JobBatchFactory.SIMPLE.equalsIgnoreCase(jobBatch.getType())) {
            job = doCreateJob(jobBatch);
        } else {
            job = doCreateFlowJob(jobBatch);
        }
                
        return job;
    }

    @Override
    public List<Job> createJobs(ApplicationBatch applicationBatch) {
        List<Job> jobs = new ArrayList<Job>();
        Job job = null;

        for (JobBatch j : applicationBatch.getJobs()) {
            job = createJob(j);
            if (job != null) {
                jobs.add(job);
            }
        }

        return jobs;
    }

}
