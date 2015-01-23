package com.bbva.batch.util;

import org.springframework.batch.item.ItemProcessor;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchProcessor implements ItemProcessor<ItemBatch, ItemBatch> {

    @Override
    public ItemBatch process(ItemBatch item) throws Exception {
        return item.existsKey("__filter__") ? item : null;
    }

}
