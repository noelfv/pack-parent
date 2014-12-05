package com.bbva.packws.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbva.packws.batch.job.GenerarArchivoThread;
import com.bbva.packws.model.SchedulerModel;
import com.bbva.packws.service.JobInstanceService;
import com.bbva.packws.service.SchedulerService;
import com.everis.web.controller.impl.AbstractSpringControllerImpl;

@Controller("schedulerAction")
@Scope("prototype")
public class SchedulerController extends AbstractSpringControllerImpl {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SchedulerController.class);
	
	@Resource(name = "schedulerService")
	private SchedulerService schedulerService;
	
	@Resource(name = "jobInstanceService")
	private JobInstanceService jobInstanceService;

	@Resource(name = "jobRepository")
	private JobRepository jobRepository;
	
	@Resource(name = "jobExplorer")
	private JobExplorer jobExplorer;
	
	@RequestMapping(value="/scheduler/index")
	public String index(ModelMap model) {
		Long id = jobInstanceService.obtenerUltimaInstancia();
		JobInstance jobInstance = jobExplorer.getJobInstance(id);
		List<JobExecution> runningJobInstances = jobExplorer.getJobExecutions(jobInstance);
		
		model.addAttribute("runningJobInstances", runningJobInstances);
		
		return "index/index";
    }
	
	@RequestMapping(value="/scheduler/obtenerJob")
	public @ResponseBody String obtenerJob(ModelMap model) {
		GenerarArchivoThread generarArchivoThread = new GenerarArchivoThread();
		generarArchivoThread.start();
		
		SchedulerModel schedulerModel = new SchedulerModel();
		Long id = jobInstanceService.obtenerUltimaInstancia();
		JobInstance jobInstance = jobExplorer.getJobInstance(id);
		
		schedulerModel.setRunningJobInstances(jobExplorer.getJobExecutions(jobInstance));
		
		return this.renderModelJson(schedulerModel);
    }
	
	@RequestMapping(value="/scheduler/reschedule")
	public String reschedule(ModelMap model) {
		LOG.error("Entro");
		return "index";
    }
}