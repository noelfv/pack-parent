package com.bbva.batch.factory.impl;

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
    public void createJob1() {
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

}
