package com.bbva.quartz.factory.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunnerJNDI;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.quartz.factory.QuartzFactory;

@RunWith(SpringJUnit4ClassRunnerJNDI.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class QuartzFactoryImplTest extends AbstractJUnit4Test {

    @Resource(name="quartzFactory")
    private QuartzFactory quartzFactory;
    
//    @Resource(name="stepD1")
//    private Step step;
    
    @Test
    public void createJob() {
        ApplicationBatch app = new ApplicationBatch();
        app.setName("demo");
        app.setJndi("jdbc/APP_CONELE");
        
//        LOGGER.error(step.getName());
        
//        SimpleJob job = new SimpleJob();
//        TaskletStep step1 = new TaskletStep(); 
        
//        RepeatTemplate repeatTemplate=new TaskExecutorRepeatTemplate();
//        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(5));
//        
//        // step1.setTasklet(tasklet);
//        SimpleChunkProvider<?> chunkProvider = new SimpleChunkProvider<?>(itemReader, repeatOperations);
//        SimpleChunkProcessor<?, ?> chunkProcessor = new SimpleChunkProcessor<?, ?>(itemProcessor, itemWriter);
//        
//        ChunkOrientedTasklet<?> chunk = new ChunkOrientedTasklet<?>(chunkProvider, chunkProcessor);
        
        
        JobBatch jobBatch = new JobBatch();
        jobBatch.setId(1L);
        jobBatch.setApplication(app);
        jobBatch.setName("demoProcedure");
        jobBatch.setCronExpression("0 0/1 * 1/1 * ? *");
        
        quartzFactory.createJob(jobBatch);
        try {
            Thread.sleep(240000);
        } catch (InterruptedException e) {
            Assert.fail("Error");
        }
    }
}
