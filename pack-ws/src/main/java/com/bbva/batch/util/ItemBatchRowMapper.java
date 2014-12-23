package com.bbva.batch.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchRowMapper implements RowMapper<ItemBatch> {

    @Override
    public ItemBatch mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return null;
    }

}
