package com.bbva.packws.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;

import com.bbva.packws.batch.job.GenerarArchivoHandler;
import com.bbva.quartz.domain.Trigger;
import com.everis.web.model.BaseModel;

public class SchedulerModel extends BaseModel {

    private List<JobExecution> runningJobInstances;
    private List<Trigger> triggerInstances;
    private GenerarArchivoHandler handler;
    private String cronTrigger;
    private String time;

    public List<JobExecution> getRunningJobInstances() {
        if (runningJobInstances == null) {
            runningJobInstances = new ArrayList<JobExecution>();
        }
        return runningJobInstances;
    }

    public void setRunningJobInstances(List<JobExecution> runningJobInstances) {
        this.runningJobInstances = runningJobInstances;
    }

    public List<Trigger> getTriggerInstances() {
        return triggerInstances;
    }

    public void setTriggerInstances(List<Trigger> triggerInstances) {
        this.triggerInstances = triggerInstances;
    }

    public GenerarArchivoHandler getHandler() {
        return handler;
    }

    public void setHandler(GenerarArchivoHandler handler) {
        this.handler = handler;
    }

    public String getCronTrigger() {
        return cronTrigger;
    }

    public void setCronTrigger(String cronTrigger) {
        this.cronTrigger = cronTrigger;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
