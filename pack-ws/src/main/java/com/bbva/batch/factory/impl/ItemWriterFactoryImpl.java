package com.bbva.batch.factory.impl;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemWriterFactory;
import com.bbva.batch.file.FlatFileItemWriter;
import com.bbva.batch.util.ItemBatchFieldExtractor;
import com.bbva.batch.util.ItemBatchSqlParameterSourceProvider;
import com.bbva.batch.util.ParamUtil;
import com.everis.util.DBUtilSpring;

@Component("itemWriterFactory")
public class ItemWriterFactoryImpl implements ItemWriterFactory {

    private FlatFileItemWriter<ItemBatch> createFlatFileItemWriter(ParamUtil params) throws Exception {
        ItemBatchFieldExtractor fieldExtractor = new ItemBatchFieldExtractor();
        fieldExtractor.setFields(params.getParamAsString(ParameterType.PARAM_FIELDS));

        FormatterLineAggregator<ItemBatch> lineAggregator = new FormatterLineAggregator<ItemBatch>();
        lineAggregator.setFormat(params.getParamAsString(ParameterType.PARAM_FORMAT));
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<ItemBatch> writer = new FlatFileItemWriter<ItemBatch>();
        writer.setResource(new FileSystemResource(params.getParamAsString(ParameterType.PARAM_RESOURCE)));
        writer.setAppendAllowed(Boolean.parseBoolean(params.getParamAsString(ParameterType.PARAM_APPEND)));
        writer.setShouldDeleteIfExists(true);
        writer.setLineAggregator(lineAggregator);
        writer.afterPropertiesSet();
        return writer;
    }

    private JdbcBatchItemWriter<ItemBatch> createJdbcBatchItemWriter(ParamUtil params) {
        JdbcBatchItemWriter<ItemBatch> writer = new JdbcBatchItemWriter<ItemBatch>();
        ItemBatchSqlParameterSourceProvider itemBatchSqlParameterSourceProvider = new ItemBatchSqlParameterSourceProvider();
        
        writer.setDataSource(DBUtilSpring.getInstance().getDataSource(params.getParamAsString(ParameterType.PARAM_JNDI)));
        writer.setSql(params.getParamAsString(ParameterType.PARAM_QUERY));
        writer.setItemSqlParameterSourceProvider(itemBatchSqlParameterSourceProvider);
        writer.afterPropertiesSet();
        return writer;
    }
    
    @Override
    public ItemWriter<ItemBatch> create(ItemWriterType type, ParamUtil params) throws Exception {
        ItemWriter<ItemBatch> writer = null;

        if (ItemWriterType.WRITER_TABLE.compareTo(type) == 0) {
            writer = createJdbcBatchItemWriter(params);
        } else if (ItemWriterType.WRITER_TEXT_DELIMIT.compareTo(type) == 0) {
            throw new UnsupportedOperationException();
        } else if (ItemWriterType.WRITER_TEXT_POSITION.compareTo(type) == 0) {
            writer = createFlatFileItemWriter(params);
        } else if (ItemWriterType.WRITER_XML.compareTo(type) == 0) {
            throw new UnsupportedOperationException();
        }

        return writer;
    }

}