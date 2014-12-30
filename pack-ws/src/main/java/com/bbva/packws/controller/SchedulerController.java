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
    private static final Logger LOG = Logger.getLogger(SchedulerController.class);

    @Resource(name = "schedulerService")
    private SchedulerService schedulerService;

    @Resource(name = "triggerService")
    private TriggerService triggerService;

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

    @RequestMapping(value = "reschedule", method = RequestMethod.POST)
    public @ResponseBody String reschedule(@ModelAttribute("schedulerModel") SchedulerModel schedulerModel, BindingResult bindingResult) {
        String result = "";
        try {
            LOG.info("Cron: [" + schedulerModel.getTime() + "]");

            SchedulerModel schedulerModelResult = new SchedulerModel();
            if (!bindingResult.hasErrors()) {
                schedulerModelResult.setTipoResultado(Resultado.EXITO);

                if (schedulerModel.getCronTrigger().equalsIgnoreCase("cronTriggerGenerarArchivo")) {
                    schedulerService.reschedulerTriggerGenerarArchivo(schedulerModel.getTime());
                } else if (schedulerModel.getCronTrigger().equalsIgnoreCase("cronTriggerDepurarArchivo")) {
                    schedulerService.reschedulerTriggerDepurarArchivo(schedulerModel.getTime());
                }

                schedulerModelResult.setTriggerInstances(triggerService.listar());
                result = this.renderModelJson(schedulerModelResult);
            } else {
                result = this.renderErrorSistema(bindingResult.getAllErrors());
            }
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }
        return result;
    }
}