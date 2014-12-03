package com.bbva.packws.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.packws.dao.ParametroDAO;
import com.bbva.packws.domain.Parametro;
import com.bbva.packws.service.ParametroService;

@Service("parametroService")
public class ParametroServiceImpl implements Serializable, ParametroService {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "parametroDAO")
	private ParametroDAO parametroDAO;

	@Override
	@Transactional(readOnly = true)
	public Parametro obtener(long idParametro) {
		return parametroDAO.obtener(idParametro);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Parametro> listarHijos(long idParametroPadre) {
		return parametroDAO.listarHijos(idParametroPadre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Parametro> listarHijosOrdPorEntero(long idParametroPadre) {
		return parametroDAO.listarHijosOrdPorEntero(idParametroPadre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Parametro> listarPadres() {
		return parametroDAO.listarPadres();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Parametro> listarPadresPermitenHijos() {
		return parametroDAO.listarPadresPermitenHijos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Parametro> listar(long idTipo, long idPadre, String nombre, String idEstado) {
		return parametroDAO.listar(idTipo, idPadre, nombre, idEstado);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean codigoExiste(String codigo, long idTipo, long idParametro, long idPadre) {
		return parametroDAO.codigoExiste(codigo, idTipo, idParametro, idPadre);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nombreExiste(String nombre, long idTipo, long idParametro, long idPadre) {
		return parametroDAO.nombreExiste(nombre, idTipo, idParametro, idPadre);
	}

	@Override
	@Transactional
	public void insertar(Parametro objParametro) {
		parametroDAO.insertar(objParametro);
	}

	@Override
	@Transactional
	public void modificar(Parametro objParametro) {
		parametroDAO.modificar(objParametro);
	}

	@Override
	public List<Parametro> listarParametrosPorTipo(Long idParametria, Long idTipo) {
		return parametroDAO.listarParametrosPorTipo(idParametria,idTipo);
	}

	@Override
	public Parametro obtenerParametroPorCodigo(Long idPadre, String codigo) {
		return parametroDAO.obtenerParametroPorCodigo(idPadre, codigo);
	}

}
