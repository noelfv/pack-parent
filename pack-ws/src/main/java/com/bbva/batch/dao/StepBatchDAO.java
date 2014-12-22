package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.StepBatch;

public interface StepBatchDAO {

    List<StepBatch> listar(Long idJobBatch);
}
