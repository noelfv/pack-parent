package com.bbva.packws.domain;

public class ParametroConfiguracion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private Long codigoAplicativo;
	private String nombre;
	private String valor;

	public ParametroConfiguracion() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodigoAplicativo() {
		return codigoAplicativo;
	}

	public void setCodigoAplicativo(Long codigoAplicativo) {
		this.codigoAplicativo = codigoAplicativo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
