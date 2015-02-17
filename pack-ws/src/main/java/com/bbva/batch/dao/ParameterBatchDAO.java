package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.ParameterBatch;
import com.everis.core.dao.IHibernateDAO;

public interface ParameterBatchDAO extends IHibernateDAO<ParameterBatch> {

    List<ParameterBatch> listar(Long idStepBatch, boolean lazy);
}
