package com.bbva.batch.factory.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
import com.everis.util.DBUtilSpring;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class JobBatchFactoryImplTest extends AbstractJUnit4Test {

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Resource(name = "jobBatchFactory")
    private JobBatchFactory jobBatchFactory;

    @Resource(name = "jobLauncher")
    private JobLauncher jobLauncher;

    @Before
    public void setUp() {
        super.setUp();
        DBUtilSpring.getInstance().setDataSource("jdbc/APP_CONELE", dataSource);
    }

    @Test
    public void _01createJobSolicitudCONELE() {
        ApplicationBatch application = new ApplicationBatch();
        application.setName("packBBVA");
        application.setJndi("jdbc/APP_CONELE");

        List<ParameterBatch> parameters;
        parameters = new ArrayList<ParameterBatch>();
        parameters.add(new ParameterBatch("READER_TABLE", "JNDI", 1L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("READER_TABLE", "SELECT", 2L, "String", "select SOLICITUD ,PRODUCTO_PACK ,SUBPRODUCTO_PACK ,ESTADO_PACK ,TO_CHAR(FECHA_ALTA, 'YYYYMMDD') FECHA_ALTA ,TRUNC(ROUND(IMPORTE, 2) * 100, 0) IMPORTE ,DIVISA ,TIPODOCUMENTO_PACK ,NUM_DOI ,CODIGO_CLIENTE ,CONTRATO ,PLAZO ,OFICINA ,EJECUTIVO ,TRUNC(ROUND(TASA, 2) * 100, 0) TASA"));
        parameters.add(new ParameterBatch("READER_TABLE", "FROM", 3L, "String", "from CONELE.V_SOLICITUD_IICE"));
        parameters.add(new ParameterBatch("READER_TABLE", "WHERE", 4L, "String", "where trunc(fecha_alta) >= trunc(add_months(sysdate, -32))"));
        parameters.add(new ParameterBatch("READER_TABLE", "SORT", 5L, "String", "fecha_alta"));
        parameters.add(new ParameterBatch("READER_TABLE", "PAGE_SIZE", 6L, "Long", 100L));
        
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RESOURCE", 1L, "String", "/mnt/compartido/conele/out/packDEMO.txt"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FIELDS", 2L, "String", "SOLICITUD,PRODUCTO_PACK,SUBPRODUCTO_PACK,ESTADO_PACK,FECHA_ALTA,IMPORTE,DIVISA,TIPODOCUMENTO_PACK,NUM_DOI,CODIGO_CLIENTE,CONTRATO,PLAZO,OFICINA,EJECUTIVO,TASA"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FORMAT", 2L, "String", "%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s"));

        List<StepBatch> steps = new ArrayList<StepBatch>();
        StepBatch step;

        step = new StepBatch();
        step.setId(1L);
        step.setName("step1");
        step.setReader(ItemReaderType.READER_TABLE.getName());
        step.setWriter(ItemWriterType.WRITER_TEXT_POSITION.getName());
        step.setParameters(parameters);
        steps.add(step);

        JobBatch jobBatch = new JobBatch();
        jobBatch.setId(1L);
        jobBatch.setName("job1");
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(application);
        jobBatch.setSteps(steps);

        SimpleJob job = jobBatchFactory.createJob(jobBatch);
        JobParameters param = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();

        JobExecution execution;
        try {
            execution = jobLauncher.run(job, param);
            Long outId = execution.getId();
            LOGGER.error("Id Proceso: [" + outId + "]");
            LOGGER.error("Estado del Proceso: " + execution.getStatus());
            if (!execution.getAllFailureExceptions().isEmpty()) {
                LOGGER.error("Estado del Proceso: " + execution.getAllFailureExceptions());
            }
        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error("", e);
            Assert.fail("JobExecutionAlreadyRunningException");
        } catch (JobRestartException e) {
            LOGGER.error("", e);
            Assert.fail("JobRestartException");
        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error("", e);
            Assert.fail("JobInstanceAlreadyCompleteException");
        } catch (JobParametersInvalidException e) {
            LOGGER.error("", e);
            Assert.fail("JobParametersInvalidException");
        }
    }

    @Test
    public void _02createJobCargaOficina() {
        ApplicationBatch application = new ApplicationBatch();
        application.setName("packBBVA");
        application.setJndi("jdbc/APP_CONELE");

        String wsdlResource = "{\"class\":\"com.everis.webservice.WSDLResource\",\"elements\":{\"obtenerTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"Oficina\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Oficina\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarAreaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaMadre\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaMadre\",\"ref\":null,\"type\":null,\"value\":null},\"Territorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinas\",\"ref\":null,\"type\":\"OficinaTerritorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Territorio\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaHija\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaHija\",\"ref\":null,\"type\":null,\"value\":null},\"Banca\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Banca\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"Suprarea\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreSuprarea\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"suprarea\",\"ref\":null,\"type\":\"Banca\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"Suprarea\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"obtenerOficinaRequest\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"listarOficinaTerritorioResponse\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"OficinaTerritorio\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"id\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"nombreOficina\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinaMadre\",\"ref\":null,\"type\":\"OficinaMadre\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"idTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficinasHijas\",\"ref\":null,\"type\":\"OficinaHija\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"OficinaTerritorio\",\"ref\":null,\"type\":null,\"value\":null}},\"messages\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"obtenerOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null},\"listarAreaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null},\"listarTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"obtenerTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null},\"listarOficinaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null},\"listarOficinaTerritorioSuprareaResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"listarOficinaTerritorioResponse\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"operations\":{\"obtenerTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorio\",\"part\":null},\"name\":\"obtenerTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"territorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerTerritorioResponse\",\"part\":null}},\"obtenerOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codOficina\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficina\",\"part\":null},\"name\":\"obtenerOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"oficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"obtenerOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"obtenerOficinaResponse\",\"part\":null}},\"listarTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codArea\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorio\",\"part\":null},\"name\":\"listarTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaTerritorio\",\"ref\":null,\"type\":\"Territorio\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarTerritorioResponse\",\"part\":null}},\"listarOficinaTerritorio\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorio\",\"part\":null},\"name\":\"listarOficinaTerritorio\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioResponse\",\"part\":null}},\"listarOficina\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codTerritorio\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficina\",\"part\":null},\"name\":\"listarOficina\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaResponse\",\"part\":null}},\"listarOficinaTerritorioSuprarea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"term\",\"ref\":null,\"type\":\"string\",\"value\":\"%\"}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"part\":null},\"name\":\"listarOficinaTerritorioSuprarea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaOficina\",\"ref\":null,\"type\":\"Oficina\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarOficinaTerritorioSuprareaResponse\",\"part\":null}},\"listarArea\":{\"class\":\"com.everis.webservice.WSDLOperation\",\"input\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"codBanca\",\"ref\":null,\"type\":\"string\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaRequest\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarArea\",\"part\":null},\"name\":\"listarArea\",\"output\":{\"class\":\"com.everis.webservice.WSDLMessage\",\"element\":{\"attributes\":[{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"tipoResultado\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"mensaje\",\"ref\":null,\"type\":\"string\",\"value\":null},{\"attributes\":[],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listaArea\",\"ref\":null,\"type\":\"Suprarea\",\"value\":null}],\"class\":\"com.everis.webservice.WSDLElement\",\"name\":\"listarAreaResponse\",\"ref\":null,\"type\":null,\"value\":null},\"name\":\"listarAreaResponse\",\"part\":null}}},\"targetNamespace\":\"http://www.bbva.com.pe/CentrosBBVAWebService/\",\"url\":\"http://192.168.247.1:8081/CentrosBBVAWS/CentrosBBVAWebService?wsdl\"}";
        
        List<ParameterBatch> parameters;        
        List<StepBatch> steps = new ArrayList<StepBatch>();
        StepBatch step;

        parameters = new ArrayList<ParameterBatch>();
        parameters.add(new ParameterBatch("WRITER_QUERY", "QUERY", 1L, "String", "UPDATE CONELE.TBL_CE_IBM_TERRITORIO SET FLAG_ACTIVO=0"));
        parameters.add(new ParameterBatch("WRITER_QUERY", "JNDI", 2L, "String", "jdbc/APP_CONELE"));
        
        step = new StepBatch();
        step.setId(1L);
        step.setName("desactivarTerritorios");
        step.setReader(null);
        step.setWriter(ItemWriterType.WRITER_QUERY.getName());
        step.setParameters(parameters);
        steps.add(step);
        
        parameters = new ArrayList<ParameterBatch>();
        try {
            parameters.add(new ParameterBatch("READER_XML", "WSDL", 1L, "Byte", wsdlResource.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e1) {
            LOGGER.error("No se pudo codificar.");
        }
        parameters.add(new ParameterBatch("READER_XML", "WSDL_OPERATION", 2L, "String", "listarOficinaTerritorioSuprarea"));
        
//        parameters.add(new ParameterBatch("WRITER_TABLE", "QUERY", 1L, "String", "/mnt/compartido/conele/out/packDEMO.txt"));
//        parameters.add(new ParameterBatch("WRITER_TABLE", "JNDI", 2L, "String", "jdbc/APP_CONELE"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "RESOURCE", 1L, "String", "/mnt/compartido/conele/out/packDEMOi.txt"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FIELDS", 2L, "String", "listaOficina.id"));
        parameters.add(new ParameterBatch("WRITER_TEXT_POSITION", "FORMAT", 2L, "String", "%-10s"));
        
        step = new StepBatch();
        step.setId(2L);
        step.setName("cargandoTerritorio");
        step.setReader(ItemReaderType.READER_XML.getName());
        step.setWriter(ItemWriterType.WRITER_TEXT_POSITION.getName());
        step.setParameters(parameters);
        steps.add(step);

        JobBatch jobBatch = new JobBatch();
        jobBatch.setId(1L);
        jobBatch.setName("job1");
        jobBatch.setCronExpression("0 0 1 ? * * *");
        jobBatch.setApplication(application);
        jobBatch.setSteps(steps);

        SimpleJob job = jobBatchFactory.createJob(jobBatch);
        JobParameters param = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();

        JobExecution execution;
        try {
            execution = jobLauncher.run(job, param);
            Long outId = execution.getId();
            LOGGER.error("Id Proceso: [" + outId + "]");
            LOGGER.error("Estado del Proceso: " + execution.getStatus());
            if (!execution.getAllFailureExceptions().isEmpty()) {
                LOGGER.error("Estado del Proceso: " + execution.getAllFailureExceptions());
            }
        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error("", e);
            Assert.fail("JobExecutionAlreadyRunningException");
        } catch (JobRestartException e) {
            LOGGER.error("", e);
            Assert.fail("JobRestartException");
        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error("", e);
            Assert.fail("JobInstanceAlreadyCompleteException");
        } catch (JobParametersInvalidException e) {
            LOGGER.error("", e);
            Assert.fail("JobParametersInvalidException");
        }
    }
    
}
