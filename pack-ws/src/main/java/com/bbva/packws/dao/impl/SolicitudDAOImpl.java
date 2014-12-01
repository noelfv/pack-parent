package com.bbva.packws.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.everis.core.dao.impl.HibernateDAO;

public class SolicitudDAOImpl extends HibernateDAO<Solicitud> implements SolicitudDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudes(Solicitud parametro, Solicitud ultimoRegistro, int nroRegistro) {
		Criteria criterioSolicitud = super.getCriteria(Solicitud.class);
		
		if(ultimoRegistro != null) {
			criterioSolicitud.add(Restrictions.gt("solicitud", ultimoRegistro.getSolicitud()) );
		}
		
		criterioSolicitud.add(Restrictions.eq("tipoDOI", parametro.getTipoDOI()));
		criterioSolicitud.add(Restrictions.eq("numDOI", parametro.getNumDOI()));
		criterioSolicitud.add(Restrictions.eq("codigoProducto", parametro.getCodigoProducto()));
		criterioSolicitud.add(Restrictions.eq("estado", parametro.getEstado()));
		
		criterioSolicitud.setMaxResults(nroRegistro);
		
		return (List<Solicitud>) criterioSolicitud.list();
	}

}
