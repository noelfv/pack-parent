package com.bbva.packws.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;
import com.bbva.packws.domain.SolicitudIICE;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("solicitudDAO")
public class SolicitudDAOImpl extends HibernateDAO<Solicitud> implements SolicitudDAO {

	@SuppressWarnings("unchecked")
	private List<Solicitud> ejecutarConsultaSolicitudes(Class<?> clazz, String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro) {
		Criteria criterioSolicitud = super.getCriteria(clazz);
		
		if(ultimoRegistro != null) {
			criterioSolicitud.add(Restrictions.gt("solicitud", ultimoRegistro.getSolicitud()) );
		}
		
		criterioSolicitud.add(Restrictions.eq("tipoDocumentoPack", tipoDOI));
		criterioSolicitud.add(Restrictions.eq("numDOI", numDOI));
		
		if(codigoProducto != null && codigoProducto.length > 0) {
			criterioSolicitud.add(Restrictions.in("productoPack", codigoProducto));
		}
		
		if(estado != null && estado.length > 0) {
			criterioSolicitud.add(Restrictions.in("estadoPack", estado));
		}
		
		criterioSolicitud.setFirstResult(0);
		criterioSolicitud.setMaxResults(nroRegistro);
		
		return criterioSolicitud.list();
	}
	
	@Override
	public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo){
		List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
		
		listaSolicitud.addAll(ejecutarConsultaSolicitudes(SolicitudCONELE.class, tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro));
		
		if(iiceActivo) {
			listaSolicitud.addAll(ejecutarConsultaSolicitudes(SolicitudIICE.class, tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro));
		}
		
		return listaSolicitud;
	}

}
