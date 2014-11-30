package com.bbva.packws.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.everis.core.dao.impl.HibernateDAO;

public class SolicitudDAOImpl extends HibernateDAO<Solicitud> implements SolicitudDAO {

	@Override
	public List<Solicitud> consultarSolicitudes(Solicitud parametro) {
		Criteria criterioSolicitud = super.getCriteria(Solicitud.class);
		criterioSolicitud.add(Restrictions.eq("tipoDOI", parametro.getTipoDOI()));
		criterioSolicitud.add(Restrictions.eq("numDOI", parametro.getNumDOI()));
		criterioSolicitud.add(Restrictions.eq("codigoProducto", parametro.getCodigoProducto()));
		criterioSolicitud.add(Restrictions.eq("estado", parametro.getEstado()));
		return (List<Solicitud>)criterioSolicitud.list();
	}

}
