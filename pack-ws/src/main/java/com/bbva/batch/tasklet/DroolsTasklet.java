package com.bbva.batch.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bbva.batch.util.DeciderParam;
import com.bbva.drools.DroolsRuleRunner;
import com.everis.core.exception.BussinesException;
import com.everis.util.DBUtilSpring;

public class DroolsTasklet implements Tasklet {

    private static final Logger LOG = Logger.getLogger(DroolsTasklet.class);
    private byte[] rule;
    private String dataSourceName;
    private List<DeciderParam> params;
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        RepeatStatus result = RepeatStatus.FINISHED;
        StepExecution execution = chunkContext.getStepContext().getStepExecution();
        ExitStatus exitStatus = ExitStatus.COMPLETED;
        DroolsRuleRunner evaluator = new DroolsRuleRunner();
        Map<String, Object> globals = new HashMap<String, Object>();
        Map<String, String> status = new HashMap<String, String>();
        
        status.put("status", "COMPLETED");
        globals.put("status", status);
        
        for(DeciderParam param : params) {
            try {
                param.setValue(DBUtilSpring.getInstance().executeQueryUniqueResult(dataSourceName, param.getQuery()));
                LOG.info("{name: " + param.getName() + ", value: " + param.getValue() +  "}");
            } catch (BussinesException e) {
                status.put("status", "FAILED");
                execution.addFailureException(e);
                exitStatus = ExitStatus.FAILED;
                break;
            }
        }        
                
        if(ExitStatus.COMPLETED.compareTo(exitStatus) == 0) {
            evaluator.runRules(new byte[][]{rule}, params.toArray(), globals);
            exitStatus = new ExitStatus(status.get("status"));
        }
        
        execution.setExitStatus(exitStatus);
        contribution.setExitStatus(exitStatus);
        
        return result;
    }

    public byte[] getRule() {
        return rule;
    }

    public void setRule(byte[] rule) {
        this.rule = rule;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public List<DeciderParam> getParams() {
        return params;
    }

    public void setParams(List<DeciderParam> params) {
        this.params = params;
    }

}
