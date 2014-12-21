package com.bbva.packws.batch.job;

import com.everis.core.enums.Estado;

public class GenerarArchivoHandler {

	private Long id;
	private Estado estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
