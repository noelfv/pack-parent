package com.bbva.batch.util;

import org.springframework.batch.item.file.transform.FieldExtractor;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchFieldExtractor implements FieldExtractor<ItemBatch> {

    private String fields;

    @Override
    public Object[] extract(ItemBatch param) {
        String[] names = fields.split(",");
        Object[] o = new Object[names.length];

        for (int i = 0; i < names.length; i++) {
            o[i] = param.getObject(names[i]);
        }

        return o;
    }

    public String fields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
