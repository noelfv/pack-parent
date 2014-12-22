package com.bbva.batch.dao.impl;

import java.util.List;

import com.bbva.batch.dao.ApplicationBatchDAO;
import com.bbva.batch.domain.ApplicationBatch;
import com.everis.core.dao.impl.HibernateDAO;

public class ApplicationBatchDAOImpl extends HibernateDAO<ApplicationBatch> implements ApplicationBatchDAO {

	@Override
	public List<ApplicationBatch> listar() {
		return null;
	}

}
