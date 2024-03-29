//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//

package com.bbva.packws.webservice.solicitud;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.bbva.packws.webservice package.
 * <p/>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListarSolicitudResponse_QNAME = new QName("http://www.bbva.com.pe/pack-ws/", "listarSolicitudResponse");
    private final static QName _ListarSolicitudRequest_QNAME = new QName("http://www.bbva.com.pe/pack-ws/", "listarSolicitudRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: com.bbva.packws.webservice
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListarSolicitudResponse }
     */
    public ListarSolicitudResponse createListarSolicitudResponse() {
        return new ListarSolicitudResponse();
    }

    /**
     * Create an instance of {@link ListarSolicitudRequest }
     */
    public ListarSolicitudRequest createListarSolicitudRequest() {
        return new ListarSolicitudRequest();
    }

    /**
     * Create an instance of {@link HeaderRequest }
     */
    public HeaderRequest createHeaderRequest() {
        return new HeaderRequest();
    }

    /**
     * Create an instance of {@link Solicitud }
     */
    public Solicitud createSolicitud() {
        return new Solicitud();
    }

    /**
     * Create an instance of {@link ListarSolicitudBodyResponse }
     */
    public ListarSolicitudBodyResponse createListarSolicitudBodyResponse() {
        return new ListarSolicitudBodyResponse();
    }

    /**
     * Create an instance of {@link ListarSolicitudBodyRequest }
     */
    public ListarSolicitudBodyRequest createListarSolicitudBodyRequest() {
        return new ListarSolicitudBodyRequest();
    }

    /**
     * Create an instance of {@link HeaderResponse }
     */
    public HeaderResponse createHeaderResponse() {
        return new HeaderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link ListarSolicitudResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bbva.com.pe/pack-ws/", name = "listarSolicitudResponse")
    public JAXBElement<ListarSolicitudResponse> createListarSolicitudResponse(ListarSolicitudResponse value) {
        return new JAXBElement<ListarSolicitudResponse>(_ListarSolicitudResponse_QNAME, ListarSolicitudResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}
     * {@link ListarSolicitudRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.bbva.com.pe/pack-ws/", name = "listarSolicitudRequest")
    public JAXBElement<ListarSolicitudRequest> createListarSolicitudRequest(ListarSolicitudRequest value) {
        return new JAXBElement<ListarSolicitudRequest>(_ListarSolicitudRequest_QNAME, ListarSolicitudRequest.class, null, value);
    }

}
