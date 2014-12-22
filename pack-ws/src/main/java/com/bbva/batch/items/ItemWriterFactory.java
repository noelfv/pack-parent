package com.bbva.batch.items;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.enums.ItemWriterType;

public interface ItemWriterFactory {

    ItemWriter<ItemBatch> create(ItemWriterType type, List<ParameterBatch> paramsBatch) throws Exception;
}
