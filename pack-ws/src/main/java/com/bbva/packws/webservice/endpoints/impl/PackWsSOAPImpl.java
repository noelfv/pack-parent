package com.bbva.packws.webservice.endpoints.impl;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;
import com.bbva.packws.enums.Configuracion;
import com.bbva.packws.service.ParametroConfiguracionService;
import com.bbva.packws.service.SolicitudService;
import com.bbva.packws.util.Constantes;
import com.bbva.packws.webservice.endpoints.PackWs;
import com.bbva.packws.webservice.enums.CodigoRetorno;
import com.bbva.packws.webservice.exception.BussinesWebServiceException;
import com.bbva.packws.webservice.solicitud.ListarSolicitudBodyRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudBodyResponse;
import com.bbva.packws.webservice.solicitud.ListarSolicitudRequest;
import com.bbva.packws.webservice.solicitud.ListarSolicitudResponse;
import com.everis.web.listener.WebServletContextListener;
import org.apache.log4j.Logger;

@javax.jws.WebService (endpointInterface="com.bbva.packws.webservice.endpoints.PackWs", targetNamespace="http://www.bbva.com.pe/pack-ws/", serviceName="pack-ws", portName="pack-wsSOAP")
public class PackWsSOAPImpl implements PackWs{

	private static final Logger LOG = Logger.getLogger(PackWsSOAPImpl.class);
	
	private Solicitud crearSolicitud(com.bbva.packws.webservice.solicitud.Solicitud sws) {
		Solicitud s = null;
		
		if(sws != null) {
			s = new SolicitudCONELE();
			s.setSolicitud(sws.getSolicitud());
			s.setProductoPack(sws.getCodigoProducto());
			s.setSubProductoPack(sws.getCodigoSubProducto());
			s.setEstadoPack(sws.getEstado());
			if(sws.getFechaAlta() != null) {
				s.setFechaAlta(sws.getFechaAlta().toGregorianCalendar().getTime());
			}
			s.setImporte(sws.getImporte());
			s.setDivisa(sws.getDivisa());
			s.setTipoDocumentoPack(sws.getTipoDOI());
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
		sws.setCodigoProducto(s.getProductoPack());
		sws.setCodigoSubProducto(s.getSubProductoPack());
		sws.setEstado(s.getEstadoPack());
		try {
			if(s.getFechaAlta() != null) {
				calendar = new GregorianCalendar();
				calendar.setTime(s.getFechaAlta());
				sws.setFechaAlta(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			}
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException", e);
		}
		sws.setImporte(s.getImporte());
		sws.setDivisa(s.getDivisa());
		sws.setTipoDOI(s.getTipoDocumentoPack());
		sws.setNumDOI(s.getNumDOI());
		sws.setCodigoCliente(s.getCodigoCliente());
		sws.setContrato(s.getContrato());
		sws.setPlazo(s.getPlazo());
		sws.setOficina(s.getOficina());
		sws.setEjecutivo(s.getEjecutivo());
		sws.setTasa(s.getTasa());
		
		return sws;
	}
	
	private ListarSolicitudBodyResponse crearBodyResponse(ListarSolicitudRequest parameters, com.bbva.packws.webservice.solicitud.Solicitud solicitud) throws BussinesWebServiceException {
		SolicitudService solicitudService = WebServletContextListener.getBean("solicitudService");
		ParametroConfiguracionService parametroConfiguracionService = WebServletContextListener.getBean("parametroConfiguracionService");
		
		Solicitud ultimaPagina = crearSolicitud(parameters.getHeader().getUltimaPagina());
		ListarSolicitudBodyRequest request = parameters.getBody();
		ListarSolicitudBodyResponse response = new ListarSolicitudBodyResponse();
		ParametroConfiguracion param[] = new ParametroConfiguracion[2];  
		param[0] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_NRO_REGISTRO.getKey());
		param[1] = parametroConfiguracionService.obtenerParametro(Configuracion.PB_APAGAR_APLICACION_PLD.getKey());
		Integer nroRegistros = 0;
		
		try {
			if(param[0] != null) {
				nroRegistros = Integer.parseInt(param[0].getValor());
			} else {
				throw new BussinesWebServiceException("No se pudo obtener el nro. de registros por peticion");
			}
		} catch(NumberFormatException e) {
			throw new BussinesWebServiceException("No se pudo obtener el nro. de registros por peticion", e);
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

        Integer totalRegistros = solicitudService.contarSolicitudes(request.getTipoDocumento()
                , request.getNroDocumento()
                , request.getCodigoProducto().toArray(new String[]{})
                , request.getEstadoSolicitud().toArray(new String[]{})
                , Constantes.HABILITAR_IICE.equalsIgnoreCase(param[1].getValor()));
		List<Solicitud> listaSolicitud = solicitudService.consultarSolicitudes(request.getTipoDocumento()
				, request.getNroDocumento()
				, request.getCodigoProducto().toArray(new String[]{})
				, request.getEstadoSolicitud().toArray(new String[]{})
				, ultimaPagina
				, nroRegistros
				, Constantes.HABILITAR_IICE.equalsIgnoreCase(param[1].getValor()));

		if(listaSolicitud.isEmpty()) {
			throw new BussinesWebServiceException(CodigoRetorno.SIN_RESULTADO, "No se encontraron registros con los parametros ingresados");
		}

        Map<String, Solicitud> mapSolicitud = new HashMap<String, Solicitud>();
        mapSolicitud.put("SolicitudCONELE", null);
        mapSolicitud.put("SolicitudIICE", null);

        LOG.info("Total de Registro: " + totalRegistros);
		for(Solicitud s : listaSolicitud) {
            mapSolicitud.put(s.getClass().getSimpleName(), s);
			response.getSolicitudes().add(crearSolicitudWS(s));

            LOG.info("Fecha: " + s.getFechaAltaConFormato() + ", Solicitud: " + s.getSolicitud() + ", ClassName: " + s.getClass().getSimpleName());
		}

        if(response.getSolicitudes().size() < nroRegistros || totalRegistros == nroRegistros) {
            solicitud = null;
        } else {
            com.bbva.packws.webservice.solicitud.Solicitud solicitudTemp = response.getSolicitudes().get(response.getSolicitudes().size() - 1);
            String idsSolicitudTemp[] = ultimaPagina != null ? ultimaPagina.getSolicitud().split("-") : new String[]{"CONELE:0", "IICE:0"};
            String idSolicitudCONELE = "";
            String idSolicitudIICE = "";
            if(mapSolicitud.get("SolicitudCONELE") != null) {
                idSolicitudCONELE = "CONELE:" + mapSolicitud.get("SolicitudCONELE").getSolicitud();
            } else {
                idSolicitudCONELE = idsSolicitudTemp[0];
            }
            if(mapSolicitud.get("SolicitudIICE") != null) {
                idSolicitudIICE = "IICE:" + mapSolicitud.get("SolicitudIICE").getSolicitud();
            } else {
                idSolicitudIICE = idsSolicitudTemp[1];
            }
            solicitud.setSolicitud(idSolicitudCONELE + "-" + idSolicitudIICE);
            solicitud.setFechaAlta(solicitudTemp.getFechaAlta());
            solicitud.setTipoDOI(solicitudTemp.getTipoDOI());
            solicitud.setNumDOI(solicitudTemp.getNumDOI());
        }

		return response;
	}
	
    public ListarSolicitudResponse listarSolicitud(ListarSolicitudRequest parameters) {
    	ListarSolicitudResponse response = new ListarSolicitudResponse();
		ListarSolicitudBodyResponse body;
        com.bbva.packws.webservice.solicitud.Solicitud solicitud = new com.bbva.packws.webservice.solicitud.Solicitud();
    	try {
    		body = crearBodyResponse(parameters, solicitud);
    		response.setBody(body);
    		response.getHeader().setCodigoRetorno(CodigoRetorno.CON_RESULTADO.getCodigo());
    		response.getHeader().setUltimoRegistro(solicitud);
    	} catch (BussinesWebServiceException e) {
    		if(e.getCodigoRetorno() == null) {
    			e.setCodigoRetorno(CodigoRetorno.ERROR_ACCESO);
    			e.setDetailMessage("No especificado");
    		}
    		
    		response.getHeader().setCodigoRetorno(e.getCodigoRetorno().getCodigo());
    		response.getHeader().getErrores().add(e.getCodigoRetorno().getDescripcion());
    		response.getHeader().getErrores().add(e.getDetailMessage());
    	}
    	
		return response;
    }

}