//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.01 at 01:46:48 AM COT 
//


package com.bbva.packws.webservice.solicitud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Solicitud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Solicitud">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="solicitud" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoProducto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoSubProducto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="divisa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoDOI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numDOI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoCliente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contrato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="plazo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ejecutivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tasa" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Solicitud", propOrder = {
    "solicitud",
    "codigoProducto",
    "codigoSubProducto",
    "estado",
    "fechaAlta",
    "importe",
    "divisa",
    "tipoDOI",
    "numDOI",
    "codigoCliente",
    "contrato",
    "plazo",
    "oficina",
    "ejecutivo",
    "tasa"
})
public class Solicitud {

    @XmlElement(required = true)
    protected String solicitud;
    @XmlElement(required = true)
    protected String codigoProducto;
    @XmlElement(required = true)
    protected String codigoSubProducto;
    @XmlElement(required = true)
    protected String estado;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaAlta;
    protected double importe;
    @XmlElement(required = true)
    protected String divisa;
    @XmlElement(required = true)
    protected String tipoDOI;
    @XmlElement(required = true)
    protected String numDOI;
    @XmlElement(required = true)
    protected String codigoCliente;
    @XmlElement(required = true)
    protected String contrato;
    @XmlElement(required = true)
    protected String plazo;
    @XmlElement(required = true)
    protected String oficina;
    @XmlElement(required = true)
    protected String ejecutivo;
    protected double tasa;

    /**
     * Gets the value of the solicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSolicitud() {
        return solicitud;
    }

    /**
     * Sets the value of the solicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSolicitud(String value) {
        this.solicitud = value;
    }

    /**
     * Gets the value of the codigoProducto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Sets the value of the codigoProducto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoProducto(String value) {
        this.codigoProducto = value;
    }

    /**
     * Gets the value of the codigoSubProducto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSubProducto() {
        return codigoSubProducto;
    }

    /**
     * Sets the value of the codigoSubProducto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSubProducto(String value) {
        this.codigoSubProducto = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fechaAlta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Sets the value of the fechaAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAlta(XMLGregorianCalendar value) {
        this.fechaAlta = value;
    }

    /**
     * Gets the value of the importe property.
     * 
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     * 
     */
    public void setImporte(double value) {
        this.importe = value;
    }

    /**
     * Gets the value of the divisa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * Sets the value of the divisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivisa(String value) {
        this.divisa = value;
    }

    /**
     * Gets the value of the tipoDOI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDOI() {
        return tipoDOI;
    }

    /**
     * Sets the value of the tipoDOI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDOI(String value) {
        this.tipoDOI = value;
    }

    /**
     * Gets the value of the numDOI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDOI() {
        return numDOI;
    }

    /**
     * Sets the value of the numDOI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDOI(String value) {
        this.numDOI = value;
    }

    /**
     * Gets the value of the codigoCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * Sets the value of the codigoCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCliente(String value) {
        this.codigoCliente = value;
    }

    /**
     * Gets the value of the contrato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * Sets the value of the contrato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrato(String value) {
        this.contrato = value;
    }

    /**
     * Gets the value of the plazo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlazo() {
        return plazo;
    }

    /**
     * Sets the value of the plazo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlazo(String value) {
        this.plazo = value;
    }

    /**
     * Gets the value of the oficina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * Sets the value of the oficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficina(String value) {
        this.oficina = value;
    }

    /**
     * Gets the value of the ejecutivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEjecutivo() {
        return ejecutivo;
    }

    /**
     * Sets the value of the ejecutivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEjecutivo(String value) {
        this.ejecutivo = value;
    }

    /**
     * Gets the value of the tasa property.
     * 
     */
    public double getTasa() {
        return tasa;
    }

    /**
     * Sets the value of the tasa property.
     * 
     */
    public void setTasa(double value) {
        this.tasa = value;
    }

}
