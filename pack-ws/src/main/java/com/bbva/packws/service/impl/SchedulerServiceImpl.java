package com.bbva.packws.service.impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.bbva.packws.service.SchedulerService;
import com.everis.util.FechaUtil;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOG = Logger.getLogger(SchedulerServiceImpl.class);

    @Resource(name = "parametroConfiguracionService")
    private ParametroConfiguracionService parametroConfiguracionService;

    @Resource(name = "quartzScheduler")
    private Scheduler scheduler;

    @Resource(name = "cronTriggerGenerarArchivo")
    private CronTriggerBean cronTriggerGenerarArchivo;

    @Resource(name = "cronTriggerDepurarArchivo")
    private CronTriggerBean cronTriggerDepurarArchivo;

    @Resource(name = "jobLauncher")
    private JobLauncher jobLauncher;

    @Resource(name = "jobRegistry")
    private JobRegistry jobRegistry;

    @Override
    public boolean reschedulerTriggerGenerarArchivo(String time) {
        boolean result = false;
        JobDetail jobDetail;
        JobDetail newJob;

        try {
            if (cronTriggerGenerarArchivo != null) {
                jobDetail = cronTriggerGenerarArchivo.getJobDetail();
                newJob = (JobDetail) jobDetail.clone();
                scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());

                cronTriggerGenerarArchivo.setJobDetail(newJob);
                cronTriggerGenerarArchivo.setCronExpression(time);
                cronTriggerGenerarArchivo.afterPropertiesSet();
                scheduler.scheduleJob(newJob, cronTriggerGenerarArchivo);

                LOG.error("Generar archivo, reprogramado a las: " + time);
                result = true;
            }
        } catch (Exception e) {
            LOG.error("", e);
        }

        return result;
    }

    @Override
    public boolean reschedulerTriggerDepurarArchivo(String time) {
        boolean result = false;
        JobDetail jobDetail;
        JobDetail newJob;

        try {
            if (cronTriggerDepurarArchivo != null) {
                jobDetail = cronTriggerDepurarArchivo.getJobDetail();
                newJob = (JobDetail) jobDetail.clone();
                scheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());

                cronTriggerDepurarArchivo.setJobDetail(newJob);
                cronTriggerDepurarArchivo.setCronExpression(time);
                cronTriggerDepurarArchivo.afterPropertiesSet();
                scheduler.scheduleJob(newJob, cronTriggerDepurarArchivo);

                LOG.error("Depurar archivo, reprogramado a las: " + time);
                result = true;
            }
        } catch (Exception e) {
            LOG.error("", e);
        }

        return result;
    }

    @Override
    public void executeJob(Long outId)
            throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException,
            NoSuchJobException {
        ParametroConfiguracion paramConfig[] = new ParametroConfiguracion[4];
        paramConfig[0] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_RUTA_ARCHIVO.getKey());
        paramConfig[1] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_NOMBRE_ARCHIVO.getKey());
        paramConfig[2] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_FORMATO_FECHA_SUFIJO.getKey());
        paramConfig[3] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_MESES_ANTERIORES.getKey());

        Long mesesAnteriores = 0L;
        if (paramConfig[3] != null) {
            try {
                mesesAnteriores = Long.valueOf(paramConfig[3].getValor());
            } catch (NumberFormatException e) {
                mesesAnteriores = -2L;
            }
        }

        File directorio = new File(paramConfig[0].getValor());
        boolean creado = false;
        if (!directorio.exists()) {
            creado = directorio.mkdirs();
            if (creado) {
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

        JobParameters param = new JobParametersBuilder()
                .addString("outputFileName", filename)
                .addLong("tiempoEspera", mesesAnteriores).toJobParameters();
        Job job = jobRegistry.getJob("jobSolicitud");
        JobExecution execution = jobLauncher.run(job, param);
        outId = execution.getId();
        LOG.error("Id Proceso: [" + outId + "]");
        LOG.error("Archivo: [" + filename + "]");
        LOG.error("Estado del Proceso: " + execution.getStatus());
        if (!execution.getAllFailureExceptions().isEmpty()) {
            LOG.error("Estado del Proceso: " + execution.getAllFailureExceptions());
        }
    }
}
