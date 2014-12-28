package com.bbva.batch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;
import com.bbva.batch.service.ParameterBatchService;
import com.bbva.batch.service.StepBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[]{"*.class", "jobs.application", "jobs.steps"};

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;

    @Resource(name = "parameterBatchService")
    private ParameterBatchService parameterBatchService;

    @Test
    public void actualizar() {
        ApplicationBatch application = new ApplicationBatch();
        application.setId(2L);

        try {
            application = applicationBatchService.obtener(application.getId());
            if (application != null) {
                applicationBatchService.eliminar(application);
            }

            application = new ApplicationBatch();
            application.setId(2L);
            application.setName("packBBVA");
            application.setJndi("jdbc/APP_CONELE");
            applicationBatchService.actualizar(application);
        } catch (HibernateOptimisticLockingFailureException e) {
            LOGGER.debug("Version conflicto", e);
        }

        application = applicationBatchService.obtener(application.getId(), true);

        printer(application, exclude, "actualizar");
    }

    @Test
    public void listar() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar();

        try {
            printer(applicationBatchs, exclude);
        } catch (LazyInitializationException e) {
            LOGGER.info("No printer", e);
            Assert.assertTrue("LazyInitializationException", true);
        }

        Assert.assertTrue(applicationBatchs.size() > 0);
    }

    @Test
    public void listarLazy() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar(true);
        try {
            printer(applicationBatchs, exclude);
        } catch (Exception e) {
            LOGGER.info("No printer", e);
            Assert.fail();
        }
        Assert.assertTrue(applicationBatchs.size() > 0);
    }

    @Test
    public void insertar() {
        ApplicationBatch application = new ApplicationBatch();
        application.setId(1L);

        try {
            application = applicationBatchService.obtener(application.getId());
            if (application != null) {
                applicationBatchService.eliminar(application);
            }

            application = new ApplicationBatch();
            application.setId(1L);
            application.setVersion(0L);
            application.setName("packBBVA");
            application.setJndi("jdbc/APP_CONELE");
            applicationBatchService.insertar(application);
        } catch (HibernateOptimisticLockingFailureException e) {
            LOGGER.debug("Version conflicto", e);
        }

        application = applicationBatchService.obtener(application.getId(), true);

        printer(application, exclude, "insertar");
    }


}
