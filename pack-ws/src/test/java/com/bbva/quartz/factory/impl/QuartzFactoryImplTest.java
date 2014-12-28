package com.bbva.quartz.factory.impl;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.quartz.factory.QuartzFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class QuartzFactoryImplTest extends AbstractJUnit4Test {

    @Resource(name = "quartzFactory")
    private QuartzFactory quartzFactory;

    @Test
    public void createJob() {
        ApplicationBatch app = new ApplicationBatch();
        app.setName("demo");
        app.setJndi("jdbc/APP_CONELE");


        JobBatch jobBatch = new JobBatch();
        jobBatch.setId(1L);
        jobBatch.setApplication(app);
        jobBatch.setName("demoProcedure");
        jobBatch.setCronExpression("0 0/1 * 1/1 * ? *");

        quartzFactory.createJob(jobBatch);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Assert.fail("Error");
        }
    }
}
