//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.bbva.packws.webservice.solicitud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderRequest complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="HeaderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoRegistro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ultimaPagina" type="{http://www.bbva.com.pe/pack-ws/}Solicitud" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderRequest", propOrder = {
        "codigoRegistro",
        "ultimaPagina"
})
public class HeaderRequest {

    @XmlElement(required = true)
    private String codigoRegistro;
    private Solicitud ultimaPagina;

    /**
     * Gets the value of the codigoRegistro property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    /**
     * Sets the value of the codigoRegistro property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCodigoRegistro(String value) {
        this.codigoRegistro = value;
    }

    /**
     * Gets the value of the ultimaPagina property.
     *
     * @return possible object is
     * {@link Solicitud }
     */
    public Solicitud getUltimaPagina() {
        return ultimaPagina;
    }

    /**
     * Sets the value of the ultimaPagina property.
     *
     * @param value allowed object is
     *              {@link Solicitud }
     */
    public void setUltimaPagina(Solicitud value) {
        this.ultimaPagina = value;
    }

}
