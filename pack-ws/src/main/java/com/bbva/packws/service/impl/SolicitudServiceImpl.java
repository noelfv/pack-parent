package com.bbva.packws.service.impl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bbva.packws.dao.SolicitudDAO;
import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.service.SolicitudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("solicitudService")
public class SolicitudServiceImpl implements SolicitudService {

    @Resource(name = "solicitudDAO")
    private SolicitudDAO solicitudDAO;

    @Transactional(readOnly = true)
    public List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo) {
        List<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
        List<Solicitud> listaSolicitudTemp = new ArrayList<Solicitud>();
        listaSolicitud = solicitudDAO.consultarSolicitudes(tipoDOI, numDOI, codigoProducto, estado, ultimoRegistro, nroRegistro, iiceActivo);
        Collections.sort(listaSolicitud);
        listaSolicitudTemp = listaSolicitud.subList(0, (listaSolicitud.size() < nroRegistro ? listaSolicitud.size() : nroRegistro));
        return listaSolicitudTemp;
    }

    @Transactional(readOnly = true)
    public int contarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, boolean iiceActivo) {
        return solicitudDAO.contarSolicitudes(tipoDOI, numDOI, codigoProducto, estado, iiceActivo);
    }
}
