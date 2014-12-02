package com.bbva.packws.dao;

import java.util.List;

import com.bbva.packws.domain.Solicitud;

public interface SolicitudDAO {
	
	public List<Solicitud> consultarSolicitudes(Solicitud parametro, Solicitud ultimoRegistro, int nroRegistro);
	
	public List<Solicitud> consultarSolicitudesIice(Solicitud parametro, Solicitud ultimoRegistro, int nroRegistro);

}
