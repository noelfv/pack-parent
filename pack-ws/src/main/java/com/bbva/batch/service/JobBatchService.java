package com.bbva.batch.service;

import java.util.List;

import com.bbva.batch.domain.JobBatch;

public interface JobBatchService {

    List<JobBatch> listar(Long idApplicationBatch);
    JobBatch obtener(Long idJobBatch);
    void insertar(JobBatch o);
    void actualizar(JobBatch o);
    void eliminar(JobBatch o);
}
