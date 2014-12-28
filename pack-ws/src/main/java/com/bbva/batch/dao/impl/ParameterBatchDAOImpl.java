package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.ParameterBatchDAO;
import com.bbva.batch.domain.ParameterBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("parameterBatchDAO")
public class ParameterBatchDAOImpl extends HibernateDAO<ParameterBatch> implements ParameterBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<ParameterBatch> listar(Long idStepBatch, boolean lazy) {
        Criteria criteria = super.getCriteria(ParameterBatch.class);
        criteria.add(Restrictions.eq("step", idStepBatch));
        return (List<ParameterBatch>) criteria.list();
    }

}
