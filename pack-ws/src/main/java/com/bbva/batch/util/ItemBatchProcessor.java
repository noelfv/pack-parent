package com.bbva.batch.util;

import org.springframework.batch.item.ItemProcessor;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchProcessor implements ItemProcessor<ItemBatch, ItemBatch> {

    private static final String filter = "__filter__";
    
    @Override
    public ItemBatch process(ItemBatch item) throws Exception {
        return !item.existsKey(filter) ? item : (item.getString(filter).equalsIgnoreCase("1") ? item : null);
    }

}
