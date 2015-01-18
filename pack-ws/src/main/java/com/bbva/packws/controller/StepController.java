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

import com.bbva.batch.domain.StepBatch;
import com.bbva.batch.service.JobBatchService;
import com.bbva.batch.service.StepBatchService;
import com.bbva.packws.model.StepModel;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("stepController")
@Scope("prototype")
@RequestMapping(value = "step")
public class StepController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final String EXCLUDE_STEP[] = new String[] { "*.class", "jobs" };

    @Resource(name = "stepBatchService")
    private StepBatchService stepBatchService;

    @Resource(name = "jobBatchService")
    private JobBatchService jobBatchService;
    
    @RequestMapping(value = "list/{id}")
    public String index(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("schedulerClass", "");
        model.addAttribute("jobClass", "ui-state-active-bbva");
        model.addAttribute("application", jobBatchService.obtener(id));
        return "job/paso";
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public @ResponseBody String buscar(@ModelAttribute("stepModel") StepModel stepModel, BindingResult bindingResult) {
        String result = "";
        try {
            StepModel model = new StepModel();
            if (!bindingResult.hasErrors()) {
                StepBatch step = stepModel.getStep();
                model.setTipoResultado(Resultado.EXITO);
                model.setSteps(stepBatchService.listar(step.getJob().getId(), step.getName()));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_STEP);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public @ResponseBody String obtener(@ModelAttribute("stepModel") StepModel stepModel, BindingResult bindingResult) {
        String result = "";
        try {
            StepModel model = new StepModel();
            if (!bindingResult.hasErrors()) {
                StepBatch step = stepModel.getStep();
                model.setTipoResultado(Resultado.EXITO);
                model.setStep(stepBatchService.obtener(step.getId()));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_STEP);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody String guardar(@ModelAttribute("stepModel") StepModel stepModel, BindingResult bindingResult) {
        String result = "";
        try {
            StepModel model = new StepModel();
            if (!bindingResult.hasErrors()) {
                StepBatch step = stepModel.getStep();
                model.setTipoResultado(Resultado.EXITO);
                stepBatchService.actualizar(step);
                model.setMensaje("Registro actualizado correctamente");
                model.setSteps(stepBatchService.listar(step.getJob().getId()));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_STEP);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody String eliminar(@ModelAttribute("stepModel") StepModel stepModel, BindingResult bindingResult) {
        String result = "";
        try {
            StepModel model = new StepModel();
            if (!bindingResult.hasErrors()) {
                StepBatch step = stepModel.getStep();
                step = stepBatchService.obtener(step.getId());
                stepBatchService.eliminar(step);
                
                model.setTipoResultado(Resultado.EXITO);
                model.setMensaje("Registro eliminado");
                model.setSteps(stepBatchService.listar(step.getJob().getId()));
                result = this.renderModelJsonDeepExclude(model, EXCLUDE_STEP);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
}