package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.JobBatch;

public interface JobBatchService {

	List<JobBatch> listar(Long idApplicationBatch);
    List<JobBatch> listar(Long idApplicationBatch, boolean lazy);
    JobBatch obtener(Long idJobBatch);
    JobBatch obtener(Long idJobBatch, boolean lazy);
    void insertar(JobBatch o);
    void actualizar(JobBatch o);
    void eliminar(JobBatch o);
}
