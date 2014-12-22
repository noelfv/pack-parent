package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.JobBatch;

public interface JobBatchDAO {

    List<JobBatch> listar(Long idApplicationBatch);
    JobBatch obtener(Long idJobBatch);
}
