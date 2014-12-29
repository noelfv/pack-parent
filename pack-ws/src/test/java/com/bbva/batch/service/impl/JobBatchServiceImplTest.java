package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.JobBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "application", "steps", "steps.job", "steps.parameters.step" };

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @Test
    public void _01Eliminar() {
        JobBatch o = new JobBatch();

        o = jobBatchService.obtener(1L);
        if (o != null) {
            jobBatchService.eliminar(o);
            o = jobBatchService.obtener(1L);
            Assert.assertNull("Not delete", o);
        }

        o = jobBatchService.obtener(2L);
        if (o != null) {
            jobBatchService.eliminar(o);
            o = jobBatchService.obtener(2L);
            Assert.assertNull("Not delete", o);
        }
    }

    @Test
    public void _02Insertar() {
        //jobBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
    }

    @Test
    public void _03Actualizar() {
        //jobBatchService.insertar(new ApplicationBatch("gescar", "jdbc/APP_GESCAR"));
    }

    @Test
    public void _04listarLazy() {
        List<JobBatch> jobs = jobBatchService.listar(1L, true);
        printer(jobs, exclude);
        Assert.assertTrue(jobs.size() == 2);
    }
    
    @Test
    public void actualizar() {
        ApplicationBatch application = new ApplicationBatch();
        application.setId(1L);
        application.setVersion(0L);
        application.setName("packBBVA");
        application.setJndi("jdbc/APP_CONELE");

        JobBatch jobBatch = new JobBatch();
        jobBatch.setId(1L);
        jobBatch.setVersion(0L);
        jobBatch.setName("job1");
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(application);

        try {
            jobBatchService.actualizar(jobBatch);
        } catch (Exception e) {
            LOGGER.error("Version conflicto", e);
            Assert.fail("Version conflicto");
        }

        prettyPrinter(jobBatch);
    }
}
