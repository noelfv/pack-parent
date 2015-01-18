package com.bbva.packws.controller;

import javax.annotation.Resource;

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
    private static final String EXCLUDE_APPLICATION[] = new String[] { "*.class", "*.jobs" };

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;

    @RequestMapping(value = "list")
    public String index(ModelMap model) {
        model.addAttribute("schedulerClass", "");
        model.addAttribute("jobClass", "ui-state-active-bbva");
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
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_APPLICATION);
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
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_APPLICATION);
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
                ApplicationBatch tmp = null;
                ApplicationBatch app = applicationModel.getApplication();
                if(app.getId() == null) {
                    tmp = applicationBatchService.obtener(app.getName());
                }
                if(tmp == null) {
                    model.setTipoResultado(Resultado.EXITO);
                    applicationBatchService.actualizar(app);
                    model.setMensaje("Registro actualizado correctamente");
                    model.setApplications(applicationBatchService.listar());
                } else {
                    model.setTipoResultado(Resultado.ADVERTENCIA);
                    model.setMensaje("El nombre de la aplicaci&#243;n ya esta registrado");
                }
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_APPLICATION);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody String eliminar(@ModelAttribute("applicationModel") ApplicationModel applicationModel, BindingResult bindingResult) {
        String result = "";
        try {
            ApplicationModel model = new ApplicationModel();
            if (!bindingResult.hasErrors()) {
                ApplicationBatch app = applicationModel.getApplication();
                app = applicationBatchService.obtener(app.getId());
                applicationBatchService.eliminar(app);
                
                model.setTipoResultado(Resultado.EXITO);
                model.setMensaje("Registro eliminado");
                model.setApplications(applicationBatchService.listar());
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_APPLICATION);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
}