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
import com.bbva.batch.service.ApplicationBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
public class ApplicationBatchServiceImplTest extends AbstractJUnit4Test {

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Test
    public void listar1() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar();

        Assert.assertTrue(applicationBatchs.size() >= 0);
        try {
            String exclude[] = new String[]{"*.class", "jobs.application", "jobs.steps"};
            
            printer(applicationBatchService.obtener(1L, true), exclude);
            printer(applicationBatchs, exclude);
            printer(applicationBatchService.obtener(1L), exclude);
            
        } catch(Exception e) {
            LOGGER.info("No printer", e);
            return;
        }

        Assert.fail();
    }

    @Test
    public void listar2() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar(true);

        Assert.assertTrue(applicationBatchs.size() >= 0);
        try {
            String exclude[] = new String[]{"*.class", "jobs.application", "jobs.steps"};
            
            printer(applicationBatchService.obtener(1L, true), exclude);
            printer(applicationBatchs, exclude);
            printer(applicationBatchService.obtener(1L), exclude);
        } catch(Exception e) {
            LOGGER.info("No printer", e);
            Assert.fail();
        }
    }
    
    // @Test
    public void actualizar() {
        ApplicationBatch application = new ApplicationBatch();
        application.setId(1L);
        application.setVersion(0L);
        application.setName("packBBVA");
        application.setJndi("jdbc/APP_CONELE");
        
        try {
            applicationBatchService.actualizar(application);
        } catch(Exception e) {
            LOGGER.error("Version conflicto", e);
            Assert.fail("Version conflicto");
        }
        
        prettyPrinter(application);
    }
}
