package com.bbva.packws.batch.job;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.everis.util.FechaUtil;
import com.everis.web.listener.WebServletContextListener;

public class DepurarArchivoJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(DepurarArchivoJob.class);

    @Override
    protected void executeInternal(JobExecutionContext arg) throws JobExecutionException {
        try {
            ParametroConfiguracionService parametroConfiguracionService = WebServletContextListener.getBean("parametroConfiguracionService");
            ParametroConfiguracion paramConfig[] = new ParametroConfiguracion[4];
            paramConfig[0] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_RUTA_ARCHIVO.getKey());
            paramConfig[1] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_NOMBRE_ARCHIVO.getKey());
            paramConfig[2] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_FORMATO_FECHA_SUFIJO.getKey());
            paramConfig[3] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_DIAS_ESPERA.getKey());

            File directorio = new File(paramConfig[0].getValor());
            String filenameSufix;
            Integer diasEspera = Integer.parseInt(paramConfig[3].getValor());
            Date date;
            Date hoy = new Date();

            if (directorio.isDirectory()) {
                for (File f : directorio.listFiles()) {
                    filenameSufix = f.getName().replaceAll(paramConfig[1].getValor(), "");
                    date = FechaUtil.parseFecha(filenameSufix, paramConfig[2].getValor());
                    if (FechaUtil.diff(date, hoy) <= diasEspera) {
                        if (!f.delete()) {
                            LOG.error("El archivo " + f.getName() + " no se pudo eliminar");
                        }
                    }
                }
            }

        } catch (Exception e) {
            LOG.error("Error al depurar el directorio", e);
        }
        LOG.info("Proceso terminado");
    }
}
