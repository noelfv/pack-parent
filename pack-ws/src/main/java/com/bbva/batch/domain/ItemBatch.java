package com.bbva.batch.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ItemBatch {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Map<String, Object> values;
    
    public ItemBatch() {
        values = new HashMap<String, Object>();
    }

    public Object getObject(String key) {
        return values.get(key);
    }
    
    public void setObject(String key, Object value) {
        values.put(key, value);
    }

    public Double getDouble(String key) {
        return (Double) values.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) values.get(key);
    }

    public Long getLong(String key) {
        return (Long) values.get(key);
    }

    public BigDecimal getBigDecimal(String key) {
        return (BigDecimal) values.get(key);
    }

    public Number getNumber(String key) {
        return (Number) values.get(key);
    }

    public String getString(String key) {
        return (String) values.get(key);
    }

    public Date getDate(String key) {
        return (Date) values.get(key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{class:\"" + this.getClass().getName() + "\"");
        Iterator<Entry<String, Object>> element = values.entrySet().iterator();
        while(element.hasNext()) {
            if(sb.length() > 1) {
                sb.append(",");
            }
            Entry<String, Object> e = element.next();
            sb.append("\"");
            sb.append(e.getKey());
            sb.append("\":");
            if(e.getValue() != null) {
                if(e.getValue() instanceof Date) {
                    sb.append("\"");
                    sb.append(formatter.format(e.getValue()));
                    sb.append("\"");
                } else if(e.getValue() instanceof String) {
                    sb.append("\"");
                    sb.append(e.getValue());
                    sb.append("\"");
                } else {
                    sb.append(e.getValue().toString());
                }
            } else {
                sb.append("null");
            }
        }
        sb.append("}");
        
        return sb.toString();
    }
}
