package com.bbva.batch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.ParameterBatchDAO;
import com.bbva.batch.domain.ParameterBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("parameterBatchDAO")
public class ParameterBatchDAOImpl extends HibernateDAO<ParameterBatch> implements ParameterBatchDAO {

    @Override
    public List<ParameterBatch> listar(Long idStepBatch) {
        return null;
    }

}
