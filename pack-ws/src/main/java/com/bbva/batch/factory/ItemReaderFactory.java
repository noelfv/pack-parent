package com.bbva.batch.factory;

import org.springframework.batch.item.ItemReader;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.util.ParamUtil;

public interface ItemReaderFactory {

    ItemReader<ItemBatch> create(ItemReaderType type, ParamUtil params) throws Exception;
}
