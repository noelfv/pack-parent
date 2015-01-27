package com.bbva.quartz.factory.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.packws.service.SchedulerService;
import com.bbva.quartz.factory.QuartzFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class QuartzFactoryImplTest extends AbstractJUnit4Test {

    @Resource(name = "quartzFactory")
    private QuartzFactory quartzFactory;

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Resource(name = "schedulerService")
    private SchedulerService schedulerService;

    @Test
    public void _01deleteAllJobs() {
        schedulerService.deleteAll();
    }
    
    @Test
    public void _02createJob() {
        ApplicationBatch app = applicationBatchService.obtener("packBBVA", true);
        try {
            quartzFactory.createJobs(app.getJobs());
        } catch (Exception e) {
            LOGGER.error("", e);
            Assert.fail("Error");
        }
        /** try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Assert.fail("Error");
        } **/
    }
}
