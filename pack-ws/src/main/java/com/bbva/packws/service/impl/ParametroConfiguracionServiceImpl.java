package com.bbva.packws.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.packws.dao.ParametroConfiguracionDAO;
import com.bbva.packws.domain.ParametroConfiguracion;
import com.bbva.packws.service.ParametroConfiguracionService;

@Service("parametroConfiguracionService")
public class ParametroConfiguracionServiceImpl implements Serializable, ParametroConfiguracionService {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "parametroConfiguracionDAO")
	private ParametroConfiguracionDAO parametroConfiguracionDAO;

	@Override
	@Transactional(readOnly = true)
	public ParametroConfiguracion obtenerParametro(String nombre) {
		return parametroConfiguracionDAO.obtenerParametro(nombre);
	}

}
