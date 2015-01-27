package com.bbva.batch.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.enums.DataType;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.enums.ParameterType;

public class ParamUtil {

    private static final Logger LOG = Logger.getLogger(ParamUtil.class);
    private Map<ParameterType, Object> params;

    public ParamUtil(List<ParameterBatch> paramsBatch, boolean isReader) {
        super();
        this.params = convertParameterBatch(paramsBatch, isReader);
        LOG.info(params);
    }

    private Map<ParameterType, Object> convertParameterBatch(List<ParameterBatch> paramsBatch, boolean isReader) {
        Map<ParameterType, Object> params = new HashMap<ParameterType, Object>();
        ParameterType type;
        ItemReaderType readerType;
        ItemWriterType writerType;
        
        for (ParameterBatch p : paramsBatch) {
            
            try {
                if(isReader) {
                    readerType = ItemReaderType.valueOf(p.getName());
                    LOG.info(readerType.getName());
                } else {
                    writerType = ItemWriterType.valueOf(p.getName());
                    LOG.info(writerType.getName());
                }
            } catch(Exception e) {
                continue;
            }
            
            type = ParameterType.valueOf("PARAM_" + p.getType());
            if (DataType.STRING.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getStringVal());
            } else if (DataType.LONG.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getLongVal());
            } else if (DataType.DOUBLE.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getDoubleVal());
            } else if (DataType.DATE.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getDateVal());
            } else if (DataType.BYTE.getName().equalsIgnoreCase(type.getDataType())) {
                params.put(type, p.getByteVal());
            }
        }

        return params;
    }

    public <T> T getParam(ParameterType type) {
        return getParam(type, true);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getParam(ParameterType type, boolean convertByte) {
        T param = null;
        Object o = params.get(type);

        if(DataType.BYTE.getName().equalsIgnoreCase(type.getDataType()) && convertByte) {
            o = new String((byte[]) o);
        } 
        
        if (o != null) {
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

    public byte[] getParamAsByte(ParameterType type) {
        byte[] param = getParam(type, false);
        return param;
    }
    
    public void setObject(ParameterType type, Object o) {
        params.put(type, o);
    }
    
    public boolean exists(ParameterType type) {
        return params.containsKey(type);
    }
}
