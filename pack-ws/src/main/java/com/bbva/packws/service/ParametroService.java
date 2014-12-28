package com.bbva.packws.service;

import java.util.List;

import com.bbva.packws.domain.Parametro;

public interface ParametroService {

    Parametro obtener(long idParametro);

    List<Parametro> listarHijos(long idParametroPadre);

    List<Parametro> listarHijosOrdPorEntero(long idParametroPadre);

    List<Parametro> listarPadres();

    List<Parametro> listarPadresPermitenHijos();

    List<Parametro> listar(long idTipo, long idPadre, String nombre, String idEstado);

    boolean codigoExiste(String codigo, long idTipo, long idParametro, long idPadre);

    boolean nombreExiste(String nombre, long idTipo, long idParametro, long idPadre);

    void insertar(Parametro objParametro);

    void modificar(Parametro objParametro);

    List<Parametro> listarParametrosPorTipo(Long idParametria, Long idTipo);

    Parametro obtenerParametroPorCodigo(Long idPadre, String codigo);
}