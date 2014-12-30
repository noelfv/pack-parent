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
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "application", "steps", "steps.job", "steps.parameters.step" };

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;
    
    @Test
    public void _01Eliminar() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = new JobBatch();
        List<JobBatch> jobs;
        
        jobs = jobBatchService.listar(application.getId(), true);
        if (jobs != null) {
            jobBatchService.eliminar(jobs);
            jobs = jobBatchService.listar(application.getId(), true);
            Assert.assertTrue("Not delete", jobs.size() == 0);
        }

        application = applicationBatchService.obtener("gescar");
        o = jobBatchService.obtener(application.getId(), "jobOficina");
        if (o != null) {
            jobBatchService.eliminar(o);
            o = jobBatchService.obtener(o.getId());
            Assert.assertNull("Not delete", o);
        }
    }

    @Test
    public void _02Insertar() {
        JobBatch jobBatch = new JobBatch();
        jobBatch.setName("jobSimulacion");
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(applicationBatchService.obtener("packBBVA"));
        
        jobBatch = jobBatchService.insertar(jobBatch);
        Assert.assertNotNull("No inserto", jobBatch.getId());
    }

    @Test
    public void _03Actualizar() {
        JobBatch jobBatch = new JobBatch();
        jobBatch.setName("jobOficina");
        jobBatch.setCronExpression("0 0 0/1 1/1 * ? *");
        jobBatch.setApplication(applicationBatchService.obtener("gescar"));
        
        jobBatchService.actualizar(jobBatch);
        Assert.assertNotNull("No inserto actualizo", jobBatch.getId());
    }

    @Test
    public void _04listarLazy() {
        List<JobBatch> jobs = jobBatchService.listar(applicationBatchService.obtener("packBBVA").getId(), true);
        printer(jobs, exclude);
        Assert.assertTrue(jobs.size() == 1);
    }
}
