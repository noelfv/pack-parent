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
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;
import com.bbva.batch.service.StepBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StepBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "jobs.application", "jobs.steps", "jobs.steps.job", "jobs.steps.parameters.step" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;
    
    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;
    
    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;

    @Test
    public void _01Eliminar() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobSimulacion");
        List<StepBatch> steps;
        
        if (o != null) {
            steps = stepBatchService.listar(o.getId());
            stepBatchService.eliminar(steps);
            steps = stepBatchService.listar(o.getId());
            Assert.assertTrue("Not delete", steps.size() == 0);
        }
    }

    @Test
    public void _02Insertar() {
        //applicationBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
    }

    @Test
    public void _03Actualizar() {
        //applicationBatchService.insertar(new ApplicationBatch("gescar", "jdbc/APP_GESCAR"));
    }

    @Test
    public void _04listarLazy() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobSimulacion");
        List<StepBatch> steps = stepBatchService.listar(o.getId(), true);
        printer(steps, exclude);
        Assert.assertTrue(steps.size() == 2);
    }

}
