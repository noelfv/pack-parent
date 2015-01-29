package com.bbva.quartz.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.JobBatch;
import com.bbva.quartz.ExecuteJob;
import com.bbva.quartz.factory.QuartzFactory;

@Component("quartzFactory")
public class QuartzFactoryImpl implements QuartzFactory {

    private static final Logger LOG = Logger.getLogger(QuartzFactoryImpl.class);

    @Resource(name = "quartzScheduler")
    private Scheduler scheduler;
    
    @Override
    public void createJob(JobBatch jobBatch) {
        String name = jobBatch.getName() + "Cron";

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("jobBatch", jobBatch.getId());

        JobKey jobKey = new JobKey(jobBatch.getName(), jobBatch.getApplication().getName().toUpperCase());
        JobDetail jobDetail = JobBuilder
                .newJob(ExecuteJob.class)
                .setJobData(jobDataMap)
                .withIdentity(jobKey)
                .build();

        TriggerKey triggerKey = TriggerKey.triggerKey(name, jobBatch.getApplication().getName().toUpperCase());
        Trigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerKey)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(jobBatch.getCronExpression()))
                .build();
        
        try {
            scheduler.deleteJob(jobKey);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            LOG.error("No se pudo programar la tarea", e);
        }
    }

    @Override
    public void createJobs(List<JobBatch> jobsBatch) {
        for(JobBatch job : jobsBatch) {
            if(job != null) {
                createJob(job);
            }
        }
    }
}
