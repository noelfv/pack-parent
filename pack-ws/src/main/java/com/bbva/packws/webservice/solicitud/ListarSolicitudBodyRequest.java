//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.bbva.packws.webservice.solicitud;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListarSolicitudBodyRequest complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ListarSolicitudBodyRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoProducto" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="estadoSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListarSolicitudBodyRequest", propOrder = {
        "tipoDocumento",
        "nroDocumento",
        "codigoProducto",
        "estadoSolicitud"
})
public class ListarSolicitudBodyRequest {

    @XmlElement(required = true)
    private String tipoDocumento;
    @XmlElement(required = true)
    private String nroDocumento;
    private List<String> codigoProducto;
    private List<String> estadoSolicitud;

    /**
     * Gets the value of the tipoDocumento property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the nroDocumento property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNroDocumento() {
        return nroDocumento;
    }

    /**
     * Sets the value of the nroDocumento property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNroDocumento(String value) {
        this.nroDocumento = value;
    }

    /**
     * Gets the value of the codigoProducto property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codigoProducto property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodigoProducto().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getCodigoProducto() {
        if (codigoProducto == null) {
            codigoProducto = new ArrayList<String>();
        }
        return this.codigoProducto;
    }

    /**
     * Gets the value of the estadoSolicitud property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estadoSolicitud property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstadoSolicitud().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getEstadoSolicitud() {
        if (estadoSolicitud == null) {
            estadoSolicitud = new ArrayList<String>();
        }
        return this.estadoSolicitud;
    }

}
