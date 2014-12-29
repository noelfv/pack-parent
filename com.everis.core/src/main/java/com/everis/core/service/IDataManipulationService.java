package com.everis.core.service;

import java.util.List;

public interface IDataManipulationService<T> {

    T insertar(T o);

    T actualizar(T o);

    List<T> actualizar(List<T> o);

    void eliminar(T o);

    void eliminar(List<T> o);
}
