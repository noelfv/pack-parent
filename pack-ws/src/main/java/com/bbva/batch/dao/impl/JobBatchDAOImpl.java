package com.bbva.batch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bbva.batch.dao.JobBatchDAO;
import com.bbva.batch.domain.JobBatch;
import com.everis.core.dao.impl.HibernateDAO;

@Repository("jobBatchDAO")
public class JobBatchDAOImpl extends HibernateDAO<JobBatch> implements JobBatchDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<JobBatch> listar(Long idApplicationBatch, String name, boolean lazy) {
        Criteria criteria = super.getCriteria(JobBatch.class);
        criteria.add(Restrictions.eq("application.id", idApplicationBatch));
        if(name != null && !name.trim().isEmpty()) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        criteria.addOrder(Order.asc("name"));
        return (List<JobBatch>) criteria.list();
    }

    @Override
    public JobBatch obtener(Long idApplicationBatch, String name, boolean lazy) {
        Criteria criteria = super.getCriteria(JobBatch.class);
        if(idApplicationBatch != null) {
            criteria.add(Restrictions.eq("application.id", idApplicationBatch));
        }
        criteria.add(Restrictions.eq("name", name));
        return (JobBatch) criteria.uniqueResult();
    }

    @Override
    public JobBatch obtener(Long idJobBatch, boolean lazy) {
        Criteria criteria = super.getCriteria(JobBatch.class);
        criteria.add(Restrictions.eq("id", idJobBatch));
        return (JobBatch) criteria.uniqueResult();
    }

}
