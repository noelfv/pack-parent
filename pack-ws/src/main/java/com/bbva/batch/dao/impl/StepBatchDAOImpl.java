package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.StepBatchDAO;
import com.bbva.batch.domain.StepBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("stepBatchDAO")
public class StepBatchDAOImpl extends HibernateDAO<StepBatch> implements StepBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<StepBatch> listar(Long idJobBatch, String name, boolean lazy) {
        Criteria criteria = super.getCriteria(StepBatch.class);
        criteria.add(Restrictions.eq("job", idJobBatch));
        if(name != null && !name.trim().isEmpty()) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        return (List<StepBatch>) criteria.list();
    }

    @Override
    public StepBatch obtener(Long idJobBatch, String name, boolean lazy) {
        Criteria criteria = super.getCriteria(StepBatch.class);
        criteria.add(Restrictions.eq("job.id", idJobBatch));
        criteria.add(Restrictions.eq("name", name));
        return (StepBatch) criteria.uniqueResult();
    }
    
    @Override
    public StepBatch obtener(Long idStepBatch, boolean lazy) {
        Criteria criteria = super.getCriteria(StepBatch.class);
        criteria.add(Restrictions.eq("id", idStepBatch));
        return (StepBatch) criteria.uniqueResult();
    }
}
