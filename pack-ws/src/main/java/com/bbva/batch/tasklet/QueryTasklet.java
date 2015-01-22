package com.bbva.batch.tasklet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class QueryTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(QueryTasklet.class);
    private DataSource datasource;
    private String query;
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        RepeatStatus result = RepeatStatus.CONTINUABLE;
        StepExecution execution = chunkContext.getStepContext().getStepExecution();
        
        try {
            Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();            
            execution.setExitStatus(ExitStatus.COMPLETED);
            contribution.setExitStatus(ExitStatus.COMPLETED);
        } catch (SQLException e) {
            LOG.error("No se pudo ejecutar la consulta: [" + query + "]");
            execution.addFailureException(e);
            execution.setExitStatus(ExitStatus.FAILED);
        }
        
        return result;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
