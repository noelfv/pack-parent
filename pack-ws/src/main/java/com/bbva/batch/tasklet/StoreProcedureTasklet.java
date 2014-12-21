package com.bbva.batch.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class StoreProcedureTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(StoreProcedureTasklet.class);
    private 
    
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
    	chunkContext.getStepContext().getJobParameters()
    	
    	Connection connection = getConnection(jndiName) 
        return null;
    }

}
