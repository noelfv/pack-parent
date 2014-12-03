package com.bbva.packws.webservice.endpoints.impl;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
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

	private Solicitud crearSolicitud(com.bbva.packws.webservice.solicitud.Solicitud sws) {
		Solicitud s = null;
		
		if(sws != null) {
			s = new SolicitudCONELE();
			s.setSolicitud(sws.getSolicitud());
			s.setCodigoProducto(sws.getCodigoProducto());
			s.setCodigoSubProducto(sws.getCodigoSubProducto());
			s.setEstado(sws.getEstado());
			if(sws.getFechaAlta() != null) {
				s.setFechaAlta(sws.getFechaAlta().toGregorianCalendar().getTime());
			}
			s.setImporte(sws.getImporte());
			s.setDivisa(sws.getDivisa());
			s.setTipoDOI(sws.getTipoDOI());
			s.setNumDOI(sws.getNumDOI());
			s.setCodigoCliente(sws.getCodigoCliente());
			s.setContrato(sws.getContrato());
			s.setPlazo(sws.getPlazo());
			s.setOficina(sws.getOficina());
			s.setEjecutivo(sws.getEjecutivo());
			s.setTasa(sws.getTasa());
		}
		
		return s;
	}
	
	private com.bbva.packws.webservice.solicitud.Solicitud crearSolicitudWS(Solicitud s) {
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
		ParametroConfiguracionService parametroConfiguracionService = (ParametroConfiguracionService) WebServletContextListener.getBean("parametroConfiguracionService");
		
		Solicitud ultimaPagina = crearSolicitud(parameters.getHeader().getUltimaPagina());
		ListarSolicitudBodyRequest request = parameters.getBody();
		ListarSolicitudBodyResponse response = new ListarSolicitudBodyResponse();
		ParametroConfiguracion param = parametroConfiguracionService.obtenerParametro(Configuracion.PB_NRO_REGISTRO.getKey());
		Integer nroRegistros = 0;
		
		try {
			if(param != null) {
				nroRegistros = Integer.parseInt(param.getNombre());
			} else {
				throw new BussinesWebServiceException("No se pudo obtener el nro. de registros por petición");
			}
		} catch(NumberFormatException e) {
			throw new BussinesWebServiceException("No se pudo obtener el nro. de registros por petición", e);
		}
		
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
				, ultimaPagina
				, nroRegistros);
		
		if(listaSolicitud.isEmpty()) {
			throw new BussinesWebServiceException(CodigoRetorno.SIN_RESULTADO, "No se encontraron registros con los parametros ingresados");
		}
		
		for(Solicitud s : listaSolicitud) {			
			response.getSolicitudes().add(crearSolicitudWS(s));
		}
		
		return response;
	}
	
    public ListarSolicitudResponse listarSolicitud(ListarSolicitudRequest parameters) {
    	ListarSolicitudResponse response = new ListarSolicitudResponse();
		ListarSolicitudBodyResponse body;
    	try {
    		body = crearBodyResponse(parameters);
    		response.setBody(body);
    		response.getHeader().setCodigoRetorno(CodigoRetorno.CON_RESULTADO.getCodigo());
    		response.getHeader().setUltimoRegistro(body.getSolicitudes().get(body.getSolicitudes().size() - 1));
    	} catch (BussinesWebServiceException e) {
    		response.getHeader().setCodigoRetorno(e.getCodigoRetorno().getCodigo());
    		response.getHeader().getErrores().add(e.getCodigoRetorno().getDescripcion());
    		response.getHeader().getErrores().add(e.getDetailMessage());
    	}
    	
		return response;
    }

}