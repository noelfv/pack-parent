package com.bbva.quartz.factory.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.JobBatch;
import com.bbva.quartz.ExecuteJob;
import com.bbva.quartz.factory.QuartzFactory;

@Component("quartzFactory")
public class QuartzFactoryImpl implements QuartzFactory {

    private static final Logger LOG = Logger.getLogger(QuartzFactoryImpl.class);
    
    @Resource(name="quartzScheduler")
    private Scheduler scheduler;
    
    @Override
    public void createJob(JobBatch jobBatch) {
        String name = jobBatch.getName() + "Cron";
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("jobBatch", jobBatch.getId());
        
        JobDetail jobDetail = new JobDetail();
        jobDetail.setGroup(jobBatch.getApplication().getName().toUpperCase());
        jobDetail.setJobClass(ExecuteJob.class);
        jobDetail.setJobDataMap(jobDataMap);
        jobDetail.setName(jobBatch.getName());
        
        try {
            scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
            CronTriggerBean cronTrigger = new CronTriggerBean();
            cronTrigger.setGroup(jobBatch.getApplication().getName().toUpperCase());
            cronTrigger.setName(name);
            cronTrigger.setJobDetail(jobDetail);
            cronTrigger.setCronExpression(jobBatch.getCronExpression());
            cronTrigger.afterPropertiesSet();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch(Exception e) {
            LOG.error("No se pudo programar la tarea", e);
        }
    }
}
