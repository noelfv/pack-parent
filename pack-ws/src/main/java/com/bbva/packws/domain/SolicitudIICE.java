package com.bbva.packws.domain;

import java.io.Serializable;
import java.util.Date;

import com.everis.enums.FormatoFecha;
import com.everis.util.FechaUtil;

public class SolicitudIICE implements Serializable, Solicitud {

	private static final long serialVersionUID = 1L;
	private String solicitud;
	private String codigoProducto;
	private String codigoSubProducto;
	private String estado;
	private Date fechaAlta;
	private Double importe;
	private String divisa;
	private String tipoDOI;
	private String numDOI;
	private String codigoCliente;
	private String contrato;
	private Long plazo;
	private String oficina;
	private String oficinaCodigo;
	private String ejecutivo;
	private String ejecutivoCodigo;
	private Double tasa;
	private String estadoPack;
	private String tipoDocumentoPack;
	private String productoPack;
	private String subProductoPack;

	@Override
	public String getSolicitud() {
		return solicitud;
	}

	@Override
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	@Override
	public String getCodigoProducto() {
		return codigoProducto;
	}

	@Override
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	@Override
	public String getCodigoSubProducto() {
		return codigoSubProducto;
	}

	@Override
	public void setCodigoSubProducto(String codigoSubProducto) {
		this.codigoSubProducto = codigoSubProducto;
	}

	@Override
	public String getEstado() {
		return estado;
	}

	@Override
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public Date getFechaAlta() {
		return fechaAlta;
	}

	@Override
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public Double getImporte() {
		return importe;
	}

	@Override
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	@Override
	public String getDivisa() {
		return divisa;
	}

	@Override
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	@Override
	public String getTipoDOI() {
		return tipoDOI;
	}

	@Override
	public void setTipoDOI(String tipoDOI) {
		this.tipoDOI = tipoDOI;
	}

	@Override
	public String getNumDOI() {
		return numDOI;
	}

	@Override
	public void setNumDOI(String numDOI) {
		this.numDOI = numDOI;
	}

	@Override
	public String getCodigoCliente() {
		return codigoCliente;
	}

	@Override
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	@Override
	public String getContrato() {
		return contrato;
	}

	@Override
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	@Override
	public Long getPlazo() {
		return plazo;
	}

	@Override
	public void setPlazo(Long plazo) {
		this.plazo = plazo;
	}

	@Override
	public String getOficina() {
		return oficina;
	}

	@Override
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	@Override
	public String getOficinaCodigo() {
		return oficinaCodigo;
	}

	@Override
	public void setOficinaCodigo(String oficinaCodigo) {
		this.oficinaCodigo = oficinaCodigo;
	}

	@Override
	public String getEjecutivo() {
		return ejecutivo;
	}

	@Override
	public void setEjecutivo(String ejecutivo) {
		this.ejecutivo = ejecutivo;
	}

	@Override
	public String getEjecutivoCodigo() {
		return ejecutivoCodigo;
	}

	@Override
	public void setEjecutivoCodigo(String ejecutivoCodigo) {
		this.ejecutivoCodigo = ejecutivoCodigo;
	}

	@Override
	public Double getTasa() {
		return tasa;
	}

	@Override
	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}
	
	@Override
	public String getFechaAltaConFormato() {
		return FechaUtil.formatFecha(fechaAlta, FormatoFecha.YYYYMMDD);
	}
	
	@Override
	public int compareTo(Solicitud o) {
		int i = 0;
		Long original = Long.parseLong(this.getSolicitud());
		Long parametro = Long.parseLong(o.getSolicitud());
		i= original.compareTo(parametro);
		if(i!=0){
		return i;}
		else{
			return this.getFechaAlta().compareTo(o.getFechaAlta());
		}
	}
	
	@Override
	public String getEstadoPack() {
		return estadoPack;
	}

	@Override
	public void setEstadoPack(String estadoPack) {
		this.estadoPack = estadoPack;
	}

	@Override
	public String getTipoDocumentoPack() {
		return tipoDocumentoPack;
	}

	@Override
	public void setTipoDocumentoPack(String tipoDocumentoPack) {
		this.tipoDocumentoPack = tipoDocumentoPack;
	}

	@Override
	public String getProductoPack() {
		return productoPack;
	}

	@Override
	public void setProductoPack(String productoPack) {
		this.productoPack = productoPack;
	}

	@Override
	public String getSubProductoPack() {
		return subProductoPack;
	}

	@Override
	public void setSubProductoPack(String subProductoPack) {
		this.subProductoPack = subProductoPack;
	}
	
}
