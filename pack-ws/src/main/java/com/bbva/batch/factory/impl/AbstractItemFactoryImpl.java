package com.bbva.batch.factory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.util.DeciderParam;
import com.bbva.batch.util.ParamUtil;
import com.bbva.drools.DroolsRuleRunner;
import com.everis.core.exception.BussinesException;
import com.everis.util.DBUtilSpring;

import flexjson.JSONDeserializer;

public abstract class AbstractItemFactoryImpl {

    private static final Logger LOG = Logger.getLogger(AbstractItemFactoryImpl.class);
    
    @SuppressWarnings("unchecked")
    protected void afterConfigureParam(ParamUtil params) {
        if(params.exists(ParameterType.PARAM_RULE_DECISOR_PARAM)) {
            Map<String, String> ruleParams = new HashMap<String, String>();
            JSONDeserializer<DeciderParam> deserializerDecider = new JSONDeserializer<DeciderParam>();
            List<DeciderParam> deciderParams = (List<DeciderParam>) deserializerDecider.deserialize(params.getParamAsString(ParameterType.PARAM_RULE_DECISOR_PARAM));
            String dataSourceName = params.getParamAsString(ParameterType.PARAM_RULE_JNDI);
            
            for(DeciderParam param : deciderParams) {
                try {
                    param.setValue(DBUtilSpring.getInstance().executeQueryUniqueResult(dataSourceName, param.getQuery()));
                    ruleParams.put(param.getName(), param.getValue());
                    LOG.info("{name: " + param.getName() + ", value: " + param.getValue() +  "}");
                } catch (BussinesException e) {
                    break;
                }
            }
        
            DroolsRuleRunner evaluator = new DroolsRuleRunner();
            evaluator.runRules(new byte[][]{params.getParamAsByte(ParameterType.PARAM_RULE_PARAM)}, new Object[]{params, ruleParams}, null);
        }
    }
}
