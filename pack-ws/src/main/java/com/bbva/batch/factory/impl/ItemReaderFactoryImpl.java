package com.bbva.batch.factory.impl;

import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemReaderFactory;
import com.bbva.batch.file.WSDLItemReader;
import com.bbva.batch.util.ItemBatchFieldSetMapper;
import com.bbva.batch.util.ItemBatchRowMapper;
import com.bbva.batch.util.ParamUtil;
import com.bbva.drools.DroolsRuleRunner;
import com.everis.util.DBUtilSpring;
import com.everis.webservice.WSDLResource;

import flexjson.JSONDeserializer;

@Component("itemReaderFactory")
public class ItemReaderFactoryImpl extends AbstractItemFactoryImpl implements ItemReaderFactory {

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
        if (groupClause != null) {
            queryProviderFactory.setGroupClause(groupClause);
        }

        String sortKey = params.getParamAsString(ParameterType.PARAM_SORT);
        if (sortKey != null) {
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

    private LineTokenizer createDelimitedLineTokenizer(ParamUtil params, FieldSetFactory fieldSetFactory) {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setFieldSetFactory(fieldSetFactory);
        lineTokenizer.setDelimiter(params.getParamAsString(ParameterType.PARAM_DELIMITER));
        lineTokenizer.setQuoteCharacter(params.getParamAsString(ParameterType.PARAM_QUOTE_CHARACTER).charAt(0));
        lineTokenizer.setNames(params.getParamAsString(ParameterType.PARAM_FIELDS).split(","));
        
        return lineTokenizer;
    }
    
    private LineTokenizer createFixedLengthTokenizer(ParamUtil params, FieldSetFactory fieldSetFactory) {
        String[] cols = params.getParamAsString(ParameterType.PARAM_RANGE).split(",");
        String[] fixeds = params.getParamAsString(ParameterType.PARAM_RANGE).split(",");
        Range[] ranges = new Range[fixeds.length];
        
        for(int i = 0; i < cols.length; i++) {
            fixeds = cols[i].split("-");
            ranges[i] = new Range(Integer.parseInt(fixeds[0]), Integer.parseInt(fixeds[1]));
        }
        
        FixedLengthTokenizer lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setFieldSetFactory(fieldSetFactory);
        lineTokenizer.setNames(params.getParamAsString(ParameterType.PARAM_FIELDS).split(","));
        lineTokenizer.setColumns(ranges);
        
        return lineTokenizer;
    }
    
    private FlatFileItemReader<ItemBatch> createFlatFileItemReader(ParamUtil params, boolean delimit) throws Exception {
        FlatFileItemReader<ItemBatch> reader = new FlatFileItemReader<ItemBatch>();
        DefaultLineMapper<ItemBatch> lineMapper = new DefaultLineMapper<ItemBatch>();
        LineTokenizer lineTokenizer;
        DefaultFieldSetFactory fieldSetFactory = new DefaultFieldSetFactory();
        ItemBatchFieldSetMapper fieldSetMapper = new ItemBatchFieldSetMapper();
        SimpleDateFormat formatter = new SimpleDateFormat(params.getParamAsString(ParameterType.PARAM_FORMAT_DATE));
        
        fieldSetFactory.setDateFormat(formatter);
        
        fieldSetMapper.setClassName(params.getParamAsString(ParameterType.PARAM_CLASS_NAME).split(","));
        
        if(delimit) {
            lineTokenizer = createDelimitedLineTokenizer(params, fieldSetFactory);
        } else {
            lineTokenizer = createFixedLengthTokenizer(params, fieldSetFactory);
        }
        
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        
        reader.setResource(new FileSystemResource(params.getParamAsString(ParameterType.PARAM_RESOURCE)));
        reader.setLineMapper(lineMapper);
        reader.afterPropertiesSet();
        return reader;
    }
    
    private WSDLItemReader createWSDLItemReader(ParamUtil params) throws Exception {
        WSDLItemReader reader = new WSDLItemReader();
        JSONDeserializer<WSDLResource> jsonDeserializer = new JSONDeserializer<WSDLResource>();
        WSDLResource wsdlResource = jsonDeserializer.deserialize(params.getParamAsString(ParameterType.PARAM_WSDL));
        
        reader.setWsdlResource(wsdlResource);
        reader.setOperation(params.getParamAsString(ParameterType.PARAM_WSDL_OPERATION));
        reader.setRule(params.getParamAsByte(ParameterType.PARAM_RULE));
        reader.afterPropertiesSet();
        return reader;
    }
    
    @Override
    public ItemReader<ItemBatch> create(ItemReaderType type, ParamUtil params) throws Exception {
        ItemReader<ItemBatch> reader = null;

        afterConfigureParam(params);
        
        if (ItemReaderType.READER_TABLE.compareTo(type) == 0) {
            reader = createJdbcPagingItemReader(params);
        } else if (ItemReaderType.READER_TEXT_DELIMIT.compareTo(type) == 0) {
            reader = createFlatFileItemReader(params, true);
        } else if (ItemReaderType.READER_TEXT_POSITION.compareTo(type) == 0) {
            reader = createFlatFileItemReader(params, false);
        } else if (ItemReaderType.READER_XML.compareTo(type) == 0) {
            reader = createWSDLItemReader(params);
        }

        return reader;
    }

}