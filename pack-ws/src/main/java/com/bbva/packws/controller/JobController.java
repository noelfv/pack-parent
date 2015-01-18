package com.bbva.packws.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobBatchService;
import com.bbva.packws.model.JobModel;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("jobController")
@Scope("prototype")
@RequestMapping(value = "job")
public class JobController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final String EXCLUDE_JOB[] = new String[] { "*.class", "*.application", "*.steps" };

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;
    
    @RequestMapping(value = "list/{id}")
    public String index(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("schedulerClass", "");
        model.addAttribute("jobClass", "ui-state-active-bbva");
        model.addAttribute("application", applicationBatchService.obtener(id));
        return "job/trabajo";
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public @ResponseBody String buscar(@ModelAttribute("jobModel") JobModel jobModel, BindingResult bindingResult) {
        String result = "";
        try {
            JobModel model = new JobModel();
            if (!bindingResult.hasErrors()) {
                JobBatch job = jobModel.getJob();
                model.setTipoResultado(Resultado.EXITO);
                model.setJobs(jobBatchService.listar(job.getApplication().getId(), job.getName(), true));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_JOB);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public @ResponseBody String obtener(@ModelAttribute("jobModel") JobModel jobModel, BindingResult bindingResult) {
        String result = "";
        try {
            JobModel model = new JobModel();
            if (!bindingResult.hasErrors()) {
                JobBatch job = jobModel.getJob();
                model.setTipoResultado(Resultado.EXITO);
                model.setJob(jobBatchService.obtener(job.getId()));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_JOB);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody String guardar(@ModelAttribute("jobModel") JobModel jobModel, BindingResult bindingResult) {
        String result = "";
        try {
            JobModel model = new JobModel();
            if (!bindingResult.hasErrors()) {
                JobBatch tmp = null;
                JobBatch job = jobModel.getJob();
                if(job.getId() == null) {
                    tmp = jobBatchService.obtener(job.getApplication().getId(), job.getName());
                }
                if(tmp == null) {
                    model.setTipoResultado(Resultado.EXITO);
                    job.setApplication(applicationBatchService.obtener(job.getApplication().getId()));
                    jobBatchService.actualizar(job);
                    model.setMensaje("Registro actualizado correctamente");
                } else {
                    model.setTipoResultado(Resultado.ADVERTENCIA);
                    model.setMensaje("El nombre del trabajo ya esta registrado");
                }                
                model.setJobs(jobBatchService.listar(job.getApplication().getId(), true));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_JOB);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody String eliminar(@ModelAttribute("jobModel") JobModel jobModel, BindingResult bindingResult) {
        String result = "";
        try {
            JobModel model = new JobModel();
            if (!bindingResult.hasErrors()) {
                JobBatch job = jobModel.getJob();
                job = jobBatchService.obtener(job.getId());
                jobBatchService.eliminar(job);
                
                model.setTipoResultado(Resultado.EXITO);
                model.setMensaje("Registro eliminado");
                model.setJobs(jobBatchService.listar(job.getApplication().getId(), true));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_JOB);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
}