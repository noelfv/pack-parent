package com.bbva.batch.factory.impl;

import java.util.List;

import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.StepDeciderFactory;
import com.bbva.batch.util.DeciderParam;
import com.bbva.batch.util.ParamUtil;
import com.bbva.batch.util.StepDecider;

import flexjson.JSONDeserializer;

@Component("stepDeciderFactory")
public class StepDeciderFactoryImpl implements StepDeciderFactory {

    @SuppressWarnings("unchecked")
    @Override
    public JobExecutionDecider create(StepBatch stepBatch) throws Exception {
        StepDecider stepDecider = new StepDecider();
        JSONDeserializer<DeciderParam> deserializerDecider = new JSONDeserializer<DeciderParam>();
        ParamUtil params = new ParamUtil(stepBatch.getParameters(), false);
        
        stepDecider.setDataSourceName(params.getParamAsString(ParameterType.PARAM_JNDI));
        stepDecider.setParams((List<DeciderParam>) deserializerDecider.deserialize(params.getParamAsString(ParameterType.PARAM_DECISOR_PARAM)));
        stepDecider.setRule(params.getParamAsByte(ParameterType.PARAM_RULE));
        
        return stepDecider;
    }

}
