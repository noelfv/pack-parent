package com.bbva.packws.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;
import com.bbva.packws.domain.SolicitudIICE;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("solicitudDAO")
public class SolicitudDAOImpl extends HibernateDAO<Solicitud> implements SolicitudDAO {

    private Criteria builderCriteria(Class<?> clazz, String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro) {
        Criteria criterioSolicitud = super.getCriteria(clazz);

        if (ultimoRegistro != null && ultimoRegistro.getSolicitud() != null) {
            String idsSolicitudTemp[] = ultimoRegistro.getSolicitud().split("-");
            String idSolicitud = "";

            if (clazz.getSimpleName().equalsIgnoreCase("SolicitudCONELE")) {
                idSolicitud = idsSolicitudTemp[0].split(":")[1];
            } else {
                idSolicitud = idsSolicitudTemp[1].split(":")[1];
            }

            criterioSolicitud.add(Restrictions.gt("solicitud", idSolicitud));
        }

        criterioSolicitud.add(Restrictions.eq("tipoDocumentoPack", tipoDOI));
        criterioSolicitud.add(Restrictions.eq("numDOI", numDOI));

        if (codigoProducto != null && codigoProducto.length > 0 && (!(codigoProducto.length == 1 && codigoProducto[0].trim().length() == 0))) {
            criterioSolicitud.add(Restrictions.in("productoPack", codigoProducto));
        }

        if (estado != null && estado.length > 0 && (!(estado.length == 1 && estado[0].trim().length() == 0))) {
            criterioSolicitud.add(Restrictions.in("estadoPack", estado));
        }

        return criterioSolicitud;
    }

    private int contarSolicitudes(Class<?> clazz, String tipoDOI, String numDOI, String[] codigoProducto, String[] estado) {
        Criteria criterioSolicitud = builderCriteria(clazz, tipoDOI, numDOI, codigoProducto, estado, null, 0);
        criterioSolicitud.setProjection(Projections.rowCount());

        return ((Number) criterioSolicitud.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private List<Solicitud> ejecutarConsultaSolicitudes(Class<?> clazz, String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro) {
        Criteria criterioSolicitud = builderCriteria(clazz, tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro);

        criterioSolicitud.setFirstResult(0);
        criterioSolicitud.setMaxResults(nroRegistro);

        return criterioSolicitud.list();
    }

    @Override
    public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo) {
        List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();

        listaSolicitud.addAll(ejecutarConsultaSolicitudes(SolicitudCONELE.class, tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro));
        if (!iiceActivo) {
            listaSolicitud.addAll(ejecutarConsultaSolicitudes(SolicitudIICE.class, tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro));
        }

        return listaSolicitud;
    }

    @Override
    public int contarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, boolean iiceActivo) {
        int totalRegistros = 0;

        totalRegistros = contarSolicitudes(SolicitudCONELE.class, tipoDOI, numDOI, codigoProducto, estado);
        if (!iiceActivo) {
            totalRegistros += contarSolicitudes(SolicitudIICE.class, tipoDOI, numDOI, codigoProducto, estado);
        }

        return totalRegistros;
    }
}
