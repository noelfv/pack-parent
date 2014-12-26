package com.bbva.batch.factory.impl;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemWriterFactory;
import com.bbva.batch.file.FlatFileItemWriter;
import com.bbva.batch.util.ItemBatchFieldExtractor;
import com.bbva.batch.util.ParamUtil;

@Component("itemWriterFactory")
public class ItemWriterFactoryImpl implements ItemWriterFactory {
    
    private ItemWriter<ItemBatch> createFlatFileItemWriter(ParamUtil params) throws Exception {
        ItemBatchFieldExtractor fieldExtractor = new ItemBatchFieldExtractor();
        fieldExtractor.setFields(params.getParamAsString(ParameterType.PARAM_FIELDS));
        
        FormatterLineAggregator<ItemBatch> lineAggregator = new FormatterLineAggregator<ItemBatch>();
        lineAggregator.setFormat(params.getParamAsString(ParameterType.PARAM_FORMAT));
        lineAggregator.setFieldExtractor(fieldExtractor);
        
        FlatFileItemWriter<ItemBatch> writer = new FlatFileItemWriter<ItemBatch>();
        writer.setResource(new FileSystemResource(params.getParamAsString(ParameterType.PARAM_RESOURCE)));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(lineAggregator);
        writer.afterPropertiesSet();
        return writer;
    }
    
    @Override
    public ItemWriter<ItemBatch> create(ItemWriterType type, ParamUtil params) throws Exception {
        ItemWriter<ItemBatch> writer = null;
        
        if(type.compareTo(ItemWriterType.WRITER_TABLE) == 0) {
            
        } else if (type.compareTo(ItemWriterType.WRITER_TEXT_DELIMIT) == 0) {
            
        } else if (type.compareTo(ItemWriterType.WRITER_TEXT_POSITION) == 0) {
            writer = createFlatFileItemWriter(params);
        } else if (type.compareTo(ItemWriterType.WRITER_XML) == 0) {
            
        }
        
        return writer;
    }

}