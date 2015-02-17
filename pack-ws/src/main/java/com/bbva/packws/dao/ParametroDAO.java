package com.bbva.packws.dao;

import java.util.List;

import com.bbva.packws.domain.Parametro;
import com.bbva.packws.enums.Respuesta;
import com.everis.core.enums.Estado;

public interface ParametroDAO {

    List<Parametro> listarHijos(long idParametroPadre, Estado estado);

    List<Parametro> listarHijosOrdPorEntero(long idParametroPadre, Estado estado);

    List<Parametro> listarHijos(long idParametroPadre);

    List<Parametro> listarHijosOrdPorEntero(long idParametroPadre);

    List<Parametro> listarPadres(Estado estado, Respuesta permiteHijos);

    List<Parametro> listarPadres();

    List<Parametro> listarPadresPermitenHijos();

    Parametro obtener(long idParametro);

    List<Parametro> listar(long idTipo, long idPadre, String nombre, String idEstado);

    boolean codigoExiste(String codigo, long idTipo, long idParametro, long idPadre);

    boolean nombreExiste(String nombre, long idTipo, long idParametro, long idPadre);

    void insertar(Parametro objParametria);

    void modificar(Parametro objParametria);

    List<Parametro> listarParametrosPorTipo(Long idParametria, Long idTipo);

    Parametro obtenerParametroPorCodigo(Long idPadre, String codigo);
}