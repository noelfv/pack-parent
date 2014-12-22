package com.bbva.batch.dao.impl;

import java.util.List;

import com.bbva.batch.dao.JobBatchDAO;
import com.bbva.batch.domain.JobBatch;
import com.everis.core.dao.impl.HibernateDAO;

public class JobBatchDAOImpl extends HibernateDAO<JobBatch> implements JobBatchDAO {

    @Override
    public List<JobBatch> listar(Long idApplicationBatch) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JobBatch obtener(Long idJobBatch) {
        // TODO Auto-generated method stub
        return null;
    }

}
