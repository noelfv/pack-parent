package com.bbva.batch.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.enums.DataType;
import com.bbva.batch.enums.ParameterType;

public class ParamUtil {
    
    private static final Logger LOG = Logger.getLogger(ParamUtil.class);
    private Map<ParameterType, Object> params;
    
    public ParamUtil(List<ParameterBatch> paramsBatch) {
        super();
        this.params = convertParameterBatch(paramsBatch);
        LOG.info(params);
    }

    private Map<ParameterType, Object> convertParameterBatch(List<ParameterBatch> paramsBatch) {
        Map<ParameterType, Object> params = new HashMap<ParameterType, Object>();
        ParameterType type;
        
        for(ParameterBatch p : paramsBatch) {
            type = ParameterType.valueOf("PARAM_" + p.getName());
            if(DataType.STRING.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getStringVal());
            } else if(DataType.LONG.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getLongVal());
            } else if(DataType.DOUBLE.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getDoubleVal());
            } else if(DataType.DATE.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getDateVal());
            }
        }
        
        return params;
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(ParameterType type) {
        T param = null;
        Object o = params.get(type);
        
        if(o != null) {
            param = (T) o;
        }
        
        return param;
    }
    
    public String getParamAsString(ParameterType type) {
        String param = getParam(type);
        return param;
    }

    public Long getParamAsLong(ParameterType type) {
        Long param = getParam(type);
        return param;
    }

    public Double getParamAsDouble(ParameterType type) {
        Double param = getParam(type);
        return param;
    }

    public Date getParamAsDate(ParameterType type) {
        Date param = getParam(type);
        return param;
    }
}
