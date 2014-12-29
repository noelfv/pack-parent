package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.StepBatch;
import com.everis.core.service.IDataManipulationService;

public interface StepBatchService extends IDataManipulationService<StepBatch> {

    List<StepBatch> listar(Long idJobBatch);

    List<StepBatch> listar(Long idJobBatch, boolean lazy);

}
