package com.bbva.packws.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.packws.service.SchedulerService;
import com.bbva.quartz.domain.Trigger;
import com.bbva.quartz.factory.QuartzFactory;
import com.bbva.quartz.service.TriggerService;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOG = Logger.getLogger(SchedulerServiceImpl.class);

    @Resource(name = "quartzScheduler")
    private Scheduler scheduler;

    @Resource(name = "triggerService")
    private TriggerService triggerService;

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Resource(name = "quartzFactory")
    private QuartzFactory quartzFactory;
    
    @PostConstruct
    public void init() {
        deleteAll();
        List<ApplicationBatch> apps = applicationBatchService.listar(true);
            for(ApplicationBatch app : apps) {
                if(app != null && app.getJobs() != null) {
                    try {
                        quartzFactory.createJobs(app.getJobs());
                    } catch (Exception e) {
                        throw new UnsupportedOperationException("Not create quartzBean: [" + app.getName() + "]", e);
                    }
                }
            }
    }

    @Override
    public void deleteAll() {
        List<Trigger> triggers = triggerService.listar();
        for(Trigger t : triggers) {
            try {
                scheduler.deleteJob(t.getJobName(), t.getJobGroup());
            } catch (SchedulerException e) {
                LOG.error("No se pudo eliminar el Job: [" + t.getJobName() + "]", e);
            }
        }
    }

    @Override
    public void delete(JobBatch jobBatch) throws SchedulerException {
        scheduler.deleteJob(jobBatch.getName(), jobBatch.getApplication().getName().toUpperCase());
    }

    @Override
    public void rescheduler(JobBatch jobBatch) throws SchedulerException {
        delete(jobBatch);
        quartzFactory.createJob(jobBatch);
    }
    
     
}
