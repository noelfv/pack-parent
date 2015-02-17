package com.bbva.batch.factory.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.core.step.item.SimpleChunkProcessor;
import org.springframework.batch.core.step.item.SimpleChunkProvider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.batch.repeat.support.TaskExecutorRepeatTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.bbva.batch.domain.ItemBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.enums.ItemReaderType;
import com.bbva.batch.enums.ItemWriterType;
import com.bbva.batch.enums.ParameterType;
import com.bbva.batch.factory.ItemReaderFactory;
import com.bbva.batch.factory.ItemWriterFactory;
import com.bbva.batch.factory.StepFactory;
import com.bbva.batch.tasklet.DroolsTasklet;
import com.bbva.batch.tasklet.QueryTasklet;
import com.bbva.batch.util.DeciderParam;
import com.bbva.batch.util.ItemBatchProcessor;
import com.bbva.batch.util.ParamUtil;
import com.everis.core.exception.BussinesException;

import flexjson.JSONDeserializer;

@Component("stepFactory")
public class StepFactoryImpl implements StepFactory {

    private static final Logger LOG = Logger.getLogger(StepFactoryImpl.class);
    
    @Resource(name = "jobRepository")
    private JobRepository jobRepository;

    @Resource(name = "transactionManagerBatch")
    private PlatformTransactionManager transactionManager;

    @Resource(name = "itemReaderFactory")
    private ItemReaderFactory itemReaderFactory;

    @Resource(name = "itemWriterFactory")
    private ItemWriterFactory itemWriterFactory;

    @SuppressWarnings("unchecked")
    private Tasklet createTasklet(ItemReaderType readerType, ItemWriterType writeType, ParamUtil paramsReader, ParamUtil paramsWriter) throws BussinesException {
        Tasklet tasklet = null;
        
        try {
            if(ItemWriterType.WRITER_QUERY.compareTo(writeType) == 0) {
                QueryTasklet queryTasklet = new QueryTasklet();
                queryTasklet.setQuery(paramsWriter.getParamAsString(ParameterType.PARAM_QUERY));
                queryTasklet.setDataSourceName(paramsWriter.getParamAsString(ParameterType.PARAM_JNDI));
                tasklet = queryTasklet;
            } else if(ItemWriterType.WRITER_DROOLS.compareTo(writeType) == 0){
                JSONDeserializer<DeciderParam> deserializerDecider = new JSONDeserializer<DeciderParam>();
                DroolsTasklet droolsTasklet = new DroolsTasklet();
                droolsTasklet.setRule(paramsWriter.getParamAsByte(ParameterType.PARAM_RULE));
                droolsTasklet.setParams((List<DeciderParam>) deserializerDecider.deserialize(paramsWriter.getParamAsString(ParameterType.PARAM_DECISOR_PARAM)));
                droolsTasklet.setDataSourceName(paramsWriter.getParamAsString(ParameterType.PARAM_JNDI));
                tasklet = droolsTasklet;
            } else {
                ItemReader<ItemBatch> itemReader = itemReaderFactory.create(readerType, paramsReader);
                ItemWriter<ItemBatch> itemWriter = itemWriterFactory.create(writeType, paramsWriter);
                ItemProcessor<ItemBatch, ItemBatch> itemProcessor = new ItemBatchProcessor();
    
                RepeatTemplate repeatTemplate = new TaskExecutorRepeatTemplate();
                repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(100));
    
                SimpleChunkProvider<ItemBatch> chunkProvider = new SimpleChunkProvider<ItemBatch>(itemReader, repeatTemplate);
                SimpleChunkProcessor<ItemBatch, ItemBatch> chunkProcessor = new SimpleChunkProcessor<ItemBatch, ItemBatch>(itemProcessor, itemWriter);
    
                tasklet = new ChunkOrientedTasklet<ItemBatch>(chunkProvider, chunkProcessor);
            }
        } catch(Exception e) {
            throw new BussinesException("No se pudo crear el tasklet", e);
        }
        
        return tasklet;
    }

    public Step createStep(StepBatch stepBatch) {
        TaskletStep step = new TaskletStep(stepBatch.getName());

        try {
            ItemReaderType readerType = null;
            if(stepBatch.getReader() != null && !stepBatch.getReader().isEmpty()) {
                readerType = ItemReaderType.valueOf(stepBatch.getReader());
            }
            ItemWriterType writeType = ItemWriterType.valueOf(stepBatch.getWriter());
            ParamUtil paramsReader = new ParamUtil(stepBatch.getParameters(), true);
            ParamUtil paramsWriter = new ParamUtil(stepBatch.getParameters(), false);
            
            step.setJobRepository(jobRepository);
            step.setTransactionManager(transactionManager);
            step.setTasklet(createTasklet(readerType, writeType, paramsReader, paramsWriter));
        } catch (BussinesException e) {
            LOG.error("Error al crear el paso: [" + stepBatch.getName() + "]", e);
            step = null;
        }

        return step;
    }

    public List<Step> createSteps(JobBatch jobBatch) {
        List<Step> steps = new ArrayList<Step>();
        Step step = null;
        int i = 0;
        StepBatch s;
        
        for (i = 0; i < jobBatch.getSteps().size(); i++) {
            s = jobBatch.getSteps().get(i);
            if(s != null) {
                step = createStep(s);
                if (step != null) {
                    steps.add(step);
                }
            }
        }

        return steps;
    }
}
