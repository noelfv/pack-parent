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
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;
import com.bbva.batch.service.StepBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StepBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "application" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;
    
    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;
    
    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;

    /*** @Test
    public void _00ListClasspath() {
        LOGGER.info(System.getProperty("java.class.path"));
    } ***/
    
    @Test
    public void _01EliminarStepJobSimulacion() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobSimulacion", true);
        List<StepBatch> steps;
        
        if (o != null) {
            steps = stepBatchService.listar(o.getId());
            stepBatchService.eliminar(steps);
            steps = stepBatchService.listar(o.getId());
            Assert.assertTrue("Not delete", steps.size() == 0);
        }
    }

    @Test
    public void _02EliminarStepJobCargaTerrirorioOficina() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobTerritorioOficina", true);
        List<StepBatch> steps;
        
        if (o != null) {
            steps = stepBatchService.listar(o.getId());
            stepBatchService.eliminar(steps);
            steps = stepBatchService.listar(o.getId());
            Assert.assertTrue("Not delete", steps.size() == 0);
        }
    }
    
    @Test
    public void _03InsertarStepJobSimulacion() {
        //applicationBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
    }
    
    @Test
    public void _03InsertarStepJobCargaTerrirorioOficina() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobTerritorioOficina");
        StepBatch step;
        
        step = new StepBatch();
        step.setJob(o);
        step.setName("desactivarTerritorios");
        step.setOrder(1L);
        step.setDescription("Desactiva los territorios");
        step.setReader(null);
        step.setWriter(ItemWriterType.WRITER_QUERY.getName());
        stepBatchService.insertar(step);
        
        step = new StepBatch();
        step.setJob(o);
        step.setName("cargandoTerritorio");
        step.setOrder(2L);
        step.setDescription("Inserta o actualiza la lista de territorios");
        step.setReader(ItemReaderType.READER_XML.getName());
        step.setWriter(ItemWriterType.WRITER_TABLE.getName());
        stepBatchService.insertar(step);
    }
    
    @Test
    public void _04listarLazy() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        List<JobBatch> o = jobBatchService.listar(application.getId(), true);
        printer(o, exclude);
        Assert.assertTrue(o.size() == 2);
    }

}
