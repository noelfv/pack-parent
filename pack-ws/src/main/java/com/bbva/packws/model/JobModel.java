package com.bbva.packws.model;

import java.util.List;

import com.bbva.batch.domain.JobBatch;
import com.everis.web.model.BaseModel;

public class JobModel extends BaseModel {

    private List<JobBatch> jobs;
    private JobBatch job;

    public List<JobBatch> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobBatch> jobs) {
        this.jobs = jobs;
    }

    public JobBatch getJob() {
        return job;
    }

    public void setJob(JobBatch job) {
        this.job = job;
    }

}
