package com.bbva.packws.service;

import java.util.List;

import com.bbva.packws.domain.Solicitud;

public interface SolicitudService {
	
	public List<Solicitud> consultarSolicitudes(Solicitud parametro);

}
