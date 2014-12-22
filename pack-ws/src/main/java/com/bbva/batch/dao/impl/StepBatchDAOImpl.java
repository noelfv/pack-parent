package com.bbva.batch.dao.impl;

import java.util.List;

import com.bbva.batch.dao.StepBatchDAO;
import com.bbva.batch.domain.StepBatch;
import com.everis.core.dao.impl.HibernateDAO;

public class StepBatchDAOImpl extends HibernateDAO<StepBatch> implements StepBatchDAO {

    @Override
    public List<StepBatch> listar(Long idJobBatch) {
        // TODO Auto-generated method stub
        return null;
    }

}
