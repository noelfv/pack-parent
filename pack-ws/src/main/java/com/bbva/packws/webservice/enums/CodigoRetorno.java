package com.bbva.packws.webservice.enums;

public enum CodigoRetorno {

	SIN_RESULTADO("00", "CLIENTE SIN SOLICITUDES"),
	CON_RESULTADO("10", "CLIENTE CON SOLICITUDES"),
	ERROR_ACCESO("99", "ERROR EN ACCESO");
	
	private String codigo;
	private String descripcion;
	
	CodigoRetorno(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
