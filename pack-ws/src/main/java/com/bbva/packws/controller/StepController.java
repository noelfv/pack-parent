package com.bbva.packws.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
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
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobInstanceService;
import com.bbva.batch.service.StepBatchService;
import com.bbva.packws.batch.job.GenerarArchivoHandler;
import com.bbva.packws.batch.job.GenerarArchivoThread;
import com.bbva.packws.model.ApplicationModel;
import com.bbva.packws.model.SchedulerModel;
import com.bbva.packws.model.StepModel;
import com.bbva.packws.service.SchedulerService;
import com.everis.core.enums.Estado;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("stepController")
@Scope("prototype")
@RequestMapping(value = "step")
public class StepController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(StepController.class);
    private static final String EXCLUDE_STEP[] = new String[] { "*.class", "jobs" };

    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;

    @RequestMapping(value = "list")
    public String index(ModelMap model, @PathVariable("id") Long id) {
        String result = "{}";
        StepModel m = new StepModel();

        try {
            m.setTipoResultado(Resultado.EXITO);
            m.setApplicationClass("ui-state-active-bbva");
            m.setSteps(stepBatchService.listar(id, true));
            result = this.renderModelJsonDeepExclude(m, EXCLUDE_STEP);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        model.addAttribute("modelJSON", result);
        return "job/aplicacion";
    }

//    @RequestMapping(value = "find", method = RequestMethod.POST)
//    public @ResponseBody String buscar(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
//        String result = "";
//        try {
//            ApplicationModel model = new ApplicationModel();
//            if (!bindingResult.hasErrors()) {
//                ApplicationBatch app = applicationModel.getApplication();
//                model.setTipoResultado(Resultado.EXITO);
//                model.setApplications(stepBatchService.listar(app.getName()));
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
//    @RequestMapping(value = "get", method = RequestMethod.POST)
//    public @ResponseBody String obtener(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
//        String result = "";
//        try {
//            ApplicationModel model = new ApplicationModel();
//            if (!bindingResult.hasErrors()) {
//                ApplicationBatch app = applicationModel.getApplication();
//                model.setTipoResultado(Resultado.EXITO);
//                model.setApplication(stepBatchService.obtener(app.getId()));
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
//                model.setApplication(stepBatchService.actualizar(app));
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