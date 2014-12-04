package com.bbva.packws.batch.job;

import java.io.File;
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

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.everis.util.FechaUtil;
import com.everis.web.listener.WebServletContextListener;

public class GenerarArchivoJob extends QuartzJobBean {

	private static final Logger LOG = Logger.getLogger(GenerarArchivoJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
		try {
			ParametroConfiguracionService parametroConfiguracionService = (ParametroConfiguracionService) WebServletContextListener.getBean("parametroConfiguracionService");
			ParametroConfiguracion paramConfig[] = new ParametroConfiguracion[3];
			paramConfig[0] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_RUTA_ARCHIVO.getKey());
			paramConfig[1] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_NOMBRE_ARCHIVO.getKey());
			paramConfig[2] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_FORMATO_FECHA_SUFIJO.getKey());
			
			JobLauncher jobLauncher = (JobLauncher) WebServletContextListener.getBean("jobLauncher");
			JobRegistry jobRegistry = (JobRegistry) WebServletContextListener.getBean("jobRegistry");
			
			File directorio = new File(paramConfig[0].getValor());
			boolean creado = false;
			if(!directorio.exists()) {
				creado = directorio.mkdirs();
				if(creado) {
					LOG.error("Directorio " + paramConfig[0].getValor() + " creado.");
				}
			}
			
			String pathSource = paramConfig[0].getValor();
			if (!pathSource.endsWith("/")) {
				pathSource += "/";
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("file:");
			sb.append(pathSource);
			sb.append(paramConfig[1].getValor());
			sb.append(FechaUtil.formatFecha(new Date(), paramConfig[2].getValor()));
			sb.append(".txt");
						
			String filename = sb.toString();
			
			JobParameters param = new JobParametersBuilder().addString("outputFileName", filename).toJobParameters();
			Job job = jobRegistry.getJob("jobSolicitud");
			JobExecution execution = jobLauncher.run(job, param);
			LOG.error("Archivo: [" + filename + "]");
			LOG.error("Estado del proceso: " + execution.getStatus());
			if(!execution.getAllFailureExceptions().isEmpty()) {
				LOG.error("Estado del proceso: " + execution.getAllFailureExceptions());
			}
		} catch (Exception e) {
			LOG.error("Error al iniciar el proceso", e);
		}
		LOG.info("Proceso terminado");
	}
}
