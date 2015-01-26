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

import com.everis.core.exception.BussinesException;
import com.everis.util.DBUtilSpring;

public class QueryTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(QueryTasklet.class);
    private String dataSourceName;
    private String query;
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        RepeatStatus result = RepeatStatus.FINISHED;
        StepExecution execution = chunkContext.getStepContext().getStepExecution();
        ExitStatus exitStatus = ExitStatus.FAILED;
        
        try {
            if(DBUtilSpring.getInstance().execute(dataSourceName, query)) {
                exitStatus = ExitStatus.COMPLETED;
            }
        } catch (BussinesException e) {
            LOG.error("No se pudo ejecutar la consulta: [" + query + "]");
            execution.addFailureException(e);
        }
        
        execution.setExitStatus(exitStatus);
        contribution.setExitStatus(exitStatus);
        
        return result;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
