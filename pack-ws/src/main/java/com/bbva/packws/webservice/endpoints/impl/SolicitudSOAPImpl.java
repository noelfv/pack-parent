package com.bbva.packws.webservice.endpoints.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.bbva.packws.webservice.solicitud.HeaderResponse;
import com.bbva.packws.webservice.solicitud.ListarSolicitudRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudResponse;

@Component("solicitudSOAPImpl")
@WebService(serviceName = "pack-ws", portName = "pack-wsSOAP", endpointInterface = "com.bbva.packws.webservice.endpoints.SolicitudSOAP", targetNamespace = "http://www.bbva.com.pe/pack-ws/", wsdlLocation = "WEB-INF/wsdl/pack-ws.wsdl")
public class SolicitudSOAPImpl {

    public ListarSolicitudResponse listarSolicitud(ListarSolicitudRequest parameters) {
		ListarSolicitudResponse response = new ListarSolicitudResponse();
		response.setHeader(new HeaderResponse());
		response.getHeader().setCodigoRetorno("01");
		
		return response;
    }
}