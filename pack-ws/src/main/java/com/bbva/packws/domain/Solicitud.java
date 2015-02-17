package com.bbva.packws.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.everis.enums.FormatoFecha;
import com.everis.util.FechaUtil;
import com.everis.util.NumeroUtil;

import org.apache.commons.lang3.builder.CompareToBuilder;

public abstract class Solicitud implements Serializable, Comparable<Solicitud> {

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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
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

    public Long getPlazo() {
        return plazo;
    }

    public void setPlazo(Long plazo) {
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

    public Double getTasa() {
        return tasa;
    }

    public void setTasa(Double tasa) {
        this.tasa = tasa;
    }

    public String getEstadoPack() {
        return estadoPack;
    }

    public void setEstadoPack(String estadoPack) {
        this.estadoPack = estadoPack;
    }

    public String getTipoDocumentoPack() {
        return tipoDocumentoPack;
    }

    public void setTipoDocumentoPack(String tipoDocumentoPack) {
        this.tipoDocumentoPack = tipoDocumentoPack;
    }

    public String getProductoPack() {
        return productoPack;
    }

    public void setProductoPack(String productoPack) {
        this.productoPack = productoPack;
    }

    public String getSubProductoPack() {
        return subProductoPack;
    }

    public void setSubProductoPack(String subProductoPack) {
        this.subProductoPack = subProductoPack;
    }

    public String getFechaAltaConFormato() {
        return FechaUtil.formatFecha(fechaAlta, FormatoFecha.YYYYMMDD);
    }

    public String getImporteConFormatoHOST() {
        BigDecimal dec = new BigDecimal(this.getImporte().doubleValue());
        return String.valueOf(dec.multiply(NumeroUtil.ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP).intValue());
    }

    public String getTasaConFormatoHOST() {
        BigDecimal dec = new BigDecimal(this.getTasa().doubleValue());
        return String.valueOf(dec.multiply(NumeroUtil.ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP).intValue());
    }

    @Override
    public int compareTo(Solicitud o) {
        Long thisSolicitud = 0L;
        Long oSolicitud = 0L;

        try {
            thisSolicitud = Long.parseLong(this.getSolicitud());
        } catch (Exception e) {
            thisSolicitud = 0L;
        }

        try {
            oSolicitud = Long.parseLong(o.getSolicitud());
        } catch (Exception e) {
            oSolicitud = 0L;
        }

        CompareToBuilder compareToBuilder = new CompareToBuilder();
        compareToBuilder.append(this.getFechaAlta(), o.getFechaAlta());
        compareToBuilder.append(thisSolicitud, oSolicitud);

        return compareToBuilder.toComparison();
    }
}