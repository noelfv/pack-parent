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
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
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
import com.bbva.batch.util.DeciderBatch;
import com.bbva.batch.util.DeciderParam;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContextTest.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationBatchServiceImplTest extends AbstractJUnit4Test {

    private String exclude[] = new String[] { "*.class", "jobs.application", "jobs.steps", "jobs.steps.job", "jobs.steps.parameters.step" };
    private static final String path = "C:\\jquedena\\Proyectos\\workspace-pack-conele\\pack-parent\\pack-ws\\src\\main\\resources\\";
    
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
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobSimulacion", true);

        List<StepBatch> steps = new ArrayList<StepBatch>();
        StepBatch step;

        step = stepBatchService.obtener(o.getId(), "generarSolicitudCONELE");
        if(step == null) {
            step = new StepBatch();
        }
        step.setName("generarSolicitudCONELE");
        step.setOrder(1L);
        step.setDescription("A partir de los datos de la tabla V_SOLICITUD generar un archivo de las solicitudes del sistema PLD unificado");
        step.setReader(ItemReaderType.READER_TABLE.getName());
        step.setWriter(ItemWriterType.WRITER_TEXT_POSITION.getName());
        step.setNextStep("decisionSolicitudIICE");
        step.setJob(o);
        steps.add(step);
               
        List<DeciderBatch> flowDecider = new ArrayList<DeciderBatch>();
        flowDecider.add(new DeciderBatch(FlowExecutionStatus.COMPLETED.getName(), "generarSolicitudIICE"));
        flowDecider.add(new DeciderBatch(FlowExecutionStatus.FAILED.getName(), "endFail"));        
        
        step = stepBatchService.obtener(o.getId(), "decisionSolicitudIICE");
        if(step == null) {
            step = new StepBatch();
        }
        step.setName("decisionSolicitudIICE");
        step.setOrder(2L);
        step.setDescription("Evalua si usa el esquema IICE para la generación del archivo");
        step.setWriter(ItemWriterType.WRITER_DECISOR.getName());
        step.setNextStep(new JSONSerializer().deepSerialize(flowDecider));
        step.setJob(o);
        steps.add(step);

        step = stepBatchService.obtener(o.getId(), "generarSolicitudIICE");
        if(step == null) {
            step = new StepBatch();
        }
        step.setName("generarSolicitudIICE");
        step.setOrder(3L);
        step.setDescription("A partir de los datos de la tabla V_SOLICITUD generar un archivo de las solicitudes del sistema PLD unificado");
        step.setReader(ItemReaderType.READER_TABLE.getName());
        step.setWriter(ItemWriterType.WRITER_TEXT_POSITION.getName());
        step.setNextStep("end");
        step.setJob(o);
        steps.add(step);
        
        stepBatchService.actualizar(steps);
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
        
        step = new StepBatch();
        step.setJob(o);
        step.setName("cargandoOficina");
        step.setOrder(3L);
        step.setDescription("Inserta o actualiza la lista de oficina");
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
    public void _13EliminarParametersStepsJobCargaTerritorioOficina() {
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
    public void _14InsertarParametersStepsJobCargaTerritorioOficina() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobTerritorioOficina", true);
        StepBatch s;
        ParameterBatch p;
        List<ParameterBatch> parameters;
        String wsdlResource = "{\"class\":\"com.everis.webservice.WSDLResource\",\"elements\":{\"obtenerTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"Oficina\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Oficina\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaMadre\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaMadre\",\"ref\":null,\"type\":null,\"value\":null},\"Territorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinas\",\"ref\":null,\"type\":\"OficinaTerritorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Territorio\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaHija\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaHija\",\"ref\":null,\"type\":null,\"value\":null},\"Banca\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Banca\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"Suprarea\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Banca\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Suprarea\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaTerritorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"idTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaTerritorio\",\"ref\":null,\"type\":null,\"value\":null}},\"messages\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"obtenerOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null},\"listarAreaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null},\"listarTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"obtenerTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null},\"listarOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"listarOficinaTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"operations\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"name\":\"obtenerTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null}},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"name\":\"obtenerOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null}},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"name\":\"listarTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null}},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"name\":\"listarOficinaTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"name\":\"listarOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null}},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null}},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"name\":\"listarArea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null}}},\"targetNamespace\":\"http://www.bbva.com.pe/CentrosBBVAWebService/\",\"url\":\"http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl\"}";
        String queryTerritorio = "MERGE INTO CONELE.TBL_CE_IBM_TERRITORIO A "
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
        String queryOficina = "MERGE INTO CONELE.TBL_CE_IBM_OFICINA A "
                + "USING ( "
                + "  SELECT (SELECT ID FROM CONELE.TBL_CE_IBM_TERRITORIO WHERE CODIGO = :listaOficina.territorio.id) ID_TERRITORIO  "
                + "  , :listaOficina.id ID "
                + "  , :listaOficina.nombreOficina NOMBRE_OFICINA "
                + "  FROM DUAL) B "
                + "ON (A.CODIGO = B.ID) "
                + "WHEN MATCHED THEN "
                + "  UPDATE SET A.DESCRIPCION = B.NOMBRE_OFICINA, "
                + "         A.ID_TERRITORIO_FK = B.ID_TERRITORIO, "
                + "         A.FLAG_ACTIVO = 1 "
                + "WHEN NOT MATCHED THEN "
                + "  INSERT (ID, CODIGO, DESCRIPCION, ID_TERRITORIO_FK, ID_OFICINA_PRINCIPAL_FK, FLAG_ACTIVO) "
                + "  VALUES (CONELE.SEQ_CE_IBM_OFICINA.NEXTVAL, B.ID, B.NOMBRE_OFICINA, B.ID_TERRITORIO, NULL, 1)";
        
        if(o != null) {
            parameters = new ArrayList<ParameterBatch>();         
            parameters.add(new ParameterBatch("WRITER_QUERY", "QUERY", 1L, "String", "UPDATE CONELE.TBL_CE_IBM_TERRITORIO SET FLAG_ACTIVO=0"));
            parameters.add(new ParameterBatch("WRITER_QUERY", "JNDI", 2L, "String", "jdbc/APP_CONELE"));
            insertarParametros(o, "desactivarTerritorios", parameters);
            
            parameters = new ArrayList<ParameterBatch>();
            parameters.add(new ParameterBatch("READER_XML", "WSDL", 1L, "Byte", wsdlResource.getBytes()));
            parameters.add(new ParameterBatch("READER_XML", "WSDL_OPERATION", 2L, "String", "listarOficinaTerritorioSuprarea"));
            parameters.add(new ParameterBatch("READER_XML", "RULE", 3L, "Byte", obtenerBytes(path + "EvalCargaTerritorio.drl")));
            parameters.add(new ParameterBatch("WRITER_TABLE", "QUERY", 1L, "String", queryTerritorio));
            parameters.add(new ParameterBatch("WRITER_TABLE", "JNDI", 2L, "String", "jdbc/APP_CONELE"));
            insertarParametros(o, "cargandoTerritorio", parameters);
            
            parameters = new ArrayList<ParameterBatch>();
            parameters.add(new ParameterBatch("READER_XML", "WSDL", 1L, "Byte", wsdlResource.getBytes()));
            parameters.add(new ParameterBatch("READER_XML", "WSDL_OPERATION", 2L, "String", "listarOficinaTerritorioSuprarea"));
            parameters.add(new ParameterBatch("READER_XML", "RULE", 3L, "Byte", obtenerBytes(path + "EvalCargaOficina.drl")));
            parameters.add(new ParameterBatch("WRITER_TABLE", "QUERY", 1L, "String", queryOficina));
            parameters.add(new ParameterBatch("WRITER_TABLE", "JNDI", 2L, "String", "jdbc/APP_CONELE"));
            insertarParametros(o, "cargandoOficina", parameters);
        }
    }

    private void insertarParametros(JobBatch o, String stepName, List<ParameterBatch> params) {
        StepBatch s = stepBatchService.obtener(o.getId(), stepName);
        for(ParameterBatch p : params) {
            p.setStep(s);
        }
        parameterBatchService.actualizar(params);
    }
    
    @Test
    public void _15InsertarParametersStepsJobSimulacion() {
        ApplicationBatch application = applicationBatchService.obtener("packBBVA");
        JobBatch o = jobBatchService.obtener(application.getId(), "jobSimulacion", true);
        List<ParameterBatch> parameters;
        
        parameters = new ArrayList<ParameterBatch>();
        List<DeciderParam> deciderParams = new ArrayList<DeciderParam>();
        String decisorParam;
        
        deciderParams = new ArrayList<DeciderParam>();
        deciderParams.add(new DeciderParam("flagIICE", "SELECT VALOR_VARIABLE FROM CONELE.TBL_CE_IBM_PARAMETROS_CONF WHERE CODIGO_APLICATIVO=1000 AND NOMBRE_VARIABLE='PB_APAGAR_APLICACION_PLD'"));
        decisorParam = new JSONSerializer().deepSerialize(deciderParams); 
        
        parameters = new ArrayList<ParameterBatch>();
        parameters.add(new ParameterBatch("WRITER_DECISOR", "JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("WRITER_DECISOR", "DECISOR_PARAM", 2L, "Byte", decisorParam.getBytes()));
        parameters.add(new ParameterBatch("WRITER_DECISOR", "RULE", 3L, "Byte", obtenerBytes(path + "Decisor.drl")));     
        insertarParametros(o, "decisionSolicitudIICE", parameters);
        
        
        
        deciderParams.add(new DeciderParam("PB_RUTA_ARCHIVO", "SELECT VALOR_VARIABLE FROM CONELE.TBL_CE_IBM_PARAMETROS_CONF WHERE CODIGO_APLICATIVO=1000 AND NOMBRE_VARIABLE='PB_RUTA_ARCHIVO'"));
        deciderParams.add(new DeciderParam("PB_NOMBRE_ARCHIVO", "SELECT VALOR_VARIABLE FROM CONELE.TBL_CE_IBM_PARAMETROS_CONF WHERE CODIGO_APLICATIVO=1000 AND NOMBRE_VARIABLE='PB_NOMBRE_ARCHIVO'"));
        deciderParams.add(new DeciderParam("PB_FORMATO_FECHA_SUFIJO", "SELECT VALOR_VARIABLE FROM CONELE.TBL_CE_IBM_PARAMETROS_CONF WHERE CODIGO_APLICATIVO=1000 AND NOMBRE_VARIABLE='PB_FORMATO_FECHA_SUFIJO'"));
        deciderParams.add(new DeciderParam("PB_MESES_ANTERIORES", "SELECT VALOR_VARIABLE FROM CONELE.TBL_CE_IBM_PARAMETROS_CONF WHERE CODIGO_APLICATIVO=1000 AND NOMBRE_VARIABLE='PB_MESES_ANTERIORES'"));
        decisorParam = new JSONSerializer().deepSerialize(deciderParams);

        parameters = new ArrayList<ParameterBatch>();
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_PARAM", 1L, "String", obtenerBytes(path + "ParamsCustom.drl")));
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_DECISOR_PARAM", 1L, "String", decisorParam.getBytes()));
        parameters.add(new ParameterBatch("READER_TABLE", "JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("READER_TABLE", "SELECT", 2L, "String", "select SOLICITUD ,PRODUCTO_PACK ,SUBPRODUCTO_PACK ,ESTADO_PACK ,TO_CHAR(FECHA_ALTA, 'YYYYMMDD') FECHA_ALTA ,TRUNC(ROUND(IMPORTE, 2) * 100, 0) IMPORTE ,DIVISA ,TIPODOCUMENTO_PACK ,NUM_DOI ,CODIGO_CLIENTE ,CONTRATO ,PLAZO ,OFICINA ,EJECUTIVO ,TRUNC(ROUND(TASA, 2) * 100, 0) TASA"));
        parameters.add(new ParameterBatch("READER_TABLE", "FROM", 3L, "String", "from CONELE.V_SOLICITUD"));
        parameters.add(new ParameterBatch("READER_TABLE", "WHERE", 4L, "String", "where trunc(fecha_alta) >= trunc(add_months(sysdate, -32))"));
        parameters.add(new ParameterBatch("READER_TABLE", "SORT", 5L, "String", "fecha_alta"));
        parameters.add(new ParameterBatch("READER_TABLE", "PAGE_SIZE", 6L, "Long", 100L));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_PARAM", 1L, "String", obtenerBytes(path + "ParamsCustom.drl")));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_DECISOR_PARAM", 1L, "String", decisorParam.getBytes()));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RESOURCE", 1L, "String", "/mnt/compartido/conele/out/packDEMO.txt"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FIELDS", 2L, "String", "SOLICITUD,PRODUCTO_PACK,SUBPRODUCTO_PACK,ESTADO_PACK,FECHA_ALTA,IMPORTE,DIVISA,TIPODOCUMENTO_PACK,NUM_DOI,CODIGO_CLIENTE,CONTRATO,PLAZO,OFICINA,EJECUTIVO,TASA"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FORMAT", 3L, "String", "%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "APPEND", 4L, "String", "False"));
        insertarParametros(o, "generarSolicitudCONELE", parameters);
        
        
        
        parameters = new ArrayList<ParameterBatch>();
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_PARAM", 1L, "String", obtenerBytes(path + "ParamsCustom.drl")));
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("READER_TABLE", "RULE_DECISOR_PARAM", 1L, "String", decisorParam.getBytes()));
        parameters.add(new ParameterBatch("READER_TABLE", "JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("READER_TABLE", "SELECT", 2L, "String", "select SOLICITUD ,PRODUCTO_PACK ,SUBPRODUCTO_PACK ,ESTADO_PACK ,TO_CHAR(FECHA_ALTA, 'YYYYMMDD') FECHA_ALTA ,TRUNC(ROUND(IMPORTE, 2) * 100, 0) IMPORTE ,DIVISA ,TIPODOCUMENTO_PACK ,NUM_DOI ,CODIGO_CLIENTE ,CONTRATO ,PLAZO ,OFICINA ,EJECUTIVO ,TRUNC(ROUND(TASA, 2) * 100, 0) TASA"));
        parameters.add(new ParameterBatch("READER_TABLE", "FROM", 3L, "String", "from CONELE.V_SOLICITUD_IICE"));
        parameters.add(new ParameterBatch("READER_TABLE", "WHERE", 4L, "String", "where trunc(fecha_alta) >= trunc(add_months(sysdate, -32))"));
        parameters.add(new ParameterBatch("READER_TABLE", "SORT", 5L, "String", "fecha_alta"));
        parameters.add(new ParameterBatch("READER_TABLE", "PAGE_SIZE", 6L, "Long", 100L));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_PARAM", 1L, "String", obtenerBytes(path + "ParamsCustom.drl")));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RULE_DECISOR_PARAM", 1L, "String", decisorParam.getBytes()));        
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RESOURCE", 1L, "String", "/mnt/compartido/conele/out/packDEMO.txt"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FIELDS", 2L, "String", "SOLICITUD,PRODUCTO_PACK,SUBPRODUCTO_PACK,ESTADO_PACK,FECHA_ALTA,IMPORTE,DIVISA,TIPODOCUMENTO_PACK,NUM_DOI,CODIGO_CLIENTE,CONTRATO,PLAZO,OFICINA,EJECUTIVO,TASA"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FORMAT", 3L, "String", "%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "APPEND", 4L, "String", "True"));
        insertarParametros(o, "generarSolicitudIICE", parameters);
    }
    
    @Test
    public void _16InsertarJobDepuracionArchivo() {
        JobBatch jobBatch = new JobBatch();
        jobBatch.setName("jobDepuracionArchivo");
        jobBatch.setDescription("Eliminar los archivos mas antiguos");
        jobBatch.setType(JobBatchFactory.SIMPLE);
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(applicationBatchService.obtener("packBBVA"));
        
        jobBatch = jobBatchService.insertar(jobBatch);
        Assert.assertNotNull("No inserto", jobBatch.getId());
    }
}
