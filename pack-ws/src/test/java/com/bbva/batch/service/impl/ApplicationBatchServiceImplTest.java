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
import com.bbva.batch.service.ApplicationBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "jobs.application", "jobs.steps", "jobs.steps.job", "jobs.steps.parameters.step" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Test
    public void _01Eliminar() {
        ApplicationBatch application = new ApplicationBatch();

        application = applicationBatchService.obtener("packBBVA");
        if (application != null) {
            applicationBatchService.eliminar(application);
        }

        application = applicationBatchService.obtener("gescar");
        if (application != null) {
            applicationBatchService.eliminar(application);
        }

        List<ApplicationBatch> applications = applicationBatchService.listar();
        Assert.assertTrue("Sin elementos", applications.size() == 0);
    }

    @Test
    public void _02Insertar() {
        applicationBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
        applicationBatchService.insertar(new ApplicationBatch("gescar", "jdbc/APP_GESCAR"));
    }

    @Test
    public void _03listarLazy() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar(true);
        printer(applicationBatchs, exclude);
        Assert.assertTrue(applicationBatchs.size() == 2);
    }

}
