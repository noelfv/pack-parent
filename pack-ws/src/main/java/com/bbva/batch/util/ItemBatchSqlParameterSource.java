package com.bbva.batch.util;

import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchSqlParameterSource implements SqlParameterSource {

    private ItemBatch item;

    public ItemBatchSqlParameterSource(ItemBatch item) {
        super();
        this.item = item;
    }

    public String[] getReadablePropertyNames() {
        return item.properties();
    }

    @Override
    public boolean hasValue(String name) {
        return item.existsKey(name);
    }

    @Override
    public Object getValue(String name) throws IllegalArgumentException {
        Object o = item.getObject(name);
        if(o == null) {
            throw new IllegalArgumentException("Key [" + name + "] not exists");
        }
        return o;
    }

    @Override
    public int getSqlType(String name) {
        Class<?> propType = getValue(name).getClass();
        return StatementCreatorUtils.javaTypeToSqlParameterType(propType);
    }

    @Override
    public String getTypeName(String name) {
        Class<?> propType = getValue(name).getClass();
        return propType.getName();
    }

}
