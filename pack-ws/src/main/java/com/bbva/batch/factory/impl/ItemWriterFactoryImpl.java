package com.bbva.batch.factory.impl;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemWriterFactory;
import com.bbva.batch.util.ItemBatchFieldExtractor;
import com.bbva.batch.util.ParamUtil;

@Component("itemWriterFactory")
public class ItemWriterFactoryImpl implements ItemWriterFactory {

//    private JdbcPagingItemReader<ItemBatch> createJdbcPagingItemReader(ParamUtil params) throws Exception {
//        JdbcPagingItemReader<ItemBatch> reader = new JdbcPagingItemReader<ItemBatch>();
//        
//
//        return reader;
//    }
    
    /***
    <bean id="itemWriter"
        class="org.springframework.batch.item.file.FlatFileItemWriter" scope="step">
        <property name="resource" value="#{jobParameters[outputFileName]}"/>
        <property name="appendAllowed" value="true"/>
        <property name="lineAggregator">
            <bean class="org.springframework.batch.item.file.transform.FormatterLineAggregator">
                <property name="fieldExtractor">
                    <bean class="org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor">
                        <property name="names" value="solicitud, codigoProducto, codigoSubProducto, estado, fechaAltaConFormato, importeConFormatoHOST, divisa, tipoDOI, numDOI, codigoCliente, contrato, plazo, oficina, ejecutivo, tasaConFormatoHOST" />
                    </bean>
                </property>
                <property name="format" value="%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s" />
            </bean>
        </property>
    </bean>
     * @throws Exception 
     */
    
    private ItemWriter<ItemBatch> createFlatFileItemWriter(ParamUtil params) throws Exception {
        ItemBatchFieldExtractor fieldExtractor = new ItemBatchFieldExtractor();
        FormatterLineAggregator<ItemBatch> lineAggregator = new FormatterLineAggregator<ItemBatch>();
        lineAggregator.setFormat(params.getParamAsString(ParameterType.PARAM_FORMAT));
        lineAggregator.setFieldExtractor(fieldExtractor);
        
        FlatFileItemWriter<ItemBatch> writer = new FlatFileItemWriter<ItemBatch>();
        writer.setResource(new UrlResource(params.getParamAsString(ParameterType.PARAM_RESOURCE)));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(lineAggregator);
        return writer;
    }
    
    @Override
    public ItemWriter<ItemBatch> create(ItemWriterType type, ParamUtil params) throws Exception {
        ItemWriter<ItemBatch> writer = null;
        
        if(type.compareTo(ItemWriterType.WRITER_TABLE) == 0) {
            
        } else if (type.compareTo(ItemWriterType.WRITER_TEXT_DELIMIT) == 0) {
            writer = createFlatFileItemWriter(params);
        } else if (type.compareTo(ItemWriterType.WRITER_TEXT_POSITION) == 0) {
            
        } else if (type.compareTo(ItemWriterType.WRITER_XML) == 0) {
            
        }
        
        return writer;
    }

}