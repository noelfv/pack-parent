package com.bbva.packws.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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
	public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo) {
		List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
		listaSolicitud = solicitudDAO.consultarSolicitudes(tipoDOI, numDOI, codigoProducto,estado, ultimoRegistro, nroRegistro, iiceActivo);
		
		Collections.sort(listaSolicitud);
		return listaSolicitud;
	}

}
