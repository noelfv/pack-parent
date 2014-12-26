package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.JobBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
public class JobBatchServiceImplTest extends AbstractJUnit4Test {

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @Test
    public void listar() {
        List<JobBatch> jobBatchs = jobBatchService.listar(1L);
        
        printer(jobBatchs);
        Assert.assertTrue(jobBatchs.size() >= 0);
    }

    @Test
    public void actualizar() {
        ApplicationBatch application = new ApplicationBatch();
        application.setId(1L);
        application.setVersion(0L);
        application.setName("packBBVA");
        application.setJndi("jdbc/APP_CONELE");
        
        JobBatch jobBatch = new JobBatch();
        // jobBatch.setId(1L);
        jobBatch.setName("job1");
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(application);
        
        try {
            jobBatchService.actualizar(jobBatch);
        } catch(Exception e) {
            LOGGER.error("Version conflicto", e);
            Assert.fail("Version conflicto");
        }
        
        prettyPrinter(jobBatch);
    }
}
