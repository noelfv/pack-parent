package com.bbva.packws.dao;

import java.util.List;

import com.bbva.packws.domain.Solicitud;

public interface SolicitudDAO {
	
	public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro);
	
	public List<Solicitud> consultarSolicitudesIice(String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro);

}
