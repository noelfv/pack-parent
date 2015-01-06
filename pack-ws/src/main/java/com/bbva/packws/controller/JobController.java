package com.bbva.packws.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.domain.JobBatch;
import com.bbva.batch.service.JobBatchService;
import com.bbva.packws.model.ApplicationModel;
import com.bbva.packws.model.JobModel;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("jobController")
@Scope("prototype")
@RequestMapping(value = "job")
public class JobController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JobController.class);
    private static final String EXCLUDE_JOB[] = new String[] { "*.class", "application", "steps" };

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;

    @RequestMapping(value = "list/{id}")
    public String index(ModelMap model, @PathVariable("id") Long id) {
        String result = "{}";
        JobModel m = new JobModel();

        try {
            m.setTipoResultado(Resultado.EXITO);
            m.setApplicationClass("ui-state-active-bbva");
            m.setJobs(jobBatchService.listar(id));
            result = this.renderModelJsonDeepExclude(m, EXCLUDE_JOB);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        model.addAttribute("modelJSON", result);
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
                model.setJobs(jobBatchService.listar(job.getApplication().getId(), job.getName()));
                result = this.renderModelJson(model);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

//    @RequestMapping(value = "get", method = RequestMethod.POST)
//    public @ResponseBody String obtener(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
//        String result = "";
//        try {
//            ApplicationModel model = new ApplicationModel();
//            if (!bindingResult.hasErrors()) {
//                ApplicationBatch app = applicationModel.getApplication();
//                model.setTipoResultado(Resultado.EXITO);
//                model.setApplication(jobBatchService.obtener(app.getId()));
//                result = this.renderModelJson(model);
//            } else {
//                result = this.renderErrorSistema(bindingResult.getAllErrors());
//            }
//        } catch (Exception e) {
//            result = this.renderErrorSistema(e);
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "save", method = RequestMethod.POST)
//    public @ResponseBody String guardar(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
//        String result = "";
//        try {
//            ApplicationModel model = new ApplicationModel();
//            if (!bindingResult.hasErrors()) {
//                ApplicationBatch app = applicationModel.getApplication();
//                model.setTipoResultado(Resultado.EXITO);
//                model.setApplication(jobBatchService.actualizar(app));
//                result = this.renderModelJson(model);
//            } else {
//                result = this.renderErrorSistema(bindingResult.getAllErrors());
//            }
//        } catch (Exception e) {
//            result = this.renderErrorSistema(e);
//        }
//        return result;
//    }
}