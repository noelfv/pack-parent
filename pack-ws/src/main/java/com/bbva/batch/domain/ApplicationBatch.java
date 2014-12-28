package com.bbva.batch.domain;

import java.util.List;

public class ApplicationBatch extends EntityBatch {

    private static final long serialVersionUID = 1L;
    private String name;
    private String jndi;
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

    public List<JobBatch> getJobs() {
        return jobs;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public void setJobs(List<JobBatch> jobs) {
        this.jobs = jobs;
    }
}
