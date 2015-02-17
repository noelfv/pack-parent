package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.JobBatch;
import com.everis.core.service.IDataManipulationService;

public interface JobBatchService extends IDataManipulationService<JobBatch> {

    List<JobBatch> listar(Long idApplicationBatch, String name);
    List<JobBatch> listar(Long idApplicationBatch, String name, boolean lazy);
    List<JobBatch> listar(Long idApplicationBatch);
    List<JobBatch> listar(Long idApplicationBatch, boolean lazy);
    JobBatch obtener(Long idJobBatch);
    JobBatch obtener(Long idJobBatch, boolean lazy);
    JobBatch obtener(Long idApplicationBatch, String name);
    JobBatch obtener(Long idApplicationBatch, String name, boolean lazy); 
}
