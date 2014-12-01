package com.bbva.packws.webservice.endpoints;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.bbva.packws.webservice.solicitud.ListarSolicitudRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudResponse;
import com.bbva.packws.webservice.solicitud.ObjectFactory;

@WebService(name = "pack-ws", targetNamespace = "http://www.bbva.com.pe/pack-ws/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SolicitudSOAP {


    /**
     * 
     * @param parameters
     * @return
     *     returns com.bbva.packws.webservice.solicitud.ListarSolicitudResponse
     */
    @WebMethod(action = "http://www.bbva.com.pe/pack-ws/listarSolicitud")
    @WebResult(name = "listarSolicitudResponse", targetNamespace = "http://www.bbva.com.pe/pack-ws/", partName = "parameters")
    public ListarSolicitudResponse listarSolicitud(@WebParam(name = "listarSolicitudRequest", targetNamespace = "http://www.bbva.com.pe/pack-ws/", partName = "parameters") ListarSolicitudRequest parameters);

}