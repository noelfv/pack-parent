package com.bbva.packws.webservice.exception;

import com.bbva.packws.webservice.enums.CodigoRetorno;

public class BussinesWebServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private CodigoRetorno codigoRetorno;
	private String detailMessage;
	
	public BussinesWebServiceException(String detailMessage, Throwable ex) {
		super(ex);
		this.detailMessage = detailMessage;
	}

	public BussinesWebServiceException(String detailMessage) {
		this.detailMessage = detailMessage;
		this.codigoRetorno = CodigoRetorno.ERROR_ACCESO;
	}

	public BussinesWebServiceException(CodigoRetorno codigoRetorno, String detailMessage) {
		this.detailMessage = detailMessage;
		this.codigoRetorno = codigoRetorno;
	}
	
	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public CodigoRetorno getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(CodigoRetorno codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
}
