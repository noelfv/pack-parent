package com.bbva.batch.domain;

import java.util.List;

public class ApplicationBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String jndi;
    private String description;
    private List<JobBatch> jobs;

    public ApplicationBatch() {
        super();
    }

    public ApplicationBatch(String name, String jndi) {
        super();
        this.name = name;
        this.jndi = jndi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<JobBatch> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobBatch> jobs) {
        this.jobs = jobs;
    }

}
