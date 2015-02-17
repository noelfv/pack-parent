package com.bbva.packws.service.impl;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.everis.core.service.Log4jService;

@Service("log4jService")
public class Log4jServiceImpl implements Log4jService {

    private static final Logger LOG = Logger.getLogger(Log4jServiceImpl.class);

    @Resource(name = "parametroConfiguracionService")
    private ParametroConfiguracionService parametroConfiguracionService;

    @Override
    public String obtener(String key) {
        String value = "";

        try {
            if (Log4jService.ROOT_CATEGORY.equalsIgnoreCase(key)) {
                value = parametroConfiguracionService.obtenerParametro(Configuracion.PB_ROOT_CATEGORY.getKey()).getValor();
            } else if (Log4jService.FILE.equalsIgnoreCase(key)) {
                value = parametroConfiguracionService.obtenerParametro(Configuracion.PB_FILE.getKey()).getValor();
            } else if (Log4jService.MAX_FILE_SIZE.equalsIgnoreCase(key)) {
                value = parametroConfiguracionService.obtenerParametro(Configuracion.PB_MAX_FILE_SIZE.getKey()).getValor();
            } else if (Log4jService.MAX_BACKUP_INDEX.equalsIgnoreCase(key)) {
                value = parametroConfiguracionService.obtenerParametro(Configuracion.PB_MAX_BACKUP_INDEX.getKey()).getValor();
            }
        } catch (Exception e) {
            LOG.error("Error al obtener el valor de: [" + key + "]");
            value = "";
        }

        return value;
    }

    @Override
    public void test(PrintWriter out) {
        out.write("No implementado...");
    }

}
