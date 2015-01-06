package com.bbva.packws.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbva.batch.domain.ApplicationBatch;
import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.packws.model.ApplicationModel;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("applicationController")
@Scope("prototype")
@RequestMapping(value = "application")
public class ApplicationController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ApplicationController.class);
    private static final String EXCLUDE_APPLICATION[] = new String[] { "*.class", "jobs" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @RequestMapping(value = "list")
    public String index(ModelMap model) {
        String result = "{}";
        ApplicationModel m = new ApplicationModel();

        try {
            m.setTipoResultado(Resultado.EXITO);
            m.setApplicationClass("ui-state-active-bbva");
            m.setApplications(applicationBatchService.listar());
            result = this.renderModelJsonDeepExclude(m, EXCLUDE_APPLICATION);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        model.addAttribute("modelJSON", result);
        return "job/aplicacion";
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public @ResponseBody String buscar(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
        String result = "";
        try {
            ApplicationModel model = new ApplicationModel();
            if (!bindingResult.hasErrors()) {
                ApplicationBatch app = applicationModel.getApplication();
                model.setTipoResultado(Resultado.EXITO);
                model.setApplications(applicationBatchService.listar(app.getName()));
                result = this.renderModelJson(model);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public @ResponseBody String obtener(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
        String result = "";
        try {
            ApplicationModel model = new ApplicationModel();
            if (!bindingResult.hasErrors()) {
                ApplicationBatch app = applicationModel.getApplication();
                model.setTipoResultado(Resultado.EXITO);
                model.setApplication(applicationBatchService.obtener(app.getId()));
                result = this.renderModelJson(model);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody String guardar(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
        String result = "";
        try {
            ApplicationModel model = new ApplicationModel();
            if (!bindingResult.hasErrors()) {
                ApplicationBatch app = applicationModel.getApplication();
                model.setTipoResultado(Resultado.EXITO);
                model.setApplication(applicationBatchService.actualizar(app));
                result = this.renderModelJson(model);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
}