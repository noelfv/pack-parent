package com.bbva.batch.util;

import java.io.Serializable;

public class DeciderBatch implements Serializable {

    private static final long serialVersionUID = 1L;
    private String status;
    private String nextStep;

    public DeciderBatch() {
        super();
    }

    public DeciderBatch(String status, String nextStep) {
        super();
        this.status = status;
        this.nextStep = nextStep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

}
