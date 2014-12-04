package com.bbva.packws.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.everis.web.listener.WebServletContextListener;

public class SolicitudDecider implements JobExecutionDecider {

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		ParametroConfiguracionService parametroConfiguracionService = (ParametroConfiguracionService) WebServletContextListener.getBean("parametroConfiguracionService");
		ParametroConfiguracion param = parametroConfiguracionService.obtenerParametro(Configuracion.PB_APAGAR_APLICACION_PLD.getKey());
		FlowExecutionStatus status = FlowExecutionStatus.COMPLETED;
		
		if ("SI".equalsIgnoreCase(param.getValor())) {
			status = FlowExecutionStatus.FAILED;
        }
		
		return status;
	}

}
