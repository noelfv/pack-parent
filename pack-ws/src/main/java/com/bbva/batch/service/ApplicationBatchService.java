package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.ApplicationBatch;
import com.everis.core.service.IDataManipulationService;

public interface ApplicationBatchService extends IDataManipulationService<ApplicationBatch>{

    List<ApplicationBatch> listar();

    List<ApplicationBatch> listar(boolean lazy);

    ApplicationBatch obtener(String name);

    ApplicationBatch obtener(String name, boolean lazy);

    ApplicationBatch obtener(Long idApplication);

    ApplicationBatch obtener(Long idApplication, boolean lazy);
}
