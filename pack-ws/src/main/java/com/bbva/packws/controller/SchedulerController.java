package com.bbva.packws.controller;

import javax.annotation.Resource;

import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbva.batch.service.JobInstanceService;
import com.bbva.packws.model.SchedulerModel;
import com.bbva.packws.service.SchedulerService;
import com.bbva.quartz.service.TriggerService;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("schedulerController")
@Scope("prototype")
@RequestMapping(value = "scheduler")
public class SchedulerController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;

    @Resource(name = "schedulerService")
    private SchedulerService schedulerService;

    @Resource(name = "triggerService")
    private TriggerService triggerService;

    @Resource(name = "jobInstanceService")
    private JobInstanceService jobInstanceService;

    @Resource(name = "jobExplorer")
    private JobExplorer jobExplorer;
    
    @RequestMapping(value = "index")
    public String index(ModelMap model) {
        model.addAttribute("schedulerClass", "ui-state-active-bbva");
        model.addAttribute("jobClass", "");
        return "scheduler/index";
    }

    @RequestMapping(value = "listar", method = RequestMethod.POST)
    public @ResponseBody String listar() {
        String result;

        try {
            SchedulerModel schedulerModel = new SchedulerModel();
            schedulerModel.setTipoResultado(Resultado.EXITO);
            schedulerModel.setTriggerInstances(triggerService.listar());
            result = this.renderModelJson(schedulerModel);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        return result;
    }

    @RequestMapping(value = "detail/{jobName}", method = RequestMethod.POST)
    public @ResponseBody String detail(ModelMap model, @PathVariable("jobName") String jobName) {
        String result; 
        
        try {
            SchedulerModel schedulerModel = new SchedulerModel();
            Long id = jobInstanceService.obtenerUltimaInstancia(jobName);
            JobInstance jobInstance = jobExplorer.getJobInstance(id);
        
            schedulerModel.setTipoResultado(Resultado.EXITO);
            if(id != null) {
                schedulerModel.setRunningJobInstances(jobExplorer.getJobExecutions(jobInstance));
            }
            schedulerModel.setTriggerInstances(triggerService.listar());
            result = this.renderModelJson(schedulerModel);
        } catch(Exception e) {
            result = this.renderErrorSistema(e);
        }
        
        return result;
    }

    
}