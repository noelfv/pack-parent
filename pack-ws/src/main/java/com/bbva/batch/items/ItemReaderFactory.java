package com.bbva.batch.items;

import java.util.List;

import org.springframework.batch.item.ItemReader;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.enums.ItemReaderType;

public interface ItemReaderFactory {

    ItemReader<ItemBatch> create(ItemReaderType type, List<ParameterBatch> paramsBatch) throws Exception;
}
