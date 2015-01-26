package com.bbva.batch.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
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
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.factory.JobBatchFactory;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;
import com.bbva.batch.service.ParameterBatchService;
import com.bbva.batch.service.StepBatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "jobs.application", "jobs.steps", "jobs.steps.job", "jobs.steps.parameters.step" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;
    
    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;
    
    @Resource(name = "parameterBatchService")
    private ParameterBatchService parameterBatchService;
    
    @Test
    public void _01EliminarApp() {
        List<ApplicationBatch> applications = applicationBatchService.listar(true);
        applicationBatchService.eliminar(applicationBatchService.listar());
        applications = applicationBatchService.listar();
        Assert.assertTrue("Sin elementos", applications.size() == 0);
    }

    @Test
    public void _02InsertarApp() {
        applicationBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
        applicationBatchService.insertar(new ApplicationBatch("gescar", "jdbc/APP_GESCAR"));
    }

    @Test
    public void _03listarAppLazy() {
        List<ApplicationBatch> applicationBatchs = applicationBatchService.listar(true);
        printer(applicationBatchs, exclude);
        Assert.assertTrue(applicationBatchs.size() == 2);
    }

    @Test
    public void _04EliminarJobs() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        List<JobBatch> jobs;
        
        if(application != null) {
            jobs = jobBatchService.listar(application.getId(), true);
            if (jobs != null) {
                jobBatchService.eliminar(jobs);
                jobs = jobBatchService.listar(application.getId(), true);
                Assert.assertTrue("Not delete", jobs.size() == 0);
            }
        }
    }

    @Test
    public void _05InsertarJobSimulacion() {
        JobBatch jobBatch = new JobBatch();
        jobBatch.setName("jobSimulacion");
        jobBatch.setDescription("Genera un archivo plano ");
        jobBatch.setType(JobBatchFactory.FLOW);
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(applicationBatchService.obtener("packBBVA"));
        
        jobBatch = jobBatchService.insertar(jobBatch);
        Assert.assertNotNull("No inserto", jobBatch.getId());
    }

    @Test
    public void _06InsertarJobCargaTerrirorioOficina() {
        JobBatch jobBatch = new JobBatch();
        jobBatch.setName("jobTerritorioOficina");
        jobBatch.setDescription("Realizar la actualización de los territorios y oficinas leyendo el servico de CentroWebServiceBBVA (GESCAR)");
        jobBatch.setType(JobBatchFactory.SIMPLE);
        jobBatch.setCronExpression("0 0 0/1 1/1 * ? *");
        jobBatch.setApplication(applicationBatchService.obtener("packBBVA"));
        
        jobBatchService.insertar(jobBatch);
        Assert.assertNotNull("No inserto actualizo", jobBatch.getId());
    }

    @Test
    public void _07listarJobsLazy() {
        List<JobBatch> jobs = jobBatchService.listar(applicationBatchService.obtener("packBBVA").getId(), true);
        printer(jobs, exclude);
        Assert.assertTrue(jobs.size() == 2);
    }
    
    @Test
    public void _08EliminarStepJobSimulacion() {
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
    public void _09EliminarStepJobCargaTerrirorioOficina() {
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
    public void _10InsertarStepJobSimulacion() {
        //applicationBatchService.insertar(new ApplicationBatch("packBBVA", "jdbc/APP_CONELE"));
    }
    
    @Test
    public void _11InsertarStepJobCargaTerrirorioOficina() {
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
    public void _12listarStepsLazy() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        if(application != null) {
            List<JobBatch> o = jobBatchService.listar(application.getId(), true);
            printer(o, exclude);
            Assert.assertTrue(o.size() == 2);
        }
    }
    
    @Test
    public void _13EliminarParametersStepsJobCargaTerrirorioOficina() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobTerritorioOficina", true);
        
        try {
            if(o != null) {
                LOGGER.info("Size: " + o.getSteps().size());
                for(StepBatch s : o.getSteps()) {
                    if(s != null && s.getParameters() != null) {
                        parameterBatchService.eliminar(s.getParameters());
                    }
                }
            }
        } catch(Exception e) {
            LOGGER.error("", e);
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void _14InsertarParametersStepsJobCargaTerrirorioOficina() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobTerritorioOficina", true);
        StepBatch s;
        ParameterBatch p;
        List<ParameterBatch> parameters;
        
        if(o != null) {
            s = stepBatchService.obtener(o.getId(), "desactivarTerritorios");
            parameters = new ArrayList<ParameterBatch>();
            
            p = new ParameterBatch("WRITER_QUERY", "QUERY", 1L, "String", "UPDATE CONELE.TBL_CE_IBM_TERRITORIO SET FLAG_ACTIVO=0");
            p.setStep(s);
            parameters.add(p);
            
            p = new ParameterBatch("WRITER_QUERY", "JNDI", 2L, "String", "jdbc/APP_CONELE");
            p.setStep(s);
            parameters.add(p);
            
            parameterBatchService.actualizar(parameters);
            
            String wsdlResource = "{\"class\":\"com.everis.webservice.WSDLResource\",\"elements\":{\"obtenerTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"Oficina\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Oficina\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaMadre\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaMadre\",\"ref\":null,\"type\":null,\"value\":null},\"Territorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinas\",\"ref\":null,\"type\":\"OficinaTerritorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Territorio\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaHija\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaHija\",\"ref\":null,\"type\":null,\"value\":null},\"Banca\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Banca\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"Suprarea\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Banca\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Suprarea\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaTerritorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"idTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaTerritorio\",\"ref\":null,\"type\":null,\"value\":null}},\"messages\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"obtenerOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null},\"listarAreaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null},\"listarTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"obtenerTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null},\"listarOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"listarOficinaTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"operations\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"name\":\"obtenerTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null}},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"name\":\"obtenerOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null}},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"name\":\"listarTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null}},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"name\":\"listarOficinaTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"name\":\"listarOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null}},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null}},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"name\":\"listarArea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null}}},\"targetNamespace\":\"http://www.bbva.com.pe/CentrosBBVAWebService/\",\"url\":\"http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl\"}";
            s = stepBatchService.obtener(o.getId(), "cargandoTerritorio");
            parameters = new ArrayList<ParameterBatch>();
            
            p = new ParameterBatch("READER_XML", "WSDL", 1L, "Byte", wsdlResource.getBytes());
            p.setStep(s);
            parameters.add(p);
            
            p = new ParameterBatch("READER_XML", "WSDL_OPERATION", 2L, "String", "listarOficinaTerritorioSuprarea");
            p.setStep(s);
            parameters.add(p);
            
            try {
                File ruleFile = new File("C:\\jquedena\\Proyectos\\workspace-pack-conele\\pack-parent\\pack-ws\\src\\main\\resources\\EvalCargaTerritorio.drl");
                InputStream input = new FileInputStream(ruleFile);
                ByteArrayOutputStream output = new ByteArrayOutputStream(); 
                IOUtils.copy(input, output);
                
                p = new ParameterBatch("READER_XML", "RULE", 3L, "Byte", output.toByteArray());
                p.setStep(s);
                parameters.add(p);    
            } catch (Exception e) {
                LOGGER.error("No se pudo obtener la regla");
            }
            
            String query = "MERGE INTO CONELE.TBL_CE_IBM_TERRITORIO A "
                    + "USING ( "
                    + "  SELECT :listaOficina.territorio.id ID, :listaOficina.territorio.nombreTerritorio NOMBRE "
                    + "  FROM DUAL) B "
                    + "ON (A.CODIGO = B.ID) "
                    + "WHEN MATCHED THEN "
                    + "  UPDATE SET A.DESCRIPCION = B.NOMBRE, "
                    + "         A.UBICACION = B.NOMBRE, "
                    + "         A.FLAG_ACTIVO = 1 "
                    + "WHEN NOT MATCHED THEN "
                    + "  INSERT (ID, CODIGO, DESCRIPCION, UBICACION, FLAG_PROV, FLAG_ACTIVO) "
                    + "  VALUES (CONELE.SEQ_CE_IBM_TERRITORIO.NEXTVAL, B.ID, B.NOMBRE, B.NOMBRE, 0, 1) ";
            
            p = new ParameterBatch("WRITER_TABLE", "QUERY", 1L, "String", query);
            p.setStep(s);
            parameters.add(p);
            
            p = new ParameterBatch("WRITER_TABLE", "JNDI", 2L, "String", "jdbc/APP_CONELE");
            p.setStep(s);
            parameters.add(p);      
            
            try {
                parameterBatchService.actualizar(parameters);
            } catch (Exception e) {
                LOGGER.error("", e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
