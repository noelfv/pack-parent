package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.JobBatch;
import com.everis.core.dao.IHibernateDAO;

public interface JobBatchDAO extends IHibernateDAO<JobBatch> {

    List<JobBatch> listar(Long idApplicationBatch, String name, boolean lazy);
    JobBatch obtener(Long idApplicationBatch, String name, boolean lazy);
    JobBatch obtener(Long idJobBatch, boolean lazy);
}
