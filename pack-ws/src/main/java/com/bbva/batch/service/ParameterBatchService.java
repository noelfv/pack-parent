package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.ParameterBatch;
import com.everis.core.service.IDataManipulationService;

public interface ParameterBatchService extends IDataManipulationService<ParameterBatch> {

    List<ParameterBatch> listar(Long idStepBatch);

    List<ParameterBatch> listar(Long idStepBatch, boolean lazy);

}
