package com.bbva.batch.tasklet;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bbva.batch.enums.OptionBatch;
import com.bbva.batch.service.JobBatchService;

public class StoreProcedureTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(StoreProcedureTasklet.class);
    private JobBatchService jobBatchService;
    
    private Connection getConnection(String jndiName) throws SQLException {
        InitialContext context = null;
        DataSource dataSource = null;

        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup(jndiName);
        } catch (NamingException e) {
            try {
                context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:/comp/env/" + jndiName);
            } catch (NamingException e1) {
                LOG.error("Error:", e1);
            }
        }

        return dataSource == null ? null : dataSource.getConnection();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    	Map<String, Object> params = chunkContext.getStepContext().getJobParameters();
    	String jndi = null;
    	Long idJob = 0L;
    	RepeatStatus result = RepeatStatus.CONTINUABLE;
    	Connection connection;
    	CallableStatement statement;
    	
    	try {
    		jndi = params.get(OptionBatch.BATCH_JNDI).toString();
        	idJob = Long.parseLong(params.get(OptionBatch.BATCH_ID_JOB).toString());
    	} catch (Exception e) {
    		chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);
    		contribution.setExitStatus(ExitStatus.FAILED);
    		result = RepeatStatus.FINISHED;
    	}
    	
    	LOG.info("jndi: [" + jndi + "]");
    	LOG.info("idJob: [" + idJob + "]");
    	
    	if(result.isContinuable()) {
    		connection = getConnection(jndi);
    		
    	}
    	
        return result;
    }

	public void setJobBatchService(JobBatchService jobBatchService) {
		this.jobBatchService = jobBatchService;
	}
    
}
