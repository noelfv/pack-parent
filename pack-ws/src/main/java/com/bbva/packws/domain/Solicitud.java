package com.bbva.packws.domain;

import java.util.Date;

public interface Solicitud extends Comparable<Solicitud> {

	String getSolicitud();
	void setSolicitud(String solicitud);

	String getCodigoProducto();
	void setCodigoProducto(String codigoProducto);

	String getCodigoSubProducto();
	void setCodigoSubProducto(String codigoSubProducto);

	String getEstado();
	void setEstado(String estado);

	Date getFechaAlta();
	void setFechaAlta(Date fechaAlta);

	Double getImporte();
	void setImporte(Double importe);

	String getDivisa();
	void setDivisa(String divisa);

	String getTipoDOI();
	void setTipoDOI(String tipoDOI);

	String getNumDOI();
	void setNumDOI(String numDOI);

	String getCodigoCliente();
	void setCodigoCliente(String codigoCliente);

	String getContrato();
	void setContrato(String contrato);

	Long getPlazo();
	void setPlazo(Long plazo);

	String getOficina();
	void setOficina(String oficina);

	String getOficinaCodigo();
	void setOficinaCodigo(String oficinaCodigo);

	String getEjecutivo();
	void setEjecutivo(String ejecutivo);

	String getEjecutivoCodigo();
	void setEjecutivoCodigo(String ejecutivoCodigo);

	Double getTasa();
	void setTasa(Double tasa);

	String getFechaAltaConFormato();
	
	String getEstadoPack();
	void setEstadoPack(String estadoPack);
	
	String getTipoDocumentoPack();
	void setTipoDocumentoPack(String tipoDocumentoPack);
	
	String getProductoPack();
	void setProductoPack(String productoPack);
	
	String getSubProductoPack();
	void setSubProductoPack(String subProductoPack);
}