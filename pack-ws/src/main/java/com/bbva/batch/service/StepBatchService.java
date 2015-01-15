package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.StepBatch;
import com.everis.core.service.IDataManipulationService;

public interface StepBatchService extends IDataManipulationService<StepBatch> {

    List<StepBatch> listar(Long idJobBatch, String name);
    List<StepBatch> listar(Long idJobBatch, String name, boolean lazy);
    List<StepBatch> listar(Long idJobBatch);
    List<StepBatch> listar(Long idJobBatch, boolean lazy);
    StepBatch obtener(Long idStepBatch);
    StepBatch obtener(Long idStepBatch, boolean lazy);
    StepBatch obtener(Long idJobBatch, String name);
    StepBatch obtener(Long idJobBatch, String name, boolean lazy); 
}
