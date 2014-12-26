package com.bbva.batch.dao;

import java.util.List;

import com.bbva.batch.domain.ApplicationBatch;
import com.everis.core.dao.IHibernateDAO;

public interface ApplicationBatchDAO extends IHibernateDAO<ApplicationBatch> {

    List<ApplicationBatch> listar();
}
