package com.bbva.batch.factory;

import org.springframework.batch.item.ItemWriter;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.util.ParamUtil;

public interface ItemWriterFactory {

    ItemWriter<ItemBatch> create(ItemWriterType type, ParamUtil params) throws Exception;
}
