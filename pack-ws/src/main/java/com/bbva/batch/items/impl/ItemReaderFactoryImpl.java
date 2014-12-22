package com.bbva.batch.items.impl;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.domain.ParameterBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.items.ItemReaderFactory;
import com.bbva.batch.mapper.ItemBatchRowMapper;
import com.bbva.batch.util.ParamUtil;
import com.everis.util.DBUtil;

@Component("itemReaderFactory")
public class ItemReaderFactoryImpl implements ItemReaderFactory {

    private JdbcPagingItemReader<ItemBatch> createJdbcPagingItemReader(ParamUtil params) throws Exception {
        JdbcPagingItemReader<ItemBatch> reader = new JdbcPagingItemReader<ItemBatch>();
        
        SqlPagingQueryProviderFactoryBean queryProviderFactory = new SqlPagingQueryProviderFactoryBean();
        queryProviderFactory.setDatabaseType("oracle");
        queryProviderFactory.setDataSource(DBUtil.getInstance().getDataSource(params.getParamAsString(ParameterType.PARAM_JNDI)));
        queryProviderFactory.setSelectClause(params.getParamAsString(ParameterType.PARAM_SELECT));
        queryProviderFactory.setFromClause(params.getParamAsString(ParameterType.PARAM_FROM));
        queryProviderFactory.setWhereClause(params.getParamAsString(ParameterType.PARAM_WHERE));
        queryProviderFactory.setGroupClause(params.getParamAsString(ParameterType.PARAM_GROUP));
        queryProviderFactory.setSortKey(params.getParamAsString(ParameterType.PARAM_SORT));
        
        PagingQueryProvider queryProvider;
        queryProvider = (PagingQueryProvider) queryProviderFactory.getObject();
        
        reader.setQueryProvider(queryProvider);
        reader.setPageSize(params.getParamAsLong(ParameterType.PARAM_PAGE_SIZE).intValue());
        reader.setRowMapper(new ItemBatchRowMapper());
        return reader;
    }
    
    @Override
    public ItemReader<ItemBatch> create(ItemReaderType type, List<ParameterBatch> paramsBatch) throws Exception {
        ParamUtil params = new ParamUtil(paramsBatch);
        ItemReader<ItemBatch> reader = null;
        
        if(type.compareTo(ItemReaderType.READER_TABLE) == 0) {
            reader = createJdbcPagingItemReader(params);
        } else if (type.compareTo(ItemReaderType.READER_TEXT_DELIMIT) == 0) {
            
        } else if (type.compareTo(ItemReaderType.READER_TEXT_POSITION) == 0) {
            
        } else if (type.compareTo(ItemReaderType.READER_XML) == 0) {
            
        }
        
        return reader;
    }

}