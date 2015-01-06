package com.bbva.packws.model;

import com.everis.web.model.BaseModel;

public class Model extends BaseModel {

    private String schedulerClass;
    private String applicationClass;
    
    public String getSchedulerClass() {
        return schedulerClass;
    }

    public void setSchedulerClass(String schedulerClass) {
        this.schedulerClass = schedulerClass;
    }

    public String getApplicationClass() {
        return applicationClass;
    }

    public void setApplicationClass(String applicationClass) {
        this.applicationClass = applicationClass;
    }

}
