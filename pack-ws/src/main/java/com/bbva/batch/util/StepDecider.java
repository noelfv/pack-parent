package com.bbva.batch.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import com.bbva.drools.DroolsRuleRunner;
import com.everis.core.exception.BussinesException;
import com.everis.util.DBUtilSpring;

public class StepDecider implements JobExecutionDecider {

    private List<DeciderParam> params;
    private String dataSourceName;
    private byte[] rule;
    
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        DroolsRuleRunner evaluator = new DroolsRuleRunner();
        Map<String, Object> globals = new HashMap<String, Object>();
        Map<String, String> status = new HashMap<String, String>();
        
        status.put("status", "COMPLETED");
        
        for(DeciderParam param : params) {
            try {
                param.setValue(DBUtilSpring.getInstance().executeQueryUniqueResult(dataSourceName, param.getQuery()));
            } catch (BussinesException e) {
                status.put("status", "FAILED");
                stepExecution.addFailureException(e);
                stepExecution.setExitStatus(ExitStatus.FAILED);
                
                jobExecution.addFailureException(e);
                jobExecution.setExitStatus(ExitStatus.FAILED);
                break;
            }
        }
        
        if(FlowExecutionStatus.COMPLETED.getName().equalsIgnoreCase(status.get("status"))) {
            globals.put("status", status);
            evaluator.runRules(new byte[][]{rule}, params.toArray(), globals);
        }
        
        return new FlowExecutionStatus(status.get("status"));
    }

    public List<DeciderParam> getParams() {
        return params;
    }

    public void setParams(List<DeciderParam> params) {
        this.params = params;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public byte[] getRule() {
        return rule;
    }

    public void setRule(byte[] rule) {
        this.rule = rule;
    }

}
