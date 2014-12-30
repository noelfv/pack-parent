package com.bbva.batch.util;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<ItemBatch> {

    @Override
    public SqlParameterSource createSqlParameterSource(ItemBatch item) {
        return new ItemBatchSqlParameterSource(item);
    }

}
