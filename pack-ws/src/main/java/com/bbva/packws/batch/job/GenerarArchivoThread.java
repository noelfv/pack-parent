package com.bbva.packws.batch.job;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.bbva.packws.service.SchedulerService;
import com.everis.core.enums.Estado;
import com.everis.web.listener.WebServletContextListener;

public class GenerarArchivoThread extends Thread {

    private static final Logger LOG = Logger.getLogger(GenerarArchivoThread.class);
    private GenerarArchivoHandler handler;

    public GenerarArchivoThread(GenerarArchivoHandler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void run() {
        SchedulerService schedulerService = WebServletContextListener.getBean("schedulerService");
        try {
            handler.setEstado(Estado.ACTIVO);
            Long id = 0L;
            schedulerService.executeJob(id);
            Thread.sleep(20000);
            handler.setEstado(Estado.INACTIVO);
            handler.setId(id);
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.error("JobExecutionAlreadyRunningException", e);
        } catch (JobRestartException e) {
            LOG.error("JobRestartException", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.error("JobInstanceAlreadyCompleteException", e);
        } catch (JobParametersInvalidException e) {
            LOG.error("JobParametersInvalidException", e);
        } catch (NoSuchJobException e) {
            LOG.error("NoSuchJobException", e);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
    }

}
