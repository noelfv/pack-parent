package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.StepBatch;
import com.everis.core.dao.IHibernateDAO;

public interface StepBatchDAO extends IHibernateDAO<StepBatch> {

    List<StepBatch> listar(Long idJobBatch, String name, boolean lazy);
    StepBatch obtener(Long idJobBatch, String name, boolean lazy);
    StepBatch obtener(Long idStepBatch, boolean lazy);
}
