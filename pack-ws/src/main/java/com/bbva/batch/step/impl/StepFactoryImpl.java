package com.bbva.batch.step.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.stereotype.Component;

import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.step.StepFactory;

@Component("stepFactory")
public class StepFactoryImpl implements StepFactory {

//    private Tasklet createTasklet() {
//        RepeatTemplate repeatTemplate=new TaskExecutorRepeatTemplate();
//        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(5));
//        
//        SimpleChunkProvider<?> chunkProvider = new SimpleChunkProvider<?>(itemReader, repeatOperations);
//        SimpleChunkProcessor<?, ?> chunkProcessor = new SimpleChunkProcessor<?, ?>(itemProcessor, itemWriter);
//        
//        
//        
//        Tasklet tasklet = new ChunkOrientedTasklet<ItemBatch>();
//        
//        
//        return tasklet;
//    }
    
    public Step createStep(StepBatch stepBatch) {
        TaskletStep step = new TaskletStep(stepBatch.getName()+ "Step");
        
        try {
        } catch(Exception e) {
            step = null;
        }
        
        return step;
    }
    
    public List<Step> createSteps(JobBatch jobBatch) {
        List<Step> steps = new ArrayList<Step>();
        Step step = null;
        
        for(StepBatch s : jobBatch.getSteps()) {
            step = createStep(s);
            if(step != null) {
                steps.add(step);
            }
        }
        
        return steps;
    }
}
