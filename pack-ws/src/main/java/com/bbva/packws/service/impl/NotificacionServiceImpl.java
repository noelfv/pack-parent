package com.bbva.packws.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.stereotype.Service;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.NotificacionService;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.everis.mail.Message;
import com.everis.mail.MessageBody;
import com.everis.mail.MessageHeader;
import com.everis.mail.filter.FilterChainMail;
import com.everis.mail.manager.MailManager;
import com.everis.util.TrazaUtil;

@Service("notificacionService")
public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger LOG = Logger.getLogger(NotificacionServiceImpl.class);
    private MailManager mailManager;

    @Resource(name = "parametroConfiguracionService")
    private ParametroConfiguracionService parametroConfiguracionService;

    public NotificacionServiceImpl() {
        mailManager = new MailManager();
        mailManager.setFilterChainMail(new FilterChainMail());
    }

    @Override
    public boolean notificarPorCorreo(JobExecution jobExecution) {
        ParametroConfiguracion[] params = new ParametroConfiguracion[5];

        params[0] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_HOST_MAIL.getKey());
        params[1] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_PORT_MAIL.getKey());
        params[2] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_USER_MAIL.getKey());
        params[3] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_PASSWD_MAIL.getKey());
        params[4] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_LIST_SEND.getKey());

        MessageHeader header = new MessageHeader();
        header.setHost(params[0].getValor());
        header.setPort(params[1].getValor());
        header.setUserFrom(params[2].getValor());
        header.setEmailFrom(params[2].getValor());
        if (params[3] != null) {
            header.setPasswordFrom(params[3].getValor());
        } else {
            header.setPasswordFrom("");
        }
        header.setListTO(params[4].getValor());
        header.setSubject("Pack BBVA - Proceso de generacion de archivo");

        MessageBody body = new MessageBody();
        StringBuilder sb = new StringBuilder();
        sb.append("Ocurrio un error al ejecutar el proceso de generacion del archivo<br><br>");
        for (Throwable e : jobExecution.getFailureExceptions()) {
            sb.append(TrazaUtil.mostrarMensajeHTML(new Exception(e)));
            sb.append("<br>");
        }

        sb.append("Por favor no responder a este correo.");
        body.setMessage(sb.toString());
        LOG.info(body.getMessage());

        Message message = new Message();
        message.setHeader(header);
        message.setBody(body);

        mailManager.add(message);
        mailManager.send();

        return false;
    }

}
