package com.bbva.batch.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bbva.batch.domain.ItemBatch;

public class ItemBatchRowMapper implements RowMapper<ItemBatch> {

    @Override
    public ItemBatch mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ItemBatch item = new ItemBatch();

        ResultSetMetaData metadata = resultSet.getMetaData();
        String columnName = "";
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            columnName = metadata.getColumnName(i);
            item.setObject(columnName.toUpperCase(), resultSet.getObject(columnName));
        }

        return item;
    }

}
