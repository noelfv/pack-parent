package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.StepBatch;

public interface StepBatchService {

	List<StepBatch> listar(Long idJobBatch);
    List<StepBatch> listar(Long idJobBatch, boolean lazy);
    void insertar(StepBatch o);
    void actualizar(StepBatch o);
    void eliminar(StepBatch o);
}
