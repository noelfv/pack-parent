package com.bbva.packws.webservice.endpoints.impl;

import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.stereotype.Component;

import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;
import com.bbva.packws.webservice.solicitud.ListarSolicitudRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudResponse;

@Component("solicitudSOAPImpl")
@WebService(serviceName = "pack-ws", portName = "pack-wsSOAP", endpointInterface = "com.bbva.packws.webservice.endpoints.SolicitudSOAP", targetNamespace = "http://www.bbva.com.pe/pack-ws/", wsdlLocation = "WEB-INF/wsdl/pack-ws.wsdl")
public class SolicitudSOAPImpl {

	@Resource(name = "solicitudService")
	private SolicitudService solicitudService;
	
    public ListarSolicitudResponse listarSolicitud(ListarSolicitudRequest parameters) {
		Solicitud solicitud = new Solicitud();
		solicitud.setTipoDOI(parameters.getBody().getTipoDocumento());
		solicitud.setNumDOI(parameters.getBody().getNroDocumento());
		
		Solicitud ultimaPagina = null;
		
		ListarSolicitudResponse response = new ListarSolicitudResponse();
		List<Solicitud> listaSolicitud = solicitudService.consultarSolicitudes(solicitud, ultimaPagina, 10);
		com.bbva.packws.webservice.solicitud.Solicitud sws;
		GregorianCalendar calendar;
		
		for(Solicitud s : listaSolicitud) {
			sws = new com.bbva.packws.webservice.solicitud.Solicitud();
			sws.setSolicitud(s.getSolicitud());
			sws.setCodigoProducto(s.getCodigoProducto());
			sws.setCodigoSubProducto(s.getCodigoSubProducto());
			sws.setEstado(s.getEstado());
			try {
				if(s.getFechaAlta() != null) {
					calendar = new GregorianCalendar();
					calendar.setTime(s.getFechaAlta());
					sws.setFechaAlta(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				}
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			sws.setImporte(s.getImporte());
			sws.setDivisa(s.getDivisa());
			sws.setTipoDOI(s.getTipoDOI());
			sws.setNumDOI(s.getNumDOI());
			sws.setCodigoCliente(s.getCodigoCliente());
			sws.setContrato(s.getContrato());
			sws.setPlazo(s.getPlazo());
			sws.setOficina(s.getOficina());
			sws.setEjecutivo(s.getEjecutivo());
			sws.setTasa(s.getTasa());
			
			response.getBody().getSolicitudes().add(sws);
		}
		
		
		response.getHeader().setCodigoRetorno("01");
		
		return response;
    }
}