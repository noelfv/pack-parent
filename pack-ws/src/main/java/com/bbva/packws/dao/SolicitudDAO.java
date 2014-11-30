package com.bbva.packws.dao;

import java.util.List;

import com.bbva.packws.domain.Solicitud;

public interface SolicitudDAO {
	
	public List<Solicitud> consultarSolicitudes(Solicitud parametro);

}
