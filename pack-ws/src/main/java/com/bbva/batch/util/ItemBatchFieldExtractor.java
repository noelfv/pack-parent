package com.bbva.batch.util;

import org.springframework.batch.item.file.transform.FieldExtractor;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchFieldExtractor implements FieldExtractor<ItemBatch> {

    private String[] names;
    
    @Override
    public Object[] extract(ItemBatch param) {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
}
