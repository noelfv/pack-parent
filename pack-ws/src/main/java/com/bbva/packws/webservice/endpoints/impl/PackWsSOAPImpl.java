package com.bbva.packws.webservice.endpoints.impl;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;
import com.bbva.packws.webservice.endpoints.PackWs;
import com.bbva.packws.webservice.enums.CodigoRetorno;
import com.bbva.packws.webservice.exception.BussinesWebServiceException;
import com.bbva.packws.webservice.solicitud.ListarSolicitudBodyRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudBodyResponse;
import com.bbva.packws.webservice.solicitud.ListarSolicitudRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudResponse;
import com.everis.web.listener.WebServletContextListener;



@javax.jws.WebService (endpointInterface="com.bbva.packws.webservice.endpoints.PackWs", targetNamespace="http://www.bbva.com.pe/pack-ws/", serviceName="pack-ws", portName="pack-wsSOAP")
public class PackWsSOAPImpl implements PackWs{

	private com.bbva.packws.webservice.solicitud.Solicitud crearSolicitud(Solicitud s) {
		GregorianCalendar calendar;
		com.bbva.packws.webservice.solicitud.Solicitud sws = new com.bbva.packws.webservice.solicitud.Solicitud();
		
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
		
		return sws;
	}
	
	private ListarSolicitudBodyResponse crearBodyResponse(ListarSolicitudRequest parameters) throws BussinesWebServiceException {
		SolicitudService solicitudService = (SolicitudService) WebServletContextListener.getBean("solicitudService"); 
		Solicitud ultimaPagina = null;
		ListarSolicitudBodyRequest request = parameters.getBody();
		ListarSolicitudBodyResponse response = new ListarSolicitudBodyResponse();
		
		if(request == null) {
			throw new BussinesWebServiceException("No se pudo obtener el valor del request");
		}
		
		if(request.getTipoDocumento().trim().length() == 0) {
			throw new BussinesWebServiceException("No ingreso el tipo de documento");
		}
		
		if(request.getNroDocumento().trim().length() == 0) {
			throw new BussinesWebServiceException("No ingreso el nro. de documento");
		}
		
		List<Solicitud> listaSolicitud = solicitudService.consultarSolicitudes(request.getTipoDocumento()
				, request.getNroDocumento()
				, request.getCodigoProducto().toArray(new String[]{})
				, request.getEstadoSolicitud().toArray(new String[]{})
				, ultimaPagina, 10);
		
		if(listaSolicitud.isEmpty()) {
			throw new BussinesWebServiceException(CodigoRetorno.SIN_RESULTADO, "No se encontraron registros con los parametros ingresados");
		}
		
		for(Solicitud s : listaSolicitud) {			
			response.getSolicitudes().add(crearSolicitud(s));
		}
		
		return response;
	}
	
    public ListarSolicitudResponse listarSolicitud(ListarSolicitudRequest parameters) {
    	ListarSolicitudResponse response = new ListarSolicitudResponse();
		
    	try {
    		response.getHeader().setCodigoRetorno(CodigoRetorno.CON_RESULTADO.getCodigo());
    		response.setBody(crearBodyResponse(parameters));
    	} catch (BussinesWebServiceException e) {
    		response.getHeader().setCodigoRetorno(e.getCodigoRetorno().getCodigo());
    		response.getHeader().getErrores().add(e.getCodigoRetorno().getDescripcion());
    		response.getHeader().getErrores().add(e.getDetailMessage());
    	}
    	
		return response;
    }

}