package com.bbva.batch.factory.impl;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemReaderFactory;
import com.bbva.batch.util.ItemBatchRowMapper;
import com.bbva.batch.util.ParamUtil;
import com.everis.util.DBUtilSpring;

@Component("itemReaderFactory")
public class ItemReaderFactoryImpl implements ItemReaderFactory {

    private JdbcPagingItemReader<ItemBatch> createJdbcPagingItemReader(ParamUtil params) throws Exception {
        DataSource dataSource = DBUtilSpring.getInstance().getDataSource(params.getParamAsString(ParameterType.PARAM_JNDI));
        JdbcPagingItemReader<ItemBatch> reader = new JdbcPagingItemReader<ItemBatch>();
        
        SqlPagingQueryProviderFactoryBean queryProviderFactory = new SqlPagingQueryProviderFactoryBean();
        queryProviderFactory.setDatabaseType("oracle");
        queryProviderFactory.setDataSource(dataSource);
        queryProviderFactory.setSelectClause(params.getParamAsString(ParameterType.PARAM_SELECT));
        queryProviderFactory.setFromClause(params.getParamAsString(ParameterType.PARAM_FROM));
        queryProviderFactory.setWhereClause(params.getParamAsString(ParameterType.PARAM_WHERE));
        
        String groupClause = params.getParamAsString(ParameterType.PARAM_GROUP);
        if(groupClause != null) {
            queryProviderFactory.setGroupClause(groupClause);
        }
        
        String sortKey = params.getParamAsString(ParameterType.PARAM_SORT);
        if(sortKey != null) {
            queryProviderFactory.setSortKey(sortKey);
        }
        
        PagingQueryProvider queryProvider;
        queryProvider = (PagingQueryProvider) queryProviderFactory.getObject();
        
        reader.setDataSource(dataSource);
        reader.setQueryProvider(queryProvider);
        reader.setPageSize(params.getParamAsLong(ParameterType.PARAM_PAGE_SIZE).intValue());
        reader.setRowMapper(new ItemBatchRowMapper());
        reader.afterPropertiesSet();
        return reader;
    }
    
    @Override
    public ItemReader<ItemBatch> create(ItemReaderType type, ParamUtil params) throws Exception {
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