package com.bbva.packws.dao;

import com.bbva.packws.domain.ParametroConfiguracion;

public interface ParametroConfiguracionDAO {

    ParametroConfiguracion obtenerParametro(String nombre);
}