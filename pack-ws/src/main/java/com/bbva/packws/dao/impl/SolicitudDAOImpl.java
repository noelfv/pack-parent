package com.bbva.packws.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("solicitudDAO")
public class SolicitudDAOImpl extends HibernateDAO<Solicitud> implements SolicitudDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro){
		Criteria criterioSolicitud = super.getCriteria(Solicitud.class);
		
		if(ultimoRegistro != null) {
			criterioSolicitud.add(Restrictions.gt("solicitud", ultimoRegistro.getSolicitud()) );
		}
		
		criterioSolicitud.add(Restrictions.eq("tipoDOI", tipoDOI));
		criterioSolicitud.add(Restrictions.eq("numDOI", numDOI));
		if(codigoProducto != null) {
			criterioSolicitud.add(Restrictions.in("codigoProducto", codigoProducto));
		}
		if(estado != null) {
			criterioSolicitud.add(Restrictions.in("estado", estado));
		}
		
		criterioSolicitud.setMaxResults(nroRegistro);
		
		return (List<Solicitud>) criterioSolicitud.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesIice(String tipoDOI, String numDOI,String[] codigoProducto,String[] estado, Solicitud ultimoRegistro, int nroRegistro){
	Criteria criterioSolicitud = super.getCriteria(Solicitud.class);
		
		if(ultimoRegistro != null) {
			criterioSolicitud.add(Restrictions.gt("solicitud", ultimoRegistro.getSolicitud()) );
		}
		
		criterioSolicitud.add(Restrictions.eq("tipoDOI", tipoDOI));
		criterioSolicitud.add(Restrictions.eq("numDOI", numDOI));
		if(codigoProducto != null) {
			criterioSolicitud.add(Restrictions.in("codigoProducto", codigoProducto));
		}
		if(estado != null) {
			criterioSolicitud.add(Restrictions.in("estado", estado));
		}
		
		criterioSolicitud.setMaxResults(nroRegistro);
		
		return (List<Solicitud>) criterioSolicitud.list();
	}

}
