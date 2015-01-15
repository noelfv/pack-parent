package com.bbva.packws.model;

import java.util.List;

import com.bbva.batch.domain.ApplicationBatch;
import com.everis.web.model.BaseModel;

public class ApplicationModel extends BaseModel {

    private List<ApplicationBatch> applications;
    private ApplicationBatch application;

    public List<ApplicationBatch> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationBatch> applications) {
        this.applications = applications;
    }

    public ApplicationBatch getApplication() {
        return application;
    }

    public void setApplication(ApplicationBatch application) {
        this.application = application;
    }

}
