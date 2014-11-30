package com.bbva.packws.domain;

import java.io.Serializable;
import java.util.Date;

public class Solicitud implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String solicitud;
	private String codigoProducto;
	private String codigoSubProducto;
	private String estado;
	private Date fechaAlta;
	private double importe;
	private String divisa;
	private String tipoDOI;
	private String numDOI;
	private String codigoCliente;
	private String contrato;
	private String plazo;
	private String oficina;
	private String oficinaCodigo;
	private String ejecutivo;
	private String ejecutivoCodigo;
	private String tasa;
	
	public String getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getCodigoSubProducto() {
		return codigoSubProducto;
	}
	public void setCodigoSubProducto(String codigoSubProducto) {
		this.codigoSubProducto = codigoSubProducto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getTipoDOI() {
		return tipoDOI;
	}
	public void setTipoDOI(String tipoDOI) {
		this.tipoDOI = tipoDOI;
	}
	public String getNumDOI() {
		return numDOI;
	}
	public void setNumDOI(String numDOI) {
		this.numDOI = numDOI;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getPlazo() {
		return plazo;
	}
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getOficinaCodigo() {
		return oficinaCodigo;
	}
	public void setOficinaCodigo(String oficinaCodigo) {
		this.oficinaCodigo = oficinaCodigo;
	}
	public String getEjecutivo() {
		return ejecutivo;
	}
	public void setEjecutivo(String ejecutivo) {
		this.ejecutivo = ejecutivo;
	}
	public String getEjecutivoCodigo() {
		return ejecutivoCodigo;
	}
	public void setEjecutivoCodigo(String ejecutivoCodigo) {
		this.ejecutivoCodigo = ejecutivoCodigo;
	}
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
}

