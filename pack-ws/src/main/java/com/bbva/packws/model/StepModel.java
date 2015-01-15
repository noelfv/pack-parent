package com.bbva.packws.model;

import java.util.List;

import com.bbva.batch.domain.StepBatch;
import com.everis.web.model.BaseModel;

public class StepModel extends BaseModel {

    private List<StepBatch> steps;
    private StepBatch step;

    public List<StepBatch> getSteps() {
        return steps;
    }

    public void setSteps(List<StepBatch> steps) {
        this.steps = steps;
    }

    public StepBatch getStep() {
        return step;
    }

    public void setStep(StepBatch step) {
        this.step = step;
    }

}
