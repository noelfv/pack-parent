package com.bbva.packws.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;

@Service("solicitudService")
public class SolicitudServiceImpl implements SolicitudService {
	
	@Resource(name = "solicitudDAO")
	private SolicitudDAO solicitudDAO;

	public List<Solicitud> consultarSolicitudes(Solicitud parametro, Solicitud ultimoRegistro, int nroRegistro) {
		return solicitudDAO.consultarSolicitudes(parametro, ultimoRegistro, nroRegistro);
	}

}
