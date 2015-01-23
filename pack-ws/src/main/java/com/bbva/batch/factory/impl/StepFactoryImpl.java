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
import com.bbva.batch.tasklet.QueryTasklet;
import com.bbva.batch.util.ItemBatchProcessor;
import com.bbva.batch.util.ParamUtil;
import com.everis.util.DBUtilSpring;

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

    private Tasklet createTasklet(ItemReaderType readerType, ItemWriterType writeType, ParamUtil paramsReader, ParamUtil paramsWriter) throws Exception {
        Tasklet tasklet = null;
        
        if(writeType.compareTo(ItemWriterType.WRITER_QUERY) != 0) {
            ItemReader<ItemBatch> itemReader = itemReaderFactory.create(readerType, paramsReader);
            ItemWriter<ItemBatch> itemWriter = itemWriterFactory.create(writeType, paramsWriter);
            ItemProcessor<ItemBatch, ItemBatch> itemProcessor = new ItemBatchProcessor();

            RepeatTemplate repeatTemplate = new TaskExecutorRepeatTemplate();
            repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(100));

            SimpleChunkProvider<ItemBatch> chunkProvider = new SimpleChunkProvider<ItemBatch>(itemReader, repeatTemplate);
            SimpleChunkProcessor<ItemBatch, ItemBatch> chunkProcessor = new SimpleChunkProcessor<ItemBatch, ItemBatch>(itemProcessor, itemWriter);

            tasklet = new ChunkOrientedTasklet<ItemBatch>(chunkProvider, chunkProcessor);
        } else if(writeType.compareTo(ItemWriterType.WRITER_QUERY) == 0){
            QueryTasklet queryTasklet = new QueryTasklet();
            queryTasklet.setQuery(paramsWriter.getParamAsString(ParameterType.PARAM_QUERY));
            queryTasklet.setDatasource(DBUtilSpring.getInstance().getDataSource(paramsWriter.getParamAsString(ParameterType.PARAM_JNDI)));
            tasklet = queryTasklet;
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

            // RepeatTemplate repeatTemplate = new TaskExecutorRepeatTemplate();
            // repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(1));
            
            step.setJobRepository(jobRepository);
            step.setTransactionManager(transactionManager);
            step.setTasklet(createTasklet(readerType, writeType, paramsReader, paramsWriter));
            // TODO: step.setStepOperations(stepOperations); // Validar si es COMMIT
        } catch (Exception e) {
            LOG.error("Error al crear el paso: [" + stepBatch.getName() + "]", e);
            step = null;
        }

        return step;
    }

    public List<Step> createSteps(JobBatch jobBatch) {
        List<Step> steps = new ArrayList<Step>();
        Step step = null;

        for (StepBatch s : jobBatch.getSteps()) {
            step = createStep(s);
            if (step != null) {
                steps.add(step);
            }
        }

        return steps;
    }
}
