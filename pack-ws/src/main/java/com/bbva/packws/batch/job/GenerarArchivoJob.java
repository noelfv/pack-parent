package com.bbva.packws.batch.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.everis.enums.FormatoFecha;
import com.everis.util.FechaUtil;
import com.everis.web.listener.WebServletContextListener;

public class GenerarArchivoJob extends QuartzJobBean {

	private static final Logger LOG = Logger.getLogger(GenerarArchivoJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
		try {
			JobLauncher jobLauncher = (JobLauncher) WebServletContextListener.getBean("jobLauncher");
			JobRegistry jobRegistry = (JobRegistry) WebServletContextListener.getBean("jobRegistry");
						
			String filename = "file:C:/out" + FechaUtil.formatFecha(new Date(), FormatoFecha.DDMMYYYY_HH24MMSS) + ".txt";
			
			JobParameters param = new JobParametersBuilder().addString("outputFileName", filename).toJobParameters(); // .addString("age", "20").toJobParameters();
			Job job = jobRegistry.getJob("jobSolicitud");
			JobExecution execution = jobLauncher.run(job, param);
			LOG.info("Estado del proceso: " + execution.getStatus());
			if(!execution.getAllFailureExceptions().isEmpty()) {
				LOG.info("Estado del proceso: " + execution.getAllFailureExceptions());
			}
		} catch (Exception e) {
			LOG.error("Error al iniciar el proceso", e);
		}
		LOG.info("Proceso terminado");
	}
}
