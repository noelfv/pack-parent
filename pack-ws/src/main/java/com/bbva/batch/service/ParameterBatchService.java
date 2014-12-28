package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.ParameterBatch;

public interface ParameterBatchService {

    List<ParameterBatch> listar(Long idStepBatch);

    List<ParameterBatch> listar(Long idStepBatch, boolean lazy);

    void insertar(ParameterBatch o);

    void actualizar(ParameterBatch o);

    void actualizar(List<ParameterBatch> o);

    void eliminar(ParameterBatch o);
}
