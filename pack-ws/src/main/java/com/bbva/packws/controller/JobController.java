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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bbva.batch.service.ApplicationBatchService;
import com.bbva.batch.service.JobInstanceService;
import com.bbva.packws.batch.job.GenerarArchivoHandler;
import com.bbva.packws.batch.job.GenerarArchivoThread;
import com.bbva.packws.model.SchedulerModel;
import com.bbva.packws.service.SchedulerService;
import com.everis.core.enums.Estado;
import com.everis.enums.Resultado;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("jobController")
@Scope("prototype")
@SessionAttributes({ "handler" })
@RequestMapping(value = "job")
public class JobController extends AbstractSpringControllerImpl {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JobController.class);
    private static final String OPERACION_INICIAR = "iniciarJob";
    private static final String OPERACION_LISTAR = "listarJob";
    private static final String EXCLUDE_APPLICATION[] = new String[] { "*.class", "jobs" };
    
    @Resource(name = "schedulerService")
    private SchedulerService schedulerService;

    @Resource(name = "jobInstanceService")
    private JobInstanceService jobInstanceService;

    @Resource(name = "jobRepository")
    private JobRepository jobRepository;

    @Resource(name = "jobExplorer")
    private JobExplorer jobExplorer;

    @Resource(name = "applicationBatchService")
    private ApplicationBatchService applicationBatchService;
    
    @ModelAttribute("handler")
    public GenerarArchivoHandler populateHandler() {
        return new GenerarArchivoHandler();
    }

    @RequestMapping(value = "index")
    public String index(ModelMap model) {
        model.addAttribute("schedulerClass", "");
        model.addAttribute("jobClass", "ui-state-active-bbva");
        model.addAttribute("applications", this.renderModelJsonDeepExclude(applicationBatchService.listar(), EXCLUDE_APPLICATION));
        return "job/index";
    }

    @RequestMapping(value = "listar", method = RequestMethod.POST)
    public @ResponseBody String listar(@ModelAttribute(value = "handler") GenerarArchivoHandler handler) {
        String result;

        try {
            SchedulerModel schedulerModel = new SchedulerModel();
            Long id = jobInstanceService.obtenerUltimaInstancia();
            JobInstance jobInstance = jobExplorer.getJobInstance(id);

            schedulerModel.setTipoResultado(Resultado.EXITO);
            if (id != null) {
                schedulerModel.setRunningJobInstances(jobExplorer.getJobExecutions(jobInstance));
            }
            schedulerModel.setHandler(handler);
            result = this.renderModelJson(schedulerModel);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        return result;
    }

    @RequestMapping(value = "obtenerJob/{operacion}", method = RequestMethod.POST)
    public @ResponseBody String obtenerJob(@ModelAttribute(value = "handler") GenerarArchivoHandler handler, @PathVariable("operacion") String operacion) {
        String result;

        try {
            SchedulerModel schedulerModel = new SchedulerModel();
            if (handler == null) {
                handler = new GenerarArchivoHandler();
                handler.setEstado(Estado.INACTIVO);
            }

            if (OPERACION_INICIAR.equalsIgnoreCase(operacion) && (handler.getEstado() == null || handler.getEstado().compareTo(Estado.INACTIVO) == 0)) {
                handler.setEstado(Estado.ACTIVO);
                GenerarArchivoThread generarArchivoThread = new GenerarArchivoThread(handler);
                generarArchivoThread.start();

                JobExecution jobExecution = new JobExecution(0L);
                schedulerModel.getRunningJobInstances().add(jobExecution);
            } else if (OPERACION_LISTAR.equalsIgnoreCase(operacion)) {
                if (handler.getEstado().compareTo(Estado.INACTIVO) == 0) {
                    Long id = jobInstanceService.obtenerUltimaInstancia();
                    if (id != null) {
                        JobInstance jobInstance = jobExplorer.getJobInstance(id);
                        if (jobInstance != null) {
                            schedulerModel.setRunningJobInstances(jobExplorer.getJobExecutions(jobInstance));
                        }
                    }
                } else {
                    JobExecution jobExecution = new JobExecution(0L);
                    schedulerModel.getRunningJobInstances().add(jobExecution);
                }
            }

            schedulerModel.setTipoResultado(Resultado.EXITO);
            schedulerModel.setHandler(handler);
            result = this.renderModelJson(schedulerModel);
        } catch (Exception e) {
            result = this.renderErrorSistema(e);
        }

        return result;
    }

}