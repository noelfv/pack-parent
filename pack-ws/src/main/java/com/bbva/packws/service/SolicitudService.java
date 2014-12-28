package com.bbva.packws.service;

import java.util.List;

import com.bbva.packws.domain.Solicitud;

public interface SolicitudService {

    List<Solicitud> consultarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, Solicitud ultimoRegistro, int nroRegistro, boolean iiceActivo);

    int contarSolicitudes(String tipoDOI, String numDOI, String[] codigoProducto, String[] estado, boolean iiceActivo);

}
