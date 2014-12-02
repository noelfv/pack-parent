package com.bbva.packws.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;

@Service("solicitudService")
public class SolicitudServiceImpl implements SolicitudService {
	
	@Resource(name = "solicitudDAO")
	private SolicitudDAO solicitudDAO;

	@Transactional(readOnly = true)
	public List<Solicitud> consultarSolicitudes(Solicitud parametro, Solicitud ultimoRegistro, int nroRegistro) {
		List<Solicitud> listaConele = new ArrayList<Solicitud>();
		List<Solicitud> listaIice = new ArrayList<Solicitud>();
		listaConele = solicitudDAO.consultarSolicitudes(parametro, ultimoRegistro, 10);
		listaIice = solicitudDAO.consultarSolicitudes(parametro, ultimoRegistro, 10);
		
		listaConele.addAll(listaIice);
		
		return listaConele;
	}

}
